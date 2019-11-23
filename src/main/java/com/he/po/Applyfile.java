package com.he.po;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-08-31
 */
public class Applyfile {
    /**
     * 文件表id
     */
    private Integer id;

    /**
     * 文件显示的名称
     */
    private String filename;

    /**
     * 文件保存时的名称
     */
    private String fileurl;

    /**
     * 与申请表对应
     */
    private Integer applyId;

    /**
     * 备注，审核的反馈
     */
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl == null ? null : fileurl.trim();
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}