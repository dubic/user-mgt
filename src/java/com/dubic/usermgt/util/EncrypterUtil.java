/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.util;

import org.apache.log4j.Logger;

/**
 *
 * @author DUBIC
 */
public class EncrypterUtil
{
  public static String salt = "ctmsclient";
  public static Logger log = Logger.getLogger(EncrypterUtil.class);

  public static String decrypt(String content) {
    try {
      Encrypter encrypter = Encrypter.getEncrypter(salt.toUpperCase());
      return encrypter.decrypt(content);
    } catch (Exception ex) {
      log.error("oops error converting de content!!!", ex.fillInStackTrace());
    }
    return content;
  }

  public static String encrypt(String content) {
    try {
      Encrypter encrypter = Encrypter.getEncrypter(salt.toUpperCase());
      return encrypter.encrypt(content);
    } catch (Exception ex) {
      log.error("oops error converting en content!!!", ex.fillInStackTrace());
    }
    return content;
  }
}
