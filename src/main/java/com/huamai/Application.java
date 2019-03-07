package com.huamai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.huamai.*.dao")
@EnableCaching // 开启缓存注解
@EnableScheduling // 开启定时任务注解
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
