package com.he.viewpo;

import java.util.ArrayList;
import java.util.List;

import com.he.po.StudentBasic;

/**
 * 省名/人数
 * @author Administrator
 *
 */
public class ProvincialNumber {

	private String name;//省名
	private int value;//人数
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public List getprovincialList(List<StudentBasic> sbs) {
		List list=new ArrayList<>();
		List<ProvincialNumber> pns=new ArrayList<>();	
		for(StudentBasic sb:sbs) {
			String provincial =sb.getAdress().substring(0, 2);
			if(provincial.equals("内蒙")||provincial.equals("黑龙")) {
				provincial =sb.getAdress().substring(0, 3);
			}
			if(pns.size()==0) {
				ProvincialNumber pn=new ProvincialNumber();
				pn.setName(provincial);
				pn.setValue(1);
				pns.add(pn);
				pn=null;
				continue;
			}
			boolean isok=true;
			//与集合里的内容判断
			look:for(int i=0;i<pns.size();i++) {
				if(pns.get(i).getName().equals(provincial)) {
					pns.get(i).setValue(pns.get(i).getValue()+1);
					isok=false;
					break look;
				}
			}
			if(isok) {
				//集合里没有，直接加入
				ProvincialNumber pn=new ProvincialNumber();
				pn.setName(provincial);
				pn.setValue(1);
				pns.add(pn);
				pn=null;
			}	
		}

		list.add(pns);
		list.add(sbs.size());
			
		return list;
	}
	
}
