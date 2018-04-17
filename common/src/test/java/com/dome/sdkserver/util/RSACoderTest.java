package com.dome.sdkserver.util;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class RSACoderTest {

	@Test
	public void testGenerateKey() {
		try {
			String updateSql="update dome_sdkserver.domesdk_app_info set app_key=?, test_app_key=?, out_public_rsakey=?, out_private_rsakey=?, test_out_public_rsakey=?, test_out_private_rsakey=?"
				+	" where del_flag=0 and app_code=?;";
			updateSql=updateSql.replaceAll("\\?", "'\\%s'");
			Map<String, String> m = RSACoder.generateKey();
			String publicKey = m.get(RSACoder.PUBLIC_KEY);
			String privateKey = m.get(RSACoder.PRIVATE_KEY);
			
			// 测试
			m = RSACoder.generateKey();
			String testPublicKey = m.get(RSACoder.PUBLIC_KEY);
			String testPrivateKey = m.get(RSACoder.PRIVATE_KEY);
			String finalUpdateSql=String.format(updateSql, UuidGenerator.getUuid(), UuidGenerator.getUuid(),
					publicKey, privateKey, testPublicKey, testPrivateKey,
					"D0000197");
			System.out.println("sql: " +finalUpdateSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
