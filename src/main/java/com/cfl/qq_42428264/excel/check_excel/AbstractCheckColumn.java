package com.cfl.qq_42428264.excel.check_excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查excel的格式 抽象检测类
 * @ClassName AbstractCheckColumn
 * @Description TODO
 * @Author msi
 * @Date 2019/8/16 23:47
 */
public abstract class AbstractCheckColumn {

	/**
	 * 检查 excel的数据是否满足条件
	 * @Param []
	 * @Return java.util.Map<java.lang.String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                               java.lang.Object>
	 */
	public Map<String, Object> checkExcelOk (CommonsMultipartFile file, Object importExcel) {
		// 创建一个结果集合
		Map<String, Object> map = new HashMap<>();
		// list有序，添加所有数据到一个对象集合。
		List<Object> tableList = new ArrayList<>();
		// 记录错误信息（1 true， 0 false） 根据错误信息，前端进行不同的设置
		List<String> error = new ArrayList<>();

		Class clazz = importExcel.getClass();
		// 通过输入流获取excel对象
		InputStream inputStream = null;
		XSSFWorkbook wb = null;//由输入流文件得到工作簿对象


		try {
			inputStream = file.getInputStream();
			wb = new XSSFWorkbook(inputStream);
			// 查看有多少个sheet 默认为1个（从0 开始）
			int number = wb.getNumberOfSheets();
			System.out.println("number = " + number);

			//获取第一个sheet
			XSSFSheet sheet = wb.getSheetAt(0);
			// 需要检查格式的列索引
			Integer[] checkColumnNumber = getCheckColumnNumber(sheet);

			// 获取有多少行（0 开始）, 根据表头得到理应多少列
			int lastRowNum = sheet.getLastRowNum();
			System.out.println("lastRowNum = " + lastRowNum);
			int lastCellNum = sheet.getRow(0).getLastCellNum();
			System.out.println("lastCellNum = " + lastCellNum);

			String str = sheet.getRow(0).getCell(0).getStringCellValue();
			System.out.println("str = " + str);



			// 行对象，列对象 都是从0开始
			Row row = null;
			Cell cell = null;

			// 开始解析正为数据（0是表头）
			for (int i = 1; i <= lastRowNum; i++) {
				// 记录错误信息
				StringBuilder sb = new StringBuilder();
				// 获取当前行
				row = sheet.getRow(i);
				// 创建对象 利用反射给属性赋值
				Object obj = null;
				try {
					obj = (Object) clazz.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				// 获取对象属性
				Field[] fields = clazz.getDeclaredFields();

				// 解析单元格
				for (int j = 0; j < lastCellNum; j++) {
					// 记录单元格是否匹配格式 默认可以 1
					int sbValue = 1;
					cell = row.getCell(j);

					// 设置单元格解析格式
					if (cell != null) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
					} else { // 空着的就跳过循环
						if (checkColumnNumber[j] == 1) {// 需要检查却没填就需要记录
							sb.append(0);
						} else {
							sb.append(1);
						}
						continue;
					}
					// 获取单元格value
					String cellValue = cell.getStringCellValue();

					// checkColumnNumber数组的索引 j 对应的元素值=1 时就要进行格式检查
					if (checkColumnNumber[j] == 1) {  // 这里调用一个具体检擦的方法
						System.out.println(cellValue + ":需要检查");
						// 检查格式(并不是所有列都要检查)
						cellValue = checkColumnFormat(j, sb, cellValue);
					} else { // 当不检查时就默认格式正确
						sb.append(sbValue);
					}
					try {
						// 给集合中的对象赋值
						fields[j].setAccessible(true);
						fields[j].set(obj, cellValue == null ? "" : cellValue);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					System.out.println("第" + i + "行 , 第" + j + "列:" + cellValue);
				}
				error.add(sb.toString());
				tableList.add(obj);
			}

			for (int i = 0; i < error.size(); i++) {
				System.out.println(error.get(i));
			}
			// 错误信息
			map.put("error", error);
			// 集合
			map.put("tableList", tableList);
			return map;
		} catch (IOException e) {
			System.err.printf("AbstractCheckColumn的方法checkExcelOk的IO出错");
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 获取需要检查的表格列的索引
	 * @param sheet
	 * @return
	 */
	public abstract Integer[] getCheckColumnNumber(XSSFSheet sheet);

	/**
	 * 判断excel表格符合规则否
	 * @param index 检查的索引
	 * @param sb    记录格式正确还是不正确，1 正确， 0 不正确
	 * @param cellValue 当前单元格的值
	 * @return
	 */
	public abstract String checkColumnFormat(Integer index, StringBuilder sb, String cellValue);


}
