package com.he.viewpo;

import java.util.List;

import com.he.po.StudentBasic;
import com.he.po.StudentStatus;
import com.he.po.Class;

/**
 * 班级编号、学生基础信息集合,男、女学生学籍信息集合、班级实体、缺少的男、女床位数
 * @author Administrator
 *
 */
public class ClassIdAndStudentList {
	/**
     * 班级编号
     */
    private String classId;
    /**
     * 班级学生集合
     */
    private List<StudentBasic> lsb;
    
    
	/**
     * 班级男学生集合
     */
    private List<StudentStatus> boylsb;
    
	/**
     * 班级女学生集合
     */
    private List<StudentStatus> girllsb;
    
    
    
    //班级
    private Class  c;
    //男生缺少的床位
    private int boylessnumber=0;
    //女生缺少的床位
    private int girllessnumber=0;
    
    
  //缺少的床位,看情况决定为男或女
    private int lessnumber=0;
    
    
    public int getLessnumber() {
		return lessnumber;
	}

	public void setLessnumber(int lessnumber) {
		this.lessnumber = lessnumber;
	}

	public Class getC() {
		return c;
	}

	public void setC(Class c) {
		this.c = c;
	}

	public int getBoylessnumber() {
		return boylessnumber;
	}

	public void setBoylessnumber(int boylessnumber) {
		this.boylessnumber = boylessnumber;
	}

	public int getGirllessnumber() {
		return girllessnumber;
	}

	public void setGirllessnumber(int girllessnumber) {
		this.girllessnumber = girllessnumber;
	}

	public List<StudentStatus> getBoylsb() {
		return boylsb;
	}

	public void setBoylsb(List<StudentStatus> boylsb) {
		this.boylsb = boylsb;
	}

	public List<StudentStatus> getGirllsb() {
		return girllsb;
	}

	public void setGirllsb(List<StudentStatus> girllsb) {
		this.girllsb = girllsb;
	}


    



    public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public List<StudentBasic> getLsb() {
		return lsb;
	}

	public void setLsb(List<StudentBasic> lsb) {
		this.lsb = lsb;
	}

	
}
