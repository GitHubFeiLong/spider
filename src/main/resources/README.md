
###  修改服务器上tomcat的配置文件

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

