/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.util;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 *
 * @author DUBIC
 */



public class PBEWithMD5AndDESEncrypter extends Encrypter
{
  private Cipher ecipher;
  private Cipher dcipher;
  private final byte[] salt = { -87, -101, -56, 50, 86, 53, -29, 3 };
  private static final int ITERATION_COUNT = 19;

  protected PBEWithMD5AndDESEncrypter(String passPhrase)
    throws Exception
  {
    KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray());

    SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

    this.ecipher = Cipher.getInstance(key.getAlgorithm());

    this.dcipher = Cipher.getInstance(key.getAlgorithm());

    AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, 19);

    this.ecipher.init(1, key, paramSpec);
    this.dcipher.init(2, key, paramSpec);
  }

    @Override
  public String encrypt(String plainStr)
    throws Exception
  {
    if (plainStr == null) {
      throw new IllegalArgumentException("The argument 'plainStr' is null");
    }

    byte[] utf8 = plainStr.getBytes("UTF8");

    byte[] enc = this.ecipher.doFinal(utf8);

    return new BASE64Encoder().encode(enc);
  }

    @Override
  public String decrypt(String encryptedStr)
    throws Exception
  {
    if (encryptedStr == null) {
      throw new IllegalArgumentException("The argument 'encryptedStr' is null");
    }

    byte[] dec = new BASE64Decoder().decodeBuffer(encryptedStr);

    byte[] utf8 = this.dcipher.doFinal(dec);

    return new String(utf8, "UTF8");
  }

  
}