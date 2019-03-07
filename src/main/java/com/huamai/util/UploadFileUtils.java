package com.huamai.util;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class UploadFileUtils {

	public static final String DEFAULTPATH = "/static/upload/";

	/**
	 * 字符串以逗号隔开
	 */
	public static String getMsgInfo(String msg) {
		char[] s_arr = msg.toCharArray();
		StringBuffer newMsg = new StringBuffer();
		for (int i = 0; i < s_arr.length; i++) {
			if (i != s_arr.length - 1) {
				newMsg.append(String.valueOf(s_arr[i])).append(",");
			} else {
				newMsg.append(String.valueOf(s_arr[i]));
			}
		}
		return newMsg.toString();
	}

	/***
	 * 上传文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> uploadCommonFile(MultipartFile[] files) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> str = new ArrayList<String>();

		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				String fileName = "";
				String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));// 取文件格式后缀名
				fileName = UploadFileUtils.getRandomFileName() + type;// 取当前时间戳作为文件名
				// 保存文件
				String savePath = saveFile(file, fileName);
				str.add(savePath);
			}
		}
		map.put("code", 0);
		map.put("filesPath", str);
		return map;
	}

	/***
	 * 保存文件
	 * 
	 * @param file
	 * @return
	 */
	private static String saveFile(MultipartFile file, String fileName) throws Exception {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				String savedDir = ProjectPath.getProjectPath();
				savedDir = savedDir.substring(0, savedDir.lastIndexOf("/"));
				String Path1 = "/download/pig";
				String uploadPath = savedDir + Path1; // 用于存放上传文件的服务器目录绝对路径
				File filepath = new File(uploadPath);
				if (!filepath.exists()) {
					filepath.mkdirs();
				}
				// 文件保存路径
				String savePath = "/" + uploadPath + "/" + fileName;
				File toFile = new File(savePath);
				// 转存文件
				file.transferTo(toFile);

				// 判断是否为图片
				if (isImage(toFile)) {
					// 对图片进行压缩
					Thumbnails.of(toFile).scale(1f).outputQuality(0.2f).toFile(toFile);
				}
				return savePath;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 图片转byte数组
	public static byte[] getImages(String path) {
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}

	/**
	 * 将二进制转换成图片保存
	 * 
	 * @param imgStr  二进制流转换的字符串
	 * @param imgPath 图片的保存路径
	 * @param imgName 图片的名称
	 * @return
	 * @return
	 */
	public static boolean saveToImgByBytes(byte[] imgFile, String imgPath) {
		if (imgFile.length != 0 && null != imgFile) {
			try {
				FileImageOutputStream imageOutput = new FileImageOutputStream(new File(imgPath));// 打开输入流
				imageOutput.write(imgFile, 0, imgFile.length);// 将byte写入硬盘
				imageOutput.close();
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为图片
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isImage(File file) {
		try {
			// 通过ImageReader来解码这个file并返回一个BufferedImage对象
			// 如果找不到合适的ImageReader则会返回null，我们可以认为这不是图片文件
			// 或者在解析过程中报错，也返回false
			Image image = ImageIO.read(file);
			return image != null;
		} catch (IOException ex) {
			return false;
		}
	}

	/**
	 * 生成随机文件名：当前年月日时分秒+五位随机数
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		Random random = new Random();

		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

		return rannum + str;
	}

	/**
	 * 获取上传文件的服务器目录绝对路径
	 * 
	 * @return
	 */
	public static String getUploadPath() {
		String savedDir = ProjectPath.getProjectPath();
		String Path1 = DEFAULTPATH;
		String uploadPath = savedDir + Path1; // 用于存放上传文件的服务器目录绝对路径
		File filepath = new File(uploadPath);
		if (!filepath.exists()) {
			filepath.mkdirs();
		}
		return uploadPath;
	}

	/**
	 * 
	 * @Title: buildImgPath
	 * @Description: 构建上文文件路径和图片名
	 * @return String 
	 * 
	 */
	public static String buildImgPath() {
		return getUploadPath() + getRandomFileName() + ".png";
	}
}
