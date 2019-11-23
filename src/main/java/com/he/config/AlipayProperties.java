package com.he.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 支付宝支付的参数配置
 */
@Data
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {
	/** 支付宝gatewayUrl */
	private String gatewayUrl;
	/** 商户应用id */
	private String appid;
	/** RSA私钥，用于对商户请求报文加签 */
	private String appPrivateKey;
	/** 支付宝RSA公钥，用于验签支付宝应答 */
	private String alipayPublicKey;
	/** 签名类型 */
	private String signType = "RSA2";

	/** 格式 */
	private String formate = "json";
	/** 编码 */
	private String charset = "UTF-8";

	/** 同步地址 */
	private String returnUrl;

	/** 异步地址 */
	private String notifyUrl;

	/** 最大查询次数 */
	private static int maxQueryRetry = 5;
	/** 查询间隔（毫秒） */
	private static long queryDuration = 5000;
	/** 最大撤销次数 */
	private static int maxCancelRetry = 3;
	/** 撤销间隔（毫秒） */
	private static long cancelDuration = 3000;
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppPrivateKey() {
		return appPrivateKey;
	}
	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey = appPrivateKey;
	}
	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}
	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getFormate() {
		return formate;
	}
	public void setFormate(String formate) {
		this.formate = formate;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public static int getMaxQueryRetry() {
		return maxQueryRetry;
	}
	public static void setMaxQueryRetry(int maxQueryRetry) {
		AlipayProperties.maxQueryRetry = maxQueryRetry;
	}
	public static long getQueryDuration() {
		return queryDuration;
	}
	public static void setQueryDuration(long queryDuration) {
		AlipayProperties.queryDuration = queryDuration;
	}
	public static int getMaxCancelRetry() {
		return maxCancelRetry;
	}
	public static void setMaxCancelRetry(int maxCancelRetry) {
		AlipayProperties.maxCancelRetry = maxCancelRetry;
	}
	public static long getCancelDuration() {
		return cancelDuration;
	}
	public static void setCancelDuration(long cancelDuration) {
		AlipayProperties.cancelDuration = cancelDuration;
	}

}
