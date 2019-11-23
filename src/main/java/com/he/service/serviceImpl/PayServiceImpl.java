package com.he.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.PayMapper;
import com.he.po.Pay;
import com.he.service.PayService;
import com.he.viewpo.DDMC;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private PayMapper payMapper;
	@Override
	public int insertPay(Pay pay) {
		// TODO Auto-generated method stub
		return this.payMapper.insertPay(pay);
	}
	@Override
	public Pay getPayBystudentId(Pay pay) {
		// TODO Auto-generated method stub
		return this.payMapper.getPayBystudentId(pay);
	}
	@Override
	public int selectPaycount(DDMC ddmc) {
		// TODO Auto-generated method stub
		return this.payMapper.selectPaycount(ddmc);
	}

}
