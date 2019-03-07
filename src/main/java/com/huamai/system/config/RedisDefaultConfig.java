package com.huamai.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName: RedisConfig
 * @Description: Redis配置类
 * @author bai
 * @date 2019年2月22日
 */
@Configuration
@PropertySource("classpath:config/redis.properties")
public class RedisDefaultConfig extends RedisConfig {

	@Value("${redis.hostName}")
	private String hostName;

	@Value("${redis.port}")
	private Integer port;

	@Value("${redis.password}")
	private String password;

	@Value("${redis.database}")
	private Integer database;

	@Value("${redis.timeout}")
	private Integer timeout;

	/**
	 * @Title: functionDomainRedisTemplate
	 * @Description: 实例化 RedisTemplate 对象
	 * @param redisConnectionFactory
	 * @return RedisTemplate<String,Object>
	 */
	@Bean(name = "defaultRedisTemplate")
	public RedisTemplate<String, Object> functionDomainRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		initDomainRedisTemplate(redisTemplate, createJedisConnectionFactory(hostName,port,password,database,timeout));
		return redisTemplate;
	}

}
