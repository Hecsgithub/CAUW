package com.he.po;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-08-28
 */
public class Apply {
    /**
     * 申请表id
     */
    private Integer id;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 申请状态（未审核、已审核、）
     */
    private String state;
    
    //学生实体
    private StudentStatus ss;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

	public StudentStatus getSs() {
		return ss;
	}

	public void setSs(StudentStatus ss) {
		this.ss = ss;
	}
    
    
}