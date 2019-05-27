### 快速爬虫帮助

#### 1.打开软件，默认采用快速爬虫，快速爬虫采用的方式为两次爬取，第一次爬取外链，第二次爬取内容

#### 2.头部标识为访问网站时采用的标识，为空则为默认方式，既不伪造

#### 3.爬取网站第一页，一般网站第二页到最后一页网址相对有规则，第一页则较为特殊，因此单独列出

#### 4.爬取前标识，页数标识，后标识。这里以网址https://www.kanunu8.com/files/terrorist/20-2.html为例，该网址为该网站恐怖灵异条目下第二页，则https://www.kanunu8.com/files/terrorist/20-为页码前标识，2为页码标识，.html为页码后标识。以此类推，https://www.kanunu8.com/files/terrorist/20-4.html为该网站恐怖灵异条目下第四页

#### 5.外链标识。包含智能爬取，简单爬取，选择器爬取三种方式，智能爬取爬取所有外链，通过下面的正则爬取规则与正则剔除规则筛选，简单爬取只能爬取id=与class=的网页元素，例如想要爬取的网站元素为class="novel content"，则选择class,下方输入novel content即可，选择器爬取范围较广，使用最专业，采用jsoup选择器，例如想要爬取的网站元素为class="novel content"，则需要输入.novel.content

#### 6.爬取的睡眠时间。某些网站具有短时间内不允许大量访问的特点，通过设置睡眠时间可以实现跳过这一限制

#### 7.内容标识。与外链标识类似，包含智能爬取，简单爬取，选择器爬取三种方式，简单爬取与选择器爬取与外链标识爬取方式一样，智能爬取则默认爬取元素下文本量最长的文本。

#### 8.保存方式。包括完整的路径+文件名,例如/sdcard/我的爬虫.txt

#### 9.示例:爬取小说明朝那些事儿1,网址:http://m.mingchaonaxieshier.com/ming-chao-na-xie-shi-er-1

1. 输入标识(可省略):Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19
2. 输入网站第一页:http://m.mingchaonaxieshier.com/ming-chao-na-xie-shi-er-1
3. 由于只有一页，页码前标识，页码后标识可省略，页码为第1页到第1页
4. 外链标识，内容标识选择智能爬取
5. 正则表达式爬取规则，设置输入:(第.+?章|前言|引子)
6. 爬取睡眠时间1,2均设置为0
7. 保存路径+完整文件名设置为/sdcard/明朝那些事儿1.txt，选择覆盖
8. 点击开始爬取
