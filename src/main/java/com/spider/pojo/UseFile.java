package com.spider.pojo;

/**附件
 * @author jingjing
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }
}
