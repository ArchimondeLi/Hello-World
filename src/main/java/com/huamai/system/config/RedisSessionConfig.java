package com.huamai.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @ClassName: RedisConfig
 * @Description: Redis配置类
 * @author bai
 * @date 2019年2月22日
 */
@Configuration
@EnableRedisHttpSession
@PropertySource("classpath:config/redis.properties")
public class RedisSessionConfig extends RedisConfig {

	@Value("${redis.hostName}")
	private String hostName;

	@Value("${redis.port}")
	private Integer port;

	@Value("${redis.password}")
	private String password;

	@Value("${redis.session.database}")
	private Integer database;

	@Value("${redis.timeout}")
	private Integer timeout;

	/**
	 * @Title: JedisConnectionFactory
	 * @Description: 注入创建的JedisConnectionFactory工厂
	 * @return JedisConnectionFactory
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public JedisConnectionFactory JedisConnectionFactory() {
		JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory();
		// 连接池
		JedisConnectionFactory.setPoolConfig(setPoolConfig());
		// IP地址
		JedisConnectionFactory.setHostName(hostName);
		// 端口号
		JedisConnectionFactory.setPort(port);
		// 如果Redis设置有密码
		JedisConnectionFactory.setPassword(password);
		// 数据库
		JedisConnectionFactory.setDatabase(database);
		// 客户端超时时间单位是毫秒
		JedisConnectionFactory.setTimeout(timeout);
		
		return JedisConnectionFactory;
	}
}
