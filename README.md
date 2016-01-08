# stupidbird

![](http://i.imgur.com/usJ19zp.jpg)

笨鸟先飞早起多吃身体好飞的快


0. 目前进度
----------
【整体】

1. 核心库目前1w行代码。
2. 下面的第一个版本基本完成了开发。


【version1】

1. 模板层。使用wow机制。（done）
2. 缓存层：本地缓存 + memcached + redis + ssdb的方案接入。（done）
3. 对象间通信，如何更好的封装actor，使用和理解起来都更容易？（done）

【version1.1】

1. 慢查询日志
2. 更快更容易的启动。
3. 更少的接口，facade化，更容易学习。
4. 模板层类自动生成。


【version1.2】

1. cassandra需要实体层自动生成的功能，或者寻找类似mongo-morphia这样的框架。
2. db的集群采用自己开发的router层或者使用mongos类似的方案。
3. 设计cluster集群方案或可以参考gossip协议实现。
4. 资源监控以及异常报警处理
5. 脚本机制（java或者js类脚本）
6. 消息拦截机制

0.1. dependency injection
----------
引擎开发到2016.1.6这天，我准备在其中加入di的东西。其实之前在生产中用过类似的东西，用的是spring来托管，但是进入上线后期发现spring在di这里的效率堪忧，所以去掉了大部分依赖，说以可以说之前对di是一次不愉快的使用（其实很大程度源于时间问题以及对spring di的不了解）。

这次我准备使用guice作为di驱动。原因是看了一个项目使用这个还挺简单方便的，而且据说它的效率是spring的100倍。而且主要这里的思路是源于业务层的架构：

1. 业务层有Module模块的概念，用来管理玩家指定模块的数据。
2. 模块之前的交互通过对对指定模块抽象出交互接口。

		/**
		 * 玩家物品模块;
		 * 
		 * @author crazyjohn
		 *
		 */
		public class HumanItemModule extends BaseHumanModule implements ItemModule {
			protected List<HumanItemEntity> items = new ArrayList<HumanItemEntity>();
			@Inject
			protected EquipModule itemModule;
			private Logger logger = LoggerFactory.getLogger("Action");
		
			@Inject
			public HumanItemModule(Human human) {
				super(human);
			}
		
			@Override
			public void onLogin(HumanEntity entity) {
				items = entity.getItems();
			}
如上，物品模块和装备模块交互的时候，通过注入`EquipFronter`接口来实现。

		public interface EquipFronter {
			public void addEquip(HumanEquipEntity equipEntity);
		}
3. 通过guice来进行方便的di注入管理。
4. 以上就是整体的思路，或许后面发现有问题（效率？），还会再次去掉。
5. 目前不爽的地方就是guice尼玛还需要引入javax.inject和aopalliance这两个包，让整体体积又变大了。

0.2. 整体层次
----------
1. 消息层 : protobuf packet
2. 网络层 : mina
3. 序列化层 : protobuf
4. 业务层 : guice + Fronter + module
5. 执行层 : actor
6. 缓存层 : local + redis + memcached strategy
7. 数据层 : strategy - mongo + morphia

1. 线程结构
----------
多线程驱动，graceful stop。actor communication。

2. 并发安全
----------
thread bind object and use actor communicate way

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

5. java8的使用
----------
目前只使用了lambda表达式的部分，后续可能会尝试stream处理，但是首先要保证性能。


