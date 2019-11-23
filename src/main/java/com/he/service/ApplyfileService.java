package com.he.service;

import java.util.List;

import com.he.po.Apply;
import com.he.po.Applyfile;

public interface ApplyfileService {
	 //1.批量增加申请文件数据
    int insertApplyFileByList(List<Applyfile> afs);
    
    //2.批量删除申请文件数据
    int deleteApplyFileByList(List<Applyfile> afs);
      
    //3.查询申请文件
    List<Applyfile> selectApplyfile(Apply a);
    
    //4重新上传文件修改某ID的文件名与文件路径
    int updateAppliFile(Applyfile af);
}
