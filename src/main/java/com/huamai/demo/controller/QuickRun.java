package com.huamai.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: QuickRun
 * @Description: Session共享示例
 * @author bai
 * @date 2019年2月25日
 */
@RestController
@RequestMapping(value = "/admin/v1")
public class QuickRun {
	@RequestMapping(value = "/first", method = RequestMethod.GET)
	public Map<String, Object> firstResp(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		request.getSession().setAttribute("request Url", request.getRequestURL());
		map.put("request Url", request.getRequestURL());
		return map;
	}

	@RequestMapping(value = "/sessions", method = RequestMethod.GET)
	public Object sessions(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("sessionId", request.getSession().getId());
		map.put("message", "44");
		return map;
	}
}