# README
## svn路径
### 项目路径：
https://219.153.71.175:8443/svn/menphis/Java开发组/jiwei项目/jiwei
### 数据表结构：

江津案件数据表：https://219.153.71.175:8443/svn/menphis/~公共库/技术文档/江津案件系统/江津案件表结构.xlsx

返渝ncp数据表：https://219.153.71.175:8443/svn/menphis/~公共库/技术文档/ncp返渝登记/返渝登记数据表.xlsx
##项目内容
###1. 改写net的模块
+ 人员查询
+ 项目查询
+ 涉权查询
+ 查处导入
+ 廉洁自律
###2. 江津案件办案系统

###3. 返渝登记

## jiwei项目部署
### 1.修改项目配置文件
#####  src/main/resources/config.properties 中 
```
dataSource.jdbcUrl = jdbc:sqlserver://127.0.0.1:1433;databaseName=JIWEI_DB_BA
dataSource.password =123456
```
#####  src/main/resources/netPort.xml 中 两个标签的内容
```java
<ip>net项目的ip地址</ip> 
<port>net项目的端口号</port> 
```
#####  src/main/webapp/package.json 
```java
{
  "//": "是否检查用户有没有登录，然后重定向登陆页面",
  "checkLogin": true,
  "//":"配置NET相关属性值",
  "net": {
    "//": ".NET的登录地址http://183.230.71.65:8083 | http://219.153.71.175:8083",
    "address": "http://192.168.1.185:8083",
    "//": "net设置的浏览器cookie名字",
    "cookie_name": "_sessionYongChuanId"
  }
}
```
###  2.修改服务器上tomcat的配置文件

#####  apache-tomcat-8.5.47\conf\server.xml
```java
  #复制下面标签到apache-tomcat-8.5.47\conf\server.xml的Host标签中
    <Context crossContext="true" debug="0" docBase="D:\tomcat\files\images" path="/imgUrl" reloadable="true"/>
    <Context crossContext="true" debug="0" docBase="D:\tomcat\files\others" path="/otherUrl" reloadable="true"/>
```
### 3.使用cmd脚本代码，生成附件保存目录。
```
# 复制下面代码，使用cmd执行
md D:\tomcat\files\images D:\tomcat\files\others
```
### 4. 打包放入容器
 使用maven的clean，删除target目录，
 
 使用maven的compare，编译.java->.class
 
 检查target\classes文件夹下，文件是否齐全
 
 使用maven的package将项目打成war包，修改war包的文件名为jiwei.war(强制)
 
 复制jiwei.war到tomcate的webapps下
 
 启动服务器上的tomcat服务

