## 一、服务启动顺序:

mail-server

code-server

user-server

gateway

其他nginx配置等于上的作业相同

## 二、配置中心改为nacos设置

![image-20200604022619402](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604022619402.png)

![image-20200604021517597](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604021517597.png)





## 三、配置中心改为nacos配置中心



![image-20200604021539453](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604021539453.png)

## 四、feign调用改为dubbo调用

![image-20200604023904671](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604023904671.png)
![image-20200604025517362](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/blob/master/img/image-20200604025517362.png)

## 五、sentinel对gateway限流

![image-20200604024418217](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604024418217.png)

对/api/user/login/854815059@qq.com/123456限流

![image-20200604024601859](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604024601859.png)

qps一秒1次

![image-20200604024650480](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604024650480.png)

![image-20200604025053731](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604025053731.png)

可以看到一秒钟请求了十次，只通过了一次

![image-20200604025204601](https://gitee.com/chenyuhua321/spring-cloud-alibaba-test/raw/master/img/image-20200604025204601.png)