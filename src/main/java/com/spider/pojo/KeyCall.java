package com.spider.pojo;

/**主要信息
 * @author jingjing
 */
public class KeyCall {
    /**
     * id
     */
    private Long id;
    /**
     * 附件
     */
    private String enclosure;
    /**
     * 名称
     */
    private String productName;
    /**
     * 外键
     */
    private Long allId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getAllId() {
        return allId;
    }

    public void setAllId(Long allId) {
        this.allId = allId;
    }
}
