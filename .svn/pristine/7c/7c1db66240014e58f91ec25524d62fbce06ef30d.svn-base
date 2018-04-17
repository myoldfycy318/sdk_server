package com.dome.sdkserver.util;

import com.dome.sdkserver.bq.util.MapUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * MD5加密
 *
 * @author Frank.Zhou
 */
public class MD5 {
    // md5小写摘要
    public static String md5Encode(String str) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte bytes[] = md5.digest();
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(bytes[i] & 0xff);
                if (s.length() == 1) {
                    buf.append("0");
                }
                buf.append(s);
            }
        } catch (Exception ex) {
        }
        return buf.toString();
    }

    /**
     * input 加密内容 algorithm 加密方式
     */
    public static String getHashCode(byte[] input, String algorithm)
            throws NoSuchAlgorithmException {
        MessageDigest md = null;
        md = MessageDigest.getInstance(algorithm);
        md.update(input);
        byte[] b = md.digest();
        StringBuilder output = new StringBuilder(32);
        for (int i = 0; i < b.length; i++) {
            String temp = Integer.toHexString(b[i] & 0xff);
            if (temp.length() < 2) {
                output.append("0");
            }
            output.append(temp);
        }
        return output.toString();
    }

    public static void main(String[] args) {
        String str = "appCode=H0000001&isAdult=1&time=2016-09-02 14:43:44&userId=123456789&zoneId=1";
//        System.out.println(MD5.md5Encode(str));
//        System.out.println(MD5Util.getMD5String(str));
        Map<String, String> map = new HashMap<String, String>(9);
        map.put("sdkflowId", "201706161134362807261");
        map.put("userId", "bq_000050105");
        map.put("zooCode", "z001");
        map.put("serverCode", "s001");
        map.put("chargePointCode", "c00001");
        map.put("chargePointAmount", "1");
        String base = MapUtil.createLinkString(map);//+ "&key=" + propertiesUtil.getString("recharge.centre.notify.md5.sign.key"))
        System.out.println(base);
        base = base + "1234!@#$";
//        System.out.println(MD5.md5Encode(base));
        System.out.println("-----------------------------------------");
        System.out.println(MD5.md5Encode("appCode=H0000033&channelCode=CHA000003&chargePointAmount=1&chargePointCode=C001&passport=17700000001&payOrigin=RC&payType=1&serverId=sc001&zoneId=zCode1&domesdk"));

    }
}
