package com.stupidbird.core.template;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.stupidbird.core.log.GameLog;
import com.stupidbird.core.os.GameMonitor;
import com.stupidbird.core.os.GameTime;

/**
 * 模板服务;
 * 
 * @author stupidbird
 */
public class TemplateService {
	/**
	 * xml类型配置注解
	 * 
	 * @author stupidbird
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ java.lang.annotation.ElementType.TYPE })
	public @interface XmlResource {
		/**
		 * 文件路径
		 * 
		 * @return
		 */
		public String file() default "";

		/**
		 * 存储结构, "map" | "list"
		 * 
		 * @return
		 */
		public String struct() default "map";
	}

	/**
	 * kv类型配置注解
	 * 
	 * @author stupidbird
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ java.lang.annotation.ElementType.TYPE })
	public @interface KVResource {
		/**
		 * 文件路径
		 * 
		 * @return
		 */
		public String file() default "";
	}

	/**
	 * json类型配置注解
	 * 
	 * @author stupidbird
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ java.lang.annotation.ElementType.TYPE })
	public @interface JsonResource {
		/**
		 * 文件路径
		 * 
		 * @return
		 */
		public String file() default "";

		/**
		 * 存储结构, "map" | "list"
		 * 
		 * @return
		 */
		public String struct() default "map";
	}

	/**
	 * 配置对象存储器
	 */
	private ConcurrentHashMap<Class<?>, TempalteStorage> storages = new ConcurrentHashMap<Class<?>, TempalteStorage>();
	/**
	 * 存储备份配置对象
	 */
	private Map<Class<?>, TempalteStorage> backupStorages = new ConcurrentHashMap<Class<?>, TempalteStorage>();
	/**
	 * 自动清理static容器数据
	 */
	private boolean autoClearStaticData = true;
	/**
	 * 配置管理器实例
	 */
	private static final TemplateService instance = new TemplateService();;

	/**
	 * 获取配置管理器实例
	 * 
	 * @return
	 */
	public static TemplateService getInstance() {
		return instance;
	}

	/**
	 * 初始化配置管理器
	 * 
	 * @param packages
	 *            , 多个包以逗号分隔
	 * @throws Exception
	 */
	public boolean init(String configPackages) {
		try {
			String[] configPackageArray = configPackages.split(",");
			if (configPackageArray != null) {
				for (String configPackage : configPackageArray) {
					GameLog.logPrintln("init template package: " + configPackage);
					List<Class<?>> classList = GameClassScaner.scanClassesFilter(configPackage, XmlResource.class,
							KVResource.class, JsonResource.class);

					for (Class<?> configClass : classList) {
						storages.put(configClass, new TempalteStorage(configClass));
					}
				}
			}

			// 最终校验配置文件数据
			if (!checkConfigData()) {
				return false;
			}

			return true;
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return false;
	}

	/**
	 * 自动清理静态数据
	 * 
	 * @param auto
	 */
	public void autoClearStaticData(boolean auto) {
		this.autoClearStaticData = auto;
	}

	/**
	 * 检测配置数据
	 * 
	 * @return
	 */
	private boolean checkConfigData() {
		for (Map.Entry<Class<?>, TempalteStorage> entry : storages.entrySet()) {
			TempalteStorage storage = entry.getValue();
			if (!storage.checkValid()) {
				GameLog.errPrintln("config check valid failed: " + entry.getKey().getName());
				return false;
			}
		}
		return true;
	}

	/**
	 * 清理静态数据
	 * 
	 * @param configClass
	 * @return
	 */
	private boolean clearConfigStaticData(Class<?> configClass) {
		try {
			if (autoClearStaticData) {
				for (Field field : configClass.getDeclaredFields()) {
					if (!Modifier.isStatic(field.getModifiers())) {
						continue;
					}

					for (Method method : field.getType().getDeclaredMethods()) {
						if (method.getName().equals("clear")) {
							try {
								field.setAccessible(true);
								method.invoke(field.get(null));
							} catch (Exception e) {
								GameMonitor.catchException(e);
							}
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			GameMonitor.catchException(e);
		}
		return false;
	}

	/**
	 * 更新加载
	 */
	public boolean updateReload() {
		backupStorages.clear();
		backupStorages.putAll(storages);
		List<TempalteStorage> needCheckList = new LinkedList<>();
		try {
			for (Entry<Class<?>, TempalteStorage> entry : backupStorages.entrySet()) {
				if (entry.getValue().checkUpdate()) {
					GameLog.logPrintln("check config update: " + entry.getValue().getFilePath());
					// 清理静态数据
					if (!clearConfigStaticData(entry.getKey())) {
						continue;
					}

					// 加载新配置信息
					TempalteStorage configStorage = new TempalteStorage(entry.getKey());
					storages.put(entry.getKey(), configStorage);
					// 添加待检测列表
					needCheckList.add(configStorage);
				}
			}
		} catch (Exception e) {
			// 出现异常即恢复备份对象库
			storages.clear();
			storages.putAll(backupStorages);
			// 打印异常
			GameMonitor.catchException(e);
			return false;
		}

		for (TempalteStorage storage : needCheckList) {
			// 校验失败即恢复备份配置信息
			if (!storage.checkValid()) {
				storages.clear();
				storages.putAll(backupStorages);
				GameLog.errPrintln("storage check failed: " + storage.getFilePath());
				return false;
			}
			GameLog.logPrintln("update config success: " + storage.getFilePath());
		}

		GameLog.logPrintln("check config finish: " + GameTime.getTimeString());
		return true;
	}

	/**
	 * 获取配置列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getConfigList(Class<T> cfgClass) {
		TempalteStorage storage = storages.get(cfgClass);
		if (storage != null) {
			return (List<T>) storage.getConfigList();
		}
		return null;
	}

	/**
	 * 获取配置表映射
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<Object, T> getConfigMap(Class<T> cfgClass) {
		TempalteStorage storage = storages.get(cfgClass);
		if (storage != null) {
			return (Map<Object, T>) storage.getConfigMap();
		}
		return null;
	}

	/**
	 * 获取指定配置文件中特定索引的配置
	 * 
	 * @param cfgClass
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getConfigByIndex(Class<T> cfgClass, int index) {
		TempalteStorage storage = storages.get(cfgClass);
		if (storage != null) {
			List<T> cfgList = (List<T>) storage.getConfigList();
			if (cfgList != null && cfgList.size() > index) {
				return cfgList.get(index);
			}
		}
		return null;
	}

	/**
	 * 获取指定配置文件中特定key的配置
	 * 
	 * @param cfgClass
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getConfigByKey(Class<T> cfgClass, Object key) {
		TempalteStorage storage = storages.get(cfgClass);
		if (storage != null) {
			Map<Object, T> cfgMap = (Map<Object, T>) storage.getConfigMap();
			if (cfgMap != null) {
				return cfgMap.get(key);
			}
		}
		return null;
	}
}
