package com.he.po;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-08-27
 */
public class Pay {
    private Integer id;

    /**
     * 流水号
     */
    private String pipelineId;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 转账时间
     */
    private Date time;

    /**
     * 转账金额
     */
    private String money;

    /**
     * 备注
     */
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId == null ? null : pipelineId.trim();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}