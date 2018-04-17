package com.dome.sdkserver.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA安全编码组件
 *
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public abstract class RSACoder extends Coder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);

        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> generateKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, String> keyMap = new HashMap<String, String>(2);

        keyMap.put(PUBLIC_KEY, encryptBASE64(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY, encryptBASE64(privateKey.getEncoded()));

        return keyMap;
    }

    public static void main(String[] args) throws Exception {

//        Map<String, String> keyMap = generateKey();
//        System.out.println("PUBLIC_KEY="+keyMap.get(PUBLIC_KEY));
//        System.out.println("---------------------------------------");
//        System.out.println("PRIVATE_KEY="+keyMap.get(PRIVATE_KEY));

//        appcodeSign();
//        verify签到分();
        加解密();
    }

    public static void appcodeSign() throws Exception {
        String rsaPrivate = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJiEzRFiqyR+Exeapm2/cDGvNDIyMmgG3UQHUwmQmHTf8nHOLwRBIssQFeZL0yT0lgPzZpTOqqMiSp5ViHhc+JmItJICERS2tisNEvz1Jc1RWuCj2CpgZBTweML/rrIdkKc3hDaYJDnRx2SiYz3JyiTG2r6Xiz27wwdlw5UE4tINAgMBAAECgYARUkvD5LPRIixi6uJp5msEiYRhCwhkfhsVBcvtcymNZH2xZRw73HXqH/qMvCHPuaNr8XG+BrakblmRH9+u48gr4ddS6FTLcsfiBAlibOo/nKBkftc7SVR/WPJ2GAA6yQE+5uZANGeuHTzIlE2JoRPod4SjP3WTT4AO4nJtH0cbVQJBAPOVMreuMFYQKxU7jsBR3B0cYRDyYuK4qFLTz6pybx56gOJkyfIPXa16lvkF3K/9PjKSg3RtdIdAaW/pujbB0SMCQQCgSzMjvKKyvL3mC7HQ7CNSThMwIBLvAfj3JWEJR9wuuN/aSz1eKoLAskYtgVvIZ2rhkEL50Hl5u/CX8VOk4bsPAkBz4yv6D7mXeKHGvEsyXL/oabQUp/07+La8kWFfqooLaiUfkOwzafQbsRNa7tsML+QDk4lPPzQNlSuUvxKFMrkZAkAA622HrvVgASU1xKtof3JUbo49oHF7XY+IG3bLDtZ8jiL6paPcfnHxmRQ9/pgANleuo50+watwv1tKA+Bu+byRAkEAzYCy8ihRVFEzhCGWLo0YktsziMWtrycq2zpcdyK+XlgJvvLbD1MHjko5diCnINXAetRiwweKNh2Eq1YmDvoqJA==";
//        String sign = "appCode=D0000281&orderNo=20170111180026681032401481131316&payCode=C0001883&payNotifyUrl=http://rsservice.y6.cn/platform/baowan";
        String sign = "appCode=D0000281&orderNo=20170111100032641929657076945222&payCode=C0001885&payNotifyUrl=http://rsservice.y6.cn/platform/baowan";
        String signCode1 = RSACoder.sign(sign.getBytes(), rsaPrivate);
        System.out.println(signCode1);
        String signCode = "DfjJjl+Wi60JdEofISqSv0tLDzitOVNjzpqB15HJWYJpjlywQBeSXvmI8keM2IRFmKafnB/d5P8g" +
                "oUS5Z5NR0ImsLkFCkHtx/Z6LRzDnQrf19kNz3cds/VGSh9dt53lpWRO30lpPVvnQjRav71MwcGQe" +
                "6lH9b6G71lsObcltNag=";
        System.out.println("result:\n" + signCode1.equals(signCode));

    }

    public static void verifySign() throws Exception {
        String rsaPublic = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIAt5u+adjWIi7hVA9pKGGjNQKNFeyaDgG59rwUAANREkzkYJtRrj5TtiqUKy+695QAfcowRv0u02QTTw1FBTubVxkEb2aOmHaYWV+KG73ZfcA3PdcgcuoD/ToW57JmtNC5f+dNOTJWzpaK8WZzoUshVIi5dOUe1uFnZiXx3e9dQIDAQAB";
        String str = "responseCode=1000,errorCode=\"\",sdkflowId=201609091456250236910,orderNo=S3-33542912001561";
        String signCode = "k5dqGZS7yBK4S0F/YJ+Sko8ecdGRWXAP/yvJSS1W1RAftg9UZCSi9wrIZKZrMAlOQOdwLz99xD5rJe9S1L4l3q8pM4HahqI3UV6AV17qkqKtJzAXuGTk74tfKibZG3eLwalcCDpWFFWOeSMsIIhCWfqVS4kY95E+0PkSEikNtnM=";
        boolean result = RSACoder.verify(str.getBytes(), rsaPublic, signCode);
        System.out.println("result:" + result);
    }


    public static void verify签到分() throws Exception {
        String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIzyfQbp33FoT5M8aRG+Mz51RTX33eHlLW9VzX36H4ePEltXZI8gRYuwai4n/hMZrI0OEP15QlWRtCgHa2daiHalSy0jGm5IixSGOiKTJm/UOUisnXSQWKIljbVVoExYmW/rMGbPxAfo6bLwG5JnIPP3z16RI4l2GV00HiSS4TlRAgMBAAECgYAZtpNAk6ID2nfJ8JdqeTx4BITf15xO/bRQP2MBvIPMAzniGAtsYQLO/Rwh14Hukhx8jzhiRaDoY3BubQwXCtkiJX82vbKPn70bWwyE/5KPd/a3tVaSlbR0FOQ0bQEiDHgorXnVeWVJzuMh7QnxF/AqsPynLim1+9wVcm0ZgCnKwQJBAOR8iE+wny9IE3Dx7BJmqsl2q6H0+0148FlVbSVsiATiR4THeExMcsSlDXrCFkOpjai342j61dci+JnIQpzgloUCQQCd62qQ7E6p/XpbQjdxbXusgFRTaNbeLE+ICdU5P1l9jLzgqRqjcKmFoQrceMlEPguVbBjb6etCE5IdEIKF0c9dAkEAvlXHL6tQUBDYsSO2pnWEHCORQm9T0OvIPILfU2efv4HIViHcYqLkkE92dSataVwbN8tH07Hw5TCqq2CZrRmiCQJAB/REoNoWOD+5OwGB02fFU/H+zVi5geVPeDWulLyZIFbHaLV7zwGsuDtdVa+Ly7EoWjVgpysIG4Lq2CXtvRnNTQJBAIKLFuvYLy2a5wiZGxaF2k9mXtfk0FpfHZf+z39Rmw4D9xhrr0TK3LpCpCMtm2BiNbzLiYh5zOAcB3BRDq4lnHg=";
        String str = "O/Om6bmSYqZy5czHwp09BPtuendbbjQFDOe6sD7wMD8sohGY3BvQP+g8lpAxUkvUU7aih/UxegR4z6g1XUiw1n1jO7MbxFE229Hca6zPoIJfZra6sy30mHEf2mO8L+wf9qO8HZNtAwKV9Ai2MNhhWE8dcR5wydblhyqr/DcrWmA=";
        String result = new String(RSACoder.decryptByPrivateKey(str.getBytes(),PRIVATE_KEY));
        System.out.println("result:" + result);
    }

    private static void 密码() throws Exception{
        String privateKey ="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALnan+lGhnhIbKc4wpaVmNzwMHAzKS7JX2vS/tNb/hyck1cwzmWp7XM0D2eXqsKxIUBwX/d/Nd/lSswVdlcz33REYDcyvrEifk0+pz3yfamWQfgVAQhAt3vPkGK3rGQnACkAddOhPcUD8pCK5cIHaZrbIIByETNoSOV64POqrV+1AgMBAAECgYEAkAFSPpIixzh21+sm+z7WvvpszcMWeeYWRZmrUWHKN6kqkTiD9EoCbm08MmxWJ5zPwvpPoZmzyNQFbLSEiXTsAfidwo0eHBVZd23cneWmp0SW8A2KwU2wFSAKWLg0uHz6O3Zn2rJIJOL485Hnuy8ly4tNAeHjO7jSnu0A/rOA5QECQQD+dHeZfVhugrzKuaMvMXwKKRtkP6i+3wm6c2coYTL8x+cG6m5a/oSwK7YbUt5eRInnIomEdSJE311UoyUcdz3FAkEAuvuFoPSCnAnbjWBteuaj8TcwCB4zSD7+Jp6U/Ez7t87AAWRuMmj/daU5hYRs2gp+qujn6RZC4tnVliPGWH4pMQJBAJ2HgyeeJyo7fB/Rb4pfC1jK/C9DDfgk4sPwgBPKoSNHqupoJivlWEYsuZAO7KXVrr5Obm53ieCzm/czDV2jILUCQQCO21JKGeWb//ANQMoEZyiER8zbtu8pWG9m5Cwh41NCKKULq0aCHzRmFtkuyHE92VzTiWR98fMiTJnhvu1weNtRAkBF614rYDbQNy83SKsuSG72LB9j+1rcn0y8CRdPB9Uv8JWaq0KQbQJbxa2dA7+3uNPzirlZDSr+ABPimpOi1CwO";
        String password ="Is4c3zbodpW3aBYLiQbqNHmPBtCSImiFgr4Y6zfzKocl0HAYmFNYOmyYUFbjIP7A7dFG gko9lw\n" +
                "U8By9C23vKSxW5266oukoKHxpQuUY/XrR5mZi6dQVnyiqmamjc59wtbzcKB8TC9d5KdyVRbCp/58\n" +
                "gkP5v8v6BhoE5uVdH88=";
       String res = new String(RSACoder.decryptByPrivateKey(RSACoder.decryptBASE64(password), privateKey));
        System.out.println(res);
    }


    private static void 游戏() throws Exception {
        String privatekey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKjmJCPM/MCEn06UktIfuv01NsU+axpgHXcJbazdSVmkkpAPLgjF9012DrQvVrmqRjRRtjuIlf/Iqh5l0Jh6ag/HzZwD52Hh6E1Q5rufmv01w8sJiL08Wbh5TwcugTP/im9j2mVP7JYr9BoXh0KH2mo4ExG72Oh1eMWFByd4kQXZAgMBAAECgYBITKvvv1H6v0rKgoMO+5I5zcuyR3PliHtUd27BbsB1ZSNwE29rRgME36KPsyv2slL3FnthpKhBJA7ekEro2o4Hm5juGv4IvOrrxJXAsN1qq1DjLMQ3HFkNyj3v1WumPr4sXLSf4YaaBtFazDUPuMTRWehooRjVbnbblNxTz78r4QJBAO5qWWcxO4uIdLC7W93CZO+nm7VeSo07BtmGeleNESsAq5HgUt71/+j5ajDm0YgtoYfVPlu3Le+9ua+c/g+mW00CQQC1WzblYkEs24q8qbFG0X/nGhmPHtwquqlYR8YY7xoaV7GUdq5j+5LMoVq2Iuo9C+ePy1XVsC/z3rBmsIxBAxa9AkEAsI+FwgwNwtm9QRyffez2fxyrDXfU0h6Chhk3BRttOWF3rcJo3MFlUY1T/P8S8TyJwWREl/tiQ4dHuiwTYleaiQJAfwnKRpyxMw8G/GmSOR2ADw8FYHApofTns2NzrPuwuUs8pdcNChSsHJPay0Nh+ilgqCWzluC4dohD4WtClDfpsQJBANPj9Td+LjAvmwjYMcKjOeEhrLKc6MVeSkQb19vE9avDeRY9MfzOFFZL0+KXQeBzl0VNidipkDLyS78zoc5wulg=";
//        String data = "appCode=D0000328&orderNo=12-10059-1489395830-2148&payCode=C0002141&payNotifyUrl=http%3a%2f%2fbq.api.yybpoker.com%2fapi%2fpayNotifyUrl";
                                                                                                      // http%3A%2F%2Fbq.api.yybpoker.com%2Fapi%2FpayNotifyUrl
      String data = "appCode=D0000328&orderNo=12-10059-1489395830-2148&payCode=C0002141&payNotifyUrl=http://bq.api.yybpoker.com/api/payNotifyUrl";
       String result = RSACoder.sign(data.getBytes(), privatekey);
        System.out.println(result);
        String signcode = "LcVnxE3ozlSNbhBmyKgmUOvMLyqo0avzyZWn8pKg6A/q+d/j3WSBumY1wMC6zCzRuKgGQMLLYLyqKweFRRTYVjtVaF/PPVyF/Lh0CMCGKwd86aSMw5KVy+sB3CIpex4Mpr5mHJyP0qT9x4YTAh2jznBomeQxSNUC7xDh/3tq4Bw=";
        System.out.println(result.equals(signcode));
        System.out.println("-----------------------------------------------------");
        privatekey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAInl5S/TM0B8osqRVi5uIAku+XUdySyh4QlkTp/SN1+CVCCC5lhbvi5NU4ScXy0QFoG4Y3I5bRqANB5jYCoY/h/QadqUT4tsP5oZs4Ta/Pxakp+drrJ31qwnT9fKhwEbw8uVl4qbUeZzeOxxWQWbsZbxIbugbQGeLljuaOGo/ho5AgMBAAECgYBDhGRqviXh9SRfujlQEmttE6rgRO0zBS+BwbMdXXfvM4itQmo16PV9Bi9WRpnzHmVmrjlCvAtHZm4eUtnKwuHpR4SxaK0NdRCYMU4BKEs5nDNtM1LaKCVtbfWwGZXNNxPC/ifVqA8OenBF96GPHQzmO5LJBnHWYY6gG3Eui47aYQJBANvm5A07imI0wG4l06Ii860Pqi3A0pZHj115bxJTU4yUbVCtBl4+b1oqZRhECc/9qc+VuL4CJZYd1qdXQkw43nsCQQCgiOQqzNrKncvOGM9cXfGkR9iZOSTJoM+pU3Tqcs9Y7SptrWf3FjiqpSKiOoW7RJcXhLLWtvau3Js3a+sVkCXbAkEAqxS/+deHuw3FZCyDsRG2OL8GLCBm9cnorNVr53rydl2HiouCd9HlOeXfzvgoFiwIb9rQ5uquqVK/jGNjzf80fwJALhSdShvbgeWhpttASoc84act6W2ZvbbHZRdrrBCgrCoHz8vC2A4s3e7g7ihWUtbxd/LDUxbZ4H+6nkIADQR/mQI/DGUQi7YNw/kww5L+10CWmgKNNWE/dmD+euzSQEoqhmRsdlKKlVdKNRr6ytvxHew14Adoo7mEpgU8zOHxYgZN";
        data = "appCode=D0000310&orderNo=1214450102282748041&payCode=C0001962&payNotifyUrl=http://115.159.91.228/u8/pay/qbao/payCallback/28001";
         signcode = "Aka8BkOLt4/lVAVDro8fNEh/4QmRSsCB9a5bJ/OF5pngRYRNWHwV8eRd20GA05Ww6tvfHXYbF+oyGOHG4n234vk4jNok9oUkN3zZMIvl1eMPdsOV20aCk3sSwg17EZpz35OClCPpNunE2SMp1CD99cavfuF26TfO6OhBkI1dZrk=";
        System.out.println("2:"+result.equals(signcode));
    }


    public static void 加解密() throws Exception {
        Map<String, String> keyMap = generateKey();
        String publicKey = keyMap.get(PUBLIC_KEY);
        String privateKey =keyMap.get(PRIVATE_KEY);
        System.out.println("PUBLIC_KEY="+publicKey);
        System.out.println("---------------------------------------");
        System.out.println("PRIVATE_KEY="+privateKey);
        String str = "datasource01";
        byte[] b =RSACoder.encryptByPublicKey(str.getBytes(),publicKey);

//        String result1 = EncryptUtil.base64Encode(b);
        String result1 = Base64.encodeBase64String(b);
        System.out.println(result1);
        String result2 = new String (RSACoder.decryptByPrivateKey(Base64.decodeBase64(result1),privateKey));
        System.out.println(result2);
    }


}
