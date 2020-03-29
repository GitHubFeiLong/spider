package com.spider.pojo;

/**主表信息
 * @author jingjing
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneText() {
        return phoneText;
    }

    public void setPhoneText(String phoneText) {
        this.phoneText = phoneText;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyText() {
        return companyText;
    }

    public void setCompanyText(String companyText) {
        this.companyText = companyText;
    }
}
