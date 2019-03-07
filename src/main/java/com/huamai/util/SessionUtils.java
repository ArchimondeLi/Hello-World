package com.huamai.util;

import javax.servlet.http.HttpSession;

import com.huamai.system.constant.SysConstant;
import com.huamai.system.entity.LoginUser;


public class SessionUtils {
	
	/**
	 * 添加用户到session
	 * @param session
	 * @param obj
	 */
	public static void addSessionUser(HttpSession session,LoginUser user){
		session.setAttribute(SysConstant.Core.USERSESSION, user);
	}
	
	/**
	 * 获取session中用户
	 * @param session
	 * @return
	 */
	public static LoginUser getSessionUser(HttpSession session ){
		LoginUser user = (LoginUser) session.getAttribute(SysConstant.Core.USERSESSION);
		return user;
	}
	
	
	/**
	 * 移除session中的用户
	 * @param session
	 * @param obj
	 */
	public static void removeSessionUser(HttpSession session){
		session.removeAttribute(SysConstant.Core.USERSESSION);
	}
	
	/**
	 * 添加信息到session
	 * @param session
	 * @param obj
	 */
	public static void addSessionMess(HttpSession session,String key ,Object obj){
		session.setAttribute(key, obj);
	}
	
	/**
	 * 移除session
	 * @param session
	 * @param name
	 */
	public static void removeSessionMess(HttpSession session, String key){
		session.removeAttribute(key);
	}
	
	/**
	 * session失效
	 * @param session
	 */
	public static void invalidate(HttpSession session){
		session.invalidate();
	}
}
