package com.he.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.ClassMapper;
import com.he.dao.DormitoryAllocationMapper;
import com.he.dao.StudentStatusMapper;
import com.he.po.Class;
import com.he.po.DormitoryAllocation;
import com.he.po.Major;
import com.he.po.StudentStatus;
import com.he.service.ClassService;
import com.he.service.DormitoryAllocationService;
import com.he.viewpo.ClassIdAndStudentList;
import com.he.viewpo.MajorAndClass;
import com.he.viewpo.NewClass;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	private ClassMapper classMapper;
	
	@Autowired
	private StudentStatusMapper studentStatusMapperMapper;
	
	@Autowired
	private DormitoryAllocationMapper dormitoryAllocationMapper;
	
	//1.初始化班级表(为每个专业插入班级号，代表)
	@Override
	public int insertIntClass(List<Class> classs) {
		// TODO Auto-generated method stub
		return this.classMapper.insertIntClass(classs);
	}

    //2.获取专业名称及编号
	@Override
	public List<Major> selectMajorList(Class c) {
		// TODO Auto-generated method stub
		return this.classMapper.selectMajorList(c);
	}
	
//	3.获取专业及专业下的班级的详细信息
	@Override
	public List<MajorAndClass> selectMajorAndClassList() {
		// TODO Auto-generated method stub
		List<MajorAndClass> macs=new ArrayList<>(); 
		List<Major> ms=this.classMapper.selectMajorList(new Class());
		for(Major m:ms) {
			MajorAndClass mac=new MajorAndClass();
			mac.setMajor(m.getName());
			List<ClassIdAndStudentList> cass=new ArrayList<>();
			List<Class> cs=this.classMapper.selectClassByMajorID(m.getMajorId()); 
			for(Class c: cs) {
				ClassIdAndStudentList cas = new ClassIdAndStudentList();
				// 获取男女学生
				List<StudentStatus> bsss = this.studentStatusMapperMapper.selectStudentBySexAndClass("男", c.getClassId());
				List<StudentStatus> gsss = this.studentStatusMapperMapper.selectStudentBySexAndClass("女", c	.getClassId());

				// 获取该班级所分配到的宿舍床位数，分男女
				DormitoryAllocation da = new DormitoryAllocation();
				da.setClassId(c.getClassId());
				da.setSex("男");
				List<DormitoryAllocation> bdas = this.dormitoryAllocationMapper.getDormitoryAllocationByDA(da);
				int bnumber = 0;
				for (DormitoryAllocation newd : bdas) {
					bnumber += newd.getNumber();
				}
				da.setSex("女");
				List<DormitoryAllocation> gdas = this.dormitoryAllocationMapper.getDormitoryAllocationByDA(da);
				int gnumber = 0;
				for (DormitoryAllocation newd : gdas) {
					gnumber += newd.getNumber();
				}
				if (bsss.size() > bnumber) { // 男缺少床位否则置0
					cas.setBoylessnumber(bsss.size() - bnumber);
				}else {
					cas.setBoylessnumber(0);
				}
				if (gsss.size() > gnumber) { // 女缺少床位否则置0
					cas.setGirllessnumber(gsss.size() - gnumber);
				}else {
					cas.setGirllessnumber(0);
				}
				//此班级存在缺少床位时才加入集合
				if(cas.getBoylessnumber()>0||cas.getGirllessnumber()>0) {
					cas.setClassId(c.getClassId());
					cass.add(cas);
				}
				
				cas=null;
			}
			if(cass.size()>0) {//存在班级缺少床位
				mac.setClasss(cass);
				macs.add(mac);
			}
			cass=null;
			mac=null;
		}
		return macs;
	}

	@Override
	public List<NewClass> getAllClass(NewClass nc) {
		// TODO Auto-generated method stub
		return this.classMapper.getAllClass(nc);
	}

	@Override
	public int updatedirector(String tid, String id) {
		// TODO Auto-generated method stub
		return this.classMapper.updatedirector(tid, id);
	}

	@Override
	public List<Class> selectClassByMajorID(String majorid) {
		// TODO Auto-generated method stub
		return this.classMapper.selectClassByMajorID(majorid);
	}

	@Override
	public int deleteClass(Class c) {
		// TODO Auto-generated method stub
		return this.classMapper.deleteClass(c);
	}

	@Override
	public Class selectByPrimaryKey(String classId) {
		// TODO Auto-generated method stub
		return this.classMapper.selectByPrimaryKey(classId);
	}

	@Override
	public List<Class> selectAllClass(Class c) {
		// TODO Auto-generated method stub
		return this.classMapper.selectAllClass(c);
	}

	@Override
	public Class selectMajorMaxClass(String classId) {
		// TODO Auto-generated method stub
		return this.classMapper.selectMajorMaxClass(classId);
	}


	
	
}
