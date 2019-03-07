package com.huamai.system.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huamai.system.entity.Track;

public interface TrackMapper {

	//批量插入（从insertMap缓存中取出插入数据库）
    int bulkInsert(@Param("map")Map<String,Track> map);
    
    //批量更新（从updateMap缓存中取出更新至数据库）
    int updateLeaveTime(@Param("map")Map<String,String> map);


}