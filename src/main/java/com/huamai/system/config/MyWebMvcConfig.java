package com.huamai.system.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.huamai.util.ProjectPath;

/**
 * @ClassName: MyWebMvcConfig
 * @Description: 自定义WebMV配置类
 * @author bai
 * @date 2019年1月11日
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
	
	//关键，将拦截器作为bean写入配置中
    @Bean
    public MyInterceptor myInterceptor(){
        return new MyInterceptor();
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludePatterns = new ArrayList<String>();// 设置不拦截http请求
		excludePatterns.add("/static/**");
		excludePatterns.add("/error");
		excludePatterns.add("/info/**");
		excludePatterns.add("/index");
		excludePatterns.add("/musictest");
		excludePatterns.add("/redisTest");
		registry.addInterceptor(myInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatterns);
	}

	/**
	 * 添加静态资源文件，外部可以直接访问地址
	 * 
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 其他静态资源，与本文关系不大
		registry.addResourceHandler("/upload/**")
				.addResourceLocations("file:" + ProjectPath.getProjectPath() + "/public/");
		
		// 需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
		// 第一个方法设置访问路径前缀，第二个方法设置资源路径
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}
}
