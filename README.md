### 快速开始

#### 启动dtm

首先安装docker版本20.04以上

```
git clone https://github.com/yedf/dtm
cd dtm
docker-compose up
```

#### 运行示例
运行服务

```
pip3 install flask
flask run
```

触发成功的TCC事务
```
curl localhost:8081/tccBarrier
```

触发回滚的TCC事务
```
curl localhost:8081/tccBarrierError
```
