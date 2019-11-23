package com.he.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.he.config.AlipayProperties;
import com.he.po.Dormitory;
import com.he.po.Pay;
import com.he.po.StudentStatus;
import com.he.po.Users;
import com.he.service.DormitoryService;
import com.he.service.PayService;
import com.he.service.StudentStatusService;
import com.he.tool.GetLoginUserInfo;

@Controller
@RequestMapping("/alipay/page")
public class AlipayPagePayController {
	
	@Autowired
	private AlipayProperties alipayProperties;
	
	 @Autowired
	 private AlipayClient alipayClient;
	 
	 @Autowired
	 private PayService payService;

	 @Autowired
	private StudentStatusService studentStatusService;
	 
	 @Autowired
	private DormitoryService dormitoryService;
	 
	 private String username;
	
	 @RequestMapping("/gotoPayPage")
    public void gotoPayPage(String username,String subject,String totalAmount,
    		String body, HttpServletResponse response) throws AlipayApiException, IOException {
		 this.username=username;
		 // 订单模型
        String productCode = "FAST_INSTANT_TRADE_PAY";
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(UUID.randomUUID().toString());
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setBody(body);
        model.setProductCode(productCode);

        AlipayTradePagePayRequest pagePayRequest =new AlipayTradePagePayRequest();
        pagePayRequest.setReturnUrl(alipayProperties.getReturnUrl());
        pagePayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        pagePayRequest.setBizModel(model);

        // 调用SDK生成表单, 并直接将完整的表单html输出到页面
        String form = alipayClient.pageExecute(pagePayRequest).getBody();
        response.setContentType("text/html;charset=" + alipayProperties.getCharset());
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }
	 
	 
	 /**
	     * 支付异步通知
	     *
	     * 接收到异步通知并验签通过后，一定要检查通知内容，包括通知中的app_id、out_trade_no、total_amount是否与请求中的一致，并根据trade_status进行后续业务处理。
	     *
	     * https://docs.open.alipay.com/194/103296
	     */
	 	@Transactional
	    @RequestMapping("/notify")
	    public String notify(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
	        // 一定要验签，防止黑客篡改参数
	        Map<String, String[]> parameterMap = request.getParameterMap();
	        StringBuilder notifyBuild = new StringBuilder("/****************************** alipay notify ******************************/\n");
	        parameterMap.forEach((key, value) -> notifyBuild.append(key + "=" + value[0] + "\n") );


	        boolean flag = this.rsaCheckV1(request);
	        if (flag) {
	            /**
	             * TODO 需要严格按照如下描述校验通知数据的正确性
	             *
	             * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	             * 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	             * 同时需要校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
	             *
	             * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
	             * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
	             * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
	             */

	            //交易状态
	            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
	            // 商户订单号
	            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
	            //支付宝交易号
	            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
	            //付款金额
	            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
	            // TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
	            // TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
	           
	           System.out.println("状态:"+tradeStatus+"-->s:"+out_trade_no+"z:"+trade_no+"j:"+total_amount); 
	           
	            
	            if(tradeStatus.equals("TRADE_FINISHED")){
	                //判断该笔订单是否在商户网站中已经做过处理
	                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
	                // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
	                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
	                //如果有做过处理，不执行商户的业务程序

	                //注意：
	                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
	                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
	            } else if (tradeStatus.equals("TRADE_SUCCESS")){
	                //判断该笔订单是否在商户网站中已经做过处理
	                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
	                // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
	                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
	                //如果有做过处理，不执行商户的业务程序

	                //注意：
	                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
	            	Pay pay=new Pay();
	 	           pay.setPipelineId(out_trade_no);
	 	           pay.setMoney(total_amount);
	 	           pay.setTime(new Date());
	 	          Users u = new Users();
	 	          u.setUsername(username);
	 	 		StudentStatus s = this.studentStatusService.getStudentStatusByUsers(u);			
				pay.setRemarks("正常缴费");
				pay.setStudentId(s.getStudentId());
				int i=this.payService.insertPay(pay);				
	            }

	            return "success";
	        }

	        return "fail";
	    }
 
	    /**
	     * 校验签名
	     * @param request
	     * @return
	     */
	    public boolean rsaCheckV1(HttpServletRequest request){
	        // https://docs.open.alipay.com/54/106370
	        // 获取支付宝POST过来反馈信息
	        Map<String,String> params = new HashMap<>();
	        Map requestParams = request.getParameterMap();
	        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
	            String name = (String) iter.next();
	            String[] values = (String[]) requestParams.get(name);
	            String valueStr = "";
	            for (int i = 0; i < values.length; i++) {
	                valueStr = (i == values.length - 1) ? valueStr + values[i]
	                        : valueStr + values[i] + ",";
	            }
	            params.put(name, valueStr);
	        }

	        try {
	            boolean verifyResult = AlipaySignature.rsaCheckV1(params,
	                    alipayProperties.getAlipayPublicKey(),
	                    alipayProperties.getCharset(),
	                    alipayProperties.getSignType());

	            return verifyResult;
	        } catch (AlipayApiException e) {
	           System.out.println("verify sigin error, exception is:{}"+e);
	            return false;
	        }
	    }
	
}
