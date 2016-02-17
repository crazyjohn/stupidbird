package com.stupidbird.core.template;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.reflect.TypeToken;
import com.stupidbird.core.cryption.Md5;
import com.stupidbird.core.log.GameLog;
import com.stupidbird.core.os.GameOSOperator;
import com.stupidbird.core.template.BaseTemplate.Id;
import com.stupidbird.core.util.GsonUtil;

/**
 * 单个类型
 * 
 * @author stupidbird
 * 
 */
public class TempalteStorage {

	/**
	 * 日期转化器
	 */
	private static DateFormat DATE_FORMATOR = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 配置类型
	 */
	private Class<?> cfgClass;
	/**
	 * 配置文件位置
	 */
	private String filePath;
	/**
	 * 文件修改时间
	 */
	private long modifyTime;
	/**
	 * 文件md5
	 */
	private String fileMd5;

	/**
	 * 配置对象列表
	 */
	private List<BaseTemplate> templateList = null;
	/**
	 * 配置对象映射表
	 */
	private Map<Object, BaseTemplate> templateMap = null;
	private String resourcePath = System.getProperty("user.dir") + File.separator;;
	/**
	 * 域映射
	 */
	private static Map<String, Field> classFieldMap = new ConcurrentHashMap<String, Field>();

	/**
	 * 构造函数
	 * 
	 * @param cfgClass
	 * @throws Exception
	 */
	public TempalteStorage(Class<?> cfgClass) throws Exception {
		this.cfgClass = cfgClass;

		if (cfgClass.getAnnotation(TemplateService.XmlResource.class) != null) {
			TemplateService.XmlResource xmlRes = cfgClass.getAnnotation(TemplateService.XmlResource.class);

			filePath = resourcePath + xmlRes.file();
			GameLog.logPrintln("load template: " + filePath);

			if ("map".equals(xmlRes.struct())) {
				templateMap = new TreeMap<Object, BaseTemplate>();
			} else if ("list".equals(xmlRes.struct())) {
				templateList = new LinkedList<BaseTemplate>();
			} else {
				throw new Exception("unknown template struct: " + xmlRes.struct() + ", filePath: " + filePath);
			}
			loadXmlTemplate();
		} else if (cfgClass.getAnnotation(TemplateService.KVResource.class) != null) {
			// ConfigManager.KVResource kvRes =
			// cfgClass.getAnnotation(ConfigManager.KVResource.class);
			//
			// configList = new LinkedList<ConfigBase>();
			// filePath = Server.getInstance().getWorkPath() + kvRes.file();
			GameLog.logPrintln("load config: " + filePath);

			loadKVConfig();
		} else if (cfgClass.getAnnotation(TemplateService.JsonResource.class) != null) {
			/*
			 * { "list":[{"name":"stupidbird","age":28}] } {
			 * "map":[{"id":"stupidbird","age":28}] }
			 */
			TemplateService.JsonResource jsonRes = cfgClass.getAnnotation(TemplateService.JsonResource.class);

			// filePath = Server.getInstance().getWorkPath() + jsonRes.file();
			GameLog.logPrintln("load config: " + filePath);

			if ("map".equals(jsonRes.struct())) {
				templateMap = new TreeMap<Object, BaseTemplate>();
			} else if ("list".equals(jsonRes.struct())) {
				templateList = new LinkedList<BaseTemplate>();
			} else {
				throw new Exception("unknown config struct: " + jsonRes.struct() + ", filePath: " + filePath);
			}

			loadJsonConfig();
		}
	}

	/**
	 * 字符串首字母大写
	 * 
	 * @param string
	 * @return
	 */
	public static String convertFirstCharacterUpper(String string) {
		StringBuilder sb = new StringBuilder(string);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		string = sb.toString();
		return string;
	}

	/**
	 * 加载xml配置数据
	 */
	private void loadXmlTemplate() throws Exception {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new Exception("template file not exist: " + filePath);
			}

			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dombuilder = domFactory.newDocumentBuilder();
			Document doc = dombuilder.parse(new FileInputStream(file));
			Element root = doc.getDocumentElement();
			NodeList nodes = root.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				NamedNodeMap attrMap = node.getAttributes();
				if (attrMap != null) {
					String className = node.getNodeName();
					if (className != null && !"".equals(className)) {
						Object cfg = cfgClass.newInstance();
						if (cfg instanceof BaseTemplate) {
							BaseTemplate baseTemplate = (BaseTemplate) cfg;
							baseTemplate.setStorage(this);

							Object cfgKey = null;
							for (int j = 0; j < attrMap.getLength(); j++) {
								Node attrNode = attrMap.item(j);
								// 测试Id标注是否存在
								Field field = getField(cfg, attrNode.getNodeName().trim());
								if (field == null) {
									throw new Exception("temaplte class field not exist, file: " + filePath + ", field: "
											+ attrNode.getNodeName().trim());
								}

								Id id = null;
								try {
									id = field.getAnnotation(Id.class);
								} catch (Exception e) {
									throw new NullPointerException("template id annotation not exist: "
											+ cfg.getClass().getName() + ", nodeName: " + attrNode.getNodeName());
								}

								if (id != null) {
									// id标注重复
									if (cfgKey != null) {
										throw new Exception("template id annotation duplicate: " + filePath
												+ ", nodeName: " + attrNode.getNodeName());
									}

									String type = field.getType().toString();
									if (type.indexOf("int") >= 0 || type.indexOf("Integer") >= 0) {
										cfgKey = Integer.valueOf(attrNode.getNodeValue().trim());
									} else if (type.indexOf("String") >= 0) {
										cfgKey = attrNode.getNodeValue().trim();
									}
								}
								// 设置对应属性值
								setAttr(cfg, attrNode.getNodeName().trim(), attrNode.getNodeValue().trim());
							}

							// 通知上层装配
							if (baseTemplate.assemble()) {
								// 常量锁定
								baseTemplate.lockConst(true);
								// 存储
								if (templateList != null) {
									templateList.add(baseTemplate);
								} else {
									if (cfgKey != null) {
										if (templateMap.containsKey(cfgKey)) {
											throw new Exception("template key duplicate: " + filePath + ", key: "
													+ cfgKey);
										}

										templateMap.put(cfgKey, baseTemplate);
									} else {
										throw new Exception("template mapping key is null");
									}
								}
							} else {
								throw new Exception("template assemble failed: " + cfgClass.getName() + ", key: "
										+ cfgKey);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		// 存储文件热加载信息
		updateFileInfo();
	}

	/**
	 * 加载kv配置数据
	 */
	private void loadKVConfig() throws Exception {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new Exception("config file not exist: " + filePath);
			}

			ResourceBundle bundle = null;
			try {
				bundle = new PropertyResourceBundle(new InputStreamReader(new BufferedInputStream(new FileInputStream(
						file)), "UTF-8"));
			} catch (Exception e) {
				throw e;
			}

			if (bundle != null) {
				Enumeration<String> keys = bundle.getKeys();
				Object cfg = cfgClass.newInstance();
				if (cfg instanceof BaseTemplate) {
					BaseTemplate configBase = (BaseTemplate) cfg;
					configBase.setStorage(this);

					while (keys.hasMoreElements()) {
						String key = keys.nextElement().trim();
						setAttr(configBase, key, bundle.getString(key).trim());
					}

					// 通知上层装配
					if (configBase.assemble()) {
						// 常量锁定
						configBase.lockConst(true);
						// 存储
						templateList.add(configBase);
					} else {
						throw new Exception("config assemble failed: " + cfgClass.getName());
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		updateFileInfo();
	}

	/**
	 * 加载json配置文件
	 * 
	 * @throws Exception
	 */
	private void loadJsonConfig() throws Exception {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new Exception("config file not exist: " + filePath);
			}

			String fileData = GameOSOperator.readTextFile(filePath);
			if (fileData != null) {
				JSONObject jsonObject = JSONObject.fromObject(fileData);
				JSONArray jsonArray = null;
				if (templateList != null && jsonObject.containsKey("list")) {
					jsonArray = jsonObject.getJSONArray("list");
				} else if (templateMap != null && jsonObject.containsKey("map")) {
					jsonArray = jsonObject.getJSONArray("map");
				} else {
					throw new Exception("json root key must be list or map");
				}

				for (int i = 0; jsonArray != null && jsonArray.isArray() && i < jsonArray.size(); i++) {
					JSONObject jsonCfgObj = jsonArray.getJSONObject(i);

					Object cfg = cfgClass.newInstance();
					if (cfg instanceof BaseTemplate) {
						BaseTemplate configBase = (BaseTemplate) cfg;
						configBase.setStorage(this);

						Object cfgKey = null;
						Iterator<?> it = jsonCfgObj.keys();
						while (it.hasNext()) {
							String key = (String) it.next();
							String value = jsonCfgObj.getString(key);

							// 测试Id标注是否存在
							Field field = cfg.getClass().getDeclaredField(key.trim());

							Id id = null;
							try {
								id = field.getAnnotation(Id.class);
							} catch (Exception e) {
								throw new NullPointerException("config id annotation not exist: "
										+ cfg.getClass().getName() + ", nodeName: " + key);
							}

							if (id != null) {
								// id标注重复
								if (cfgKey != null) {
									throw new Exception("config id annotation duplicate: " + filePath);
								}
								String type = field.getType().toString();
								if (type.indexOf("int") >= 0 || type.indexOf("Integer") >= 0) {
									cfgKey = Integer.valueOf(value.trim());
								} else if (type.indexOf("String") >= 0) {
									cfgKey = value.trim();
								}
							}

							// 设置对应属性值
							setAttr(cfg, key, value);
						}

						// 通知上层装配
						if (configBase.assemble()) {
							// 常量锁定
							configBase.lockConst(true);
							// 存储
							if (templateList != null) {
								templateList.add(configBase);
							} else {
								if (cfgKey != null) {
									if (templateMap.containsKey(cfgKey)) {
										throw new Exception("config key duplicate: " + filePath + ", key: " + cfgKey);
									}
									templateMap.put(cfgKey, configBase);
								} else {
									throw new Exception("config mapping key is null");
								}
							}
						} else {
							throw new Exception("config assemble failed: " + cfgClass.getName() + ", key: " + cfgKey);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		updateFileInfo();
	}

	/**
	 * 更新文件信息
	 */
	private void updateFileInfo() {
		// 存储文件热加载信息
		File cfgFile = new File(filePath);
		if (cfgFile.exists()) {
			modifyTime = cfgFile.lastModified();
			fileMd5 = Md5.makeMD5(cfgFile);
		}
	}

	/**
	 * 获取域
	 * 
	 * @param instance
	 * @param attrName
	 * @return
	 */
	private Field getField(Object instance, String attrName) {
		String fieldName = instance.getClass().getName() + "." + attrName;
		Field field = classFieldMap.get(fieldName);
		if (field == null) {
			field = GameOSOperator.getClassField(instance, attrName);
			if (field != null) {
				classFieldMap.put(fieldName, field);
			}
		}
		return field;
	}

	/**
	 * 给指定对象调用set方法设置属性值，现在仅判断int float long double string 类型
	 * 
	 * @param instance
	 * @param attrName
	 * @param attrValue
	 */
	public void setAttr(Object instance, String attrName, String attrValue) throws Exception {
		Field field = getField(instance, attrName);
		if (field == null) {
			GameLog.errPrintln(String.format("config class cannot find field, class: %s, field: %s", instance
					.getClass().getName(), attrName));
			throw new Exception("config class cannot find field");
		}

		try {
			// editby: crazyjohn 2016-1-7暂时注释，immutable以后再说
			// 必须带final属性
			// if ((field.getModifiers() & Modifier.FINAL) == 0) {
			// throw new
			// RuntimeException("config attribute must be final, class: "
			// + instance.getClass().getSimpleName() + ", field: " + attrName);
			// }

			Method setMethod = null;
			try {
				setMethod = instance.getClass()
						.getMethod("set" + convertFirstCharacterUpper(attrName), field.getType());
			} catch (NoSuchMethodException e) {
			}

			String type = field.getType().toString();
			if (setMethod != null) {
				if (field.getType() == int[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<int[]>() {
					}.getType()));
				} else if (field.getType() == Integer[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Integer[]>() {
					}.getType()));
				} else if (field.getType() == float[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<float[]>() {
					}.getType()));
				} else if (field.getType() == Float[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Float[]>() {
					}.getType()));
				} else if (field.getType() == double[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<double[]>() {
					}.getType()));
				} else if (field.getType() == Double[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Double[]>() {
					}.getType()));
				} else if (field.getType() == String[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<String[]>() {
					}.getType()));
				} else if (field.getType() == Map.class) {
					field.set(instance,
							GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Map<String, String>>() {
							}.getType()));
				} else if (type.indexOf("boolean") >= 0 || type.indexOf("Boolean") >= 0) {
					setMethod.invoke(instance, Boolean.valueOf(attrValue));
				} else if (type.indexOf("int") >= 0 || type.indexOf("Integer") >= 0) {
					setMethod.invoke(instance, Integer.valueOf(attrValue));
				} else if (type.indexOf("long") >= 0 || type.indexOf("Long") >= 0) {
					setMethod.invoke(instance, Long.valueOf(attrValue));
				} else if (type.indexOf("float") >= 0 || type.indexOf("Float") >= 0) {
					setMethod.invoke(instance, Float.valueOf(attrValue));
				} else if (type.indexOf("double") >= 0 || type.indexOf("Double") >= 0) {
					setMethod.invoke(instance, Double.valueOf(attrValue));
				} else if (type.indexOf("String") >= 0) {
					setMethod.invoke(instance, attrValue);
				} else if (type.indexOf("Date") >= 0) {
					setMethod.invoke(instance, DATE_FORMATOR.parse(attrValue));
				} else {
					throw new Exception("cannot support property type: " + type);
				}
			} else {
				field.setAccessible(true);
				final Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
				if (field.getType() == int[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<int[]>() {
					}.getType()));
				} else if (field.getType() == Integer[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Integer[]>() {
					}.getType()));
				} else if (field.getType() == long[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<long[]>() {
					}.getType()));
				} else if (field.getType() == Long[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Long[]>() {
					}.getType()));
				} else if (field.getType() == float[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<float[]>() {
					}.getType()));
				} else if (field.getType() == Float[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Float[]>() {
					}.getType()));
				} else if (field.getType() == double[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<double[]>() {
					}.getType()));
				} else if (field.getType() == Double[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Double[]>() {
					}.getType()));
				} else if (field.getType() == String[].class) {
					field.set(instance, GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<String[]>() {
					}.getType()));
				} else if (field.getType() == Map.class) {
					field.set(instance,
							GsonUtil.getJsonInstance().fromJson(attrValue, new TypeToken<Map<String, String>>() {
							}.getType()));
				} else if (type.indexOf("boolean") >= 0 || type.indexOf("Boolean") >= 0) {
					field.set(instance, Boolean.valueOf(attrValue));
				} else if (type.indexOf("int") >= 0 || type.indexOf("Integer") >= 0) {
					field.set(instance, Integer.valueOf(attrValue));
				} else if (type.indexOf("long") >= 0 || type.indexOf("Long") >= 0) {
					field.set(instance, Long.valueOf(attrValue));
				} else if (type.indexOf("float") >= 0 || type.indexOf("Float") >= 0) {
					field.set(instance, Float.valueOf(attrValue));
				} else if (type.indexOf("double") >= 0 || type.indexOf("Double") >= 0) {
					field.set(instance, Double.valueOf(attrValue));
				} else if (type.indexOf("String") >= 0) {
					field.set(instance, attrValue);
				} else if (type.indexOf("Date") >= 0) {
					field.set(instance, DATE_FORMATOR.parse(attrValue));
				} else {
					throw new Exception("cannot support property type: " + type);
				}

				modifiersField.setInt(field, field.getModifiers() | Modifier.FINAL);
				modifiersField.setAccessible(false);
				field.setAccessible(false);
			}
		} catch (Exception e) {
			GameLog.errPrintln(String.format("config setAttr failed, class: %s, field: %s", instance.getClass()
					.getName(), attrName));
			throw e;
		}
	}

	/**
	 * 检测文件是否已更新
	 * 
	 * @return
	 */
	public boolean checkUpdate() {
		// 存储文件热加载信息
		File cfgFile = new File(filePath);
		if (cfgFile.exists()) {
			long currModifyTime = cfgFile.lastModified();
			if (currModifyTime != modifyTime) {
				String currFileMd5 = Md5.makeMD5(cfgFile);
				return !currFileMd5.equals(fileMd5);
			}
		}
		return false;
	}

	/**
	 * 检测配置文件有效性
	 * 
	 * @return
	 */
	public boolean checkValid() {
		if (templateList != null) {
			for (BaseTemplate configBase : templateList) {
				if (!configBase.checkValid()) {
					return false;
				}
			}
		}

		if (templateMap != null) {
			for (Map.Entry<Object, BaseTemplate> entry : templateMap.entrySet()) {
				if (!entry.getValue().checkValid()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 获取文件路径
	 * 
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 获取配置列表
	 * 
	 * @return
	 */
	public List<? extends BaseTemplate> getConfigList() {
		return templateList;
	}

	/**
	 * 获取配置表映射
	 * 
	 * @return
	 */
	public Map<Object, ? extends BaseTemplate> getConfigMap() {
		return templateMap;
	}
}
