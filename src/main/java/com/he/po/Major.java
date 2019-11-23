package com.he.po;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-07-16
 */
public class Major implements Serializable{
    private Integer id;

    /**
     * 专业编号
     */
    private String majorId;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 学费
     */
    private String price;

    /**
     * 系别编号
     */
    private String departmentId;

    
    public List<Class> getChildren() {
		return children;
	}

	public void setChildren(List<Class> children) {
		this.children = children;
	}

	private List<Class> children;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId == null ? null : majorId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }
}