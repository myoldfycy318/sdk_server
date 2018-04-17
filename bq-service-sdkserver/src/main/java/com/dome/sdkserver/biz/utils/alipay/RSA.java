
package com.dome.sdkserver.biz.utils.alipay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	byte data[] = Base64.decode(privateKey);
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec(data); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param ali_public_key 支付宝公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	* 解密
	* @param content 密文
	* @param private_key 商户私钥
	* @param input_charset 编码格式
	* @return 解密后的字符串
	*/
	public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

	
	/**
	* 得到私钥
	* @param key 密钥字符串（经过base64编码）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64.decode(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;
	}
	
	public static void main(String[] args) {
		String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALys+oYaxqv4FYju8C1poM6qmHLjWPnXzqEJT0NxyFXgdaK/Qe9DcpcASod9mIAdlLIxJEyYNlWeonAJVYW8pQ+pTVGwI9n0iaT71ldWQzcMN3Dvi/+zpgw3HxxO7HJtEIlR84pvILv1yceCZCqqQ4O/4SemsG00oTiTyD3SM2ZvAgMBAAECgYBLToeX6ywNC7Icu7Hljll+45yBjri+0CJLKFoYw1uA21xYnxoEE9my54zX04uA502oafDhGYfmWLDhIvidrpP6oaluURb/gbV5Bdcm98gGGVgm6lpK+G5N/eawXDjP0ZjxXb114Y/Hn/oVFVM9OqcujFSV+Wg4JgJ4Mmtdr35gYQJBAPbhx030xPcep8/dL5QQMc7ddoOrfxXewKcpDmZJi2ey381X+DhuphQ5gSVBbbunRiDCEcuXFY+R7xrgnP+viWcCQQDDpN8DfqRRl+cUhc0z/TbnSPJkMT/IQoFeFOE7wMBcDIBoQePEDsr56mtc/trIUh/L6evP9bkjLzWJs/kb/i25AkEAtoOf1k/4NUEiipdYjzuRtv8emKT2ZPKytmGx1YjVWKpyrdo1FXMnsJf6k9JVD3/QZnNSuJJPTD506AfZyWS6TQJANdeF2Hxd1GatnaRFGO2y0mvs6U30c7R5zd6JLdyaE7sNC6Q2fppjmeu9qFYq975CKegykYTacqhnX4I8KEwHYQJAby60iHMAYfSUpu//f5LMMRFK2sVif9aqlYbepJcAzJ6zbiSG5E+0xg/MjEj/Blg9rNsqDG4RECGJG2nPR72O8g==";
		String content = "_input_charset=utf-8&body=购买月卡每天领取100钻石&it_b_pay=30&notify_url=http://localhost/alipay/alipayNotify&out_trade_no=1462352782184&partner=2088221500798101&payment_type=1&seller_id=cheyuanyuan@qbao.com&service=mobile.securitypay.pay&subject=月卡&total_fee=25.0";
		
		String privateKey = "MIICXQIBAAKBgQC5L5JY+4tdDsSUZWpk3+pjQz5j6i5uY0/tIqzydtDhQEioOAWcXe0Cl9dbGs/I7qFZeeG12XK/1tgCeKLz7dfbewkufwZFzZTUNhx81J+afqibxhdFDlPKxi1b3OiuqEkZ3Kdbq3hLby87fG+WpGfDNO4jiJxJDp4MgtBtjI9znQIDAQABAoGAECj1lWxGChXa07RR54Geu5DHpgJukNV7yQ7IltQFgFKSxJM28iFEKIDFmz6ouTkRvXQkXTEfkVwXN4f9eFneBzQiKjo7Lb5Z+FHbU7NtFOirqnfpkwjjP42CdvsbUlWfhZ8pdSbqoOmAOd1+dk29b6ZCVqWoC74v6D1Fw7gb1WUCQQDZviCT+PWTFStbsp88wo8rSO3OGuV9v570KdGKYWD10iBta99atJC2aXS7eNJPBhUfZvc9BNRNPqVb+DpAPZ5jAkEA2bkQ2tRgOt6d5NvxE4I/7vHHrnujF2/wKXrmxv0OLkkrt7NB7X1qDYwx1u2OBuD4AGaADBYplnXmUMmQL+pF/wJAMZ4HonB61Vdfl/euHZRgN4bY2bx7SxpdSFo3K4dhJuMJDKYmMW8wAsEpHoc7tVI3gf5enIL7Ndbuq3R3Iaho7QJBAMCDuJiWY4SKZbkAlbk3WYrRtUvePQazTvuOldLr3Ycl4gcr9Sam14ilJ0ixgv0oHYJVH/IQArPtCjrzD5CeqUcCQQCfYcT2+TiLrSCClJ5ovHIybCuL+PU18bJMNwYOnaWCoDOgN9pQ/bKUSLkglFAq5U1+80e0Hxna1XkEVJdKBmNV";
		
		String sss = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL+KsMBNcbGjXwLHnq7iB2YZ+sap1sWEQzosi55qMYRdTRU7gNXyWX5a9kFtLf5JBXuSiVd5TRdw+Bo7OnDXT4Q+IAiu4jWEegSelAQRIfnJ6XK/f8xgPLA+OLsVB2sIstlYx3oVtn5EP7qBGXbShphfAlXVz3muc4M/m1nmkk2xAgMBAAECgYEApUggTVCGkyrOia+HlpEkoU45v8Q+uCHxgPl/YStxny6DnZbDDdsarpX/eWWlEpAgUwmXygu6IkgxTzFctJKW4Z5D0pJmpPsLoNM9EDhbIHdHGLGpb3jFq+/7oy//cCOOsXLVzF91/sVCCUpuRiJBxsBLuTE+xO7DqAbYBLdCOgECQQD0zBemkcBOWqN391s9hmOAhDhWYxffC2zU35Y9x0TLEE40lo4v1a9BMdL394HuDEmf1kKVC+YJYSjW/6jORkxZAkEAyE6vyDWNpgXCI2X/B1fTem6JEKFtCyVvo9YkUT7FycEDcFp/z2obDC08jAgiFfJ2ba2uFWEU58aroFeOZ5yBGQJBAObsrJbeQXnouPJPbkvAvZabpAWiHv+yaGz7qmAG3Zdtf0jOib0IJ/YpSSnP20qzXrSgS1kMy2kntX3z2MsVt6kCQHvE2yr7pEXUwwu6Z2XzJ7I8AKdtB4Leo+f5VsF45x/uCqDu2pCgAIx6mzHviJhFFFZa/fkp3pAEm2HdGBn/g2kCQF9S2h7I6zzkH/bmdqdUNbubW7Z9P+3zA/DWeVsdo/IkC78Ee9q3bL4eCDFqEwFF7WOg3OwJ5PPbMol2xFJHj1M=";
		
		String sign = sign(content, sss, "utf8");
		System.out.println(sign);
	}
}
