### 快速开始

#### 安装运行dtm

参考[dtm安装运行](https://dtm.pub/guide/install.html)

#### 运行示例
运行服务

```
mvn package && java -jar target/dtmcli-java-sample-0.0.1-SNAPSHOT.jar
```

触发成功的TCC事务
```
curl localhost:8081/tccBarrier
```

触发回滚的TCC事务
```
curl localhost:8081/tccBarrierError
```
