package com.he.service;

import java.util.List;

import com.he.po.Pay;
import com.he.viewpo.DDMC;

public interface PayService {

	//1.插入转账信息
    int insertPay(Pay pay);
	
    
    //2.查询学生的转账记录
    Pay getPayBystudentId(Pay pay);
    
    //3.查询缴费与否学生人数
    int selectPaycount(DDMC ddmc);
}
