package com.newcapec.wapcashdesk.utils.encrypt;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
/**
 * @Title: RSA工具类
 * @ClassName: com.newcapec.wapcashdesk.utils.encrypt.RsaUtil.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-20 18:33
 * @version V1.0
 */
public class RsaUtil{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 支付前置私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) );
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
	* @param public_key 商户公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String public_key, String input_charset){
		boolean result = false;
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			result = bverify;			
		}
		catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
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
		//String content = "{\"partnerid\":\"SC0001\",\"journo\":\"415447\",\"subject\":\"测试缴费\",\"orderamt\":\"1\",\"createtime\":\"2016-05-18 16:42:55\",\"notifyurl\":\"http://192.168.1.112:8888/PayPreService/testAction!test.action\"}";
		String query="{\"partnerid\":\"SC0001\",\"journo\":\"415000\"}";
		String str = sign(query, "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANr6o/IhxJlmJVwZtj5rH0ma4WplqWrSTAfLD2OFvuLFUkw9lQjpPkqaOqOo1Iv4AoXhVT+rUYe+VnJ7DkqXCpgoa0S/NF7TyHSR8Pmc9933YCl8EbptUna0+QUHGgjLkc5lSuqPGvYtXZZq64bPL3SsRUHf/E/0yN1l1jaV1W7nAgMBAAECgYEAn9aFpu7pD0OVD5YhBYyxqMtpsH0GXdek8adR00ZD4ElB2i61Q2zlMBVFqkJI6uYcCVdCH5hQibHwbd3Ov7liW/azyF/7WKK/CgqNrilQ53G98MEJQnoOvZzsqz4t10o/mHg0l1WMM4meiYOfCUSjLBhgizvn5VP84Lm2heonmKkCQQDutOCZP2jmPo4hbx0dHec9yC2Gp5j5HAW6pqr56HQUSQNH9P8bPRbzAfDp1JSxQZmRJqlllSXQatV8gzn3i1YVAkEA6tfkJvbmYvlPFyIdG7E5WmYnV+tvtnJ0cofzmQcwauRn8QkYkgZgiDH7+9brwtVbraPs1N6XyilFndGFEUDMCwJBAIvfTSQ/NbaEvT/2HfcryI45OAQJPOfrdafzdYhOn4YralLFyp4pY8MtRf+eYyqip9uX7fbqOGgx0brOIi9dZCkCQQCoHSJ7bjXg1j3y/n297DF1cDlr6+ONxQNtVbthac8URivOVcecCSqjdLzBPMqLL/cwJ3ALtskMdF0cq/dJMYyfAkBcQocJy6v7e9YZqRai7BHz5jonbc9izsH6fV2yJf+rzQls1ZQ32g7069ywgx2Rpvm5Trdw79nfl0Br+iLUP2cD", "UTF-8");
		System.out.println(str);
		//sign
		//ak0V0gSP6VBxABzGiG7+3u3dWnQqa+W+41mn/EG2qwJMZPC+sfsuMglgUzZCxtKLUEmbiv49RP2D1p3fcp0RDegqw+MT8Kygb5VpN3yEnGWoIegxM5Q/VVoQmOEURawwa8QjnllGm46/k8OBPNIpt+2mow/AWWkQIEbcC88Truc=
		//partnerid  SC0001
		//jsonData
		//RuCs+lHaOYnEGzBMDSu0fybCoH66eP6B2dVaWv5QH/h5UoVP49Xb4F5HG3iPhfw7ckaHzOWy8RaI0cQgcHm8DCqJ368Uu/lQrJEk6Rh47Hrf3ghsC85W37hlZret5VYGg85YrH4TlpFumDWfVNBLEa5hOo2TAHq5GVbfCq54Hx6AkHpUzTrmkwbJ0D40qcqTLI6KPsxXJIWBQZpS7ye8Zy4kG+tk4ggsGTREiMBNjfE=
		try {
			String des = DesUtil.encrypt(query, "0", "X6kWEiP8BwjE3esQbIUQKST4iRHEtgKU");
			System.out.println(des);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
