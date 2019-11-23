package com.he.po;

import java.io.Serializable;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-07-23
 */
public class Dormitory implements Serializable{
    private Integer id;

    /**
     * 宿舍位于几栋
     */
    private String dong;

    /**
     * 宿舍楼数
     */
    private Integer floor;
    /**
     * 房号
     */
    private Integer room;

    private String sex;

    /**
     * 宿舍人数
     */
    private Integer number;

    /**
     * 每人每年租金
     */
    private String rent;
    
    
   /**
    * 
    *  已有人数
    * @return
    */
    private Integer hasnumber;

    
    /**
     * 宿舍门派简写
     * @return
     */
    private String roomname;
    
    /**
     * 页面的删除与查询图标是否显示
     * @return
     */
    
    private boolean show=false;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong == null ? null : dong.trim();
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

	public Integer getHasnumber() {
		return hasnumber;
	}

	public void setHasnumber(Integer hasnumber) {
		this.hasnumber = hasnumber;
	}

	public String getRoomname() {
		if(dong!=null&&floor!=null&&room!=null) {
			String sroom=room.toString();
			if(room<10) {
				sroom="0"+room;
			}
			roomname=dong+floor+sroom;
		}
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
    
}