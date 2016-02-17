// ===== import begin =====//

//=====  import end   =====//

config.id = 1;
config.name = "cluster";
// bind ip
config.bindIp = "0.0.0.0";
// bind port 
config.port = 8008;
// telnet ip
config.telnetIp = "0.0.0.0";
// telnet port
config.telnetPort = 7008;
// 消息处理线程个数
config.msgThreadCount = 4;
// 数据处理线程个数
config.dbThreadCount = 4;
// root dir
config.root = "/nodes";
// registry
// 203.195.218.172 - merge
// localhost
config.registryAddress = "localhost:2181";