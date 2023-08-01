# 网上商城购物系统-Web后端系统
## 1、online-shop(商城购物模块)
### 1.1 包结构
>com.wcx.onlineshop
>>config: 存放配置类
>> consts: 存放常量类  
> enums: 存放枚举类  
> exception: 存放自定义异常类  
> form: 存放表单类  
> interceptor: 存放自定义拦截类  
> pojo: 存储数据库表的映射类  
> vo: 存放传递给前端的视图类  
> controller: 控制层  
> dao: 存放dao类  
> service: 存放服务接口类
>>> impl: 存放service接口的实现类  
### 1.2 模块功能
提供网上商城用户注册与登录、浏览商品、创建购物车、下单等产品功能。
## 2、wcx-pay(支付模块)
### 2.1 包结构
> com.wcx.pay
>>config: 存放配置类
> enums: 存放枚举类  
> pojo: 存储数据库表的映射类  
> controller: 控制层  
> dao: 存放dao类  
> service: 存放服务接口类
>>> impl: 存放service接口的实现类  
### 1.2 模块功能
负责与支付平台微信或者支付宝对接，提供所有业务产品的支付服务。
## 3.技术栈简介和运行

技术栈: SpringBoot2.6.11 + Mysql8.x + Redis5.x+RabbitMQ3.8.2+Mybatis-plus


online-shop和wcx-pay分别作为两个Maven项目运行。运行主机需要正确安装Mysql、Redis、RabbitMQ等工具，并正确配置Maven仓库环境(阿里云镜像地址)、JDK8环境。