package com.he.viewpo;

import java.util.List;

import com.he.po.StudentStatus;

/**
 * 专业，专业分班数量 /未加入班级学生数，男生、女生数量、男生、女生集合、总集合
 * @author Administrator
 *
 */
public class MajorAndStudent {
	
	private String major;
	private Integer boynumber;
	private Integer girlnumber;
	//分班数量 /未加入班级学生数
	private Integer classnumber=0;
	private List<StudentStatus> boysss;
	private List<StudentStatus> girlsss;
	private List<StudentStatus> allsss;
	
	//提示显示开关，提示文字
	private boolean isshow=false;
	private String word;
	
	
	
	public List<StudentStatus> getAllsss() {
		return allsss;
	}
	public void setAllsss(List<StudentStatus> allsss) {
		this.allsss = allsss;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Integer getBoynumber() {
		return boynumber;
	}
	public void setBoynumber(Integer boynumber) {
		this.boynumber = boynumber;
	}
	public Integer getGirlnumber() {
		return girlnumber;
	}
	public void setGirlnumber(Integer girlnumber) {
		this.girlnumber = girlnumber;
	}
	

	
	public Integer getClassnumber() {
		return classnumber;
	}
	public void setClassnumber(Integer classnumber) {
		this.classnumber = classnumber;
	}
	

	public List<StudentStatus> getBoysss() {
		return boysss;
	}
	public void setBoysss(List<StudentStatus> boysss) {
		this.boysss = boysss;
		this.boynumber=boysss.size();
	}
	public List<StudentStatus> getGirlsss() {
		return girlsss;
	}
	public void setGirlsss(List<StudentStatus> girlsss) {
		this.girlsss = girlsss;
		this.girlnumber=girlsss.size();
	}
	
	
}
