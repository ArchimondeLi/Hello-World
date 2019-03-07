package com.huamai.system.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huamai.system.entity.LoginUser;
import com.huamai.system.entity.Track;
import com.huamai.system.util.CachePoolUtil;
import com.huamai.util.DateUtils;
import com.huamai.util.SessionUtils;

/**
 * @ClassName: LoginController
 * @Description: 登录处理控制器
 * @author bai
 * @date 2019年1月11日
 */
@Controller
public class LoginController {

	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private CachePoolUtil cachePoolutil;

	/**
	 * @Title: index
	 * @Description: 获取用户信息入口
	 * @param model
	 * @param request
	 * @return String
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {
		logger.info("访问登录链接");
		try {

			// LoginUser user = LoginUserUtil.parseLoginUser(request);

			LoginUser temp = new LoginUser(UUID.randomUUID().toString());
			SessionUtils.addSessionUser(request.getSession(), temp);
			LoginUser user = SessionUtils.getSessionUser(request.getSession());// FIXME

			if (user != null) {
				SessionUtils.addSessionUser(request.getSession(), user);
				return "redirect:/idiom/index";
			} else {
				logger.error("登录链接获取用户信息失败");
				model.addAttribute("msg", "获取用户信息失败");
				return "/info";
			}
		} catch (Exception e) {
			logger.error("用户访问登录出错，错误原因：", e);
			model.addAttribute("msg", e.getMessage());
			return "/info";
		}
	}

	/**
	 * 
	 * @Title: info
	 * @Description: 错误信息页面
	 * @param msg
	 * @param model
	 * @return String
	 */
	@RequestMapping("/info/{msg}")
	public String info(@PathVariable("msg") String msg, Model model) {
		try {
			String alert = "用户登录信息失效，请重新登录";
			model.addAttribute("msg", alert);
		} catch (Exception e) {
			model.addAttribute("msg", "错误：" + e.getMessage());
		}
		return "/info";
	}

	/**
	 * 离开页面事件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/leave")
	@ResponseBody
	public Map<String, Object> leave(@RequestParam Map<String, String> param) {
		Map<String, Object> map = new HashMap<String, Object>();

		logger.info(param.get("uid").replace("\"", ""));
		if (param.containsKey("uid") && param.containsKey("leave")) {// 判断参数是否为空
			Map<String, Track> insertMap = cachePoolutil.getInsertMap();
			// 根据生成的trace-uuid在插入map中查找，如果在插入的map中能找到，则更新插入map中的离开时间
			String uid = param.get("uid").replace("\"", "");
			if (insertMap.containsKey(uid)) {
				Track track = insertMap.get(uid);
				track.setLeaveTime(DateUtils.getNowTime());
			} else {// 如果在插入map中找不到则存入到更新map中
				Map<String, String> updateMap = cachePoolutil.getUpdateMap();
				if (!updateMap.containsKey(uid)) {
					cachePoolutil.putUpdateMap(uid, param.get("leave"));
				}
			}
		}
		map.put("code", "0");
		return map;
	}

}
