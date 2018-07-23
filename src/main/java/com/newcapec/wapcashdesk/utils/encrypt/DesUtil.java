package com.newcapec.wapcashdesk.utils.encrypt;

import com.newcapec.wapcashdesk.utils.encrypt.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * @Title: DES加解密算法
 * @ClassName: com.newcapec.wapcashdesk.utils.encrypt.DesUtil.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-20 18:32
 * @version V1.0
 */
public class DesUtil {
	//private static final byte[] keyData = Base64.decode(ThirdPayConfigUtil.getValue("keyData")); 
	private static final String algorithm = "DESede"; //算法名称
	private static final String fullAlg = algorithm + "/CBC/PKCS5Padding";
	
	/**
	 * 
	 * 功能描述：加密方法
	 * 时间：2013-8-9
	 * @author ：fulihe
	 * @param encryptSrc
	 * @param encodeFlag 是否加密 1-加密 0-不加密
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException 
	 */
	public static String encrypt(String encryptSrc,String encodeFlag,String keyData) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		Cipher cipher1 = Cipher.getInstance(fullAlg);
		int blockSize = cipher1.getBlockSize();
		byte[] iv = new byte[blockSize];
		for (int i = 0; i < blockSize; ++i) {
		 iv[i] = 0;
		}
		Cipher cipher = Cipher.getInstance(fullAlg);
		SecretKey secretKey =new SecretKeySpec(Base64.decode(keyData), StringUtils.substringBefore(fullAlg, "/"));
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		/**加密*/
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
		byte[] cipherBytes = cipher.doFinal(encryptSrc.getBytes("utf-8"));
		String cipherString = Base64.encode(cipherBytes);
		//System.out.println(cipherString);
		if(encodeFlag.equals("1")){
			cipherString = URLEncoder.encode(cipherString,"utf-8");
			//cipherString = URLEncoder.encode(cipherString,"utf-8");
		}
		return cipherString;
	}
	/**
	 * 
	 * 功能描述：解密方法
	 * 时间：2013-8-4
	 * @author ：fulihe
	 * @param decryptSrc
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws Exception
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String decrypt(String decryptSrc,String keyData) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		Cipher cipher1 = Cipher.getInstance(fullAlg);
		int blockSize = cipher1.getBlockSize();
		byte[] iv = new byte[blockSize];
		for (int i = 0; i < blockSize; ++i) {
		 iv[i] = 0;
		}
		Cipher cipher = Cipher.getInstance(fullAlg);
		SecretKey secretKey =new SecretKeySpec(Base64.decode(keyData), StringUtils.substringBefore(fullAlg, "/"));
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		/**解密*/
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
		byte[] resultBytes = cipher.doFinal(Base64.decode(decryptSrc));
		String resultStr = new String(resultBytes,"utf-8");
		return resultStr;
	}
	
	private static final String[] keyArr = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0"};
	
	public static String getDesPassword(){
		StringBuffer sb = new StringBuffer();
		Random ran = new Random();
		for (int i = 0; i < 32; i++) {
			sb.append(keyArr[ran.nextInt(58)]);
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{


		String key = "kdheyejfkdfh";
		String en = DesUtil.encrypt("待加密的字符串", "1", key);
//		String de = DesUtil.decrypt(en, key);
		System.out.println(key);
//		System.out.println(de);
	}

}