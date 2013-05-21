package com.achengxu.parse.base;

public abstract interface IParse {

	public abstract Object[] parse() throws Exception;

	public abstract void serializDataToFile() throws Exception;

	public abstract void serializFileToData() throws Exception;

	public abstract void setInputPath(String input);

	public abstract String getInputPath();

	public abstract String getClassName();

	public abstract void setClassName(String className);

}
