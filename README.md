### 基于 SpringBoot+Mybatis+Redis+RabbitMQ 秒杀系统  

### 开发工具

idea  2017.2

### 开发环境

| JDK | Maven| Mysql  | SpringBoot | redis | RabbitMQ| 
| :-------------: |:-------------:| :-------------:| :-------------:|:-------------:| :-------------:|
| 1.8  | 3.2.2 | 5.7| 1.5.10.RELEASE | 3.2 | 4.X | 

### 项目启动说明

1、启动前，请配置 application.properties 中相关redis、mysql、rabbitmq地址。

2、登录地址：http://localhost:8888/page/login   

3、商品秒杀列表地址：http://localhost:8888/goods/list

### 其它说明

数据库共有五千个用户（手机号：从18077200000~18077204999 密码为：123456），为压测准备的。（使用 com.site.seckill.util.UserUtil.java该类生成的，生成token做压测也是在此类里面）

### 页面截图
登录页
![123](img/1.png)
商品列表情页
![123](img/2.png)
商品详情页
![123](img/3.png)
订单详情页
![123](img/4.png)

