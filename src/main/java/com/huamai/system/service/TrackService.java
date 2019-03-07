package com.huamai.system.service;


public interface TrackService{
	
	/**
	 * 从缓存中获取数据插入更新到数据库中
	 * @param map
	 * @return
	 */
	void bulkInsert();


}
