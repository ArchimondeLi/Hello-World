package com.huamai.system.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.huamai.Application;

/**
 * @ClassName: ServletInitializer
 * @Description: 使用外置的tomcat启动，用于maven打包部署到tomcat
 * @author bai
 * @date 2019年1月11日
 */
public class ServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 此处的Application.class为带有@SpringBootApplication注解的启动类
		return builder.sources(Application.class);
	}
}
