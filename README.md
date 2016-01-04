# stupidbird
stupidbird fly first!

> StupidBird


-1. 目前进度
----------
【TODO】

1. 模板层？
2. cassandra需要实体层自动生成的功能，或者寻找类似mongo-morphia这样的框架。
3. db的集群采用自己开发的router层或者使用mongos类似的方案。
4. 缓存层：本地缓存 + memcached + redis的方案接入。
5. 对象间通信，如何更好的封装actor，使用和理解起来都更容易？
6. 设计以及实现gossip协议。
7. 慢查询日志
8. 资源监控系统

0. 整体层次
----------
1. 消息层，
2. 网络层，
3. 序列化层，
4. 业务层，
5. 任务层，
6. 缓存层，
7. 数据层

1. 线程结构
----------
1. 业务层多线程，可配置
2. 数据层多线程，可配置

2. 并发安全
----------
1. 高级抽象使用类似活动对象的结构。ActiveObject。
2. 线程安全管理器。ThreadSafeManager。

3. 缓存层
----------

使用策略多种策略，可配置，灵活切换。

1. Local LRU。
2. redis。
3. memcached。

4. 数据层
----------

支持实体sql化;停机超时dump文件;考虑集群方案cluster。


1. orm
2. cassandra
3. redis
4. mysql
5. mongodb

