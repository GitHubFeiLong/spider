package com.spider.pojo;

import lombok.Data;

/**附件
 * @author jingjing
 */
@Data
public class UseFile {
    /**
     * id
     */
    private Long id;
    /**
     * 附件名称
     */
    private String fileName;
    /**
     * 类型
     */
    private String type;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件地址
     */
    private String saveFilePath;


}
