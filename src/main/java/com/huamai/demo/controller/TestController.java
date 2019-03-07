package com.huamai.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huamai.system.entity.Track;
import com.huamai.system.util.CachePoolUtil;
import com.huamai.util.IpUtils;
import com.huamai.util.RedisUtil;
import com.huamai.util.Util;

/**
 * @ClassName: TestController
 * @Description:
 * @author bai
 * @date 2019年2月25日
 */
@RequestMapping("/test")
@Controller
public class TestController {

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CachePoolUtil cachePoolutil;

	@Autowired
	private HttpServletRequest request;

	/**
	 * @Title: vIndex
	 * @Description: 访问页面并向页面传值示例
	 * @param model
	 * @return String
	 */
	@RequestMapping("/index")
	public String vIndex(ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 页面描述
		String desc = "用户：" + "17855360258" + "进入***首页";
		// 封装轨迹信息
		Track track = new Track("01", "17855360258", IpUtils.getIpAddr(request), desc);
		// 放入插入轨迹缓存
		cachePoolutil.putInsertMap(track.getTraceId(), track);

		map.put("name", "张三");
		map.put("age", 25);
		map.put("traceId", track.getTraceId());
		model.addAttribute("map", map);

		return "/test/index";
	}

	/**
	 * @Title: vTest
	 * @Description: 返回json示例
	 * @return Map<String,Object>
	 */
	@RequestMapping("/test")
	@ResponseBody
	public Map<String, Object> vTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "张三");
		map.put("age", 25);
		return map;
	}

	/**
	 * @Title: getCache
	 * @Description: 获取行为轨迹示例
	 * @return Map<String,Track>
	 */
	@RequestMapping("/getCache")
	@ResponseBody
	public Map<String, Track> getCache() {
		Map<String, Track> map = cachePoolutil.getInsertMap();
		return map;
	}

	/**
	 * @Title: redisTest
	 * @Description: reids存取示例
	 * @return String
	 */
	@RequestMapping("/redisTest")
	@ResponseBody
	public String redisTest() {
		redisUtil.set("age", "25", 30);
		return "redis存取成功，age:" + (String) redisUtil.get("age");
	}
	
	/**
	 * @Title: redisTest
	 * @Description: 获取配置文件测试
	 * @return String
	 */
	@RequestMapping("/getPath")
	@ResponseBody
	public String getPath() {
		return Util.readProperties("context-path");
	}
}
