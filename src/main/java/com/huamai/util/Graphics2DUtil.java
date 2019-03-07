package com.huamai.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @ClassName: Graphics2DUtil
 * @Description: Graphics2合成图片工具类
 * @author bai
 * @date 2019年1月16日
 */
public class Graphics2DUtil {

	/**
	 * 图片合成
	 * @Title: eyeCopy
	 * @param bg 背景图片
	 * @param img 图片素材
	 * @param x 距离左边的像素
	 * @param y 距离顶部的像素
	 * @param format 输出图片格式
	 * @return byte[]
	 */
	public static byte[] eyeCopy(InputStream bg, InputStream img, int x, int y, String format) {
		BufferedImage big = null, image = null;
		try {
			big = ImageIO.read(bg);
			image = ImageIO.read(img);
			Graphics2D g = big.createGraphics();
			g.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
			g.dispose();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(big, "png", bos);
			byte[] imageBytes = bos.toByteArray();
			bos.close();
			return imageBytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
