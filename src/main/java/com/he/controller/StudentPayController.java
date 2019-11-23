package com.he.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.he.po.Apply;
import com.he.po.Applyfile;
import com.he.po.Dormitory;
import com.he.po.Major;
import com.he.po.Pay;
import com.he.po.RespBean;
import com.he.po.StudentStatus;
import com.he.po.Users;
import com.he.service.ApplyService;
import com.he.service.ApplyfileService;
import com.he.service.ClassNoticeService;
import com.he.service.DormitoryService;
import com.he.service.MajorService;
import com.he.service.PayService;
import com.he.service.StudentStatusService;
import com.he.tool.GetLoginUserInfo;

@RequestMapping("/student/studentpay")
@RestController
public class StudentPayController {

	@Autowired
	private StudentStatusService studentStatusService;

	@Autowired
	private DormitoryService dormitoryService;

	@Autowired
	private PayService payService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private ApplyService applyService;

	@Autowired
	private ApplyfileService applyfileService;

	@Value("${localfile}") // 取配置文件地址值
	private String localfile;

	@Value("${showfile}") // 取配置文件地址值
	private String showfile;
	
	
	
	
	@RequestMapping("/initdata")
	public List initData() {
		List list = new ArrayList<>();
		Users u = GetLoginUserInfo.getLoginUserInfo();
		StudentStatus s = this.studentStatusService.getStudentStatusByUsers(u);
		if(s.getStudentId().length()==8) {
			list.add(false);
		}else {	
		list.add(s);
		List<Dormitory> drs = this.dormitoryService.selectDormitoryByStudentId(s.getStudentId());
		list.add(drs.get(0));

		Major m = this.majorService.selectByPrimaryKey(s.getC().getMajorId());
		Pay pay = new Pay();
		pay.setStudentId(s.getStudentId());
		//查询该学号转账记录
		Pay ps = this.payService.getPayBystudentId(pay);
		
		list.add(ps);

		Apply apply = new Apply();
		apply.setStudentId(s.getStudentId());
		List<Apply> as = this.applyService.selectApply(apply);
		if (as.size() > 0) {
			list.add(as.get(0));
		} else {
			list.add(null);
		}
		}
		return list;
	}

	/**
	 * 查询申请文件，查询所有则不传参数
	 * @param af
	 * @return
	 */
	@RequestMapping("/getApplyfile")
	public List<Applyfile> getApplyFile(Apply a){
		return this.applyfileService.selectApplyfile(a);
	}
	
	
	
	// 上传文件//包含修改功能，根据id判断为空则第一次申请并上传文件，0则重新全部上传，非0非空则单独重新上传
	// @RequestParam("files") MultipartFile[] files,
	@RequestMapping("/upload")
	@Transactional
	public RespBean uploadFile(final HttpServletResponse response, String id, final HttpServletRequest request) {
		Users u = GetLoginUserInfo.getLoginUserInfo();
		StudentStatus s = this.studentStatusService.getStudentStatusByUsers(u);
		Apply apply = new Apply();
		apply.setState("未审核");
		apply.setStudentId(s.getStudentId());
		if (id.equals("")) { // 初始申请
			int ai = this.applyService.insertApply(apply);
		}

		boolean isdelete=true;//重新上传时，第一次删除全部就可以了，后续不用删除
		List<Apply> as = this.applyService.selectApply(apply);
		int applyid = as.get(0).getId();

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");

		if (files != null && files.size() > 0) {
			for (MultipartFile file : files) {
				int i = 0;

				String suffix =UUID.randomUUID()
						+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
				Applyfile af = new Applyfile();
				af.setFileurl(suffix);
				System.out.println("/student/studentpay/show?fileName="+suffix);
				String oldfilename = file.getOriginalFilename();
				af.setFilename(oldfilename);
				if(id.equals("")) {
					
					List<Applyfile> afs = new ArrayList<>();
					af.setApplyId(applyid);
					afs.add(af);
					i = this.applyfileService.insertApplyFileByList(afs);
				}else if(id.equals("0")) {	
					//重新上传全部文件，先删除
					List<Applyfile> afs = new ArrayList<>();
					af.setApplyId(applyid);
					af.setRemarks("已重新上传！");
					afs.add(af);
					if(isdelete) {
						this.applyfileService.deleteApplyFileByList(afs);
						isdelete=false;
					}					
					i = this.applyfileService.insertApplyFileByList(afs);
				}else {
					af.setRemarks("已重新上传！");
					af.setId(Integer.parseInt(id));
					i=this.applyfileService.updateAppliFile(af);	
				}
				
				// 服务器地址
				String filename = request.getServletContext().getRealPath("applyfile") + File.separator + suffix;
				// 本地项目地址
				String nowfilelocal = localfile + suffix;
				FileOutputStream fos = null;
				if (i > 0) {

					try {
//								//保存到项目服务器
						// FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filename));
						// 保存到本地

						// FileUtils.copyInputStreamToFile(file.getInputStream(), new File(localfile));
						fos = new FileOutputStream(nowfilelocal);
						fos.write(file.getBytes()); // 写入文件
					} catch (Exception e) {

						return RespBean.error(e.getMessage());
					} finally {
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					return RespBean.error("上传失败！请稍后再试！或联系班主任！");
				}
			}
		}

		return RespBean.ok("上传成功!");

	}

	/**
	 * 下载文件
	 * 
	 * @param fileName
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/download")
	public Object downloadFile(@RequestParam String fileurl, final HttpServletResponse response,
			final HttpServletRequest request) {
		OutputStream os = null;
		InputStream is = null;
		try {
			// 取得输出流
			os = response.getOutputStream();
			// 清空输出流
			response.reset();
			response.setContentType("application/x-download;charset=GBK");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileurl.getBytes("utf-8"), "iso-8859-1"));
			// 读取流
			File f = new File(localfile + fileurl);
			is = new FileInputStream(f);
			if (is != null) {
				// 复制
				IOUtils.copy(is, response.getOutputStream());
				response.getOutputStream().flush();
			}
		} catch (IOException e) {
		}
		// 文件的关闭放在finally中
		finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// 返回提示 logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				// 返回提示 logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
		return null;
	}

//	/**
//     * 显示单张图片
//     * @return
//     */
//    @RequestMapping("/show")
//    public ResponseEntity showPhotos(String fileName){
//
//        try {
//            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
//        	System.out.println(showfile + fileName);
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + showfile + fileName));
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

	
	
}
