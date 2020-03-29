package com.spider.pojo;

/**产品展示
 * @author jingjing
 */
public class ProductDisplay {
    /**
     * id
     */
    private Long id;
    /**
     * 附件
     */
    private String enclosure;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 外键id
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
