package com.spider.pojo;

import lombok.Data;

/**关于我们
 * @author jingjing
 */
@Data
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


}
