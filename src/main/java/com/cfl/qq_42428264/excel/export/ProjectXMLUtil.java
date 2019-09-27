package com.cfl.qq_42428264.excel.export;

import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 解析 classpath下的 projectExport.xml 文件
 * @ClassName ProjectXMLUtil
 * @Description TODO
 * @Author cfl
 * @Date 2019/8/6 20:16
 * @Version 1.0
 */
public class ProjectXMLUtil {
	private String[] fields;
	private String[] attributes;
	
	/**
	 * 通过构造函数，初始化属性
	 * @Param []
	 * @Return
	 */
	public ProjectXMLUtil() {
		// 初始化 fields, attributes;
		getProjectHead();
	}

	/**
	 * 给属性 fields 和属性 attributes 赋值
	 * @Param []
	 * @Return void
	 */
	private void getProjectHead(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		InputStream in = null;
		try {
			builder = factory.newDocumentBuilder();
			String fileName = "projectExport.xml";
			in = new ClassPathResource(fileName).getInputStream();
			document = builder.parse(in);
			// 读取表头
			NodeList node = document.getElementsByTagName("field");
			// 读取属性字段
			NodeList attrList = document.getElementsByTagName("attribute");
			// 自己手动抛一个异常
			if (node.getLength() != attrList.getLength()) {
				try{
					throw new RuntimeException();
				} catch(RuntimeException e){
					System.err.println("解析 路径下" + fileName
					+ "中的field 和 attribute 数量不匹配");
					e.printStackTrace();
				}
			}
			// 初始化数组长度
			fields = new String[node.getLength()];
			attributes = new String[attrList.getLength()];
			
			// 给 属性赋初值
			for (int i = 0; i < node.getLength(); i++) {
				fields[i] = node.item(i).getFirstChild().getTextContent();
//				System.out.println(node.item(i).getTextContent());
			}
			for (int i = 0; i < attrList.getLength(); i++) {
				attributes[i] = attrList.item(i).getFirstChild().getTextContent();
//				System.out.println(attributes[i]);
			}
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ProjectXMLUtil p = new ProjectXMLUtil();
//		System.out.println(Arrays.toString(p.getFields()));
//		System.out.println(Arrays.toString(p.getAttributes()));
	}
	
}
