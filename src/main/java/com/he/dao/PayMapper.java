package com.he.dao;

import java.util.List;

import com.he.po.Pay;
import com.he.viewpo.DDMC;

public interface PayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pay record);

    int insertSelective(Pay record);

    Pay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pay record);

    int updateByPrimaryKey(Pay record);
    
    //1.插入转账信息
    int insertPay(Pay pay);
    
    
    //2.查询学生的转账记录
    Pay getPayBystudentId(Pay pay);
    
    
    //3.查询缴费与否学生人数
    int selectPaycount(DDMC ddmc);
}