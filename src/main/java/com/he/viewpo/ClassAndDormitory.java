package com.he.viewpo;

/**
 * 存放专业名称、id 班级名称、id，男女数量、男女分配到的宿舍数目
 * @author Administrator
 *
 */
public class ClassAndDormitory {
	private String majorname;
	private String majorId;
	private String classname;
	private String classId;
	private int boynumber;
	private int girlnumber;
	private int boydormitorynumber;
	private int girldormitorynumber;
	public String getMajorname() {
		return majorname;
	}
	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public int getBoynumber() {
		return boynumber;
	}
	public void setBoynumber(int boynumber) {
		this.boynumber = boynumber;
	}
	public int getGirlnumber() {
		return girlnumber;
	}
	public void setGirlnumber(int girlnumber) {
		this.girlnumber = girlnumber;
	}
	public int getBoydormitorynumber() {
		return boydormitorynumber;
	}
	public void setBoydormitorynumber(int boydormitorynumber) {
		this.boydormitorynumber = boydormitorynumber;
	}
	public int getGirldormitorynumber() {
		return girldormitorynumber;
	}
	public void setGirldormitorynumber(int girldormitorynumber) {
		this.girldormitorynumber = girldormitorynumber;
	}

}
