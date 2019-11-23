//package com.he.config;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 过滤器解决跨域
// * 
// * @author Administrator
// *
// */
//
//@WebFilter("/*")
//public class CorsFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		// TODO Auto-generated method stub
////		HttpServletRequest request = (HttpServletRequest) req;
////		HttpServletResponse response = (HttpServletResponse) res;
////		response.setHeader("Access-Control-Allow-Origin", "*");//http://localhost:8080
////		response.setHeader("Access-Control-Allow-Credentials", "true");
////		response.setHeader("Access-Control-Allow-Headers",
////				"Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-App-Id, X-Token");
////		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
////		response.setHeader("Access-Control-Max-Age", "3600");
////		if (request.getMethod().equals( "OPTIONS" )) {
////            response.setStatus( 200 );
////            return;
////        }
////		chain.doFilter(request, response);
//		
//		System.out.println("跨域请求进来了。。。");
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        httpServletRequest.getSession();
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
//        httpResponse.setHeader("Access-Control-Max-Age", "3600");
//        httpResponse.setHeader("Access-Control-Allow-Headers",
//                "Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie");
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        httpResponse.setHeader("Content-type", "application/json");
//        httpResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
//        chain.doFilter(request, httpResponse);
//
//	}
//
//}
