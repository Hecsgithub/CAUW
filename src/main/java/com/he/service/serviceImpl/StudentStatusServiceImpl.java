package com.he.service.serviceImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.he.dao.ClassMapper;
import com.he.dao.StudentBasicMapper;
import com.he.dao.StudentStatusMapper;
import com.he.po.Major;
import com.he.po.StudentBasic;
import com.he.po.StudentStatus;
import com.he.po.Users;
import com.he.po.Class;
import com.he.service.StudentStatusService;
import com.he.tool.ExportExcel;
import com.he.viewpo.DDMC;
import com.he.viewpo.MajorAndStudent;



@Service
public class StudentStatusServiceImpl implements StudentStatusService {
	@Autowired
	private StudentStatusMapper studentStatusMapper;
	
	@Autowired
	private StudentBasicMapper studentBasicMapper;

	@Autowired
	private ClassMapper classMapper;
	
	/**
	 * 1.初始化学籍表，对应基础信息表
	 */
	
	@Override
	public int insertInitStudentStatus(List<StudentStatus> sss) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.insertInitStudentStatus(sss);
	}

	/**
	 * 2.修改学籍表，填补初始学号，班级
	 */
	
	@Override
	public int updateInitStudentIDandClass(List<StudentStatus> sss) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.updateInitStudentIDandClass(sss) ;
	}

	/**
	 * 3.查询某个班级的所有学生
	 */
	@Override
	public List<StudentStatus> selectStudentByClassID(String classid) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.selectStudentByClassID(classid);
	}

	
	/**
	 *4. 按专业查询男女生
	 */
	
	@Override
	public List<MajorAndStudent> selectStudentStatusByMajor() {
		// TODO Auto-generated method stub
		List<MajorAndStudent> mass=new ArrayList<>();
		List<String> majors=this.studentBasicMapper.selectCountMajor();
		
		StudentBasic sb=new StudentBasic();
		for(String major:majors) {
			MajorAndStudent mas=new MajorAndStudent();
			mas.setMajor(major);
			sb.setMajor(major);
			sb.setSex("男");
			mas.setBoysss(this.studentStatusMapper.selectStudentBySexAndMajor(sb));
			sb.setSex("女");
			mas.setGirlsss(this.studentStatusMapper.selectStudentBySexAndMajor(sb));
			if(mas.getBoynumber()>0||mas.getGirlnumber()>0) {
				mass.add(mas);
			}
		}
		
		return mass;
	}

	@Override
	public List<StudentStatus> getStudentStatus(DDMC ddmc) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.getStudentStatus(ddmc);
	}

	@Override
	public int updatelistclassformstudent(List<Integer> id,String classId) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.updatelistclassformstudent(id,classId);
	}

	@Override
	public List<MajorAndStudent> selectMajorAndStudentSun() {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.selectMajorAndStudentSun();
	}

	@Override
	public List<StudentStatus> getStudentByMajorId(String classId) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.getStudentByMajorId(classId);
	}

	@Override
	public List<StudentStatus> getStudentByStudentStatus(List<StudentStatus> sss) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.getStudentByStudentStatus(sss);
	}

	@Override
	public List<StudentStatus> selectStudentBySexAndClass(String sex, String classid) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.selectStudentBySexAndClass(sex, classid);
	}

	@Override
	public StudentStatus getMaxStudentId(StudentStatus s) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.getMaxStudentId(s);
	}

	@Override
	public int UpdateStudentId(StudentStatus s) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.UpdateStudentId(s);
	}

	@Override
	public StudentStatus getStudentStatusByUsers(Users u) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.getStudentStatusByUsers(u);
	}

	@Override
	public StudentStatus selectByPrimaryKey(String studentId) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.selectByPrimaryKey(studentId);
	}

	@Override
	public StudentStatus getStudentStatusByStudentBasic(StudentBasic sb) {
		// TODO Auto-generated method stub
		return this.studentStatusMapper.getStudentStatusByStudentBasic(sb);
	}

	// 16.导出学生班级信息到Excel
	@Override
	public ResponseEntity<byte[]> exportStudentStatusExcel(HttpServletResponse response, 
			String classId,String filename) {
		// TODO Auto-generated method stub
		
			//获取专业
			List<Class> cs=this.classMapper.selectByClassId(classId);
			
			// 创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			
			for(Class c:cs) {
				// 创建表
				HSSFSheet sheet = workbook.createSheet(c.getM().getName()+c.getName()+"学生信息");
				// 创建行
				HSSFRow row = sheet.createRow(0);
				// 创建单元格样式
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				// 表头
				String[] head = {"姓名","学号","性别","出生年月", "联系号码", "身份证号", "邮箱", "监护人号码", "家庭地址"};
				HSSFCell cell;
				// 设置表头
				for (int iHead = 0; iHead < head.length; iHead++) {
					cell = row.createCell(iHead);
					cell.setCellValue(head[iHead]);
					cell.setCellStyle(cellStyle);
				}
				String classid =c.getClassId();
				//获取该班级学生
			    List<StudentStatus> sss=this.studentStatusMapper.selectStudentByClassID(classid);		    
				// 设置表格内容
				Field[] fields = null;
				for (int iBody = 0; iBody < sss.size(); iBody++) {
					row = sheet.createRow(iBody + 1);
					StudentStatus u = sss.get(iBody);
//					获取属性数量
					fields = u.getClass().getDeclaredFields();
					String[] userArray = new String[9];
					userArray[0] = u.getSb().getName();
					userArray[1] = u.getStudentId();
					userArray[2] = u.getSb().getSex();
					userArray[3] = u.getSb().getBirthday();
					userArray[4] = u.getSb().getPhone();
					userArray[5] = u.getSb().getIdNumber();
					userArray[6] = u.getSb().getEmail();
					userArray[7] = u.getSb().getParentPhone();
					userArray[8] = u.getSb().getAdress();
					for (int iArray = 0; iArray < userArray.length; iArray++) {
						row.createCell(iArray).setCellValue(userArray[iArray]);
					}
				}
				
			}
				
			// 生成Excel文件

			return ExportExcel.createFile(response, workbook, filename);

	}
	
	
	
	
	
}
