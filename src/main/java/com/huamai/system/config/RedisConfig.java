package com.huamai.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName: RedisConfig
 * @Description: Redis配置类
 * @author bai
 * @date 2019年2月22日
 */
@PropertySource("classpath:config/redis.properties")
public class RedisConfig {

	@Value("${redis.maxIdle}")
	private Integer maxIdle;

	@Value("${redis.maxTotal}")
	private Integer maxTotal;

	@Value("${redis.maxWaitMillis}")
	private Integer maxWaitMillis;

	@Value("${redis.minEvictableIdleTimeMillis}")
	private Integer minEvictableIdleTimeMillis;

	@Value("${redis.numTestsPerEvictionRun}")
	private Integer numTestsPerEvictionRun;

	@Value("${redis.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;

	@Value("${redis.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${redis.testWhileIdle}")
	private boolean testWhileIdle;

	/**
	 * @Title: setPoolConfig
	 * @Description: 配置JedisPoolConfig 连接池
	 * @return JedisPoolConfig
	 */
	public JedisPoolConfig setPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 最大空闲数
		jedisPoolConfig.setMaxIdle(maxIdle);
		// 连接池的最大数据库连接数
		jedisPoolConfig.setMaxTotal(maxTotal);
		// 最大建立连接等待时间
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		// 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		// 在空闲时检查有效性, 默认false
		jedisPoolConfig.setTestWhileIdle(testWhileIdle);
		return jedisPoolConfig;
	}
	
	/**
	 * @Title: JedisConnectionFactory
	 * @Description: 单机版配置
	 * @param jedisPoolConfig
	 * @return JedisConnectionFactory
	 */
	@SuppressWarnings("deprecation")
	public JedisConnectionFactory createJedisConnectionFactory(String hostName, Integer port, String password,
			Integer database, Integer timeout) {
		JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory();
		JedisConnectionFactory.setUsePool(true);
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
		
		JedisConnectionFactory.afterPropertiesSet();
		
		return JedisConnectionFactory;
	}
	
	/**
	 * @Title: initDomainRedisTemplate
	 * @Description: 设置数据存入 redis 的序列化方式,并开启事务
	 * @param redisTemplate
	 * @param factory       void
	 */
	public void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
		// 如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to
		// String！
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		// 开启事务
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(factory);
	}

}
