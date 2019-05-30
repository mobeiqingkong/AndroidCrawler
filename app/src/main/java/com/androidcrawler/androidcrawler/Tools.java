package com.androidcrawler.androidcrawler;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    //判断编码格式方法
    public String getFilecharset(String SourceFilePath) {
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
                            {continue;}
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
    public void SaveFile(String content, String PathAndSaveName) {
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
    String ReadFileToString(String path) {
        // 定义返回结果
        StringBuilder Result = new StringBuilder();
        BufferedReader In = null;
        try {
            In = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), getFilecharset(path)));// 读取文件
            String thisLine;
            //如果读到了，继续拼接
            while ((thisLine = In.readLine()) != null) {
                Result.append(thisLine);
                Result.append("\n");
                //使用拼接会每次new一个StringBuilder，最后会内存泄漏
                //Test+=thisLine+"\n";
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
        return Result.toString();
    }
    //批量重命名
    void RenameAllFile(String TXTPath, String RegRule, String AfterReplacce, int IfContainDirectory) {
        if(TXTPath.endsWith("/")){
            TXTPath=TXTPath.substring(0,TXTPath.length()-1);}
        //获取文件夹下的所有文件(正确理解Java的文件:文件和文件夹)
        String NewName;
        //TXTPath=TXTPath.replaceAll("([^\\s\\S]|([\\t\\s\\n])+)","");
        Log.d("这是传递过来的TXTPath路径:", TXTPath);
        File f = new File(TXTPath);
        File[] files;
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
                //如果是目录则递归
                // if (file.exists() && file.isDirectory()) {
                if (file.isDirectory()) {
                    if (IfContainDirectory == 1) {
                        //如果选择了方法1，则遍历所有子目录下的.txt文件
                        RenameAllFile(file.getPath(), RegRule, AfterReplacce, IfContainDirectory);
                    }
                    //是文件,并且后缀.txt
                    //} else if((file.getName()).endsWith(".txt")){
                } else {
                    Log.d("测试重命名前:", file.getName());
                    NewName = file.getName().replaceAll(RegRule, AfterReplacce);
                    Log.d("重命名测试:", NewName);
                    if (file.renameTo(new File(f.getPath() + "/" + NewName))) {
                        Log.d("重命名成功,重命名后:", file.getName());
                    } else {
                        Log.d("重命名失败,重命名后:", file.getName());
                    }
                }
            }
    }

    //批量重编码原写法
    void ReEncode(String path, String CodingType, int SaveMethod, String RenameAdd){
        if(path.endsWith("/")){
            path=path.substring(0,path.length()-1);}
        File f=new File(path);
        Log.d("引用文件路径:",path);
        File[] files ;
        if(f.isDirectory()){
            files = f.listFiles();
        }else {
            files=new File[1];
            files[0]=f;
        }
            for (File file:files) {
                //如果是目录则返回遍历函数
                if (file.exists()&&file.isDirectory()) {
                    //如果选择了方法1，则遍历所有子目录下的.txt文件
                    ReEncode(file.getPath(),CodingType,SaveMethod,RenameAdd);
                    //是文件,并且后缀.txt
                    //} else if((file.getName()).endsWith(".txt")){
                } else if (!getFilecharset(file.getPath()).equals("UTF-8")&&file.getPath().toLowerCase().endsWith(".txt")){
                    if(SaveMethod==0){
                        SaveFile(ReadFileToString(file.getPath()) ,file.getPath()+".tmp");
                        File F=new File(file.getPath()+".tmp");
                        F.renameTo(file);
                    }
                    else if(SaveMethod==1){
                        SaveFile( ReadFileToString(file.getPath()),file.getPath()+RenameAdd);
                    }
                    }
            }
    }

    //批量重编码改进写法
    /*
    void ReEncode(String path, String CodingType, int SaveMethod, String RenameAdd) throws IOException {
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
    */
    //批量规格化
    void Normalize(String path, LinkedList<String> RegRule, LinkedList<String> AfterReplacce, int SaveMethod, String RenameAdd) {

        if(path.endsWith("/")){
            path=path.substring(0,path.length()-1);}
        File f=new File(path);
        Log.d("引用文件路径:",path);
        File[] files;
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
                //如果读到了，继续拼接
                String Content=ReadFileToString(file.getPath());
                        for(int i=0;i<RegRule.size();i++){
                            Content= Content.replaceAll(RegRule.get(i),AfterReplacce.get(i));
                        }
                        if(SaveMethod==0){
                        SaveFile( Content,file.getPath()+".Normalizetmp");
                        File F=new File(file.getPath()+".Normalizetmp") ;
                        F.renameTo(file);
                    }
                    else if(SaveMethod==1){
                        SaveFile( Content,file.getPath()+RenameAdd);}
                }
            }}
    //文本拆分
    void TextSplit(String path, String RegRule) throws IOException {

        if(path.endsWith("/")){
            path=path.substring(0,path.length()-1);}
        File f=new File(path);
        Log.d("引用文件路径:",path);
        File[] files;
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
            }
        }
    }


    //文本合并
    void TextMerge(String path, String RegRule) throws IOException {
        File f=new File(path);
        Log.d("引用文件路径:",path);
        File[] files;
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



                if(matcher.find())
                {
                    SaveFile(ReadFileToString(file.getPath()),file.getParent()+"/"+file.getParentFile().getName()+".Merge.txt");
                }
            }
        }
    }











}
