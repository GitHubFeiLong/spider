package com.cfl.qq_42428264.excel.export;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
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
 *      那么这个类的成员变量顺序只能是 ： private Integer id;
 *                                     private String name;
 *                                     private Integer age;
 *       顺序不能颠倒（不然，导出的实体数据和表头不对应）
 * @ClassName projectExportExcel
 * @Description TODO
 * @Author cfl
 * @Date 2019/8/6 19:35
 * @Version 1.1 :
 */
public class ExportExcel {
	// 日志
//	private static final Logger logger = LoggerFactory.getLogger(ExportExcel.class);
	// 表头
	private static String[] Fields;
	// 表头对应的 字段
	private static String[] Attributes;

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
        System.out.println(checkFieldsAndAttr());

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
	 * 写出Excel文件
	 * fileName:文件下载到客户端的文件名
	 * list : 需要导出的excel数据
	 * @Param [fileName：写出文件名名字；list 需要写出的数据]
	 * @Return void
	 */
	public static <T> void writeExcel(String fileName, List<T> list) {
        // 1.判断属性 files 和 attributes 属性是否满足导出excel的基本要求。
        if(!checkFieldsAndAttr()){
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

		// 2.创建表格对象
		HSSFWorkbook wb = ExportExcel.createHSSFWorkbook(list);
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
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			//logger.error("使用response写出" + fileName + "文件失败");
			e.printStackTrace();
		} finally{
			try {
				out.close();
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置导出文件名
	 *
	 * @Param [fileName] 文件名
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
	 * 返回HSSFWorkbook 对象
	 * 创建Excel文件对象
	 * @Param [list 表格数据, fields, attributes]
	 * @Return org.apache.poi.hssf.usermodel.HSSFWorkbook
	 */
	private static <T> HSSFWorkbook createHSSFWorkbook(List<T> list) {
		//创建HSSFWorkbook对象
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建内容实体
		createBody(wb, list);

		// 写出excel到硬盘
//		writeExcelTOHardDisk(wb);

		return wb;
	}


	/**
	 * 通过反射设置成员变量 fields 和attributes
	 * @Param [clazz]
	 * @Return void
	 */
	public static void setFilesAndAttributes(Class clazz) {
		Object obj = null;
		try {
		    // 通过反射创建对象
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
                    ExportExcel.Fields = (String[]) fies[i].get(obj);
				} else {
                    ExportExcel.Attributes = (String[]) fies[i].get(obj);
				}
			} catch (IllegalAccessException e) {
				//logger.error("设置表格头部和头部对应的字段失败");
				e.printStackTrace();
			}

		}
	}

    /**
     * 设置成员变量 fields 和attributes
     * @Param [clazz]
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
     * 注意这里的list集合的成员的属性，需要根据attributes顺序写。
     * @param wb excel对象
     * @param list  需要导出的excel数据
     * @param <T>   excel实体数据的对象
     */
	private static <T> void createBody(HSSFWorkbook wb, List<T> list) {
		//创建HSSFSheet对象
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell c = null;
		T p = null;
		Field[] fields = null;
		// 新建的sheet 从第一行创建起
		int row_index = 0;
		// 创建行：第二行开始
		for (int i = 0; i < list.size(); i++) {
			// 60000 换一个sheet
			if(i % 60000 == 0){
				System.gc();
				row_index = 0;
				System.out.println(i);
				sheet = wb.createSheet("sheet"+ (i / 60000));
                // 每一个工作薄都要创建表头，第一行
                row = sheet.createRow(row_index);
                // 创建表头
                createHead(row, ExportExcel.Fields);
			}
            row_index ++;
			row = sheet.createRow(row_index);
			// 当前行对象
			p = list.get(i);
			// 对象所有属性
			fields = p.getClass().getDeclaredFields();
			// 创建一行中的所有列
			try {
				for (int j = 0; j < fields.length; j++) {
					for (int k = 0; k < ExportExcel.Attributes.length; k++) {
						// 配置文件字段和对象字段相比较，相同就执行方法
						if (fields[j].getName().equalsIgnoreCase(ExportExcel.Attributes[k])) {

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
				//logger.error("导出Excel，在设置实体部分时出错！");
				e.printStackTrace();
			}
		}
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
