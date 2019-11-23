package com.he.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.viewpo.DDMC;
import com.he.viewpo.MajorAndStudent;
import com.he.po.Class;
import com.he.po.Department;
import com.he.po.Dept;
import com.he.po.Major;
import com.he.po.RespBean;
import com.he.po.StudentBasic;
import com.he.po.StudentStatus;
import com.he.service.ClassService;
import com.he.service.DepartementService;
import com.he.service.DeptService;
import com.he.service.MajorService;
import com.he.service.StudentBasicService;
import com.he.service.StudentStatusService;

/**
 * 新生分班级，初始化学号
 * 
 * @author Administrator
 *
 */
@RequestMapping("/teacher/teacherclass")
@RestController
public class CLassController {

	@Autowired
	private MajorService majorService;

	@Autowired
	private DepartementService departementService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private ClassService classService;

	@Autowired
	private StudentBasicService studentBasicService;

	@Autowired
	private StudentStatusService studentStatusService;

	/**
	 * 接受前端分班数据，初始化分班数据
	 */
	@Transactional
	@RequestMapping("/initclass")
	public RespBean InitClass(String finanumber) {
		List<MajorAndStudent> mass = new ArrayList<>();
		String[] tempid = finanumber.split(",");
		for (String a : tempid) {
			String[] majorandclassnumber = a.split(":");
			MajorAndStudent mas = new MajorAndStudent();
			mas.setMajor(majorandclassnumber[0]);
			mas.setClassnumber(Integer.parseInt(majorandclassnumber[1]));
			mass.add(mas);
			mas = null;
		}
		// 获取年级
		Class clas = new Class();
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
//	     设置年级
		clas.setGrade(year + "级");

		if (mass.size() > 0) {
			for (MajorAndStudent mas : mass) {
				List<Class> classs = new ArrayList<>();
//			     设置专业编号
				clas.setMajorId(this.majorService.selectMajorIDBuName(mas.getMajor()));
//			     设置初始班级编号开头为专业编号+年级
				clas.setClassId(clas.getMajorId() + year);

				/**
				 * 开始分配具体人员
				 */
				StudentBasic sb = new StudentBasic();
				sb.setMajor(mas.getMajor());
				sb.setSex("男");
				// 获取该专业男生数量
				List<StudentBasic> boylist = this.studentBasicService.selectStudentBasicBySexAndMajor(sb);
				sb.setSex("女");
				// 获取该专业女男生数量
				List<StudentBasic> girllist = this.studentBasicService.selectStudentBasicBySexAndMajor(sb);
				sb = null;

				// 获取该专业最终分班总情况
				List<List<StudentBasic>> alllists = ClassAssignment(boylist, girllist, mas.getClassnumber());

//			     取得该专业在专业表中的唯一编号初始化学号
				String majorid = this.majorService.selectIDBuMajorID(clas.getMajorId());
				if (Integer.parseInt(majorid) < 10) {
					majorid = "0" + majorid;
				}
				
				int nowclassnumber;
				//在建立班级前先查询目前该专业有无班级
				List<Class> oldclass=this.classService.selectClassByMajorID(clas.getMajorId());
				if(oldclass==null){
					nowclassnumber=0;
				}else {
					nowclassnumber=oldclass.size();
				}
				for (int i = 1; i <= mas.getClassnumber(); i++) {
					Class newclass = new Class();
					newclass.setGrade(clas.getGrade());
					newclass.setMajorId(clas.getMajorId());

					// 真正班级编号序数
					int xuhao=i+nowclassnumber;
					if (xuhao < 10) {
						clas.setClassId(clas.getClassId() + "0" +xuhao );
					} else {
						clas.setClassId(clas.getClassId() + xuhao);
					}
					newclass.setClassId(clas.getClassId());
					newclass.setName(xuhao + "班");
					classs.add(newclass);
					newclass = null;
					// 取到第i个班级具体人员
					List<StudentBasic> alllist = alllists.get(i - 1);

					List<StudentStatus> sss = new ArrayList<>();
					for (StudentBasic sginsb : alllist) {
						StudentStatus ss = new StudentStatus();
//			    		 取得该生基础信息编号
						ss.setBasicId(sginsb.getId());
//			    		 取得该生班级编号
						ss.setClassId(clas.getClassId());
//			    		 学号初始化，具体位数按照签到顺序编号

//			    		 取年份后两位
						String syear = String.valueOf(year);
						String newsyear = syear.substring(syear.length() - 2, syear.length()); // 截取

//			    		 取班号
						String sclass = String.valueOf(xuhao);
						if (xuhao < 10) {
							sclass = "0" + sclass;
						}

//			    		 设置自定义初始化学号

						ss.setStudentId(newsyear + "38" + majorid + sclass);
//						System.out.println(sclass + "班初始化学号" + ss.getStudentId());
						sss.add(ss);
						ss = null;
					}

					/**
					 * 修改学籍表，初始化班级编码与学生学号
					 */
					int result = this.studentStatusService.updateInitStudentIDandClass(sss);
					if (result <= 0) {
						return RespBean.error("分班失败！请重新检查数据！");
					}
//					修改一分配班级新生的分班状态
					int updateresult = this.studentBasicService.updateStateByStatus(sss);
					if (updateresult <= 0) {
						return RespBean.error("分班失败！请重新检查数据！");
					}
//				    再次 设置初始班级编号开头为专业编号+年级
					clas.setClassId(clas.getMajorId() + year);

					sss = null;
				}

				int j = this.classService.insertIntClass(classs);
				if (j <= 0) {
					return RespBean.error("分班失败！请重新检查数据！");
				}

				classs = null;

			}
		}
		clas = null;
		return RespBean.ok("划分班级成功！请尽快为班级安排辅导员！");
	}

	/**
	 * 返回新生专业集合
	 * 
	 * @return
	 */
	@RequestMapping("/getallmajorbynow")
	public List<MajorAndStudent> getAllMajorByNow() {
		return this.studentStatusService.selectStudentStatusByMajor();
	}

	/**
	 * 获取部门
	 * 
	 * @return
	 */
	@RequestMapping("/getDept")
	public List<Dept> getDept() {
		return this.deptService.getAllDept();
	}

	/**
	 * 根据部门返回系
	 * 
	 * @return
	 */
	@RequestMapping("/getDepartmentByDeptId")
	public List<Department> getDepartmentByDeptId(Department d) {
		return this.departementService.getAllDepartement(d);
	}

	/**
	 * 根据系返回专业
	 * 
	 * @return
	 */
	@RequestMapping("/getMajorByDepartmentId")
	public List<Major> getMajorByDepartmentId(Major m) {
		return this.majorService.selectAllMajor(m);
	}

	/**
	 * 根据专业返回班级
	 * 
	 * @return
	 */
	@RequestMapping("/getClassByMajorId")
	public List<Class> getClassByMajorId(String majorId) {
		List<Class> cs = this.classService.selectClassByMajorID(majorId);
		return cs;
	}

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

	/**
	 * 根据参数查询学生
	 * 
	 * @return
	 */
	@RequestMapping("/getStudentStatus")
	public List<StudentStatus> getStudentStatus(DDMC ddm) {
		return this.studentStatusService.getStudentStatus(ddm);
	}

	/**
	 * 根据参数将学生退出班级
	 * 
	 * @return
	 */
	@Transactional
	@RequestMapping("/deletelistclassformstudent")
	public RespBean deletelistclassformstudent(String id) {
		String[] tempid = id.split(",");
		List<Integer> idlist = new ArrayList<>();
		for (String tid : tempid) {
			idlist.add(Integer.parseInt(tid));
		}
		// 将学号与班级置初始化
		int i = this.studentStatusService.updatelistclassformstudent(idlist, "0");
		if (i > 0) {
			int k = this.studentBasicService.updateStudentBasicState(idlist);
			if (k > 0) {
				return RespBean.ok("移出成功！");
			} else {
				return RespBean.error("移出失败！请稍后再试！");
			}
		} else {
			return RespBean.error("移出失败！请稍后再试！");
		}
	}

	/**
	 * 先判断是否为排号在后面的班级
	 * 根据删除班级将学生退出班级并将学生学籍信息中的班级信息\初始学号重置
	 * 
	 * @return
	 */
	@Transactional
	@RequestMapping("/deleteclassandformstudent")
	public RespBean deleteclassandformstudent(String id) {
		
		Class nowc=this.classService.selectMajorMaxClass(id);
		if(nowc.getClassId().equals(id)) {		//此班级是该专业最后一个班级
			List<StudentStatus> sss = this.studentStatusService.selectStudentByClassID(id);
			if (sss.size() > 0) {
				//学籍编号集合
				List<Integer> idlist = new ArrayList<>();
				//信息表编号集合
				List<Integer> bidlist = new ArrayList<>();
				for (StudentStatus ss : sss) {
					bidlist.add(ss.getSb().getId());
					idlist.add(ss.getId());
				}
				// 将学号与班级置初始化
				int i = this.studentStatusService.updatelistclassformstudent(idlist, "0");
				if (i > 0) {
					// 并删除班级信息
					Class c = new Class();
					c.setClassId(id);
					int j = this.classService.deleteClass(c);
					if (j > 0) {
						int k = this.studentBasicService.updateStudentBasicState(idlist);
						if (k > 0) {
							return RespBean.ok("删除班级成功");
						} else {
							return RespBean.error("删除失败！请稍后再试！");
						}

					} else {
						return RespBean.error("删除失败！请稍后再试！");
					}
				} else {
					return RespBean.error("删除失败！请稍后再试！");
				}
			} else {
				// 并删除班级信息
				Class c = new Class();
				c.setClassId(id);
				int j = this.classService.deleteClass(c);
				if (j > 0) {
					return RespBean.ok("删除班级成功");
				} else {
					return RespBean.error("删除失败！请稍后再试！");
				}
			}
		}else {
			return RespBean.error("删除失败！只能从后往前开始删除班级！");
		}
		
	}

	/**
	 * 根据参数查询所有未加入班级学生数量及专业
	 * 
	 * @return
	 */
	@RequestMapping("/getMajorAndStudentSun")
	public List<MajorAndStudent> selectMajorAndStudentSun() {
		return this.studentStatusService.selectMajorAndStudentSun();
	}

	/**
	 * 根据参数查询专业未加入班级学生
	 * 
	 * @return
	 */
	@RequestMapping("/getNoCLassStudentSunByMajorId")
	public MajorAndStudent getNoCLassStudentSunByMajorId(String classId) {
		MajorAndStudent mas = new MajorAndStudent();
		Class c = new Class();
		c.setClassId(classId);
		List<Major> ms = this.classService.selectMajorList(c);
		List<StudentStatus> sss = this.studentStatusService.getStudentByMajorId(ms.get(0).getMajorId());
		mas.setAllsss(sss);
		mas.setMajor(ms.get(0).getName());
		return mas;
	}

	/**
	 * 根据参数学生加入班级
	 * 
	 * @return
	 */
	@Transactional
	@RequestMapping("/addStudentCLassByid")
	public RespBean addStudentCLassByid(String id, String classId) {
		int i = -1;

		Class c = new Class();
		c.setClassId(classId);
		List<Major> major = this.classService.selectMajorList(c);
		// 专业表id
		int majorid = major.get(0).getId();
		String smid = "";
		if (majorid < 10) {
			smid = "0" + majorid;
		} else {
			smid = String.valueOf(majorid);
		}
		// 班号
		String claid = classId.substring(classId.length() - 2, classId.length());
		// 年级
		String yearid = classId.substring(classId.length() - 4, classId.length() - 2);

		// 初始学号
		String studentId = yearid + "38" + smid + claid;

		
		String[] tempid = id.split(",");
		List<StudentStatus> sss = new ArrayList<>();
		for (String tid : tempid) {
			StudentStatus ss=new StudentStatus();
			ss.setId(Integer.parseInt(tid));
			ss.setClassId(classId);
			ss.setStudentId(studentId);
			sss.add(ss);
			ss=null;
		}

		int result = this.studentStatusService.updateInitStudentIDandClass(sss);
		if (result >0) {
			return RespBean.ok("加入班级成功");
		} else {
			return RespBean.error("加入失败！请稍后再试！");
		}
	}

	/**
	 * 根据新建班级
	 * 
	 * @return
	 */
	@Transactional
	@RequestMapping("/getNewClass")
	public RespBean getNewClass(Class c) {
		int i = -1;
		List<Class> classs = new ArrayList<>();
		classs.add(c);
		i = this.classService.insertIntClass(classs);
		if (i > 0) {
			return RespBean.ok("新建班级成功");
		} else {
			return RespBean.error("新建失败！请稍后再试！");
		}
	}

	/**
	 * 分班算法
	 * 
	 * @param boylist
	 * @param girllist
	 * @param classnumber
	 * @return
	 */
	public List<List<StudentBasic>> ClassAssignment(List<StudentBasic> boylist, List<StudentBasic> girllist,
			int classnumber) {

//		总分班集合男
		List<List<StudentBasic>> alllistboy = new ArrayList<>();

//		总分班集合女
		List<List<StudentBasic>> alllistgirl = new ArrayList<>();

//		总分班集合
		List<List<StudentBasic>> alllist = new ArrayList<>();

		/**
		 * 男生分班
		 */

		// 集合长度
		int size = boylist.size();
		// 余数
		int remaider = size % classnumber;
		// 商
		int number = size / classnumber;
		int offset = 0;// 偏移量
		for (int i = 0; i < classnumber; i++) {
			List<StudentBasic> value = null;
			if (remaider > 0) {
				value = boylist.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = boylist.subList(i * number + offset, (i + 1) * number + offset);
			}
			alllistboy.add(value);
		}

		/**
		 * 女生分班
		 */

		// 集合长度
		size = girllist.size();
		// 余数
		remaider = size % classnumber;
		// 商
		number = size / classnumber;
		offset = 0;// 偏移量
		for (int i = 0; i < classnumber; i++) {
			List<StudentBasic> value = null;
			if (remaider > 0) {
				value = girllist.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = girllist.subList(i * number + offset, (i + 1) * number + offset);
			}
			alllistgirl.add(value);
		}

//       将男女分班合并到男生分班中，有可能最后一个班人数多二或少二

		for (int i = 0; i < classnumber; i++) {
			List<StudentBasic> temp = new ArrayList<>();
			temp.addAll(alllistboy.get(i));
			temp.addAll(alllistgirl.get(i));
			alllist.add(temp);
			temp = null;
		}

		return alllist;

	}

}
