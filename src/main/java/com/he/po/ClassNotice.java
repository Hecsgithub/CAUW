package com.he.po;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-08-31
 */
public class ClassNotice {
    /**
     * 班级通知表的主键
     */
    private Integer id;

    /**
     * 班级编号
     */
    private String classId;

    /**
     * 通知内容
     */
    private String noticeWord;

    /**
     * 通知时间
     */
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public String getNoticeWord() {
        return noticeWord;
    }

    public void setNoticeWord(String noticeWord) {
        this.noticeWord = noticeWord == null ? null : noticeWord.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time =(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}