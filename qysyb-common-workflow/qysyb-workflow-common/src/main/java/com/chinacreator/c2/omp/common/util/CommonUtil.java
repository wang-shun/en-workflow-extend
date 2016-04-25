package com.chinacreator.c2.omp.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.UUID;


public class CommonUtil {
	private final static String C2CLOUD_SERVICE_PASSWORD="c2cloud_service_password";	
	public static String randomToken(){
		Properties prop = new Properties();
		String password =prop.getProperty(C2CLOUD_SERVICE_PASSWORD,"33e52386-c0cb-472a-8939-8490f2be5e3a");
		String uuid = UUID.randomUUID().toString();
		String md5_password = encodePasswordMD5(password, uuid);
		return uuid + "_@_" + md5_password;

	}

	public static String encodePasswordMD5(String password, String salt) {
		try {
			int iterations = 1;
			String saltPassword = mergePasswordAndSalt(password, salt, false);
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			byte[] digest = messageDigest.digest(Utf8.encode(saltPassword));

			// "stretch" the encoded value if configured to do so
			for (int i = 1; i < iterations; i++) {
				digest = messageDigest.digest(digest);
			}
			return new String(Hex.encode(digest));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}

	private static String mergePasswordAndSalt(String password, Object salt,
			boolean strict) {
		if (password == null) {
			password = "";
		}

		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1)
					|| (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException(
						"Cannot use { or } in salt.toString()");
			}
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}
	
	public static void main(String args[]){
		System.out.println(CommonUtil.randomToken());
	}
	
}
