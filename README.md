# stupidbird

![](http://i.imgur.com/usJ19zp.jpg)

stupidbird fly first!


0. 目前进度
----------
【TODO】

1. 模板层？
2. cassandra需要实体层自动生成的功能，或者寻找类似mongo-morphia这样的框架。
3. db的集群采用自己开发的router层或者使用mongos类似的方案。
4. 缓存层：本地缓存 + memcached + redis的方案接入。
5. 对象间通信，如何更好的封装actor，使用和理解起来都更容易？
6. 设计cluster集群方案或可以参考gossip协议实现。
7. 慢查询日志
8. 资源监控系统

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

