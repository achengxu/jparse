package com.achengxu.parse.demo;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class UpdateExcel {
	public static void main(String args[]) {
		try {
			// Excel获得文件
			Workbook wb = Workbook.getWorkbook(new File("conf/test.xls"));
			// 打开一个文件的副本，并且指定数据写回到原文件
			WritableWorkbook book = Workbook.createWorkbook(new File("conf/test.xls"), wb);
			// 添加一个工作表
			WritableSheet sheet = book.createSheet(" 第二页 ", 1);
			sheet.addCell(new Label(0, 0, " 第二页的测试数据 "));
			book.write();
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}