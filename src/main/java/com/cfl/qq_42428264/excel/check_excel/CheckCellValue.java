package com.cfl.qq_42428264.excel.check_excel;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Excel文件检查表格格式
 * @ClassName CheckCellValue
 * @Description TODO
 * @Author cfl
 * @Date 2019/8/14 17:51
 * @Version 1.0
 */
public class CheckCellValue {
	// 表头
	private static String[] Fields;
	// 表头对应的 字段
	private static String[] Attributes;

	public String[] getFields() {
		return Fields;
	}

	public void setFields(String[] fields) {
		this.Fields = fields;
	}



	/**
	 * 判断sheet对象的第一行和配置文件进行比较
	 * 得到需要判断的列下标数组
	 * @param sheet
	 * @return 返回一个Integer数组元素默认值0，即不检查，元素值=1的索引列，需要检查格式
	 */
	public static Integer[] getCheckColumnNumber(XSSFSheet sheet){
		Row row = sheet.getRow(0);
		String cellValue = null;
		// 多少列 1开始
		int cellNumber = row.getLastCellNum();
		// 配置文件配置的需要检查的字段
		String[] fields = null;//CheckWeddingXMLUtil.getCheckField();
		// 初始一个长度为 列总长的数组， 元素默认值为0即不检查，元素=1则需要检查。避免多次嵌套循环，提高性能
		Integer[] indexs = new Integer[cellNumber];

		for (int i = 0; i < indexs.length; i++) {
			indexs[i] = 0;
		}

		// 外小内大循环：设置拦截
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < cellNumber; j++) {
				cellValue = row.getCell(j).getStringCellValue();
				// 相等就记录下标
				if (fields[i].equalsIgnoreCase(cellValue)) {
					indexs[j] = 1;
					break;
				}
			}
		}

		return indexs;
	}



	/**
	 * 判断登录部门 和 excel 的部门名称
	 *
	 * @Param [departmentName, excelDepartmentName]
	 * @Return void
	 */
	public static Integer checkDepartmentName (String departmentName, String excelDepartmentName) {
		if (departmentName.equalsIgnoreCase(excelDepartmentName)) {
			return 1;
		}
		System.out.println("单位不正确");
		return 0;
	}
	
	/**
	 * 判断时间格式
	 * 设置错误信息
	 * @Param [sbTime]
	 * @Return java.lang.String 返回之前的时间格式
	 */
	public static String checkTime (StringBuilder sb, String sbTime) {
		SimpleDateFormat sdf = null;
		String[] strs = null;
		Calendar calendar = Calendar.getInstance();
		// 判断时间字符串时间 “2019/08/14”
		if (sbTime.indexOf("/") != -1) {
			sdf = new SimpleDateFormat("yyyy/MM/dd");
			strs = sbTime.split("/");
			return myCheckTime(sb, sbTime, strs, sdf);
		} else if (sbTime.indexOf("-") != -1) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			strs = sbTime.split("-");
			return myCheckTime(sb, sbTime, strs, sdf);
		} else if (sbTime.indexOf(".") != -1) {
			sdf = new SimpleDateFormat("yyyy.MM.dd");
			strs = sbTime.split("\\.");
			return myCheckTime(sb, sbTime, strs, sdf);
		} else if (sbTime.matches("[0-9]+")) {  // excel中的 yyyy/MM/dd 是1900年的天
			Date date = null;
			calendar = new GregorianCalendar(1900, 0, -1);
			Date d = calendar.getTime();
			date = DateUtils.addDays(d, Integer.valueOf(sbTime));
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int day = c.get(Calendar.DAY_OF_MONTH);
			String time = year + "/" + month + "/" + day;
			strs = new String[]{year+"", month+"", day+""};
			sdf = new SimpleDateFormat("yyyy/MM/dd");
			return myCheckTime(sb, time, strs, sdf);
		}
		// 不满足时间格式，添加错误信息
		sb.append(0);
		return sbTime;
	}
	
	/**
	 * 判断时间的辅助方法
	 *
	 * @Param [time, strs, sdf]
	 * @Return java.lang.Integer
	 */
	private static String myCheckTime (StringBuilder sb, String sbTime, String[] strs, SimpleDateFormat sdf) {
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		// 时间不全
		if (strs.length != 3) {
			sb.append(0);
			return sbTime;
		}
		Integer month = Integer.parseInt(strs[1]);
		// 月错误
		if (month < 1 || month > 12) {
			sb.append(0);
			return sbTime;
		}
		Integer day = Integer.parseInt(strs[2]);
		try {
			date = sdf.parse(sbTime);
		} catch (ParseException e) {
			sb.append(0);
			return sbTime;
		}
		calendar.setTime(date);
		
		int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("max = " + max);
		// 月最大天
		int maxDay = calendar.getMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("maxDay = " + maxDay);
		
		if (day < 0 && day > maxDay) {
			sb.append(0);
			return sbTime;
		}
		sb.append(1);
		return sbTime;
	}
	
	
	/**
	 * 判断证件号格式
	 * @Param [departmentName, excelDepartmentName]
	 * @Return void
	 */
	public static Integer checkIdnumber (String idNumber) {
		String regex = "\\d{15}(\\d{2}[0-9xX])?";
		if (idNumber.matches(regex)) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * 判断申报中的规模是否是数字
	 * @Param [scale]
	 * @Return java.lang.Integer
	 */
	public static Integer checkScale (String scale) {
		if (scale != null && scale.matches("\\d*")) {
			return 1;
		}
		return 0;
	}


	public static void main (String[] args) {
		CheckCellValue c = new CheckCellValue();
		String str = "10025";
		Integer i = 100;
		CheckCellValue.checkScale(str);
		System.out.println("i = " + i);
	}
}
