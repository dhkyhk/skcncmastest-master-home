package skcnc.framework.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.slf4j.Logger;

import jakarta.xml.bind.DatatypeConverter;
import skcnc.framework.common.ContextStoreHelper;

public class SHA256RSAUtil {
	/**
	 * @Method Name : getKey
	 * @description : 
	 * @date        : 2021. 11. 10.
	 * @author      : P21024
	 * @return
	 */
	public static KeyPair getKey() {
		KeyPair pair = null;
		
		try {
			SecureRandom random = new SecureRandom();
			KeyPairGenerator gen = KeyPairGenerator.getInstance( "RSA" );
			gen.initialize( 2048, random);
			pair = gen.generateKeyPair();
		} catch ( NoSuchAlgorithmException e ) {
			return null;
		}
		
		return pair;
	}
	
	/**
	 * @Method Name : encrypt
	 * @description : 암호화 모듈
	 * @date        : 2021. 11. 9.
	 * @author      : P21024
	 * @param data
	 * @param privateKeyStr
	 * @return
	 */
	public static String encrypt( String data, String privateKeyStr ) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec( Base64.getDecoder().decode( privateKeyStr.getBytes() ) );
			PrivateKey privateKey = keyFactory.generatePrivate( keySpec );
			String encdata = encrypt( data, privateKey ); 
			return encdata;
		} catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) { 
			throw new RuntimeException( "암호화 처리중 오류가 발생 했습니다.", e );
		} catch ( RuntimeException e ) {
			throw e;
		}
	}
	
	/**
	 * @Method Name : encrypt
	 * @description : 암호화 모듈
	 * @date        : 2021. 11. 9.
	 * @author      : P21024
	 * @param data
	 * @param privateKey
	 * @return
	 */
	public static String encrypt( String data, PrivateKey privateKey ) {
		String encData = "";
		
		try { 
			Signature rsaSha256Signature = Signature.getInstance( "SHA256withRSA" );
			rsaSha256Signature.initSign(privateKey);
			rsaSha256Signature.update( data.getBytes( StandardCharsets.UTF_8 ));
			byte[] signed2 = rsaSha256Signature.sign(); 
			encData =  BytesUtil.hexToString(signed2);
		} catch ( NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			new RuntimeException( "암호화 처리중 오류가 발생 했습니다.", e );
		}
		
		return encData;
	}

	public static String encrypt( String data, RSAPrivateKey privateKey ) {
		String encData = "";
		
		try { 
			//KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
			Signature rsaSha256Signature = Signature.getInstance( "SHA256withRSA" );
			rsaSha256Signature.initSign(privateKey);
			rsaSha256Signature.update( data.getBytes( StandardCharsets.UTF_8 ));
			byte[] signed2 = rsaSha256Signature.sign(); 
			encData =  BytesUtil.hexToString(signed2);
		} catch ( NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			new RuntimeException( "암호화 처리중 오류가 발생 했습니다.", e );
		}
		
		return encData;
	}
	
	/**
	 * @Method Name : decryptRSA
	 * @description : 복호화 모듈
	 * @date        : 2021. 11. 9.
	 * @author      : P21024
	 * @param signature
	 * @param encrypted
	 * @param publicKeyStr
	 * @return
	 */
	public static boolean decryptRSA(String signature, String encrypted, String publicKeyStr) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec( Base64.getDecoder().decode( publicKeyStr.getBytes() ) );
			PublicKey publicKey = keyFactory.generatePublic( publicKeySpec );
			return decryptRSA(signature, encrypted, publicKey);
		} catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
			//throw new RuntimeException( "복호화 처리중 오류가 발생 했습니다.", e );
			Logger log = ContextStoreHelper.getLog();
			log.error( "SHA256RSAUtil.decryptRSA ERROR", e );
			return false;
		} catch ( RuntimeException e ) {
			Logger log = ContextStoreHelper.getLog();
			log.error( "SHA256RSAUtil.decryptRSA ERROR", e );
			//throw e;
			return false;
		}
	}
	
	/**
	 * @Method Name : decryptRSA2
	 * @description : 민트에서 제공 받은 복호화 모듈 - 일부 수정함.
	 * @date        : 2021. 11. 10.
	 * @author      : P21024
	 * @param publicKey_str
	 * @param signature_str
	 * @param contents_bin
	 * @return
	 */
	public static boolean decryptRSA2(String publicKey_str, String signature_str, byte [] contents_bin) {
        boolean result = false;
        try {
        	org.bouncycastle.asn1.pkcs.RSAPublicKey publicKeyObj = org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance( DatatypeConverter.parseHexBinary(publicKey_str) );
        	RSAPublicKeySpec rsaPublicKeySpec  = new RSAPublicKeySpec(publicKeyObj.getModulus(), publicKeyObj.getPublicExponent());
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(rsaPublicKeySpec);
        	
			byte[] bytePlain = DatatypeConverter.parseHexBinary( signature_str );
			Signature rsaSha256Signature = Signature.getInstance( "SHA256withRSA" );
			rsaSha256Signature.initVerify( publicKey );
			rsaSha256Signature.update( contents_bin );
			result = rsaSha256Signature.verify( bytePlain );
        } catch ( InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e ) {
        	Logger log = ContextStoreHelper.getLog();
        	log.error( "SHA256RSAUtil.decryptRSA2 ERROR", e );
        	result = false;
        } catch ( RuntimeException e ) {
        	Logger log = ContextStoreHelper.getLog();
        	log.error( "SHA256RSAUtil.decryptRSA2 ERROR", e );
        	result = false;
        }
        
        return result;
	}
	
	/**
	 * @Method Name : decryptRSA
	 * @description : 복호화 모듈
	 * @date        : 2021. 11. 9.
	 * @author      : P21024
	 * @param signature
	 * @param encrypted
	 * @param publicKey
	 * @return
	 */
	public static boolean decryptRSA(String signature, String encrypted, PublicKey publicKey) {
		boolean isCorrect = false;
		try {
			byte[] bytePlain = DatatypeConverter.parseHexBinary( encrypted );
			Signature rsaSha256Signature = Signature.getInstance( "SHA256withRSA" );
			rsaSha256Signature.initVerify( publicKey );
			rsaSha256Signature.update( signature.getBytes( StandardCharsets.UTF_8 ));
			isCorrect = rsaSha256Signature.verify( bytePlain );
		} catch ( NoSuchAlgorithmException | InvalidKeyException | SignatureException e ) {
			Logger log = ContextStoreHelper.getLog();
			log.error( "SHA256RSAUtil.decryptRSA ERROR", e );
			//new RuntimeException( "복호화 처리중 오류가 발생 했습니다.", e );
			isCorrect = false;
		}
		
		return isCorrect;
	}
}
