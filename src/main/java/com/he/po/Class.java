package com.he.po;

import java.io.Serializable;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-07-16
 */
public class Class implements Serializable{
    private Integer id;

    /**
     * 班级编号
     */
    private String classId;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 负责人
     */
    private String director;

    /**
     * 年级
     */
    private String grade;

    /**
     * 专业编号
     */
    private String majorId;
    
    
   //专业实体 
    private Major m;
    

    public Major getM() {
		return m;
	}

	public void setM(Major m) {
		this.m = m;
	}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId == null ? null : majorId.trim();
    }
}