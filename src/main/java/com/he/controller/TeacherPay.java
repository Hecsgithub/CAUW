package com.he.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.Apply;
import com.he.po.Applyfile;
import com.he.service.DormitoryService;
import com.he.service.PayService;
import com.he.service.StudentStatusService;
import com.he.viewpo.DDMC;
import com.he.viewpo.ProvincialNumber;

@RestController
@RequestMapping("/teacher/teacherpay")
public class TeacherPay {

	@Autowired
	private StudentStatusService studentStatusService;


	@Autowired
	private PayService payService;
	
	/**
	 * 查询缴费人数
	 * @param 
	 * @return
	 */
	@RequestMapping("/getPayInfo")
	public List getPayInfo(DDMC ddmc){
		List list=new ArrayList<>();
		
		//查询未缴纳学杂费学生
		ddmc.setEnough("has");
		ddmc.setRemarks("绿色通道");
		int greenpay=this.payService.selectPaycount(ddmc);
		
		
		//查询正常缴纳的学生
		ddmc.setRemarks("正常缴费");
		int haspay=this.payService.selectPaycount(ddmc);
		
		ProvincialNumber hp=new ProvincialNumber();
		hp.setName("已缴费");
		hp.setValue(haspay);
		list.add(hp);
		
		//查询未缴纳学杂费学生
		ddmc.setEnough("not");
		ddmc.setRemarks(null);
		int nopay=this.payService.selectPaycount(ddmc);
		ProvincialNumber np=new ProvincialNumber();
		np.setName("未缴费");
		np.setValue(nopay);
		list.add(np);
		
		
		ProvincialNumber greenp=new ProvincialNumber();
		greenp.setName("绿色通道");
		greenp.setValue(greenpay);
		list.add(greenp);
		
		return list;
	}
	
	
}
