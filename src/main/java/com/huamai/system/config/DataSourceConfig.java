package com.huamai.system.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;
import com.huamai.util.TripleDESUtils;

/**  
* @ClassName: DataSourceConfig  
* @Description: 配置第三方数据源druid 
* @author bai  
* @date 2019年1月11日  
*/
@Configuration
public class DataSourceConfig {
 
	@Autowired
	private Environment env;
 
	@Bean
	public DataSource getDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(TripleDESUtils.decode(env.getProperty("spring.datasource.username")));
		dataSource.setPassword(TripleDESUtils.decode(env.getProperty("spring.datasource.password")));
		return dataSource;
	}
}
