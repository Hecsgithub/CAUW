package com.he.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.he.po.Major;
import com.he.po.RespBean;
import com.he.po.StudentBasic;
import com.he.service.MajorService;
import com.he.service.StudentBasicService;
import com.he.service.StudentStatusService;
import com.he.viewpo.MajorAndStudent;
import com.he.viewpo.ProvincialNumber;

/**
 * 教务处管理学生基础信息
 * 教务处分配班级、学号、宿舍
 * @author Administrator
 *
 */
@RequestMapping("/teacher/teacherstudentbasic")
@RestController
public class StudentController {

	
	@Autowired
	private StudentBasicService studentBasicService;
	
	@Autowired
	private StudentStatusService studentStatusService;
	
	@Autowired
	private MajorService majorService;

	/**
	 * 1.导入excel，导入学生基础信息同时生成对应的学籍信息
	 * 
	 * @param file
	 * @return
	 */

	@RequestMapping("/addstudentbasic")
	public RespBean addStudentBasic(MultipartFile file) {
		String filename = file.getOriginalFilename();
		RespBean result = this.studentBasicService.addStudentBasic(filename, file);
		return result;
	}

	/**
	 * 2.导出excel到本地
	 */
	@RequestMapping("/exportdstudentbasic")
	public ResponseEntity<byte[]> exportExcel(HttpServletResponse response,String exportname) {
		return this.studentBasicService.exportExcel(response, exportname);
	}
	
	/**
	 * 3.取得未分班学生专业数量
	 */
	@RequestMapping("/showmajors")
	public List<MajorAndStudent> DistributionClasses() {
		/**
		 * 获取未分班的学生专业数量
		 */
		List<MajorAndStudent> maslist=new ArrayList<>();
		List<String> majors=this.studentBasicService.selectCountMajor();
		StudentBasic sb=new StudentBasic();
		for(String major:majors) {
			MajorAndStudent mas=new MajorAndStudent();
			mas.setMajor(major);
			sb.setMajor(major);
			sb.setSex("男");
			List<StudentBasic> sbs=this.studentBasicService.selectStudentBasicBySexAndMajor(sb);
			mas.setBoynumber(sbs.size());
			sb.setSex("女");
			sbs=this.studentBasicService.selectStudentBasicBySexAndMajor(sb);
			mas.setGirlnumber(sbs.size());
			maslist.add(mas);
			sbs=null;
			mas=null;
		}
		sb=null;
		return maslist;
	}
	
	/**
	 * 获取数据库中的专业名称数据
	 */
	@RequestMapping("/selectmajor")
	public List<Major> selectmajor() {
		return this.majorService.selectAllMajor(new Major());
	}
	
	
	/**
	 * 获取数据库中的学生基础数据
	 */
	@RequestMapping("/selectallstudentbasic")
	public List<StudentBasic> selectAllStudentBasicHas(StudentBasic sb) {
		return this.studentBasicService.selectAllStudentBasicHas(sb);
	}
	
	/**
	 * 获取数据库中的全部学生基础数据并统计
	 */
	@RequestMapping("/selectallstudentbasicTJ")
	public List selectAllStudentBasicHasTJ() {
		List<StudentBasic> sbs=this.studentBasicService.selectAllStudentBasicHas(new StudentBasic());
		ProvincialNumber pn=new ProvincialNumber();
		return pn.getprovincialList(sbs);
	}

	
	
	
	
	
	/**
	 * 新增或删除数据库中的学生基础数据
	 */
	@Transactional
	@RequestMapping("/addorupdatestudentbasic")
	public RespBean addorupdatestudentbasic(StudentBasic sb,String update) {
		int i=-1;
		if(!update.equals("update")) {
			i=this.studentBasicService.insertSelective(sb);
			if(i>0) {
				return RespBean.ok("新增成功");
			}else {
				return RespBean.error("新增失败！");
			}	
		}else {
			i=this.studentBasicService.updateByPrimaryKeySelective(sb);
			if(i>0) {
				return RespBean.ok("修改成功！");
			}else {
				return RespBean.error("修改失败！");
			}	
		}	
		 
	}
	
	
	/**
	 * 批量删除数据
	 */
	@Transactional
	@RequestMapping("/deletestudentbasic")
	public RespBean deleteListStudentBasic(String id) {
		int i=-1;
		String[] tempid= id.split(",");;

		List<Integer>idlist =new ArrayList<>();
		
		for(int s=0;s<tempid.length;s++) {
			idlist.add(Integer.parseInt(tempid[s]));
		}
		i=this.studentBasicService.deleteListStudentBasic(idlist);
		if(i>0) {
			return RespBean.ok("删除成功！");
		}else {
			return RespBean.error("删除失败！");
		}	
	}
	
	
	/**
	 * 导出学生班级信息
	 */
	@RequestMapping("/exportStudentStatusExcel")
	public ResponseEntity<byte[]> exportStudentStatusExcel(HttpServletResponse response,String classId,String exportname) {
		return this.studentStatusService.exportStudentStatusExcel(response,classId, exportname);
	}
	
	
	
}
