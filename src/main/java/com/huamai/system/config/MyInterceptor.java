package com.huamai.system.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: MyInterceptor
 * @Description: 自定义拦截器
 * @author bai
 * @date 2019年1月11日
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

	
	// 在请求处理之前进行调用（Controller方法调用之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {
		return true;
		/*try {
			
			
			LoginUser user = SessionUtils.getSessionUser(request.getSession());
			if(user==null) {
				if(Util.isAjaxRequest(request)) {
					//TODO
					//response.setCharacterEncoding("utf-8");
			        //设置服务器端使用的编码,同时设置了客户端使用的编码
			        response.setContentType("text/html;charset=utf-8");//MIME
			        //把编码的设置放到响应的头信息中
			        //response.setHeader("content-type", "text/html;charset=utf-8");
			        PrintWriter out = response.getWriter();//得到向客户端发送数据的字符输出流
			        out.println("用户信息失效，请重新登录");//该数据在响应消息体中
			        out.close();
				}else {
					logger.error("用户session失效，返回专区首页");
					response.sendRedirect(Util.readProperties("appGoBack"));
				}
				return false;
			}else {
				
				String ip = IpUtils.getIpAddr(request);
				boolean flag = SecurityInterceptorUtil.isOverTopAccess(ip+"_"+user.getUsrmob());
				if(!flag) {
					if(Util.isAjaxRequest(request)) {
				        response.setContentType("text/html;charset=utf-8");//MIME
				        PrintWriter out = response.getWriter();//得到向客户端发送数据的字符输出流
				        out.println("请求频率太快，请稍后再试");//该数据在响应消息体中
				        logger.info("IP："+ip+"，超出访问次数限制");
				        out.close();
					}else {
						logger.info("IP："+ip+"，超出访问次数限制");
						response.sendRedirect(Util.readProperties("appGoBack"));
					}
					return false; 
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("拦截器出错，错误原因：", e);
			if(Util.isAjaxRequest(request)) {
		        response.setContentType("text/html;charset=utf-8");//MIME
		        PrintWriter out = response.getWriter();//得到向客户端发送数据的字符输出流
		        out.println("用户信息失效，请重新登录");//该数据在响应消息体中
		        out.close();
			}else {
				logger.error("用户session失效，返回南京专区");
				response.sendRedirect(Util.readProperties("appGoBack"));
			}
			return false;
		}*/
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}

}
