package com.spider.pojo;

import lombok.Data;

/**产品展示
 * @author jingjing
 */
@Data
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


}
