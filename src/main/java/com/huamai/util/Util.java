package com.huamai.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {

	private Util() {
	}

	private static Properties properties;

	/**
	 * 
	 * 读取配置文件
	 * 
	 */
	public static String readProperties(String key) {
		String ret = "";
		try {
			if (null == properties) {
				properties = new Properties();
				properties
						.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.properties"));
			}
			ret = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 
	 * HTTP GET
	 * 
	 */
	public static String doGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setUseCaches(false);
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 
	 * HTTP POST
	 * 
	 */
	public static String doPost(String content, String url) {
		String result = "";
		OutputStreamWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
			out.write(content);
			out.flush();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 
	 * MD5加密
	 * 
	 */
	public static String md5(String content) {
		String ret = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] bs = digest.digest(content.getBytes());
			for (byte b : bs) {
				int t = b & 255;
				if (t < 16 && t >= 0) {
					ret = ret + "0" + Integer.toHexString(t);
				} else {
					ret = ret + Integer.toHexString(t);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 
	 * SHA1加密
	 * 
	 */
	public static String sha1(String content) {
		String result = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(content.getBytes());
			byte[] arr = digest.digest();
			String t;
			for (int i = 0; i < arr.length; i++) {
				t = (Integer.toHexString(arr[i] & 0xFF));
				if (t.length() == 1) {
					result += "0";
				}
				result += t;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 网页重定向
	 * 
	 */
	public static void redirectUrl(String url, HttpServletResponse response) {
		try {
			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取来访IP地址
	 * 
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static int draw(int max) {
		int result = (int) (1 + Math.random() * (max - 1 + 1));
		return result;
	}

	public static void main(String args[]) {
		int result = (int) (1 + Math.random() * 15);
		System.out.println(result);
	}

	/**
	 * 
	 * @Title: isAjaxRequest
	 * @Description: 判断是否是ajax请求
	 * @param request
	 * @return boolean
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1) {
			return true;
		}

		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
			return true;
		}
		return false;
	}

}
