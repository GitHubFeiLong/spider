package com.cfl.qq_42428264.excel.check_excel.parse_xml;

import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 *  获取导入申报表时需要检查的列
 * @ClassName CheckWeddingXMLUtil
 * @Description TODO
 * @Author msi
 * @Date 2019/8/16 21:42
 */
public class CheckWeddingXMLUtil {

	public static String[] getCheckField(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		InputStream is = null;
		String[] results = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			is = new ClassPathResource("CheckWeddingFuneralExcel.xml").getInputStream();
			Document document = builder.parse(is);
			NodeList list = document.getElementsByTagName("checkFeild");
			results = new String[list.getLength()];
			for(int i = 0; i < list.getLength(); i++){
				results[i] = list.item(i).getTextContent();
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return results;
	}

	public static void main(String[] args) {
		String [] strs = CheckWeddingXMLUtil.getCheckField();
		System.out.println(Arrays.toString(strs));
	}
}
