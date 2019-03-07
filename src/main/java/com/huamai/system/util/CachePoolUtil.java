package com.huamai.system.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huamai.system.entity.Track;
import com.huamai.util.DateUtils;
import com.huamai.util.RedisUtil;

@Component
public class CachePoolUtil {

	private static final Logger logger = LoggerFactory.getLogger(CachePoolUtil.class);

	private static Map<String, Track> insertMap = new LinkedHashMap<String, Track>();// 插入缓存Map
	private static Map<String, String> updateMap = new HashMap<String, String>();// 更新缓存Map

	private final static String INSERTCACHEKEY = "topicInsertCache";// redis缓存插入数据key名称
	private final static String UPDATECACHEKEY = "topicUpdateCache";// redis缓存更新数据key名称
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 往insertMap里存入一条数据
	 * 
	 * @param key
	 * @param track
	 */
	public void putInsertMap(String key, Track track) {
		try {
			synchronized (INSERTCACHEKEY) {
				insertMap = getInsertMap();// byte[]转map
				if (insertMap == null) {
					insertMap = new LinkedHashMap<String, Track>();
				}
				insertMap.put(key, track);
				setInsertCacheMap(insertMap);
			}
		} catch (Exception e) {
			if (insertMap == null) {
				insertMap = new LinkedHashMap<String, Track>();
			}
			insertMap.put(key, track);
			logger.error("使用redis缓存用户轨迹错误（INSERTCACHEKEY），将使用本地缓存缓存用户轨迹。错误原因：", e);
		}
	}

	/**
	 * 往updateMap里存入一条数据
	 * 
	 * @param key
	 * @param leaveTime
	 */
	public void putUpdateMap(String key, String leaveTime) {
		try {
			synchronized (UPDATECACHEKEY) {
				updateMap = getUpdateMap();// byte[]转map
				if (updateMap == null) {
					updateMap = new HashMap<String, String>();
				}
				updateMap.put(key, leaveTime);
				setUpdateCacheMap(updateMap);
			}
		} catch (Exception e) {
			if (updateMap == null) {
				updateMap = new HashMap<String, String>();
			}
			updateMap.put(key, leaveTime);
			logger.error("使用redis缓存用户轨迹错误（UPDATECACHEKEY），将使用本地缓存缓存用户轨迹。错误原因：", e);
		}

	}

	/**
	 * @Title: intoInsertMapOrUpdateMap
	 * @Description: 判断离开时间是否在插入缓存里，不在则放入更新缓存
	 * @param cache
	 * @param param void
	 */
	public void intoInsertMapOrUpdateMap(Map<String, String> param) {
		if (param.containsKey("uid") && param.containsKey("leave")) {// 判断参数是否为空
			synchronized (INSERTCACHEKEY) {
				insertMap = getInsertMap();
				// 根据生成的trace-uuid在插入map中查找，如果在插入的map中能找到，则更新插入map中的离开时间
				if (insertMap != null && insertMap.containsKey(param.get("uid"))) {
					Track track = insertMap.get(param.get("uid"));
					track.setLeaveTime(DateUtils.getNowTime());
					setInsertCacheMap(insertMap);
				} else {// 如果在插入map中找不到则存入到更新map中
					updateMap = getUpdateMap();
					if (updateMap == null) {
						updateMap = new HashMap<String, String>();
					}
					if (!updateMap.containsKey(param.get("uid"))) {
						putUpdateMap(param.get("uid"), param.get("leave"));
					}
				}
			}
		}
	}

	/**
	 * 获取缓存中的插入数据Map
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Track> getInsertMap() {
		try {
			synchronized (INSERTCACHEKEY) {
				insertMap = (Map<String, Track>) redisUtil.get(INSERTCACHEKEY);
				if (insertMap != null) {
					return insertMap;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			logger.error("获取redis缓存用户轨迹错误（INSERTCACHEKEY），将使用本地缓存缓存用户轨迹。错误原因：", e);
			return insertMap;
		}
	}

	/**
	 * 获取缓存中的更新数据Map
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getUpdateMap() {
		try {
			synchronized (UPDATECACHEKEY) {
				updateMap = (Map<String, String>) redisUtil.get(UPDATECACHEKEY);
				if (updateMap != null) {
					return updateMap;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			logger.error("获取redis缓存用户轨迹错误（UPDATECACHEKEY），将使用本地缓存缓存用户轨迹。错误原因：", e);
			return updateMap;
		}
	}

	/**
	 * @Title: setInsertCacheMap
	 * @Description: 将insertMap放入redis中
	 * @param map
	 * @return void
	 */
	public void setInsertCacheMap(Map<String, Track> map) {
		synchronized (INSERTCACHEKEY) {
			redisUtil.set(INSERTCACHEKEY, map);
		}
	}

	/**
	 * @Title: setUpdateCacheMap
	 * @Description: 将updateMap放入redis中
	 * @param map
	 * @return void
	 */
	public void setUpdateCacheMap(Map<String, String> map) {
		synchronized (UPDATECACHEKEY) {
			redisUtil.set(UPDATECACHEKEY, map);
		}
	}

	/**
	 * 清除插入缓存
	 */
	public void clearInsertMap() {
		insertMap.clear();
		synchronized (INSERTCACHEKEY) {
			redisUtil.del(INSERTCACHEKEY);
		}
	}

	/**
	 * 清除更新缓存
	 */
	public void clearUpdateMap() {
		updateMap.clear();
		synchronized (UPDATECACHEKEY) {
			redisUtil.del(UPDATECACHEKEY);
		}
	}

}
