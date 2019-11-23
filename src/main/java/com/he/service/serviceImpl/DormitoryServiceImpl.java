package com.he.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.dao.DormitoryAllocationMapper;
import com.he.dao.DormitoryMapper;
import com.he.dao.DormitoryRegistrationMapper;
import com.he.po.Dormitory;
import com.he.po.DormitoryAllocation;
import com.he.service.DormitoryService;
import com.he.viewpo.DDMC;

@Service
public class DormitoryServiceImpl implements DormitoryService {

	@Autowired
	private DormitoryMapper dormitoryMapper;
	
	@Autowired
	private DormitoryAllocationMapper dormitoryAllocationMapper;
	
	@Autowired
	private DormitoryRegistrationMapper dormitoryRegistrationMapper;
	
	@Override
	public List<Dormitory> fullDormitory(Dormitory d) {
		// TODO Auto-generated method stub
		return this.dormitoryMapper.fullDormitory(d);
	}

	@Override
	public int insertListDormitory(List<Dormitory> ds) {
		// TODO Auto-generated method stub
		return this.dormitoryMapper.insertListDormitory(ds);
	}

	@Override
	public int deleteinsertListDormitory(List<Dormitory> ds) {
		// TODO Auto-generated method stub
	
		this.dormitoryAllocationMapper.deleteDormitoryAllocationByDormitoryId(ds);
		this.dormitoryRegistrationMapper.deleteDormitoryRegistrationByDormitoryId(ds);
		return this.dormitoryMapper.deleteinsertListDormitory(ds);
	}

	@Override
	public int updateListDormitory(List<Dormitory> ds) {
		// TODO Auto-generated method stub
		return this.dormitoryMapper.updateListDormitory(ds);
	}

	@Override
	public List<Dormitory> selectAllDormitory(DDMC ddmc) {
		// TODO Auto-generated method stub
		return this.dormitoryMapper.selectAllDormitory(ddmc);
	}

	@Override
	public List<Dormitory> getIinsufficientDormitory(DDMC ddmc) {
		// TODO Auto-generated method stub
		List<Dormitory> ms=new ArrayList<>();
		List<DormitoryAllocation> das=this.dormitoryAllocationMapper.getSginManyClassDormitory(ddmc); 
		for(DormitoryAllocation da:das) {
			Dormitory m=da.getDormitory();
			m.setHasnumber(da.getNumber());
			ms.add(m);
			m=null;
		}
		List<DormitoryAllocation> sdas=this.dormitoryAllocationMapper.getManyClassDormitory(ddmc);
		for(DormitoryAllocation sda: sdas) {
			
			sda.setClassId(null);
			sda.setDormitory(null);
			sda.setId(null);
			sda.setNumber(null);
			sda.setSex(null);
			List<DormitoryAllocation> ssdas=this.dormitoryAllocationMapper.getDormitoryAllocationByDA(sda);
			if(ssdas.size()>0) {
				int number=ssdas.get(0).getDormitory().getNumber();
				int sumnumber=0;
				for(DormitoryAllocation ssda:ssdas) {
					sumnumber+=ssda.getNumber();
				}
				if(sumnumber<number) {
					Dormitory m=ssdas.get(0).getDormitory();
					m.setHasnumber(sumnumber);
					ms.add(m);
					m=null;
				}
			}
			
		}
		
		return ms;
	}

	@Override
	public List<Dormitory> selectDormitoryByStudentId(String studentId) {
		// TODO Auto-generated method stub
		return this.dormitoryMapper.selectDormitoryByStudentId(studentId);
	}

	@Override
	public List<String> getDormitoryDong(String sex) {
		// TODO Auto-generated method stub
		return this.dormitoryMapper.getDormitoryDong(sex);
	}

	@Override
	public List<String> getDormitoryFloor(String dong) {
		// TODO Auto-generated method stub
		return this.dormitoryMapper.getDormitoryFloor(dong);
	}

	@Override
	public Dormitory selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.dormitoryMapper.selectByPrimaryKey(id);
	}

}
