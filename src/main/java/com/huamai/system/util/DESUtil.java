package com.huamai.system.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.StringUtils;

public class DESUtil {

	private DESUtil() {

	}

	static final String ALGORITHM = "DESede";
	static final String HEX_STRING = "0123456789ABCDEF";
	static final String TUCAO_KEY = "cmbhmdes";

	static byte[] encryptMode(String secretKey, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(buildKey(secretKey), ALGORITHM);
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
		}
		return null;
	}

	static byte[] decryptMode(String secretKey, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(buildKey(secretKey), ALGORITHM);
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
		}
		return null;
	}

	static byte[] buildKey(String secretKey) throws UnsupportedEncodingException {
		byte[] key = new byte[24], temp = secretKey.getBytes("utf-8");
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	static String byte2hex(byte[] b) {
		String hs = "", stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	static byte[] hex2byte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	static byte toByte(char c) {
		return (byte) HEX_STRING.indexOf(c);
	}

	/**
	 * 
	 * 3DES加密
	 * 
	 */
	public static String encode(String content) {
		String ret = null;
		if (!StringUtils.isEmpty(content)) {
			byte[] en = encryptMode(TUCAO_KEY, content.getBytes());
			if (null != en) {
				ret = byte2hex(en);
			}
		}
		return ret;
	}

	/**
	 * 
	 * 3DES解密
	 * 
	 */
	public static String decode(String content) {
		String ret = null;
		if (!StringUtils.isEmpty(content)) {
			byte[] b = hex2byte(content);
			if (null != b) {
				byte[] de = decryptMode(TUCAO_KEY, b);
				if (null != de) {
					ret = new String(de);
				}
			}
		}
		return ret;
	}

}
