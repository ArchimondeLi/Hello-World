package com.huamai.util;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;

public class ProjectPath {

	private static String rootPath = "";

	private ProjectPath() {
		init();
	}

	private static void init() {
		try {
			String path = ResourceUtils.getURL("classpath:").getPath();
			File upload = new File(path);
			if(!upload.exists()) upload.mkdirs();
			rootPath=upload.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取应用的根目录，路径分隔符为/，路径结尾无/
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		if ("".equals(rootPath)) {
			init();
		}
		return rootPath;
	}

}
