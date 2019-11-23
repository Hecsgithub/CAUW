package com.he.dao;

import java.util.List;

import com.he.po.Apply;
import com.he.po.Applyfile;

public interface ApplyfileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Applyfile record);

    int insertSelective(Applyfile record);

    Applyfile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Applyfile record);

    int updateByPrimaryKey(Applyfile record);
    
    //1.批量增加申请文件数据
    int insertApplyFileByList(List<Applyfile> afs);
    
    //2.批量删除申请文件数据
    int deleteApplyFileByList(List<Applyfile> afs);
      
    //3.查询申请文件
    List<Applyfile> selectApplyfile(Apply af);
    
    //4重新上传文件修改某ID的文件名与文件路径
    int updateAppliFile(Applyfile af);
    
    
}