package com.achengxu.parse.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.achengxu.parse.conf.Config;
import com.achengxu.parse.conf.Constant;

public abstract class ReadExcleBase implements IParse {

	protected HashMap<String, String> map = new HashMap<String, String>();
	protected ArrayList<String> values = new ArrayList<String>();
	protected ArrayList<String> keys = new ArrayList<String>();
	protected StringBuffer xml = new StringBuffer();
	protected JSONArray jsonArray = new JSONArray();

	private int startNum = 1;

	public abstract void setInputPath(String input);

	public abstract String getInputPath();

	public abstract String getClassName();

	public abstract void setClassName(String className);

	public List<String> getData() {
		return values;
	}

	public Object[] parse() throws BiffException, IOException, Exception {
		Workbook book = Workbook.getWorkbook(new File(getInputPath()));
		// int sheetNum = book.getNumberOfSheets();
		List<Object> list = new ArrayList<Object>();
		int start = getInputPath().indexOf('.');
		String outXmlPath = getInputPath().substring(start + 1);
		int end = outXmlPath.indexOf('.');
		outXmlPath = outXmlPath.substring(0, end);
		for (int a = 0; a < 1; a++) {
			Sheet sheet = book.getSheet(a);
			// 获得第一个工作表对象
			int rows = sheet.getRows();
			int colums = sheet.getColumns();
			xml.delete(0, xml.length());
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			xml.append("\n<info>");

			for (int i = startNum; i <= startNum; i++) {
				for (int j = 0; j < colums; j++) {
					String value = sheet.getCell(j, i).getContents().trim();
					if ("".equals(value))
						continue;
					if (!keys.contains(value))
						keys.add(value);
				}
			}
			for (int i = startNum + 1; i < rows; i++) {
				for (int j = 0; j < colums; j++) {
					String value = sheet.getCell(j, i).getContents().trim();
					if ("".equals(value))
						continue;
					values.add(value);
				}
				if (values.size() > 0) {
					for (int p = 0; p < keys.size(); p++) {
						String key = keys.get(p);
						if (p >= getData().size()) {
							continue;
						}
						String value = getData().get(p);
						map.put(key, value);
					}
					xml.append("\n\t<" + outXmlPath + ">");
					list.add(toData(getClassName()));
					values.clear();
					xml.append("\t</" + outXmlPath + ">\n");
				}
			}
		}
		xml.append("</info>");
		book.close();
		return list.toArray();
	}

	public void serializDataToFile() throws Exception {
		if (Config.map.get(Constant.OUT_XML_ENABLE).equals("true")) {
			newXml();
		}
		if (Config.map.get(Constant.OUT_BEAN_ENABLE).equals("true")) {
			newBean();
		}
		if (Config.map.get(Constant.OUT_JSON_ENABLE).equals("true")) {
			newJson();
		}
	}

	public void serializFileToData() throws FileNotFoundException, IOException, ClassNotFoundException {
		if (!Config.map.get(Constant.OUT_BEAN_ECHO).equals("true"))
			return;
		String xlsPath = Config.map.get(Constant.IN_XLS_PATH);
		String filePath = getInputPath();
		String pathName = Config.map.get(Constant.OUT_BEAN_PATH) + filePath.substring(xlsPath.length(), filePath.lastIndexOf('.')) + Config.map.get(Constant.OUT_BEAN_SUFFIX);

		File file = new File(pathName);
		if (file.exists()) {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathName));
			Object[] items = (Object[]) in.readObject();
			for (Object o : items) {
				System.out.println(o);
			}
			in.close();
		} else {
			System.out.println("error: 文件不存在 : " + getInputPath());
		}
	}

	public File mkdir(String pathName) throws IOException {
		File file = new File(pathName);
		if (!file.getParentFile().exists()) {
			System.out.println("目标文件所在路径不存在，准备创建。。。");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目录文件所在的目录失败！");
			} else {
				System.out.println("成功创建文件夹:" + file.getParentFile());
			}
		}
		if (!file.exists()) {
			file.createNewFile();
			System.out.println("成功创建新文件: " + file.getName());
		} else {
			file.createNewFile();
		}
		return file;
	}

	public void newBean() throws IOException, BiffException, Exception {
		if (Config.map.get(Constant.OUT_BEAN_ENABLE).equals("true")) {
			String xlsPath = Config.map.get(Constant.IN_XLS_PATH);
			String filePath = getInputPath();
			String pathName = Config.map.get(Constant.OUT_BEAN_PATH) + filePath.substring(xlsPath.length(), filePath.lastIndexOf('.')) + Config.map.get(Constant.OUT_BEAN_SUFFIX);
			mkdir(pathName);
			FileOutputStream fs = new FileOutputStream(pathName);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			Object[] obj = parse();
			os.writeObject(obj);
			os.flush();
			fs.close();
			os.close();
		}
	}

	public void newXml() throws IOException {
		if (Config.map.get(Constant.OUT_XML_ENABLE).equals("true")) {
			String xmlData = xml.toString();
			int start = getInputPath().indexOf('.');
			int end = getInputPath().lastIndexOf('.');
			String outXmlPath = getInputPath().substring(start + 1, end).replace('.', '_');
			String pathName = Config.map.get(Constant.OUT_XML_PATH) + "/" + outXmlPath + Config.map.get(Constant.OUT_XML_SUFFIX);
			File file = mkdir(pathName);
			FileWriter fw = new FileWriter(file);
			fw.write(xmlData, 0, xmlData.length());
			fw.flush();
			fw.close();
			if (Config.map.get(Constant.OUT_XML_ECHO).equals("true")) {
				System.out.println(xmlData);
			}
		}
	}

	public void newJson() throws IOException {
		if (Config.map.get(Constant.OUT_JSON_ENABLE).equals("true")) {
			String jsonData = jsonArray.toString();
			int start = getInputPath().indexOf('.');
			int end = getInputPath().lastIndexOf('.');
			String outXmlPath = getInputPath().substring(start + 1, end).replace('.', '_');
			String pathName = Config.map.get(Constant.OUT_JSON_PATH) + "/" + outXmlPath + Config.map.get(Constant.OUT_JSON_SUFFIX);
			File file = mkdir(pathName);
			FileWriter fw = new FileWriter(file);
			fw.write(jsonData, 0, jsonData.length());
			fw.flush();
			fw.close();
			if (Config.map.get(Constant.OUT_JSON_ECHO).equals("true")) {
				System.out.println(jsonData);
			}
		}
	}

	public Object toData(String className) throws Exception {
		Class<?> ownerClass = Class.forName(className);
		Object data = ownerClass.newInstance();
		if (!map.isEmpty()) {
			JSONObject jsonObject = new JSONObject();
			for (Entry<String, String> entry : map.entrySet()) {
				String k = entry.getKey();
				String v = entry.getValue();

				xml.append("\n\t\t<" + k + ">" + v + "</" + k + ">\n");
				Field field = ownerClass.getDeclaredField(k);
				field.setAccessible(true);
				Class<?> type = field.getType();
				String typeName = type.getName();
				if ("int".equals(typeName)) {
					field.set(data, Integer.parseInt(v));
					jsonObject.put(k, Integer.parseInt(v));
				} else if ("float".equals(typeName)) {
					field.set(data, Float.parseFloat(v));
					jsonObject.put(k, Float.parseFloat(v));
				} else if ("java.lang.String".equals(typeName)) {
					field.set(data, v);
					jsonObject.put(k, v);
				} else if ("java.util.ArrayList".equals(typeName)) {
					String values[] = v.split(";");
					ArrayList<Integer> items = new ArrayList<Integer>();
					JSONArray jsonList = new JSONArray();
					for (String value : values) {
						JSONObject jsonItem = new JSONObject();
						items.add(Integer.parseInt(value));
						jsonItem.put("item", Integer.parseInt(value));
						jsonList.put(jsonItem);
					}
					field.set(data, items);
					jsonObject.put(k, jsonList);
				}
			}
			jsonArray.put(jsonObject);
		}
		return data;
	}

	/**
	 * 执行某个类的静态方法 java反射机制
	 * 
	 * @param className
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception {
		int length = 0;
		if (null != args) {
			length = args.length;
		}
		Class<?> ownerClass = Class.forName(className);
		Class<?>[] argsClass = new Class[length];
		for (int i = 0, j = length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(null, args);
	}
}
