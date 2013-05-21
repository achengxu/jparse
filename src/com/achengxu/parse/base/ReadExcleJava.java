package com.achengxu.parse.base;

public final class ReadExcleJava extends com.achengxu.parse.base.ReadExcleBase {

	private String className;
	private String inputPath;
	private boolean parse;

	public final void setInputPath(String input) {
		inputPath = input;
	}

	public final String getInputPath() {
		return inputPath;
	}

	public final String getClassName() {
		return className;
	}

	public final void setClassName(String className) {
		this.className = className;
	}

	public final boolean isParse() {
		return parse;
	}

	public final void setParse(boolean parse) {
		this.parse = parse;
	}

	public ReadExcleJava(String input, String output, String className) {
		this.inputPath = input;
		this.className = className;
		parse = init();
	}

	private final boolean init() {
		boolean flag = false;
		try {
			this.serializDataToFile();
			this.serializFileToData();
			flag = true;
		} catch (Exception e) {
			System.out.println(this.inputPath);
			e.printStackTrace();
			System.exit(1);
		}
		return flag;
	}

}
