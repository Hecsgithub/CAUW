package com.he.po;

import java.io.Serializable;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-07-12
 */
public class StudentStatus implements Serializable{
    private Integer id;

    /**
     * 学生基础信息编号
     */
    private Integer basicId;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 班级编号
     */
    private String classId;
    
    
    /**
     * 对应的基础实体
     * @return
     */
    private StudentBasic sb;
    
//   班级实体
    private Class c;
    
    
    

    public Class getC() {
		return c;
	}

	public void setC(Class c) {
		this.c = c;
	}

	public StudentBasic getSb() {
		return sb;
	}

	public void setSb(StudentBasic sb) {
		this.sb = sb;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBasicId() {
        return basicId;
    }

    public void setBasicId(Integer basicId) {
        this.basicId = basicId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }
}