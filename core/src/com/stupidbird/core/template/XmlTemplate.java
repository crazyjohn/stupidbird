package com.stupidbird.core.template;

import org.apache.commons.configuration.XMLConfiguration;

@SuppressWarnings("serial")
public class XmlTemplate extends XMLConfiguration {
	/**
	 * 从文件构造
	 * 
	 * @param xmlCfg
	 * @throws Exception
	 */
	public XmlTemplate(String xmlCfg) throws Exception {
		super(xmlCfg);
	}
}
