package com.he.service.serviceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.he.dao.StudentBasicMapper;
import com.he.dao.StudentStatusMapper;
import com.he.dao.UserRoleMapper;
import com.he.dao.UsersMapper;
import com.he.po.RespBean;
import com.he.po.StudentBasic;
import com.he.po.StudentStatus;
import com.he.po.UserRole;
import com.he.po.Users;
import com.he.service.StudentBasicService;
import com.he.tool.ExportExcel;
import com.he.viewpo.NewUsers;

@Service
public class StudentBasicServiceImpl implements StudentBasicService {

	@Autowired
	private StudentBasicMapper studentBasicMapper;

	@Autowired
	private StudentStatusMapper studentStatusMapper;

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private UserRoleMapper userroleMapper;

	// 1. 导入excel

	private final static String XLS = "xls";
	private final static String XLSX = "xlsx";

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public RespBean addStudentBasic(String filename, MultipartFile file) {
		// TODO Auto-generated method stub
		List<StudentBasic> sbs = new ArrayList<>();
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = null;
			if (filename.endsWith(XLS)) {
				workbook = new HSSFWorkbook(is);
			} else if (filename.endsWith(XLSX)) {
				workbook = new XSSFWorkbook(is);
			} else {
				return RespBean.error("上传文件格式不对！");
			}

			// 获取工作表
			int sheets = workbook.getNumberOfSheets();
			for (int i = 0; i < sheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				if (sheet == null) {
					continue;
				}
				int rows = sheet.getPhysicalNumberOfRows();
				StudentBasic sb;
				// 遍历每一行，第 0 行为标题
				System.out.println("123456576879");
				System.out.println("进入遍历方法");
				for (int j = 1; j < rows; j++) {
					sb = new StudentBasic();
					// 获得第i行数据

					Row row = sheet.getRow(j);
					System.out.println("row=" + row.getCell(0));

					sb.setName(row.getCell(1).getStringCellValue());
					System.out.println("Name=" + sb.getName());

					sb.setSex(row.getCell(2).getStringCellValue());
					System.out.println("Sex=" + sb.getSex());

					sb.setBirthday(row.getCell(3).getStringCellValue());
					System.out.println("Birthday=" + sb.getBirthday());

					sb.setPhone(row.getCell(4).getStringCellValue());
					System.out.println("Phone=" + sb.getPhone());

					sb.setIdNumber(row.getCell(5).getStringCellValue());
					System.out.println("IdNumber=" + sb.getIdNumber());

					sb.setEmail(row.getCell(6).getStringCellValue());
					System.out.println("Email=" + sb.getEmail());

					sb.setParentPhone(row.getCell(7).getStringCellValue());
					System.out.println("ParentPhone=" + sb.getParentPhone());

					sb.setAdress(row.getCell(8).getStringCellValue());
					System.out.println("Adress=" + sb.getAdress());

					sb.setMajor(row.getCell(9).getStringCellValue());
					System.out.println("Major=" + sb.getMajor());

					sb.setState(row.getCell(10).getStringCellValue());
					System.out.println("State=" + sb.getState());

					// 加入前判断有无重复//只判断身份证号
					boolean isok = true;
					for (StudentBasic teb : sbs) {
						if (sb.getIdNumber().equals(teb.getIdNumber())) {
							isok = false;
							break;
						}
					}
					if (isok) {
						sbs.add(sb);
					}

				}
//				插入studentbasic表
				// 插入前数据库查重
				Iterator<StudentBasic> it = sbs.iterator();
				while (it.hasNext()) {
					StudentBasic tempsb = it.next();

					// 查重只查询
					List<StudentBasic> ssblist = this.studentBasicMapper.selectStudentBasicHasIdentical(tempsb);
					if (ssblist.size() > 0) {
						it.remove();
					}
				}
				if (sbs.size() == 0) {
					return RespBean.error("导入失败！请确实是否已导入数据库或者文件中是否有数据");
				} else {
					int resultNumber = this.studentBasicMapper.insertExcelStudentBasic(sbs);
					if (resultNumber > 0) {
						StudentBasic tempsb = new StudentBasic();
						tempsb.setState("0");
						List<StudentBasic> newSbs = this.studentBasicMapper.selectAllStudentBasic(tempsb);
						List<StudentStatus> sss = new ArrayList<>();
						for (StudentBasic newsb : newSbs) {
							StudentStatus ss = new StudentStatus();
							ss.setBasicId(newsb.getId());
							ss.setStudentId("0");
							ss.setClassId("0");
							sss.add(ss);
						}

						// 将学生身份证号与姓名作为系统账号与密码
						List<Users> us = new ArrayList<>();
						for (StudentBasic newsb : newSbs) {
							Users u = new Users();
							String username = newsb.getIdNumber();
							u.setUsername(username);
							String password = username.substring(username.length() - 6, username.length());
							u.setPassword(password);
							u.setType("student_basic");
							us.add(u);
						}
//						初始化学籍表
						int resultStatusNumber = this.studentStatusMapper.insertInitStudentStatus(sss);

						if (resultStatusNumber > 0) {
							// 插入users表中
							int initusers = this.usersMapper.initUsers(us);
							if (initusers > 0) {
								List<UserRole> urs = new ArrayList<>();
								NewUsers nu = new NewUsers();
								nu.setType("student_basic");
								List<NewUsers> nus = this.usersMapper.getAllUsers(nu);
								for (NewUsers newnu : nus) {
									UserRole ur = new UserRole();
									ur.setRoleId(1);
									ur.setUserId(newnu.getId());
									urs.add(ur);
								}
								int k = this.userroleMapper.insertListUserRole(urs);
								if (k > 0) {
									return RespBean.ok("导入成功！");
								} else {
									throw new Exception("导入失败！");
								}
							} else {
								throw new Exception("导入失败！");
							}

						} else {
							throw new Exception("导入失败！");
						}

					} else {
						throw new Exception("导入失败！");
					}
				}
			}
		} catch (Exception e) {
			return RespBean.error("导入失败！" + e.getMessage());
		} finally {
		}

		return RespBean.ok("导入成功！");
	}

	// 2.导出学生基础信息到Excel

	public ResponseEntity<byte[]> exportExcel(HttpServletResponse response, String fileName) {

		List<StudentBasic> sbs = this.studentBasicMapper.selectAllStudentBasic(new StudentBasic());
		// 创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建表
		HSSFSheet sheet = workbook.createSheet("学生基础信息");
		// 创建行
		HSSFRow row = sheet.createRow(0);
		// 创建单元格样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		// 表头
		String[] head = { "编号", "姓名", "性别", "出生年月", "联系号码", "身份证号", "邮箱地址", "监护人号码", "地址", "专业", "状态" };
		HSSFCell cell;
		// 设置表头
		for (int iHead = 0; iHead < head.length; iHead++) {
			cell = row.createCell(iHead);
			cell.setCellValue(head[iHead]);
			cell.setCellStyle(cellStyle);
		}
		// 设置表格内容
		Field[] fields = null;
		for (int iBody = 0; iBody < sbs.size(); iBody++) {
			row = sheet.createRow(iBody + 1);
			StudentBasic u = sbs.get(iBody);
//			获取属性数量
			fields = u.getClass().getDeclaredFields();
			String[] userArray = new String[fields.length];
			userArray[0] = String.valueOf(u.getId());
			userArray[1] = u.getName();
			userArray[2] = u.getSex();
			userArray[3] = u.getBirthday();
			userArray[4] = u.getPhone();
			userArray[5] = u.getIdNumber();
			userArray[6] = u.getEmail();
			userArray[7] = u.getParentPhone();
			userArray[8] = u.getAdress();
			userArray[9] = u.getMajor();
			userArray[10] = u.getState();
			for (int iArray = 0; iArray < userArray.length; iArray++) {
				row.createCell(iArray).setCellValue(userArray[iArray]);
			}
		}
		// 生成Excel文件

		return ExportExcel.createFile(response, workbook, fileName);

	}

	// 按性别与专业查询
	@Override
	public List<StudentBasic> selectStudentBasicBySexAndMajor(StudentBasic sb) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.selectStudentBasicBySexAndMajor(sb);
	}

	// 按专业查询
	@Override
	public List<StudentBasic> selectStudentBasicByMajor(StudentBasic sb) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.selectStudentBasicByMajor(sb);
	}

	// 专业数量查询
	@Override
	public List<String> selectCountMajor() {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.selectCountMajor();
	}

	// 7.根据身份证号查信息
	@Override
	public StudentBasic selectByIdNumber(String idNumber) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.selectByIdNumber(idNumber);
	}

	@Override
	public List<StudentBasic> selectAllStudentBasicHas(StudentBasic sb) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.selectAllStudentBasicHas(sb);
	}

	@Override
	public int updateByPrimaryKeySelective(StudentBasic record) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(StudentBasic record) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.insertSelective(record);
	}

	@Override
	public int deleteListStudentBasic(List<Integer> is) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.deleteListStudentBasic(is);
	}

	@Override
	public int updateStateByStatus(List<StudentStatus> sss) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.updateStateByStatus(sss);
	}

	@Override
	public List<StudentBasic> selectClassStudentBasic(StudentStatus ss) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.selectClassStudentBasic(ss);
	}

	@Override
	public List<StudentBasic> selectNoUsersStudentBasic() {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.selectNoUsersStudentBasic();
	}

	@Override
	public int updateStudentBasicState(List<Integer> ids) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.updateStudentBasicState(ids);
	}

	@Override
	public List<StudentBasic> selectStudentBasicHasIdentical(StudentBasic sb) {
		// TODO Auto-generated method stub
		return this.studentBasicMapper.selectStudentBasicHasIdentical(sb);
	}

}
