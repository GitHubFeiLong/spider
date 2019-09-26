package com.cfl.qq_42428264.excel.export;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

/**
 * 导出集合数据  到excel文件
 *
 * @ClassName projectExportExcel
 * @Description TODO
 * @Author cfl
 * @Date 2019/8/6 19:35
 * @Version 1.0
 */
public class ExportExcel {
	// 日志
	private static final Logger logger = LoggerFactory.getLogger(ExportExcel.class);
	// 表头
	private static String[] fields;
	// 表头对应的 字段
	private static String[] attributes;

	/**
	 * 测试
	 *
	 * @Param [args]
	 * @Return void
	 */
	public static void main(String[] args) {
//		String fileName = ProjectExportExcel.setFileName("xxx.aa");
//		System.out.println("fileName = " + fileName);

//		ExportExcel.setFilesAndAttributes(ProjectXMLUtil.class);
//		System.out.println(Arrays.toString(ExportExcel.fields));
//		System.out.println(Arrays.toString(ExportExcel.attributes));
//		setFilesAndAttributes(ProjectXMLUtil.class);
//		System.out.println(Arrays.toString(ExportExcel.fields));
//		System.out.println(Arrays.toString(ExportExcel.attributes));


	}

	/**
	 * 写出Excel文件
	 * fileName:文件下载到客户端的文件名
	 * clazz： 用来设置fields 和 attributes属性
	 * list : 需要导出的实体数据
	 *
	 * @Param [fileName：写出文件名名字；clazz：需要设置属性的值， list 需要写出的数据]
	 * @Return void
	 */
	public static <T> void writeExcel(String fileName, Class clazz, List<T> list) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

		// 1.设置属性 files 和 attributes 属性
		ExportExcel.setFilesAndAttributes(clazz);

		// 2.创建表格对象
		HSSFWorkbook wb = ExportExcel.createHSSFWorkbook(list, fields, attributes);

		// 3.设置编码格式，响应头
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			fileName = setFileName(fileName);
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
			wb.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("使用response写出" + fileName + "文件失败");
			e.printStackTrace();
		}
	}

	/**
	 * 设置导出文件名
	 *
	 * @Param [fileName]
	 * @Return java.lang.String
	 */
	private static String setFileName(String fileName) {
		// 默认 xls 格式
		if (fileName.indexOf(".") == -1) {
			fileName += ".xls";
		} else { // 没有格式
			if (fileName.indexOf(".") == fileName.length() - 1) {
				fileName += "xls";
			} else if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
				System.err.println("警告：导出文件错误：\"." + fileName.split("\\.")[1] + "\" 不是正确的excel格式：.xls, .xlsx");
				fileName = fileName.split("\\.")[0] + ".xls";
			}
		}
		return fileName;
	}

	/**
	 * 返回HSSFWorkbook 对象 createHSSFWorkbook
	 *
	 * @Param [list, fields, attributes]
	 * @Return org.apache.poi.hssf.usermodel.HSSFWorkbook
	 */
	private static <T> HSSFWorkbook createHSSFWorkbook(List<T> list, String[] fields, String[] attributes) {
		//创建HSSFWorkbook对象
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建HSSFSheet对象
		HSSFSheet sheet = wb.createSheet("sheet0");

		//创建HSSFRow对象 第一行
		HSSFRow row = sheet.createRow(0);

		int heads_length = fields.length;

		// 创建行头
		createHead(row, fields);

		// 创建内容实体
		createBody(wb, sheet, list, attributes);

		// 写出excel到硬盘
//		writeExcelTOHardDisk(wb);

		return wb;
	}


	/**
	 * 设置成员变量 fields 和attributes
	 *
	 * @Param [clazz]
	 * @Return void
	 */
	private static void setFilesAndAttributes(Class clazz) {
		Object obj = null;
		try {
			Constructor<T> c = clazz.getConstructor();
			obj = c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Field[] fies = clazz.getDeclaredFields();
		for (int i = 0; i < fies.length; i++) {
			try {
				// 如果 accessible 标志被设置为true，那么反射对象在使用的时候，不会去检查Java语言权限控制（private之类的）
				fies[i].setAccessible(true);
				// 设置成员变量
				if (fies[i].getName().equalsIgnoreCase("fields")) {
					fields = (String[]) fies[i].get(obj);
				} else {
					attributes = (String[]) fies[i].get(obj);
				}
			} catch (IllegalAccessException e) {
				logger.error("设置表格头部和头部对应的字段失败");
				e.printStackTrace();
			}

		}
	}


	/**
	 * 创建 Excel表头
	 *
	 * @Param [row, heads] row:第一行对象; heads:头对象
	 * @Return void
	 */
	private static void createHead(HSSFRow row, String[] fields) {
		// 创建行头
		for (int i = 0; i < fields.length; i++) {
			//创建HSSFCell对象 表头
			HSSFCell cell = row.createCell(i);
			//设置表头值
			cell.setCellValue(fields[i]);
		}
	}

	/**
	 * 根据反射创建excel 的实体
	 *
	 * @Param []
	 * @Return void
	 */
	private static <T> void createBody(HSSFWorkbook wb, HSSFSheet sheet, List<T> list, String[] attributes) {
		HSSFRow row = null;
		HSSFCell c = null;
		T p = null;
		Field[] fields = null;
		// 新建的sheet 从第一行创建起
		int row_index = 0;
		// 创建行：第二行开始
		for (int i = 1; i <= list.size(); i++) {
			row_index ++;
			// 60000 换一个sheet
			if(i % 60000 == 0){
				System.gc();
				row_index = 0;
				System.out.println(i);
				sheet = wb.createSheet("sheet"+ (i / 60000 + 1));
			}
			row = sheet.createRow(row_index);
			// 当前行对象
			p = list.get(i - 1);
			// 对象所有属性
			fields = p.getClass().getDeclaredFields();
			// 创建一行中的所有列
			try {
				for (int j = 0; j < fields.length; j++) {
					for (int k = 0; k < attributes.length; k++) {
						// 配置文件字段和对象字段相比较，相同就执行方法
						if (fields[j].getName().equalsIgnoreCase(attributes[k])) {

							// 创建HSSFCell对象 表头
							c = row.createCell(j);
							fields[j].setAccessible(true);
							if (fields[j].get(p) == null) {
								c.setCellValue("");
							} else {
								c.setCellValue(fields[j].get(p).toString());
							}

							break;
						}
					}
				}
			} catch (IllegalAccessException e) {
				logger.error("导出Excel，在设置实体部分时出错！");
				e.printStackTrace();
			}
		}
	}


	/**
	 * 写出excel到硬盘
	 *
	 * @Param []
	 * @Return void
	 */
	private static void writeExcelTOHardDisk(HSSFWorkbook wb) {
		FileOutputStream output = null;
		try {
			output = new FileOutputStream("d:\\AA/workbook.xls");
			wb.write(output);
			output.flush();
			logger.info("成功");
		} catch (FileNotFoundException e) {
			logger.info("失败");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
