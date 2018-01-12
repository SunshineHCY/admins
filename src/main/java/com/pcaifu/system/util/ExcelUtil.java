package com.pcaifu.system.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * excel工具类
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2015  
 * Company:		拍拍贷
 * Author:		liuqinghua
 * Version:		1.0  
 * Create at:	2015年9月13日 上午8:51:41  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class ExcelUtil {

	/**
	 * 导出excel
	 * 
	 * @param fileName
	 *            EXCEL文件名称
	 * @param Title
	 *            EXCEL文件第一行列标题集合
	 * @param listContent
	 *            EXCEL文件正文数据集合
	 * @return
	 */
	public static void exportExcel(String fileName, LinkedHashMap<String, String> title, List<Object> listContent)
			throws Exception {
		// 以下开始输出到EXCEL
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getResponse();
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			// 设定输出文件头
			// response.setContentType("application/msexcel");// 定义输出类型
			response.setContentType("application/vnd.ms-excel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);

			/** **********创建工作表************ */

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */

			Set<String> columns = title.keySet();
			Map<String, Integer> columnIndex = new HashMap<String, Integer>();
			int k = 0;
			for (String s : columns) {
				columnIndex.put(s, k);
				sheet.addCell(new Label(k, 0, title.get(s), wcf_center));
				k++;
			}
			/** ***************以下是EXCEL正文数据********************* */
			Field[] fields = null;
			int i = 1;

			for (Object obj : listContent) {
				fields = obj.getClass().getDeclaredFields();
				for (Field v : fields) {
					if (title.keySet().contains(v.getName())) {
						v.setAccessible(true);
						Object va = v.get(obj);
						if (va == null) {
							va = "";
						}
						sheet.addCell(new Label(columnIndex.get(v.getName()), i, va.toString(), wcf_left));
					}
				}
				i++;
			}
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			throw e;
		}
	}

	public static void exportExcel(String fileName, LinkedHashMap<String, String> totalTitle,
			LinkedHashMap<String, String> title, List<Object> listContent) throws Exception {
		// 以下开始输出到EXCEL
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getResponse();
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			// 设定输出文件头
			// response.setContentType("application/msexcel");// 定义输出类型
			response.setContentType("application/vnd.ms-excel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);

			/** **********创建工作表************ */

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL合计行内容********************* */
			Set<String> col = totalTitle.keySet();
			int j = 0;
			for (String s : col) {
				sheet.addCell(new Label(j, 0, s, wcf_center));
				j += 1;
				sheet.addCell(new Label(j, 0, totalTitle.get(s), wcf_center));
				j++;
			}
			/** ***************以下是EXCEL第一行列标题********************* */

			Set<String> columns = title.keySet();
			Map<String, Integer> columnIndex = new HashMap<String, Integer>();
			int k = 0;
			for (String s : columns) {
				columnIndex.put(s, k);
				sheet.addCell(new Label(k, 2, title.get(s), wcf_center));
				k++;
			}
			/** ***************以下是EXCEL正文数据********************* */
			Field[] fields = null;

			int i = 3;

			for (Object obj : listContent) {
				fields = obj.getClass().getDeclaredFields();
				for (Field v : fields) {
					if (title.keySet().contains(v.getName())) {
						v.setAccessible(true);
						Object va = v.get(obj);
						if (va == null) {
							va = "";
						}
						sheet.addCell(new Label(columnIndex.get(v.getName()), i, va.toString(), wcf_left));
					}
				}
				i++;
			}
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 导出excel
	 * 
	 * @param fileName
	 *            EXCEL文件名称
	 * @param Title
	 *            EXCEL文件第一行列标题集合
	 * @param listContent
	 *            EXCEL文件正文数据集合
	 * @return
	 */
	public static void exportExcel(String fileName, ExcelSheetDTO... sheets) throws Exception {
		if (sheets.length <= 0) {
			throw new Exception("excel 至少应有一个 ExcelSheetDTO 参数");
		}

		// 以下开始输出到EXCEL
		WritableWorkbook workbook = null;
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getResponse();
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			// 设定输出文件头
			// response.setContentType("application/msexcel");// 定义输出类型
			response.setContentType("application/vnd.ms-excel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********创建工作簿************ */
			workbook = Workbook.createWorkbook(os);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			int count = sheets.length;
			for (int j = 0; j < count; j++) {
				/** **********创建工作表************ */
				WritableSheet sheet = workbook.createSheet("Sheet" + (j + 1), j);

				/** **********设置纵横打印（默认为纵打）、打印纸***************** */
				jxl.SheetSettings sheetset = sheet.getSettings();
				sheetset.setProtected(false);

				/** ***************以下是EXCEL开头大标题，暂时省略********************* */
				// sheet.mergeCells(0, 0, colWidth, 0);
				// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
				/** ***************以下是EXCEL第一行列标题********************* */
				Map<String, String> title = sheets[j].getTitle();
				List<Object> listContent = sheets[j].getDatas();

				Set<String> columns = title.keySet();
				Map<String, Integer> columnIndex = new HashMap<String, Integer>();
				int k = 0;
				for (String s : columns) {
					columnIndex.put(s, k);
					sheet.addCell(new Label(k, 0, title.get(s), wcf_center));
					k++;
				}
				/** ***************以下是EXCEL正文数据********************* */
				Field[] fields = null;
				int i = 1;

				for (Object obj : listContent) {
					fields = obj.getClass().getDeclaredFields();
					for (Field v : fields) {
						if (title.keySet().contains(v.getName())) {
							v.setAccessible(true);
							Object va = v.get(obj);
							if (va == null) {
								va = "";
							}
							sheet.addCell(new Label(columnIndex.get(v.getName()), i, va.toString(), wcf_left));
						}
					}
					i++;
				}

			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (workbook != null) {
				try {
					workbook.write();
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
