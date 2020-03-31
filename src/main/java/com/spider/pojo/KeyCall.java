package com.spider.pojo;

import lombok.Data;

/**主要信息
 * @author jingjing
 */
@Data
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


}
