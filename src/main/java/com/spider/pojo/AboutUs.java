package com.spider.pojo;

/**关于我们
 * @author jingjing
 */
public class AboutUs {
    /**
     * id
     */
    private Long id;
    /**
     * 附件
     */
    private String enclosure;
    /**
     * 描述
     */
    private String productText;
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

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public Long getAllId() {
        return allId;
    }

    public void setAllId(Long allId) {
        this.allId = allId;
    }
}
