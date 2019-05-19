package com.androidcrawler.androidcrawler;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Result;

public class Tools {
    //判断编码格式方法
    public  String getFilecharset(String SourceFilePath) {
        File SourceFile=new File(SourceFilePath);

        String charset = "GBK";
        if(SourceFile.isFile()){
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(SourceFile));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }}
        return charset;
    }


    //保存文件到sd卡
    public void SaveFile(String content,String PathAndSaveName) {
        File file = new File(PathAndSaveName);
       /* if(SaveMethod==2&&file.exists()){
            SaveFile(content,SaveName+SaveNameAdd.getText().toString(),SaveMethod);
        }else {*/
       File de=new File(file.getParent());
       if(!de.isDirectory()&&!de.exists()){
           de.mkdirs();
       }
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter Writer = null;
        try {
            /*
            输出流的构造参数1：可以是File对象  也可以是文件路径
            输出流的构造参数2：默认为false=>覆盖内容； true=>追加内容
             */
            //out.newLine();
            Writer = new FileWriter(file, true);
            Writer.write(content);
            Writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Writer != null) {
                    Writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //打开指定文件，读取其数据，返回字符串对象
    public String ReadFileToString(String path) {
        // 定义返回结果
        String Result = "";
        BufferedReader In = null;
        try {
            In = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), getFilecharset(path)));// 读取文件
            String thisLine = null;
            //如果读到了，继续拼接
            while ((thisLine = In.readLine()) != null) {

                Result += thisLine+"\n";
            }
            In.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (In != null) {
                try {
                    In.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result;
    }
    //重命名
    public int RenameAllFile(String TXTPath,String RegRule,String AfterReplacce, int IfContainDirectory) {
        //获取文件夹下的所有文件(正确理解Java的文件:文件和文件夹)
        String NewName;
        int Result = -1;
        //TXTPath=TXTPath.replaceAll("([^\\s\\S]|([\\t\\s\\n])+)","");
        Log.d("这是传递过来的TXTPath路径:", TXTPath);
        File f = new File(TXTPath);
        File[] files = f.listFiles();
        if (RegRule.equals("")) {
            RegRule = "[^\\s\\S]";
        }
        if(f.isFile()){
            files=new File[1];
            files[0]=f;
        }else{
            files = f.listFiles();
        }
            for (File file : files) {
                //如果是目录则返回遍历函数
                // if (file.exists() && file.isDirectory()) {
                if (file.exists() && file.isDirectory()) {
                    if (IfContainDirectory == 1) {
                        //如果选择了方法1，则遍历所有子目录下的.txt文件
                        RenameAllFile(file.getName(), RegRule, AfterReplacce, IfContainDirectory);
                    }
                    //是文件,并且后缀.txt
                    //} else if((file.getName()).endsWith(".txt")){
                } else {
                    Log.d("测试重命名前:", file.getName());
                    NewName = file.getName().replaceAll(RegRule, AfterReplacce);
                    Log.d("重命名测试:", NewName);
                    if (file.renameTo(new File(f.getPath() + "/" + NewName))) {
                        Log.d("重命名成功,重命名后:", file.getName());
                        Result = 0;
                    } else {
                        Log.d("重命名失败,重命名后:", file.getName());
                    }
                }
            }
            return Result;
    }

    //批量重编码原写法,会出现内存泄漏
    /*
    public void ReEncode(String path,String CodingType,int SaveMethod,String RenameAdd){
        File f=new File(path);
        Log.d("引用文件路径:",path);
        String TextContent="";
        File[] files = f.listFiles();
        if(f.isDirectory()){
            for (File file:files) {
                //如果是目录则返回遍历函数
                if (file.exists()&&file.isDirectory()) {
                    //如果选择了方法1，则遍历所有子目录下的.txt文件
                    ReEncode(file.getPath(),CodingType,SaveMethod,RenameAdd);
                    //是文件,并且后缀.txt
                    //} else if((file.getName()).endsWith(".txt")){
                } else if (!getFilecharset(file.getPath()).equals("UTF-8")&&file.getPath().toLowerCase().endsWith(".txt")){
                    Log.d("文件路径:", file.getPath());
                    Log.d("文件原编码:", getFilecharset(file.getPath()));
                    if(SaveMethod==0){
                        TextContent=ReadFileToString(file.getPath());
                        file.delete();
                        SaveFile( TextContent,file.getPath());
                    }
                    else if(SaveMethod==1){
                        SaveFile( ReadFileToString(file.getPath()),file.getPath()+RenameAdd);
                    }
                    Log.d("文件路径:", file.getPath());
                    Log.d("文件重编码后编码:",getFilecharset(file.getPath()));
                    }
            }
        }
        else{
            if (!getFilecharset(f.getPath()).equals("UTF-8")&&f.getPath().toLowerCase().endsWith(".txt"))
            {
            if(SaveMethod==0){
                TextContent=ReadFileToString(f.getPath());
                f.delete();
                SaveFile(TextContent ,f.getPath());
            }else if(SaveMethod==1){
                SaveFile( ReadFileToString(f.getPath()),f.getPath()+RenameAdd);
            }
        }}
    }*/

    //批量重编码改进写法
    public void ReEncode(String path,String CodingType,int SaveMethod,String RenameAdd) throws IOException {
        File f=new File(path);
        BufferedReader In = null;
        Log.d("引用文件路径:",path);
        File[] files=f.listFiles();
        if(f.isDirectory()){
            files = f.listFiles();
            Log.d("该文件夹下的目录数量是",String.valueOf(files.length));
        }else {
            files=new File[1];
            files[0]=f;
        }
        for (File file:files) {
            //如果是目录则返回遍历函数
                if (file.exists()&&file.isDirectory()) {
                    Log.d(file.getName()+"是文件夹",file.getPath());
                    //如果选择了方法1，则遍历所有子目录下的.txt文件
                    ReEncode(file.getPath(),CodingType,SaveMethod,RenameAdd);
                    //是文件,并且后缀.txt
                    //} else if((file.getName()).endsWith(".txt")){
                }
                else if (!getFilecharset(file.getPath()).equals("UTF-8")&&file.getPath().toLowerCase().endsWith(".txt"))
                {
                    Log.d(file.getName()+"是文件1",file.getPath());
                    Log.d(file.getName()+"编码为",getFilecharset(file.getPath()));
                        In = new BufferedReader(new InputStreamReader(new FileInputStream(file),getFilecharset(file.getPath()) ));// 读取文件
                        String thisLine = null;
                        //如果读到了，继续拼接
                        while ((thisLine = In.readLine()) != null) {
                                if(SaveMethod==0){
                                    SaveFile( thisLine,file.getPath()+".bak.txt");
                                }
                                else if(SaveMethod==1){
                                    SaveFile( thisLine,file.getPath()+RenameAdd);}
                    }

                        if(SaveMethod==0){
                            //file.delete();
                            File F=new File(file.getPath()+".bak.txt");
                            F.renameTo(file);
                         }
                        if (In != null) {
                            try {
                                In.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }

    }
    //批量规格化
    public void Normalize(String path,String RegRule,String AfterReplacce,int SaveMethod,String RenameAdd) throws IOException {
        File f=new File(path);
        BufferedReader In = null;
        Log.d("引用文件路径:",path);
        File[] files;;
        if(f.isDirectory()){
            files = f.listFiles();
            Log.d("该文件夹下的目录数量是",String.valueOf(files.length));
        }else {
            files=new File[1];
            files[0]=f;
        }
        for (File file:files) {
            //如果是目录则返回遍历函数
            if (file.exists()&&file.isDirectory()) {
                Log.d(file.getName()+"是文件夹",file.getPath());
                //如果选择了方法1，则遍历所有子目录下的.txt文件
                Normalize(file.getPath(),RegRule,AfterReplacce,SaveMethod,RenameAdd);
                //是文件,并且后缀.txt
                //} else if((file.getName()).endsWith(".txt")){
            }
            else if (file.getPath().toLowerCase().endsWith(".txt"))
            {
                In = new BufferedReader(new InputStreamReader(new FileInputStream(file), getFilecharset(file.getPath())));// 读取文件
                String thisLine = null;
                //如果读到了，继续拼接
                String ReadOne="";
                int ReadTime=0;
                while ((thisLine = In.readLine()) != null) {
                    ReadOne+=thisLine+"\n";
                    ReadTime+=1;
                    if(ReadTime==50){
                    if(SaveMethod==0){
                        SaveFile( ReadOne.replaceAll(RegRule,AfterReplacce),file.getPath()+".bak.txt");
                    }
                    else if(SaveMethod==1){
                        SaveFile( ReadOne.replaceAll(RegRule,AfterReplacce),file.getPath()+RenameAdd);}
                        ReadTime=0;
                        ReadOne="";
                }}

                if(ReadTime!=0){
                if(SaveMethod==0){
                    SaveFile( ReadOne.replaceAll(RegRule,AfterReplacce),file.getPath()+".bak.txt");
                }
                else if(SaveMethod==1){
                    SaveFile( ReadOne.replaceAll(RegRule,AfterReplacce),file.getPath()+RenameAdd);}}


                if(SaveMethod==0){
                    //file.delete();
                    File F=new File(file.getPath()+".bak.txt");
                    F.renameTo(file);
                }
                if (In != null) {
                    try {
                        In.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //文本拆分
    public void TextSplit(String path,String RegRule) throws IOException {
        File f=new File(path);
        BufferedReader In = null;
        Log.d("引用文件路径:",path);
        File[] files;;
        if(f.isDirectory()){
            files = f.listFiles();
            Log.d("该文件夹下的目录数量是",String.valueOf(files.length));
        }else {
            files=new File[1];
            files[0]=f;
        }
        for (File file:files) {
            //如果是目录则返回遍历函数
            if (file.exists()&&file.isDirectory()) {
                Log.d(file.getName()+"是文件夹",file.getPath());
                //如果选择了方法1，则遍历所有子目录下的.txt文件
                TextSplit(file.getPath(),RegRule);
                //是文件,并且后缀.txt
                //} else if((file.getName()).endsWith(".txt")){
            }
            else if (file.getPath().toLowerCase().endsWith(".txt"))
            {
                String ReadOne="";
                //如果读到了，继续拼接
                int ReadTime=1;
                Pattern RegPa=Pattern.compile(RegRule);
                String Content=ReadFileToString(file.getPath());
                Matcher matcher=RegPa.matcher(Content);
                while(matcher.find())
                {
                        SaveFile(matcher.group(),file.getParent()+"/"+file.getName().substring(0,file.getName().length()-4)+"/第"+ReadTime+"篇.txt");
                        ReadTime+=1;
                }
                if (In != null) {
                    try {
                        In.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    //文本合并
    public void TextMerge(String path,String RegRule) throws IOException {
        File f=new File(path);
        BufferedReader In = null;
        Log.d("引用文件路径:",path);
        File[] files;;
        if(f.isDirectory()){
            files = f.listFiles();
            Log.d("该文件夹下的目录数量是",String.valueOf(files.length));
        }else {
            files=new File[1];
            files[0]=f;
        }
        for (File file:files) {
            //如果是目录则返回遍历函数
            if (file.exists()&&file.isDirectory()) {
                Log.d(file.getName()+"是文件夹",file.getPath());
                //如果选择了方法1，则遍历所有子目录下的.txt文件
                TextSplit(file.getPath(),RegRule);
                //是文件,并且后缀.txt
                //} else if((file.getName()).endsWith(".txt")){
            }
            else if (file.getPath().toLowerCase().endsWith(".txt"))
            {
                Pattern RegPa=Pattern.compile(RegRule);
                Matcher matcher=RegPa.matcher(file.getName());
                String AChapter=file.getName().substring(0,file.getName().length()-4)+"\n"+ReadFileToString(file.getPath());
                if(matcher.find())
                {
                    SaveFile(AChapter,file.getParent()+"/"+file.getParentFile().getName()+".Merge.txt");
                }
                if (In != null) {
                    try {
                        In.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
