# stupidbird

![](http://i.imgur.com/T6rMWW8.jpg)

笨鸟先飞早起多吃身体好飞的快

这个logo是我借的，罪过罪过，大概表达了我希望的logo样子。坐等@bobo给我搞定logo

1. 启动一个Server节点
----------
 构建一个server节点

	public class ServerApp {
	
		public static void main(String[] args) throws Exception {
			Node node = new ServerNode();
			node.setIoHandler(new ServerExampleIoHandler(node));
			node.startup();
		}
	
	}

以上代码构建了一个server节点，并且启动，输出日志如下：

	[INFO] 14:27:39 - ServerNode.assemble - Game thread count: 1
	[INFO] 14:27:39 - ServerNode.startup - Startup io processor...
	[INFO] 14:27:39 - IoProcessor.startup - IoProcessor bind info => ip: 0.0.0.0, port: 8001
	[INFO] 14:27:39 - ServerNode.startup - IoProcessor already started.
	[INFO] 14:27:39 - ServerNode.startup - The ServerNode already started.

如日志内容所说，目前启动了一个绑定本地ip，端口8001的服务。

2. 启动一个Commander节点
----------
构建一个Commander指挥官节点

		public class CommanderServerApp {
		
			public static void main(String[] args) throws Exception {
				Commander commander = new Commander();
				commander.setIoHandler(new ServerExampleIoHandler(commander));
				commander.startup();
			}
		
		}

如上代码创建了一个Commander节点，跟server节点的不同之处在于指挥官会启动一个telnet server进程，你可以通过telnet协议跟它进行交互，先看输出日志：

		[INFO] 14:31:29 - ServerNode.assemble - Game thread count: 1
		[INFO] 14:31:29 - IoProcessor.startup - IoProcessor bind info => ip: 0.0.0.0, port: 7001
		[INFO] 14:31:29 - ServerNode.startup - Startup io processor...
		[INFO] 14:31:29 - IoProcessor.startup - IoProcessor bind info => ip: 0.0.0.0, port: 8001
		[INFO] 14:31:29 - ServerNode.startup - IoProcessor already started.
		[INFO] 14:31:29 - ServerNode.startup - The ServerNode already started.

如日志内容，启动了一个7001端口作为telnet端口，下面我们尝试进行交互下，使用telnet连接

	输入`telnet 127.0.0.1 7001`

	输出：`who r u?`

	输入： `auth u=crazyjohn p=crazyjohn`。进行认证。
	
	输出： `nice to meet you crazyjohn`。认证通过。

	输入： `shutdown`。关机。

	输出： `holy shit, you'll destory everything, i'll be die.`

检查发现刚才启动的节点关闭。你可以自己扩展命令和Commander进行交互，以及实现你想要的功能。

	

3. 吊炸天的例子
----------
[用stupidbird写一个吊炸天的游戏服务器](https://github.com/crazyjohn/stupidbird/wiki/GameServer)

上张图看看，这是用[Dick](https://github.com/crazyjohn/Dick)我的flash前端引擎，实现对基于stupidbird的游戏服务器接入：登陆创建角色选角色进场景后的截图：

![](http://i.imgur.com/BnUuARR.jpg)


4. 深入了解引擎
----------
[stupidbird引擎wiki](https://github.com/crazyjohn/stupidbird/wiki)
