package com.he.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.Major;
import com.he.po.RespBean;
import com.he.po.StudentBasic;
import com.he.po.StudentStatus;
import com.he.po.Users;
import com.he.po.Class;
import com.he.po.ClassNotice;
import com.he.po.Dormitory;
import com.he.service.ClassNoticeService;
import com.he.service.ClassService;
import com.he.service.DormitoryAllocationService;
import com.he.service.DormitoryRegistrationService;
import com.he.service.DormitoryService;
import com.he.service.MajorService;
import com.he.service.StudentBasicService;
import com.he.service.StudentStatusService;
import com.he.tool.GetLoginUserInfo;
import com.he.viewpo.ProvincialNumber;


/**
 * 班主任相关控制器
 * @author Administrator
 *
 */
@RequestMapping("/headmaster")
@RestController
public class HeadmasterController {
	
	@Autowired
	private DormitoryService dormitoryService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private StudentStatusService studentStatusService;

	
	@Autowired
	private ClassNoticeService classNoticeService;
	
	
	@Autowired
	private StudentBasicService studentBasicService;
	
	
	/**
	 * 根据自身班主任信息获取所有班级返回树形专业班级信息
	 * @return
	 */
	@RequestMapping("/initTreeDataByHeadmaster")
	public List<Major> initTreeData(){
		Users u=GetLoginUserInfo.getLoginUserInfo();
		List<Major> allms=this.majorService.selectAllTreeMajorByUser(u);
		return allms;
	}
	
	/**
	 * 根据自身班主任的班级信息获取所有班级学生
	 * @return
	 */
	@RequestMapping("/getClassStudent")
	public List<StudentStatus> getClassStudent(String classId){
		List<StudentStatus> sss=this.studentStatusService.selectStudentByClassID(classId);
		return sss;
	}
	
	/**
	 * 根据学生学号信息获取宿舍
	 * @return
	 */
	@RequestMapping("/getStudentDormitory")
	public List<Dormitory> getStudentDormitory(String studentId){
		List<Dormitory> ds=new ArrayList<Dormitory>();
		ds=this.dormitoryService.selectDormitoryByStudentId(studentId);
		return ds;
	}
	
	/**
	 * 根据班级编号获取通知
	 * @return
	 */
	@RequestMapping("/getClassNotice")
	public List<ClassNotice> getClassNotice(String classId){
		List<ClassNotice> cns=this.classNoticeService.getAllClassBotice(classId);
		return cns;
	}
	
	/**
	 * 根据新增通知
	 * @return
	 */
	@Transactional
	@RequestMapping("/iauClassNotice")
	public RespBean iauClassNotice(ClassNotice cn ,String type){
		int i=-1;
		if(type.equals("update")) {
			i=this.classNoticeService.updateClassBotice(cn);
			if(i>0) {
				return RespBean.ok("修改成功！");
			}else {
				return RespBean.error("修改失败！");
			}
		}else {
			i=this.classNoticeService.insert(cn);
			if(i>0) {
				return RespBean.ok("新增成功！");
			}else {
				return RespBean.error("新增失败！");
			}
		}
	}
	
	/**
	 * 根据编号删除通知
	 * @return
	 */
	@Transactional
	@RequestMapping("/deletelistclassnotice")
	public RespBean deletelistclassnotice(String id){
		
		String[] tempid= id.split(",");

		List<Integer>idlist =new ArrayList<>();
		
		for(int s=0;s<tempid.length;s++) {
			idlist.add(Integer.parseInt(tempid[s]));
		}		
		int i=this.classNoticeService.deleteListClassNotice(idlist)	;	
		if(i>0) {
			return RespBean.ok("删除成功！");
		}else {
			return RespBean.error("删除失败！");
		}
	}
	
	
	/**
	 * 获取数据库中的全部学生基础数据并统计
	 */
	@RequestMapping("/selectallstudentbasicTJ")
	public List selectAllStudentBasicHasTJ(StudentStatus ss) {
		List list=new ArrayList<>();
		List<StudentBasic> sbs=this.studentBasicService.selectClassStudentBasic(ss);
		ProvincialNumber pn=new ProvincialNumber();
		List<StudentStatus> sss=this.studentStatusService.selectStudentByClassID(ss.getClassId());
		list=pn.getprovincialList(sbs);

		//		未签到
		List<StudentStatus> nosss=new ArrayList<>();
		int noreport=0;
		for(StudentStatus newss:sss) {
			if(newss.getStudentId().length()==8) {
				nosss.add(newss);
				noreport++;
			}
		}
		ProvincialNumber yespn=new ProvincialNumber();
		yespn.setName("已报到");
		yespn.setValue(sss.size()-noreport);
		ProvincialNumber nospn=new ProvincialNumber();
		nospn.setName("未报到");
		nospn.setValue(noreport);
		List reportlist=new ArrayList<>();
		reportlist.add(yespn);
		reportlist.add(nospn);
		//报到统计情况
		list.add(reportlist);
		
		//未报到学生名单
		list.add(nosss);
		return list;
	}
	
	
	
}
