package com.cfl.qq_42428264.excel.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

/**
 * 导出集合数据  到excel文件
 * excel文件：
 *      文件名，表头名字， 实体数据。
 *  1.需要设置表头的文件名，=。
 *  2.需要设置表头的字段，这里就是fields[] 数组，attributes[] 就是存放个表头对应对象的字段
 *    导出集合到excel中，通常集合中对象的所有属性不全部导出到excel，例如id字段。
 *  3.需要设置实体数据。
 *  注意： fields [] 和 attributes[] 遵循一一对应，即表头的一个格子对应对象的一个字段。
 *  另外，这里使用的是反射做处理的，所以约定优于配置，即集合中的实体对象需要和attributes[]一样的顺序(只许多，不许少)
 *  例如：
 *      fields={"序号","姓名","年龄"}
 *      attributes={"id","name","age"}
 * @ClassName projectExportExcel
 * @Description TODO
 * @Author cfl
 * @Date 2019/8/6 19:35
 * @Version 1.0 : 利用反射结合POI.jar 中HSSFWorkbook对象导出Excel。
 * @Version 1.1 （19/10/2）: 一、新增 成员变量 SUFFIX（文件后缀）、SIZE（判断大小，选择不同的Workbook对象）、MEMORY_NUM（内存中保留数据条数，其余写入硬盘临时文件）、
 *                  SHEET_SIZE（一个工作薄sheet多少条数据）；
 *              二、根据接口编程，根据里氏代换原则动态选择 HSSFWorkbook，XSSFWorkbook, SXSSFWorkbook对象；
 *              三、完善代码，使其不再依赖pojo的声明字段的顺序；
 *              四、删除 void setFilesAndAttributes(Class clazz) 方法；
 *              五、新增 void setSHEET_SIZE(int sheet_size)方法；
 */
public class ExportExcel {
	// 日志
	private static final Logger logger = LoggerFactory.getLogger(ExportExcel.class);
	// 表头
	private static String[] Fields;
	// 表头对应的 字段
	private static String[] Attributes;
	// excel文件格式（.xls,.xlsx）
	private static String SUFFIX = ".xlsx";
	// 导出时选择不同的工作博对象，小于等于SIZE 创建HSSFWorkbook，大于SIZE创建SXSSFWorkbook
	private final static  int SIZE = 10000;
	// 创建SXSSFWorkbook对象对少条flush
	private final static  int MEMORY_NUM = 1000;
	// 一个工作表有 SHEET_SIZE 数据（加一是因为表头占一行）
	private static  Integer SHEET_SIZE = 100000;


	/**
	 * 设置成员变量 fields 和attributes
	 * @param fileds 表头
	 * @param attributes    表头对应的字段
	 */
	public static void setFilesAndAttributes(String[] fileds, String[] attributes) {
		// 这是复制了一份地址，指向的还是同一个对象，原数组改变，这里也要改变
		//ExportExcel.Fields = fileds;
		//ExportExcel.Attributes = attributes;
		// 深层复制。开辟一块新内存空间
		// 先初始化数组
		int length = fileds.length;
		ExportExcel.Fields = new String[fileds.length];
		ExportExcel.Attributes = new String[attributes.length];
		// 因 fileds，attributes一一对应。
		for(int i = 0; i < length; i++) {
			ExportExcel.Fields[i] = fileds[i];
			ExportExcel.Attributes[i] = attributes[i];
		}
	}

	/**
	 * 根据excel数据的多少，设置一个工作薄有多少条数据
	 * @param sheet_size
	 */
	public static void setSHEET_SIZE(int sheet_size){
		SHEET_SIZE = sheet_size;
	}

	/**
	 * 写出Excel文件
	 * 	fileName:文件下载到客户端的文件名
	 * @param fileName  浏览器下载的excel文件名字
	 * @param list  需要写出的数据
	 * @param <T>
	 */
	public static <T> void writeExcel(String fileName, List<T> list) {
        // 1.判断属性 files 和 attributes 属性是否满足导出excel的基本要求。
        if(!checkFieldsAndAttr()){
            return;
        }
        // 获取请求对象和响应对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

		// 2.创建工作薄对象
		Workbook wb = ExportExcel.createWorkbook(list);
		// 查看内存使用
		System.out.println("创建完成");
		System.err.println("总的内存->" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
		System.err.println("剩余的内存->" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB");
		//响应对象获取输出流，通过输出流写出excel文件
		OutputStream out = null;
		// 3.设置编码格式，响应头
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			// 获取excel名字
			fileName = setFileName(fileName);
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			out = response.getOutputStream();
			// 写出到浏览器
			wb.write(out);
		}catch (RuntimeException e){
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(out!=null){
					out.close();
				}
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检查属性Fields 和 Attributes 是否满足导出excel的要求
	 * @return  满足导出要求，返回true，不满足返回false。
	 */
	private static boolean checkFieldsAndAttr(){
		Boolean flag = true;

		// Fields 和 Attributes不一一对应！
		if(ExportExcel.Fields == null || ExportExcel.Attributes == null){
			flag = false;
			System.err.println("ExportException: 导出excel时的 Fields 和 Attributes 未定义");
		}else if(ExportExcel.Fields.length != ExportExcel.Attributes.length){
			flag = false;
			System.err.println("ExportException: 导出excel时的Fields 和 Attributes 的长度不一致错误");
		}
		return flag;
	}

	/**
	 * 返回 HSSFWorkbook/ XSSFWorkbook/SXSSFWorkbook 对象
	 * 创建Excel文件对象
	 * @Param [list 表格数据, fields, attributes]
	 * @Return org.apache.poi.hssf.usermodel.HSSFWorkbook
	 */
	private static <T> Workbook createWorkbook(List<T> list) {
		//创建Workbook对象
		Workbook wb = null;
		//创建HSSFSheet对象

		if(list.size() <= SIZE){
			// 2003，如果03创建XSSFWorkbook，或者07创建HSSFWorkbook都会出现excel文件打不开。
			wb = new HSSFWorkbook();
			// 修改excel文件格式。
			ExportExcel.SUFFIX = ".xls";
		} else if(list.size() > SIZE){
			// 进行大批量写入操作解决了这个问题。防止内存溢出，构造参数是指内存中多少条flush到磁盘上
			try {
				//ExportExcel.createTempXSSFWorkbook(),
				wb = new SXSSFWorkbook(MEMORY_NUM);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.err.println("创建了SXSSFWorkbook");
		} else {
			// 2007以上，先将其保留
			wb = new XSSFWorkbook();
		}
		// 创建内容实体
		createBody(wb, list);
		// 写出excel到硬盘
//		writeExcelTOHardDisk(wb);
		return wb;
	}


	/**
	 * 根据反射创建excel 的实体
	 * 注意这里的list集合的成员的属性，需要根据attributes顺序写。
	 * @param wb    excel工作博对象
	 * @param list  需要写出到excel的集合
	 * @param <T>   集合泛型
	 */
	private static <T> void createBody(Workbook wb, List<T> list) {
		// excel工作表对象
		Sheet sheet = null;
		// excel行对象
		Row row = null;
		// 单元格对象
		Cell c = null;
		// 使用泛型声明一个集合中的元素类型对象 P
		T p = null;
		// P对象的所有字段
		Field[] fields = null;
		// 新建的sheet工作表 从第一行创建起
		int row_index = 0;
		// 创建行
		for (int i = 0; i < list.size(); i++) {
			// SHEET_SIZE 条数据 换一个工作表
			if(i % SHEET_SIZE == 0){
				System.gc();
				row_index = 0;
				System.out.println(i);
				sheet = wb.createSheet("sheet"+ (i / SHEET_SIZE));
				// 每一个工作薄都要创建表头
				row = sheet.createRow(row_index);
				// 创建表头
				createHead(row, ExportExcel.Fields);
			}
			++row_index;
			row = sheet.createRow(row_index);
			// 从集合中获取需要导出的一条数据
			p = list.get(i);
			// 获取对象所有属性
			fields = p.getClass().getDeclaredFields();

			// 创建一行中的所有列
			try {
				for (int j = 0; j < fields.length; j++) {
					for (int k = 0; k < ExportExcel.Attributes.length; k++) {
						// 配置文件字段和对象字段相比较，相同就执行方法
						if (fields[j].getName().equalsIgnoreCase(ExportExcel.Attributes[k])) {
							// 创建HSSFCell对象 表头
							c = row.createCell(k);
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
				//logger.error("导出Excel，在设置实体部分时出错！");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建 Excel表头
	 * @param row   表头行对象
	 * @param fields   表头列字段
	 */
	private static void createHead(Row row, String[] fields) {
		// 创建行头
		for (int i = 0; i < fields.length; i++) {
			//创建HSSFCell对象 表头
			Cell cell = row.createCell(i);
			//设置表头值
			cell.setCellValue(fields[i]);
		}
	}

	/**
	 * 设置导出文件名
	 *
	 * @Param [fileName] 文件名
	 * @Return java.lang.String
	 */
	private static String setFileName(String fileName) {
		// 文件名包含了.xls,和.xlsx;修改成系统定义的格式excel格式使用
		if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){
			return fileName.substring(0,fileName.lastIndexOf(".")) + SUFFIX;
		}
		// 默认 xlsx 格式
		if (fileName.indexOf(".") == -1) {
			fileName += ExportExcel.SUFFIX;
		} else if(fileName.endsWith(".")){
			// 截取小数点前面的字符串。
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			// 追加文件格式
			fileName += ExportExcel.SUFFIX;
		}else{
			// 抛出异常
			throw new RuntimeException("警告：导出文件错误: " + fileName +"不是正确的excel格式：.xls, .xlsx");
		}

		return fileName;
	}

	/**
	 * 使用SXSSFWorkboo时，需要一个临时存储数据的文件。
	 * 创建一个临时存储数据XSSFWorkbook对象。
	 * @return
	 * @throws IOException
	 */
	private static XSSFWorkbook createTempXSSFWorkbook() throws IOException {
		InputStream inputStream = ExportExcel.class.getResourceAsStream("/xssf_temp.xlsx");
		if(inputStream == null){
			throw new RuntimeException("在导出Excel文件时，因为resources目录下临时文件 xssf_temp.xlsx 不存在！");
		}
		XSSFWorkbook book = new XSSFWorkbook(inputStream);
		return book;
	}

	/**
	 * 写出excel到硬盘
	 * @Param [wb，excel对象]
	 * @Return void
	 */
	private static void writeExcelTOHardDisk(HSSFWorkbook wb) {
		FileOutputStream output = null;
		try {
			output = new FileOutputStream("d:\\AA/workbook.xls");
			wb.write(output);
			output.flush();
			//logger.info("成功");
		} catch (FileNotFoundException e) {
			//logger.info("失败");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
