package com.he.viewpo;

import java.util.List;

import com.he.po.Class;

/**
 * 专业名称、班级数量、该班级数据集合
 * @author Administrator
 *
 */
public class MajorAndClass {
	
	private String major;
	//班数量
	private Integer classnumber;
	
	private List<ClassIdAndStudentList> classs;
	
	public List<ClassIdAndStudentList> getClasss() {
		return classs;
	}
	public void setClasss(List<ClassIdAndStudentList> classs) {
		this.classs = classs;
		this.classnumber=classs.size();
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Integer getClassnumber() {
		return classnumber;
	}
	public void setClassnumber(Integer classnumber) {
		this.classnumber = classnumber;
	}


	
}
