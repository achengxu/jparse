package com.achengxu.parse.conf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 获取配置资源文件 [公共参数] 信息
 * 
 * @author lin
 */
public class CommonParam {

	public final static int LOAD_PROPERTIES = 1;
	public final static int LOAD_XML = 2;

	private Properties prop;

	private int load_type;

	public CommonParam(String filePath, int type) {
		load_type = type;
		init(filePath);
	}

	/**
	 * 加载配置
	 * 
	 * @parama filePath
	 */
	private void init(String filePath) {
		FileInputStream fis = null;
		prop = new Properties();
		try {
			fis = new FileInputStream(filePath);
			if (load_type == CommonParam.LOAD_PROPERTIES) {
				prop.load(fis);
			} else if (load_type == CommonParam.LOAD_XML) {
				prop.loadFromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getString(String key) {
		if (null == key || key.equals("") || key.equals("null")) {
			return "";
		}
		String result = "";
		try {
			result = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}