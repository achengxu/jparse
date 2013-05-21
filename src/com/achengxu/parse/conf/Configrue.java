package com.achengxu.parse.conf;

import java.util.HashMap;

import org.xml.sax.Attributes;

public class Configrue {

	public String path;
	public String suffix;
	public boolean enable;

	protected String name = "Configrue";

	public Configrue() {
	}

	public Configrue(String name) {
		this.name = name;
	}

	public void parseXML(Attributes attributes) {
		String qName;
		String value;
		for (int i = 0; i < attributes.getLength(); i++) {
			qName = attributes.getQName(i);
			value = attributes.getValue(i);
			if ("path".equals(qName)) {
				path = value;
			} else if ("enable".equals(qName)) {
				enable = Boolean.valueOf(value);
			} else if ("suffix".equals(qName)) {
				suffix = value;
			}
		}
	}

	public HashMap<String, Object> toMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("path", path);
		map.put("enable", enable);
		map.put("suffix", suffix);
		return map;
	}

	public String toString() {
		return name + ": " + toMap().toString();
	}
}
