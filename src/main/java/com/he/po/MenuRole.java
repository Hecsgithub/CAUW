package com.he.po;

import java.io.Serializable;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-07-23
 */
public class MenuRole implements Serializable{
    private Integer id;

    private Integer roleId;

    private Integer menuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}