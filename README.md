# jd-crawer-selenium

需要下载chromdriver驱动 并吧chromdriver.exe放在谷歌浏览器的下方。

抓取京东商城手机数据的爬虫的爬虫
可以通过修改 关键词抓取别的商品信息
吧手机改成其他的名称即可

数据表
create table item(
 id BIGINT(32) primary key auto_increment,
 sku BIGINT(32),
 spu BIGINT(32),
 created TIMESTAMP,
 updated TIMESTAMP,
 title varchar(2000),
 pic varchar(2000),
 price FLOAT,
 url varchar(2000)
)
