package com.cfl.qq_42428264.excel.check_excel.exte;

import com.cfl.qq_42428264.excel.check_excel.AbstractCheckColumn;
import com.cfl.qq_42428264.excel.check_excel.CheckCellValue;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * 干部申报excel导入 具体检测类
 * @ClassName WeddingCheckColumn
 * @Description TODO
 * @Author msi
 * @Date 2019/8/16 23:53
 */
public class WeddingCheckColumn extends AbstractCheckColumn {

	/**
	 * 获取需要检查列的索引
	 * @param sheet
	 * @return
	 */
	@Override
	public Integer[] getCheckColumnNumber(XSSFSheet sheet) {
		return CheckCellValue.getCheckColumnNumber(sheet);
	}

	/**
	 * 干部申报的列检查
	 * 追加sb值，返回cellValue的值(给集合的元素设置值，用于前端页面显示)
	 * （这里主要是时间是1900年的天数时，返回yyyy/MM/dd格式时间）
	 * @param index 检查的索引
	 * @param sb    记录格式正确还是不正确，1 正确， 0 不正确
	 * @param cellValue 当前单元格的值
	 * @return
	 */
	@Override
	public String checkColumnFormat(Integer index, StringBuilder sb, String cellValue) {
		int sbValue = 1;
		// 判断时间时，将在方法里进行错误检查
		boolean timeFlag = false;
		// 申报：0（检查单位名），2（检查证件号），6（检查申报时间），7（检查操办时间），9（检查规模）
		switch(index){
			case 0:
				//sbValue = CheckCellValue.checkDepartmentName(departName, cellValue);
				break;
			case 2:
				sbValue = CheckCellValue.checkIdnumber(cellValue);
				break;
			case 6:
				cellValue = CheckCellValue.checkTime(sb, cellValue);
				timeFlag = true;
				break;
			case 7:
				cellValue =  CheckCellValue.checkTime(sb, cellValue);
				timeFlag = true;
				break;
			case 9:
				sbValue = CheckCellValue.checkScale(cellValue);
				break;
		}
		if(!timeFlag){
			// 追加检测结果
			sb.append(sbValue);
		}
		return cellValue;
	}


}
