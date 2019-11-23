package com.he.viewpo;

import java.util.ArrayList;
import java.util.List;

/**
 * 为接受前端各种奇怪对象所形成的的类
 * @author Administrator
 *
 */
public class RequestObject {
	
	private ArrayList<MajorAndStudent> mass;

	public ArrayList<MajorAndStudent> getMass() {
		return mass;
	}

	public void setMass(ArrayList<MajorAndStudent> mass) {
		this.mass = mass;
	}
}
