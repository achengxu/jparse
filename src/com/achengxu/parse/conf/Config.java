package com.achengxu.parse.conf;

import java.util.HashMap;
import java.util.Map;

public final class Config {

	public final static Map<String, String> map = new HashMap<String, String>();

	private final static void parse(CommonParam param) {
		Config.map.put(Constant.IN_XLS_PATH, param.getString(Constant.IN_XLS_PATH));
		Config.map.put(Constant.IN_XLS_SUFFIX, param.getString(Constant.IN_XLS_SUFFIX));

		Config.map.put(Constant.OUT_BEAN_ENABLE, param.getString(Constant.OUT_BEAN_ENABLE));
		Config.map.put(Constant.OUT_BEAN_PATH, param.getString(Constant.OUT_BEAN_PATH));
		Config.map.put(Constant.OUT_BEAN_SUFFIX, param.getString(Constant.OUT_BEAN_SUFFIX));
		Config.map.put(Constant.OUT_BEAN_ECHO, param.getString(Constant.OUT_BEAN_ECHO));

		Config.map.put(Constant.OUT_XML_ENABLE, param.getString(Constant.OUT_XML_ENABLE));
		Config.map.put(Constant.OUT_XML_PATH, param.getString(Constant.OUT_XML_PATH));
		Config.map.put(Constant.OUT_XML_SUFFIX, param.getString(Constant.OUT_XML_SUFFIX));
		Config.map.put(Constant.OUT_XML_ECHO, param.getString(Constant.OUT_XML_ECHO));

		Config.map.put(Constant.OUT_JSON_ENABLE, param.getString(Constant.OUT_JSON_ENABLE));
		Config.map.put(Constant.OUT_JSON_PATH, param.getString(Constant.OUT_JSON_PATH));
		Config.map.put(Constant.OUT_JSON_SUFFIX, param.getString(Constant.OUT_JSON_SUFFIX));
		Config.map.put(Constant.OUT_JSON_ECHO, param.getString(Constant.OUT_JSON_ECHO));
	}

	public final static void parseProperties(String filePath) {
		CommonParam param = new CommonParam(filePath, CommonParam.LOAD_PROPERTIES);
		Config.parse(param);
	}

	public final static void parseXML(String filePath) {
		CommonParam param = new CommonParam(filePath, CommonParam.LOAD_XML);
		Config.parse(param);
	}

}
