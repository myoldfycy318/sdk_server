package com.dome.sdkserver.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@SuppressWarnings("restriction")
public final class DesEncryptUtil {
	private static final Logger logger = LoggerFactory.getLogger(DesEncryptUtil.class);
	
	private static final String ENCODER_UTF8 = "UTF-8";
	
	private static final String DEFAULT_SPKEY = "abcdvfg+7wer";
	private DesEncryptUtil(){}
	
	/**
	 * 进行MD5加密
	 * 
	 * @param String
	 *            原始的SPKEY
	 * @return byte[] 指定加密方式为md5后的byte[]
	 */
	private static byte[] md5(String spkey) {
		byte[] returnByte = null;
		
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			returnByte = md5.digest(spkey.getBytes(ENCODER_UTF8));
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such algorithm MD5", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("utf-8 isn't support", e);
		}
		
		return returnByte;
	}

	/**
	 * 得到3-DES的密钥匙 根据接口规范，密钥匙为24个字节，md5加密出来的是16个字节，因此后面补8个字节的0
	 * 
	 * @param String 原始的SPKEY
	 * @return byte[] 指定加密方式为md5后的byte[]
	 */
	private static byte[] getEnKey(String spKey) {
		byte[] desKey1 = md5(spKey);
		byte[] desKey = new byte[24];
		int i = 0;
		while (i < desKey1.length && i < 24) {
			desKey[i] = desKey1[i];
			i++;
		}
		if (i < 24) {
			desKey[i] = 0;
			i++;
		}
		return desKey;
	}

	/**
	 * 3-DES加密
	 * 
	 * @param byte[] src 要进行3-DES加密的byte[]
	 * @param byte[] enKey 3-DES加密密钥
	 * @return byte[] 3-DES加密后的byte[]
	 * @throws Exception 
	 */
	private static byte[] encrypt(byte[] src, byte[] enKey) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(enKey);
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("DESede");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedData = cipher.doFinal(src);
		return encryptedData;
	}

	/**
	 * 对字符串进行Base64编码
	 * 
	 * @param byte[] src 要进行编码的字符
	 *
	 * @return String 进行编码后的字符串
	 */
	public static String getBase64Encode(byte[] src) {
		
		BASE64Encoder base64En = new BASE64Encoder();
		String encodeStr = base64En.encode(src);
		
		return encodeStr;
	}

	/**
	 * 去掉字符串的换行符号 base64编码3-DES的数据时，得到的字符串有换行符号 ，一定要去掉，否则uni-wise平台解析票根不会成功，
	 * 提示“sp验证失败”。在开发的过程中，因为这个问题让我束手无策， 一个朋友告诉我可以问联通要一段加密后 的文字，然后去和自己生成的字符串比较，
	 * 这是个不错的调试方法。我最后比较发现我生成的字符串唯一不同的 是多了换行。 我用c#语言也写了票根请求程序，没有发现这个问题。
	 *
	 */
	private static String filter(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc != 10 && asc != 13)
				sb.append(str.subSequence(i, i + 1));
		}
		String output = sb.toString();
		return output;
	}

	/**
	 * 对字符串进行URLDecoder.encode(strEncoding)编码
	 * 
	 * @param String
	 *            src 要进行编码的字符串
	 *
	 * @return String 进行编码后的字符串
	 */
	private static String getURLEncode(String str) {
		String requestValue = "";
		try {
			requestValue = URLEncoder.encode(str, ENCODER_UTF8);
		} catch (UnsupportedEncodingException e) {
			
		}
		return requestValue;
	}

	/**
	 * 3-des加密
	 * @param password
	 * @param spkey 密钥
	 * @return
	 */
	public static String encryptPassword(String password, String spkey) {
		if (StringUtils.isEmpty(spkey)) spkey = DEFAULT_SPKEY;
		String requestValue = "";
		try {
			// 得到3-DES的密钥匙
			byte[] enKey = getEnKey(spkey);
			// 要进行3-DES加密的内容在进行/"UTF-16LE/"取字节
			byte[] src2 = password.getBytes("UTF-16LE");
			// 进行3-DES加密后的内容的字节
			byte[] encryptedData = encrypt(src2, enKey);
			// 进行3-DES加密后的内容进行BASE64编码
			String base64String = getBase64Encode(encryptedData);
			// BASE64编码去除换行符后
			String base64Encrypt = filter(base64String);
			// 对BASE64编码中的HTML控制码进行转义的过程
			requestValue = getURLEncode(base64Encrypt);
			// System.out.println(requestValue);
		} catch (Exception e) {
			logger.error("encrypt error", e);
		}
		return requestValue;
	}

	/**
	 * 对字符串进行URLDecoder.decode(strEncoding)解码
	 * 
	 * @param String
	 *            src 要进行解码的字符串
	 *
	 * @return String 进行解码后的字符串
	 */
	private static String getURLDecoder(String src) {
		String requestValue = "";
		try {
			requestValue = URLDecoder.decode(src, ENCODER_UTF8);
		} catch (Exception e) {
			
		}
		return requestValue;
	}

	/**
	 *
	 * 进行3-DES解密（密钥匙等同于加密的密钥匙）。
	 * 
	 * @param byte[] src 要进行3-DES解密byte[]
	 * @param String
	 *            spkey分配的SPKEY
	 * @return String 3-DES解密后的String
	 * @throws Exception 
	 */
	private static String decrypt(byte[] debase64, String spKey) throws Exception {
		if (StringUtils.isEmpty(spKey)) spKey = DEFAULT_SPKEY;
		Cipher cipher = Cipher.getInstance("DESede");
		byte[] key = getEnKey(spKey);
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("DESede");
		SecretKey sKey = keyFactory.generateSecret(dks);
		cipher.init(Cipher.DECRYPT_MODE, sKey);
		byte ciphertext[] = cipher.doFinal(debase64);
		String strDe = new String(ciphertext, "UTF-16LE");
		return strDe;
	}

	/**
	 * 3-DES解密
	 * 
	 * @param String
	 *            src 要进行3-DES解密的String
	 * @param String
	 *            spkey分配的SPKEY
	 * @return String 3-DES加密后的String
	 * @throws Exception 
	 */
	public static String decryptPassword(String src, String spkey) throws Exception {
		String requestValue = "";
		try {
			// 得到3-DES的密钥匙
			// URLDecoder.decodeTML控制码进行转义的过程
			String URLValue = getURLDecoder(src);
			// 进行3-DES加密后的内容进行BASE64编码
			BASE64Decoder base64Decode = new BASE64Decoder();
			byte[] base64DValue = base64Decode.decodeBuffer(URLValue);
			// 要进行3-DES加密的内容在进行/"UTF-16LE/"取字节
			requestValue = decrypt(base64DValue, spkey);
		} catch (Exception e) {
			logger.error("decrypt error", e);
			throw e;
		}
		return requestValue;
	}

	public static void main(String[] args) throws Exception {
		String passwd = "admin1234";
		String secretKey = "abcefg&qrm";
		String secret = DesEncryptUtil.encryptPassword(passwd, secretKey);
		System.out.println("secret: " + secret);
		String mingwen = DesEncryptUtil.decryptPassword(secret, secretKey);
		System.out.println("decrypt: " + mingwen);
	}
}
