package com.he.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.he.po.Apply;
import com.he.po.Applyfile;
import com.he.po.Dormitory;
import com.he.po.Pay;
import com.he.po.RespBean;
import com.he.po.StudentStatus;
import com.he.service.ApplyService;
import com.he.service.ApplyfileService;
import com.he.service.DormitoryService;
import com.he.service.PayService;
import com.he.service.StudentStatusService;

@RequestMapping("/teacher/teacherapply")
@RestController
public class ApplyController {
	
	@Autowired
	private ApplyService applyService;
	
	@Autowired
	private ApplyfileService applyfileService;
	
	@Autowired
	private DormitoryService dormitoryService;
	
	@Autowired
	private StudentStatusService studentStatusService;
	
	@Autowired
	private PayService payService;
	
	@Value("${localfile}") // 取配置文件地址值
	private String localfile;
	
	
	@RequestMapping("/getApply")
	public List<Apply> getApplyByState(Apply apply){
		return this.applyService.selectApply(apply);
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
	
	
	
	
	/**
	 * 修改某文件的意见
	 * @param af
	 * @return
	 */
	@RequestMapping("/addremarks")
	public int addremarks(Applyfile af){
		return this.applyfileService.updateAppliFile(af);
	}
	
	
	/**
	 * 修改某通知状态为已审核,斌修改其
	 * @param a
	 * @return
	 */
	@Transactional
	@RequestMapping("/applyok")
	public RespBean applyok(Apply a){
		int i=-1;
		a.setState("已审核");
		i=this.applyService.updateApply(a);
		if(i>0) {
			//取出宿舍费用
			List<Dormitory> drs = this.dormitoryService.selectDormitoryByStudentId(a.getStudentId());
			String rent=drs.get(0).getRent();
			//专业学费
			StudentStatus ss=this.studentStatusService.selectByPrimaryKey(a.getStudentId());
			String price=ss.getC().getM().getPrice();
			Pay pay=new Pay();
			pay.setRemarks("绿色通道");
			pay.setStudentId(a.getStudentId());
			pay.setTime(new Date());
			pay.setPipelineId("绿色通道无流水号");
			//费用
			pay.setMoney(String.valueOf(Integer.parseInt(rent)+Integer.parseInt(price)));
			int j=this.payService.insertPay(pay);
			if(j>0) {
				return RespBean.ok(ss.getSb().getName()+"同学已通过审核！");
			}else {
				return RespBean.error("请稍后再试！");
			}
		}else {
			return RespBean.error("请稍后再试！");
		}
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
			if (is == null) {
				// 返回提示 logger.error("下载附件失败，请检查文件“" + fileName + "”是否存在");
				// 返回提示return ResultUtil.error("下载附件失败，请检查文件“" + fileName + "”是否存在");
			}
			// 复制
			IOUtils.copy(is, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (IOException e) {
			// 返回提示return ResultUtil.error("下载附件失败,error:"+e.getMessage());
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

}
