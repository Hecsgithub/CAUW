package com.he.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.DormitoryAllocation;
import com.he.po.DormitoryRegistration;
import com.he.po.Dormitory;
import com.he.po.Major;
import com.he.po.RespBean;
import com.he.po.StudentStatus;
import com.he.po.Class;
import com.he.service.ClassService;
import com.he.service.DormitoryAllocationService;
import com.he.service.DormitoryRegistrationService;
import com.he.service.DormitoryService;
import com.he.service.MajorService;
import com.he.service.StudentStatusService;
import com.he.tool.GetLoginUserInfo;
import com.he.viewpo.ClassAndDormitory;
import com.he.viewpo.ClassIdAndStudentList;
import com.he.viewpo.DDMC;
import com.he.viewpo.DivideDormitoryInformation;
import com.he.viewpo.NewClass;

@RequestMapping("/dormitory/dividedormitory")
@RestController
public class DivideDormitoryController {

	@Autowired
	private DormitoryService dormitoryService;

	@Autowired
	private ClassService ClassService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private StudentStatusService studentStatusService;

	@Autowired
	private DormitoryAllocationService dormitoryAllocationService;

	@Autowired
	private DormitoryRegistrationService dormitoryRegistrationService;

	/**
	 * 根据专业返回树形专业班级信息
	 * 
	 * @return
	 */
	@RequestMapping("/initTreeData")
	public List<Major> initTreeData() {
		List<Major> cs = this.majorService.selectAllTreeMajor(new Major());
		return cs;
	}

	// 取出宿舍信息及该班级的ClassAndDormitory类信息
	@RequestMapping("/getallDormitory")
	public List getAllDormitory(DDMC ddmc) {
		ArrayList list = new ArrayList<>();
		List<Dormitory> ds = this.dormitoryService.selectAllDormitory(ddmc);
		list.add(ds);
		ClassAndDormitory cad = new ClassAndDormitory();
		Class c = this.ClassService.selectByPrimaryKey(ddmc.getClassId());
		cad.setClassId(c.getClassId());
		cad.setClassname(c.getName());
		cad.setMajorId(c.getM().getMajorId());
		cad.setMajorname(c.getM().getName());
		List<StudentStatus> boysss = this.studentStatusService.selectStudentBySexAndClass("男", c.getClassId());
		List<StudentStatus> girlsss = this.studentStatusService.selectStudentBySexAndClass("女", c.getClassId());
		cad.setBoynumber(boysss.size());
		cad.setGirlnumber(girlsss.size());
		DormitoryAllocation da = new DormitoryAllocation();
		da.setSex("男");
		da.setClassId(c.getClassId());
		List<DormitoryAllocation> boydas = this.dormitoryAllocationService.getDormitoryAllocationByDA(da);
		int bn = 0;
		for (DormitoryAllocation bda : boydas) {
			bn += bda.getNumber();
		}
		da.setSex("女");
		List<DormitoryAllocation> girldas = this.dormitoryAllocationService.getDormitoryAllocationByDA(da);
		int gn = 0;
		for (DormitoryAllocation gda : girldas) {
			gn += gda.getNumber();
		}

		cad.setBoydormitorynumber(bn);
		cad.setGirldormitorynumber(gn);
		list.add(cad);
		return list;
	}

	// 批量删除，将宿舍与班级的联系删除
	@Transactional
	@RequestMapping("/deletelistDormitoryAllocation")
	public RespBean deletelistDormitoryAllocation(String id) {
		String[] tempid = id.split(",");
		List<Dormitory> idlist = new ArrayList<>();
		for (int s = 0; s < tempid.length; s++) {
			Dormitory d = new Dormitory();
			d.setId(Integer.parseInt(tempid[s]));
			d.setHasnumber(0);
			idlist.add(d);
		}
		int i = this.dormitoryAllocationService.deleteDormitoryAllocationByDormitoryId(idlist);
		if (i > 0) {
			int j=this.dormitoryService.updateListDormitory(idlist);
			if(j>0) {
				return RespBean.ok("重置成功！");
			}else {
				return RespBean.error("重置失败！");
			}	
		} else {
			return RespBean.error("重置失败！");
		}
	}

	/**
	 * 获取分男女获取未满员宿舍信息
	 * 
	 * @return
	 */
	@RequestMapping("/getIinsufficientDormitory")
	public List getIinsufficientDormitory() {
		List list = new ArrayList<>();
		DDMC ddmc = new DDMC();
		ddmc.setSex("男");
		List<Dormitory> boyd = this.dormitoryService.getIinsufficientDormitory(ddmc);
		ddmc.setSex("女");
		List<Dormitory> girld = this.dormitoryService.getIinsufficientDormitory(ddmc);
		list.add(boyd);
		list.add(girld);
		return list;
	}

	/**
	 * 获取所有班级分男女未满足宿舍床位信息
	 * 
	 * @return
	 */
	@RequestMapping("/getIinsufficientClassDormitory")
	public List getIinsufficientClassDormitory() {
		List list = new ArrayList<>();
		List<ClassIdAndStudentList> bcass = new ArrayList<>();
		List<ClassIdAndStudentList> gcass = new ArrayList<>();
//			获取所有班级
		List<Class> ncs = this.ClassService.selectAllClass(new Class());
		for (Class nc : ncs) {
			ClassIdAndStudentList cas = new ClassIdAndStudentList();
			// 获取男女学生
			List<StudentStatus> bsss = this.studentStatusService.selectStudentBySexAndClass("男", nc.getClassId());
			List<StudentStatus> gsss = this.studentStatusService.selectStudentBySexAndClass("女", nc.getClassId());

			// 获取该班级所分配到的宿舍床位数，分男女
			DormitoryAllocation da = new DormitoryAllocation();
			da.setClassId(nc.getClassId());
			da.setSex("男");
			List<DormitoryAllocation> bdas = this.dormitoryAllocationService.getDormitoryAllocationByDA(da);
			int bnumber = 0;
			for (DormitoryAllocation newd : bdas) {
				bnumber += newd.getNumber();
			}
			da.setSex("女");
			List<DormitoryAllocation> gdas = this.dormitoryAllocationService.getDormitoryAllocationByDA(da);
			int gnumber = 0;
			for (DormitoryAllocation newd : gdas) {
				gnumber += newd.getNumber();
			}
			if (bsss.size() > bnumber) { // 男缺少床位
				cas.setBoylessnumber(bsss.size() - bnumber);
			}
			if (gsss.size() > gnumber) { // 女缺少床位
				cas.setGirllessnumber(gsss.size() - gnumber);
			}

			cas.setC(nc);
			if (cas.getBoylessnumber() > 0) {

				bcass.add(cas);
			}
			if (cas.getGirllessnumber() > 0) {

				gcass.add(cas);
			}
		}
		list.add(bcass);
		list.add(gcass);
		return list;
	}

	// 将班级安排到某某未满员宿舍
	@Transactional
	@RequestMapping("addDormitoryAllocation")
	public RespBean addDormitoryAllocation(Dormitory d, String daid) {
		int dlessnumber = d.getNumber() - d.getHasnumber();
		String successword = "添加成功，已将该" + d.getDong() + "栋" + d.getFloor() + "层" + d.getRoom() + "号" + d.getSex()
				+ "生宿舍空余的" + dlessnumber + "个床位分配给";
		List<DormitoryAllocation> das = new ArrayList<>();
		String[] tempid = daid.split(",");
		for (String classidandnumber : tempid) {
			String[] cn = classidandnumber.split(":");
			// 获取缺少的床位
			int neednumber = Integer.parseInt(cn[2]);

			// 获取该宿舍空余的床位
			int lessnumber = d.getNumber() - d.getHasnumber();

			if (lessnumber >= neednumber) {
				d.setHasnumber(d.getHasnumber() + neednumber);
				DormitoryAllocation newda = new DormitoryAllocation();
				newda.setClassId(cn[1]);
				newda.setDormitoryId(d.getId());
				newda.setNumber(neednumber);
				das.add(newda);
				successword += cn[0] + newda.getNumber() + "个、";
			} else { // 该班级缺少的床位太多，全部都要
				DormitoryAllocation newda = new DormitoryAllocation();
				newda.setClassId(cn[1]);
				newda.setDormitoryId(d.getId());
				newda.setNumber(lessnumber);
				das.add(newda);
				successword += cn[0] + newda.getNumber() + "个、";
				break;
			}
			// 如果该宿舍无空余的床位
			if (d.getNumber() - d.getHasnumber() <= 0) {
				break;
			}
		}
		int i = this.dormitoryAllocationService.insertDivideDormitory(das);
		if (i > 0) {
			successword.substring(0, successword.length() - 1);
			return RespBean.ok(successword);
		} else {
			return RespBean.error("添加失败！请稍后再试！");
		}
		
	}

	// 安排新宿舍给传过来的班级
	@Transactional
	@RequestMapping("addNewDormitoryAllocation")
	public RespBean addNewDormitoryAllocation(String info,String sex) {
		String successword = "";
		List<DormitoryAllocation> das = new ArrayList<>();
		DDMC ddmc = new DDMC();
		ddmc.setEnough("0");
		ddmc.setSex(sex);
		List<Dormitory> ds = this.dormitoryService.selectAllDormitory(ddmc);
		String[] sinfo = info.split(",");
		List<String> infolist = new ArrayList<>();
		for (String s : sinfo) {
			infolist.add(s);
		}
		loop: for (Dormitory d : ds) {
				successword+=d.getDong()+"栋"+d.getFloor()+"层"+d.getRoom()+"号"+d.getSex()+"生"+d.getNumber()+"人宿舍:";			
			for (int i = 0; i < d.getNumber();) {
				if (infolist.size() == 0) {
					break loop;
				}
				String tempinfo = infolist.remove(0);
				String[] cn = tempinfo.split(":");
				String mcname = cn[0];
				String classID = cn[1];
				int neednumber = Integer.parseInt(cn[2]);
				i += neednumber;
				DormitoryAllocation da = new DormitoryAllocation();
				da.setDormitoryId(d.getId());
				da.setClassId(classID);
				if (i > d.getNumber()) {
					da.setNumber(neednumber - (i - d.getNumber()));
					String newneednumber=String.valueOf(neednumber - da.getNumber());
					addFirstList(infolist,mcname+":"+classID+":"+newneednumber);
				} else {
					da.setNumber(neednumber);
				}
				if(da.getNumber()!=0) {
					das.add(da);
					successword+=mcname+"分配"+da.getNumber()+"个床位、";
					
				}
				da = null;
			}
		}
		
		int i = this.dormitoryAllocationService.insertDivideDormitory(das);
		if (i > 0) {
			successword.substring(0, successword.length() - 1);
			return RespBean.ok(successword);
		} else {
			return RespBean.error("添加失败！请稍后再试！");
		}
	}
	
	// 自定义方法将List中新增元素提到第一位
		public  void addFirstList(List<String> ddis, String ddi) {
			ddis.add(ddi);
			String newddi, oldddi = null;
//			取出第一个存起来
			oldddi = ddis.get(0);
			ddis.set(0, ddi);
			for (int i = 1; i < ddis.size(); i++) {
				newddi = ddis.get(i);
				ddis.set(i, oldddi);
				oldddi = newddi;
			}
		}
	

		
		
}
