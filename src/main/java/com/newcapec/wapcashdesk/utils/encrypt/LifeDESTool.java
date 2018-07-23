package com.datalook.util.encryption;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
/**
 * @Title: 悦生活-DES加密解密
 * @ClassName: com.datalook.util.encryption.LifeDESTool.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-05-07 12:01
 * @version V1.0
 */
public class LifeDESTool {

	public static final String ALGORITHM = "DES";
	private static final String DEFAULT_KEY = "Key4COM!"; // 8位密钥
	
	private static final String encoding="UTF-8";

	public static Key getKey(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		return getKey(strKey.getBytes());
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 */
	private static Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key;
		key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	/**
	 * 根据密匙进行DES加密
	 * 
	 * @param key
	 *            密匙
	 * @param srcString
	 *            要加密的信息
	 * @return String 加密后的信息
	 */
	public static String encrypt(Key key, String srcString) throws Exception {
		// 定义要生成的密文
		byte[] cipherByte = null;

		// 得到加密/解密器
		Cipher c1 = Cipher.getInstance(ALGORITHM);
		// 用指定的密钥和模式初始化Cipher对象
		// 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
		c1.init(Cipher.ENCRYPT_MODE, key);
		// 对要加密的内容进行编码处理,
		cipherByte = c1.doFinal(srcString.getBytes(encoding));

		// 返回密文的十六进制形式
		return byte2hex(cipherByte);
	}

	/**
	 * 根据密匙进行DES加密
	 * 
	 * @param key
	 *            密匙
	 * @param srcString
	 *            要加密的信息
	 * @return String 加密后的信息
	 */
	public static String encryptStr(String key, String srcString)
			throws Exception {
		return encrypt(getKey(key), srcString);
	}

	public static String defaultEncrypt(String srcString) throws Exception {
		return encrypt(getKey(DEFAULT_KEY), srcString);
	}

	/**
	 * 根据密匙进行DES解密
	 * 
	 * @param key
	 *            密匙
	 * @param encryptedString
	 *            要解密的密文
	 * @return String 返回解密后信息
	 */
	public static String decrypt(Key key, String encryptedString)
			throws Exception {

		byte[] cipherByte = null;
		// 得到加密/解密器
		Cipher c1 = Cipher.getInstance(ALGORITHM);
		// 用指定的密钥和模式初始化Cipher对象
		c1.init(Cipher.DECRYPT_MODE, key);
		// 对要解密的内容进行编码处理
		cipherByte = c1.doFinal(hex2byte(encryptedString));

		// return byte2hex(cipherByte);
		return new String(cipherByte,encoding);
	}

	/**
	 * 根据密匙进行DES解密
	 * 
	 * @param key
	 *            密匙
	 * @param encryptedString
	 *            要解密的密文
	 * @return String 返回解密后信息
	 */
	public static String decryptStr(String key, String encryptedString)
			throws Exception {
		return decrypt(getKey(key), encryptedString);
	}

	public static String defaultDecrypt(String encryptedString)
			throws Exception {
		return decrypt(getKey(DEFAULT_KEY), encryptedString);
	}

	/**
	 * 将二进制转化为16进制字符串
	 * 
	 * @param b
	 *            二进制字节数组
	 * @return String
	 */
	public static String byte2hex(byte[] b) {

		if (null == b) {
			return null;
		}

		StringBuffer hs = new StringBuffer("");
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * 十六进制字符串转化为2进制
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2byte(String hex) throws Exception {
		if (null == hex || hex.length() % 2 != 0) {
			throw new Exception();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	public static void main(String[] args) throws Exception {
		// 密钥
		String desKey = "kdheyejfkdfh";
		// 待加密字符串
		String data = "待加密的字符串";
		// 加密(enStr为密文)
		String enStr = LifeDESTool.encrypt(getKey(desKey), data);
		String deStr = LifeDESTool.decrypt(getKey(desKey), enStr);
		System.out.println(enStr + ":" + deStr);

		String str="5702D850677B4496F595A2B9E49884D8E35F27920B465E3D43CA52A7B7DBC09D8DD407AD2BB0A41AA187C7124CD1CC91BAA1F8F0435E33783DD1477F8BE51412E1E667DEC8CD064C92D42AA48E304574AD76250EDA546B95DED5ADBAE92359BD67F3AC41D6F64E05A09866A18EFC1DF4CEEAEE2F06F1FB77FBF835DA8C51102524D3F4B7EBCD096B845351B2FF3AB114F5491A91DFDD5EC15A86D509B7C1D0C535D3FA23DF151A8ED599EA312B2F92F0950EC36E2C8D63A7252BE15917E62D7F274F827720C87EE78510DDDCA78D25C5A9D06E8D06043DBAAD6D3651B3B6039608F6324BAD422D09FC58B8D90241956FC2D28E23D0EF44424CB41469D6B46C8ECC64D1D8D4F7F9A31510F9021FB73C59E56BE1A77BA1C8B5FB495E47EDED92B5CC0B376E8A9A81D4112CD6C05D1616293D982EDD7053851EFEDE0558D9E93D021A80C4E4E74F30ED1085B5ECA27C57688161900A9A30CC5DA080D541F25B0A22BE2F734C64E0A2F4EAED7FCAF506A405CCDF49834522522B8127D5E7D2B268A369295485A0157D6F398AE02B94E13DAA8883132C8AFB4D567D3C25C91CF27C8CBAF5C1747C7103E224F2C01A8D56B5E56FFB9D50BB088385903BF0E7C226C84CE9C09FEBE164D8CAE26B85FCA6C4C78D1B841B208D28029438B9C69CA5F016627A1858A6E0EABA4342A75AA52A6FF92FE768B1B2B32961E94F135F86BE29409BF0E3AA13617C8F4EBE4E4C0D845CD3B0A2E9651A659EC6FB7BFA982D441A5DB3769AE7D4C2C20C1437A96CBB33F1F506D1ADC8B52EAEA5E45B72BA92BE7AD679271F0857EA9E5B657443B09CA8CB81FC98B8130036C45B61774ABA160F684B751F5CC051564A549F31F9C5ACEC9D576FEDC719EE9037B0286EBD358FB65BDFA8E168A1A1BF72DB09E9F14E01C3A108398B57CD640D0B904C31CB6DDAE4CCF2BC1085B5ECA27C57686F586DDAFE4A719C9322203DD51176F7F53B4EEFE550B4526ACEA7BB0551D91032C0661B245B5696EE389C41A26C35A868F63562B4FCC4597FDA9CE1FB23C0E1D508F062AFD9502F1F8132C0FC736A478EBC3FAF423915C69F6D6B7A8EB055C2DC3D3F98C87F3BBC4ABA95DA5D1E60918B49E7129044D48032C0661B245B56960126E2C9042CEEEA1A70B9419E0799708F4AEC8BB2547FE73747638496C49662";
		String ddstr=LifeDESTool.decryptStr("27wN7Cy38KT9pb0", str);
		System.out.println(ddstr);
	
		String ss="4C4A647F371B43F1340EFAA95302442BC59D79B9E834A07D885C2122EFA4DDFE1FC6A8E9A4168CAE24567A8C85793C82A1EDC81AA0680707D1CC621267BBAAAC2B54B96D03456A36572824AFA61E91D8BA1584E76C4F39BC29263AD760FAE7EA8BE16EAAAA668D7F340B76DDFF28AB4A1D03C831E6D40CEFD3C6344317474D530A81C99D4E1E58203269EA3FCEDB9FCC58DE9D1F71F002498BC6DE4C8C5906FF5D41F61FFDFA86A18E9747D05A1DCC7E6A77B906AAA2088A";
		System.out.println(LifeDESTool.defaultDecrypt(ss));
		
		String stt="ccbParam=11111&APP_NAME=222&ccbParamSJ=333&MP_CODE=01&SYS_CODE=0760&SEC_VERSION=4.0.0&QP_KEY=111";
		System.out.println(LifeDESTool.defaultEncrypt(stt));
	}

}
