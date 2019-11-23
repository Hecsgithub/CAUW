package com.he.viewpo;

/**
 * 存放部门id
 * 系id
 * 专业id
 * 班级id
 * 宿舍楼栋数
 * 宿舍楼层数
 * 性别
 * 几人房
 * 是否满人（0、1）/ （已缴费、未缴费）
 * 备注，用于选择是否绿色通道
 * 
 * 
 * 用于接收前台参数
 * @author Administrator
 *
 */
public class DDMC {
	
	private String deptId;
	private String departmentId;
	private String majorId;
	private String classId;
	private String dong;
	private int floor;
	private String sex;
	private int number;
	private String enough;
	private String remarks;
	
	
	
	
	public String getEnough() {
		return enough;
	}
	public void setEnough(String enough) {
		this.enough = enough;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
	
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
