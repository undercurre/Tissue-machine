## 协议和授权

platform-mall并非一个开源软件，作者保留全部的权利。
擅自窃用，即属严重侵权行为，与盗窃无异。产生的一切任何后果责任由侵权者自负。

### 🚫禁止  

- 将本项目的部分或全部代码和资源进行任何形式的再发行（尤其上传GitHub、Gitee ）

- ![https://platform-wxmall.oss-cn-beijing.aliyuncs.com/copyright.jpg](https://platform-wxmall.oss-cn-beijing.aliyuncs.com/copyright.jpg "软著")


## 贡献代码

platform-mall并非一个开源项目，也不是社区共同创造，其全部功能由作者独立完成。

如果你愿意放弃所有权利，并将权利无条件转让给我们，欢迎您贡献代码。

#### 项目说明

- 服务器环境 
    - CentOS
    - Nginx
    - JDK1.8
    - MySQL5.7+
    - Redis
- 开发工具
    - IntelliJ IDEA、Eclipse
    - JetBrains WebStorm
    - 微信web开发者工具
    - Navicat for MySQL
    - Xshell
    - Xftp
    - Postman
    
#### 后端启动
- 创建数据库plaftorm-plus
- mysql执行_sql/platform-mall-MySQL.sql、platform-mall-MySQL.sql-activiti.sql，初始化数据
- 安装启动redis服务
- 修改application-dev.yml，修改MySQL账号和密码、redis端口号和密码、微信支付等配置、快递鸟账户配置
- 将证书复制到 platform-mall\platform-api\src\main\resources\apiclient_cert.p12
- Eclipse、IDEA运行
    - PlatformAdminApplication.java(port:8888)
    - PlatformApiApplication.java(port:8889)
- Swagger路径：http://localhost:8889/platform-api/doc.html

#### 生产环境打包
- 打包
    - mvn clean
    - mvn package -P prod
- 将platform-admin.jar、platform-api.jar上传至服务器
- 启动服务
    ##### 在服务器编写脚本
    > vim startPlatformAdminService.sh
    ~~~
    (netstat -tlnp|grep 8888|awk '{print $7}'|awk -F '/' '{print $1}') |xargs kill -9 
    echo "................kill the process platform-admin.............................."
    nohup java -jar platform-admin.jar &
    sleep 5s
    tail -f /home/logs/platform-admin/info.log
    ~~~
    > vim startPlatformApiService.sh
    ~~~
    (netstat -tlnp|grep 8889|awk '{print $7}'|awk -F '/' '{print $1}') |xargs kill -9 
    echo "................kill the process platform-api.............................."
    nohup java -jar platform-api.jar &
    sleep 5s
    tail -f /home/logs/platform-api/info.log
    ~~~
    ##### 执行这两个脚本，启动服务

#### 前端启动
- 请看：platform-admin-ui/README.md

#### 后台管理系统默认密码
- admin admin
- 其他账号  888888

#### 小程序端启动
- 打开微信web开发者工具
- 导入项目，选择platform-mall/wx-mall
- 填写AppID
- 修改选择platform-mall/wx-mall/utils/util.js，修改API_BASE_URL的值
- 在微信web开发者工具中上传小程序代码到微信公众平台

- 登录微信公众平台|小程序

    - 在'开发->开发设置'，填写服务器域名、业务域名（写上你自己的域名）
    - request合法域名需要额外添加域名： https://apis.map.qq.com
    - 在版本管理中提交代码审核，待审核通过后发布。

#### 常用API
- [Mybatis-Plus](https://baomidou.gitee.io/mybatis-plus-doc/#/quick-start)
- [Vue](https://cn.vuejs.org/v2/api/)
- [element-ui](http://element-cn.eleme.io/#/zh-CN/component/installation)
- [echarts](https://www.echartsjs.com/api.html#echarts)
- [iconfont](https://www.iconfont.cn/search/index)
