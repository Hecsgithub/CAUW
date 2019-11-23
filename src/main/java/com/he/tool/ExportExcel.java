package com.he.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ExportExcel {
	// 导出Excel工具方法
	public static ResponseEntity<byte[]> createFile(HttpServletResponse response, HSSFWorkbook workbook,String fileName) {
			// 设置文件名
			ResponseEntity<byte[]> entity=null;
			try{
				 HttpHeaders headers = new HttpHeaders();
			        headers.setContentDispositionFormData("attachment",
			                new String((fileName+".xls").getBytes("UTF-8"),"iso-8859-1"));
			        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			        ByteArrayOutputStream baos=new ByteArrayOutputStream();
			        workbook.write(baos);
			        entity= new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.CREATED);
			}catch(Exception e) {

			}
		        return entity;

		}
}
