package com.he.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.Department;
import com.he.po.Dept;
import com.he.po.Major;
import com.he.po.Role;
import com.he.po.StudentStatus;
import com.he.po.Teacher;
import com.he.service.ClassService;
import com.he.service.DepartementService;
import com.he.service.DeptService;
import com.he.service.MajorService;
import com.he.service.StudentStatusService;
import com.he.service.TeacherService;
import com.he.viewpo.NewClass;

@RequestMapping("/teacher/teaclassinfocontroller")
@RestController
public class ClassInfoController {
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private MajorService majorService;
	
	@Autowired
	private DepartementService departementService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentStatusService studentStatusService;
	/**
	 * 获取所有班级信息
	 * @return
	 */
	@RequestMapping("/getallclass")
	public List<NewClass> getAllClass(NewClass nc){
		return this.classService.getAllClass(nc);
	}
	
	/**
	 * 初始化获取所有专业、系、部门信息
	 * @return
	 */
	@Transactional
	@RequestMapping("/initdata")
	public ArrayList initdata(){
		ArrayList list=new ArrayList();
		List<Major> majors=this.majorService.selectAllMajor(new Major());
		List<Department> departments=this.departementService.getAllDepartement(new Department());
		List<Dept> depts=this.deptService.getAllDept();
		
		//查询班主任信息
		Role r=new Role();
		r.setName("班主任");
		
		List<Teacher> teachers=this.teacherService.getAllTeacherByRole(r);
		
		list.add(majors);
		list.add(departments);
		list.add(depts);
		list.add(teachers);
		
		return list;
	}
	
	/**
	 * 获取班级的学生信息
	 * @return
	 */
	@RequestMapping("/getclassstudent")
	public List<StudentStatus> getAllClassStudent(String id){
		List<StudentStatus> sss= this.studentStatusService.selectStudentByClassID(id);
		return sss;
	}
	
	
	/**
	 * 根据id获取班主任详细信息
	 * @return
	 */
	@RequestMapping("/getteacherinfo")
	public Teacher getteacherinfo(String id){
		return this.teacherService.selectTeacherById(id);
	}
	
	/**
	 * 根据id修改班级的班主任
	 * @return
	 */
	@Transactional
	@RequestMapping("/updatedirector")
	public int updatedirector(String tid,String id){
		return this.classService.updatedirector(tid, id);
	}
	
	
}
