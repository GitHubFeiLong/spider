package com.spider.pojo;

import lombok.Data;

/**主表信息
 * @author jingjing
 */
@Data
public class AllCompanyMessage {

    /**
     * id
     */
    private Long id;
    /**
     * title
     */
    private String title;
    /**
     * 联系方式秒速
     */
    private String phoneText;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 公司名字
     */
    private String companyName;
    /**
     * 公司描述
     */
    private String companyText;
    /**
     * 用户名
     */
   private String userName;
    /**
     * 公司简称
     */
   private String companyCode;
    /**
     * 联系人
     */
   private String person;
    /**
     * 联系人联系电话
     */
   private String personPhone;
    /**
     * 客户QQ
     */
   private String customerQq;
    /**
     * 跳转页面
     */
   private String jumpPage;
    /**
     * 客户网站
     */
   private String customerPage;
    /**
     * 技术支持qq
     */
   private String artQq;
}
