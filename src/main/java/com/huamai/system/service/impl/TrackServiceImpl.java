package com.huamai.system.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.huamai.system.dao.TrackMapper;
import com.huamai.system.entity.Track;
import com.huamai.system.service.TrackService;
import com.huamai.system.util.CachePoolUtil;
import com.huamai.util.RedisLockUtil;

@Service
public class TrackServiceImpl implements TrackService {
	
	@Autowired
	private TrackMapper tarckMapper;
	
	@Autowired
	private CachePoolUtil cache;
	
	@Autowired
	private RedisLockUtil redisLock;
	
	@Value("${server.servlet.context-path}")
	private String applicationName;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TrackServiceImpl.class);
	
	/**
	 * 从缓存中获取数据批量插入数据库中
	 * 每15秒执行一次，根据需求配置
	 */
	@Scheduled(cron = "0/15 * * * * ?")
	@Override
	public void bulkInsert() {
		String redisKey = applicationName.replace("/", "")+"_traceLock";
	    try {
	    	boolean flag = redisLock.lock(redisKey,10);
	    	if(flag) {
	    		logger.info("执行定时任务");
	    		// 获取缓存中的插入数据
				Map<String, Track> insertMap = cache.getInsertMap();
				if (insertMap != null && !insertMap.isEmpty()) {
					tarckMapper.bulkInsert(insertMap);
					cache.clearInsertMap();
				}

				// 获取缓存中的更新数据
				Map<String, String> updateMap = cache.getUpdateMap();
				if (updateMap != null && !updateMap.isEmpty()) {
					tarckMapper.updateLeaveTime(updateMap);
					cache.clearUpdateMap();
				}
	    	}else {
	    		logger.info("定时任务被其他服务器执行");
	    	}
		} catch (Exception e) {
			logger.error("批量插入用户轨迹出错，错误原因：",e);
		}finally {
			redisLock.unLock(redisKey);
		}
	}

}
