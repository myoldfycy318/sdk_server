package com.dome.sdkserver.util;

import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import com.dome.sdkserver.security.ras.RSA;


/**
 * 加解密工具类
 *
 * @author dujinxin
 */
public class EncryptUtil {

    private static final String ENC_MD5 = "MD5";

    private static final String ENC_SHA = "SHA-";

    /**
     * 将字节数组转换为十六进制字节数组
     *
     * @param bs 字节数组
     * @return 十六进制字节数组
     */
    public static byte[] hexEncode(byte[] bs) {
        return new Hex().encode(bs);
    }

    /**
     * 将字符串转换为十六进制字符串
     *
     * @param src 字符串
     * @return 十六进制字符串
     */
    public static String hexEncode(String src) {
        return new String(new Hex().encode(src.getBytes()));
    }

    /**
     * 对字符串进行十六进制解码
     *
     * @param str 十六进制字符串
     * @return 十六进制解码后的字符串
     */
    public static String hexDecode(String str) {
        try {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            char[] cs = str.toCharArray();
            return new String(Hex.decodeHex(cs));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字节数组进行MD5加密
     *
     * @param src 字节数组
     * @return MD5加密后的字节数组
     */
    public static byte[] md5(byte[] src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(ENC_MD5);
            md5.update(src);
            return md5.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串进行MD5加密并十六进制编码
     *
     * @param src 字符串
     * @return D5加密并十六进制编码字符串
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(ENC_MD5);
            md5.update(src.getBytes());
            byte[] md = md5.digest();
            return new String(hexEncode(md));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串MD5加密后在Base64编码
     *
     * @param src 字符串
     * @return 经过MD5加密和Base64编码的字符串
     */
    public static String md5Base64(String src) {
        if (src == null || src.equals("")) {
            return null;
        }

        try {
            String md5Str = md5Hex(src);
            return base64Encode(md5Str).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串Base64编码
     *
     * @param src 字符串
     * @return 经过Base64编码的字符串
     */
    public static String base64Encode(String src) {
        if (src == null || src.equals("")) {
            return null;
        }

        Base64 encoder = new Base64();
        byte[] base64Byte = encoder.encode(src.getBytes());
        return new String(base64Byte).trim();
    }
    
    /**
     * 字符串Base64编码
     *
     * @param src 字符串
     * @return 经过Base64编码的字符串
     */
    public static String base64Encode(byte[] src) {
    	if (src == null || src.length < 1) {
            return null;
        }

        Base64 encoder = new Base64();
        return new String(encoder.encode(src)).trim();
    }

    /**
     * 字符串Base64解码
     *
     * @param src 字符串
     * @return 经过Base64解码的字符串
     */
    public static String base64Decode(String src) {
        if (src == null || src.equals("")) {
            return null;
        }

        Base64 decoder = new Base64();
        byte[] deByte = decoder.decode(src.getBytes());
        return new String(deByte).trim();
    }

    /**
     * 字节数组Base64解码
     *
     * @param src 原字节数组
     * @return 经过Base64解码的字节数组
     */
    public static byte[] base64Decode(byte[] src) {
        if (src == null || src.length < 1) {
            return null;
        }

        Base64 decoder = new Base64();
        return decoder.decode(src);
    }
    
    /**
     * 字节数组Base64解码
     *
     * @param src 原字节数组
     * @return 经过Base64解码的字节数组
     */
    public static byte[] base64Decode4Byte(String src) {
    	if (src == null || src.equals("")) {
            return null;
        }

        Base64 decoder = new Base64();
        return decoder.decode(src.getBytes());
    }

    /**
     * 将字符串安全散列
     *
     * @param src       字符串
     * @param shaLength SHA长度：128、224、256、384、512
     * @return 安全散列后的字符串
     */
    public static String shaEncode(String src, int shaLength) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(ENC_SHA + shaLength);
            md5.update(src.getBytes());
            byte[] md = md5.digest();
            return new String(hexEncode(md));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字节数组安全散列为字符串
     *
     * @param src       字符串
     * @param shaLength SHA长度：128、224、256、384、512
     * @return 安全散列后的字符串
     */
    public static byte[] shaEncode(byte[] src, int shaLength) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(ENC_SHA + shaLength);
            md5.update(src);
            return md5.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
	 * 生成RSA签名
	 */
	public static String signRSA(TreeMap<String, Object> map,
			String privateKey) {
		StringBuffer sbuffer = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sbuffer.append(entry.getValue());
		}
		String signTemp = sbuffer.toString();

		String sign = "";
		if (StringUtils.isNotEmpty(privateKey)) {
			sign = RSA.sign(signTemp, privateKey);
		}
		return sign;
	}
	
    public static void main(String[] args) {
		String aaa1="MAL120509080603";
		aaa1=EncryptUtil.md5Hex(aaa1);
		String aaa2="MAL120509074311";
		aaa2=EncryptUtil.md5Hex(aaa2);
		System.out.println(aaa1);
		System.out.println(aaa2);
		
		System.out.println(EncryptUtil.base64Decode("aHR0cDovL2ltLnFpYW5iYW82NjYuY29tL2FwaS9yL3VzZXIvNjA2MDI0Mgo="));
		System.out.println(EncryptUtil.base64Encode("user/6060242"));
	}
}
