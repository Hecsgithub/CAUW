package com.he;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//mapper扫描
@MapperScan("com.he.dao")

//开启缓存，使用Ehcache缓存
//@EnableCaching

@SpringBootApplication
public class HcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HcsApplication.class, args);
	}

}
