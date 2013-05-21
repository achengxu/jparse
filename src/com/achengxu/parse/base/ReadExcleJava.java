package com.achengxu.parse.base;

public class ReadExcleJava extends com.achengxu.parse.base.ReadExcleBase {

	private String className;
	private String inputPath;
	private boolean parse;

	@Override
	public void setInputPath(String input) {
		inputPath = input;
	}

	@Override
	public String getInputPath() {
		return inputPath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the parse
	 */
	public boolean isParse() {
		return parse;
	}

	/**
	 * @param parse
	 *            the parse to set
	 */
	public void setParse(boolean parse) {
		this.parse = parse;
	}

	public ReadExcleJava(String input, String output, String className) {
		this.inputPath = input;
		this.className = className;
		parse = init();
	}

	private boolean init() {
		boolean flag = false;
		try {
			super.parse();
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
