package com.he.viewpo;

/**
 * 某专业某班男宿舍或者女宿舍多余n个床位或者少多少个床位
 * @author Administrator
 *
 */
public class DivideDormitoryInformation {
	private String major;
	private String class_id;
	private String sex;
	private int dormitory_id;
	private int manynumber;
	private int lessnumber;
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getDormitory_id() {
		return dormitory_id;
	}
	public void setDormitory_id(int dormitory_id) {
		this.dormitory_id = dormitory_id;
	}
	public int getManynumber() {
		return manynumber;
	}
	public void setManynumber(int manynumber) {
		this.manynumber = manynumber;
	}
	public int getLessnumber() {
		return lessnumber;
	}
	public void setLessnumber(int lessnumber) {
		this.lessnumber = lessnumber;
	}
	
}
