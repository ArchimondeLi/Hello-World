package com.huamai.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @ClassName: RedisLockUtil  
* @Description:  Redis分布式锁
* @author bai  
* @date 2019年2月22日
 */
@Component
public class RedisLockUtil {
	private static final int defaultExpire = 5;
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 加锁 默认过期时间为5秒
	 * 
	 * @param key
	 * @return
	 */
	public boolean lock(String key) {
		return lock(key, defaultExpire);
	}

	/**
	 * 加锁
	 * 
	 * @param key    redis key
	 * @param expire 过期时间，单位秒
	 * @return true:加锁成功，false，加锁失败
	 */
	public boolean lock(String key, int expire) {

		long value = System.currentTimeMillis() + expire;
		boolean status = redisUtil.setnx(key, String.valueOf(value));

		if (status == true) {
			return true;
		}
		if(null!=redisUtil.get(key)) {
			long oldExpireTime = Long.parseLong((String) redisUtil.get(key));
			if (oldExpireTime < System.currentTimeMillis()) {
				// 超时
				long newExpireTime = System.currentTimeMillis() + expire * 1000;
				long currentExpireTime = Long.parseLong((String) redisUtil.getSet(key, String.valueOf(newExpireTime)));
				if (currentExpireTime == oldExpireTime) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * 解锁
	 * 
	 * @param key
	 */
	public void unLock(String key) {
		redisUtil.del(key);
	}

}
