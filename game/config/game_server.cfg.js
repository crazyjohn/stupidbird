// ===== import begin =====//

//=====  import end   =====//

config.id = 1;
config.name = "game";
// bind ip
config.bindIp = "0.0.0.0";
// bind port 
config.port = 8001;
// telnet ip
config.telnetIp = "0.0.0.0";
// telnet port
config.telnetPort = 7001;
// 消息处理线程个数
config.msgThreadCount = 4;
// 数据处理线程个数
config.dbThreadCount = 4;
// 数据库连接
config.dbHost = "127.0.0.1";
// mongo:27017
// cassandra:9042
config.dbPort = 27017;
config.dbName = "stupidbird";
// root
config.root = "/nodes";
config.registryAddress = "localhost:2181";