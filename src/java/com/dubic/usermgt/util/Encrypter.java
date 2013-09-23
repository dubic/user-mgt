/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.util;

import com.sun.crypto.provider.SunJCE;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DUBIC
 */
public abstract class Encrypter {

    private static final String DEFAULT_PASS_PHRASE = "This is a jilam project !";
    private static Map<String, Encrypter> encrypterMap = new HashMap();

    protected Encrypter() {
        Security.addProvider(new SunJCE());
    }

    public static Encrypter getEncrypter(String passPhrase)
            throws Exception {
        Encrypter encrypter = null;

        if (encrypterMap.containsKey(passPhrase)) {
            return (Encrypter) encrypterMap.get(passPhrase);
        }

        encrypter = new PBEWithMD5AndDESEncrypter(passPhrase);
        encrypterMap.put(passPhrase, encrypter);

        return encrypter;
    }

    public static Encrypter getEncrypter()
            throws Exception {
        return getEncrypter("This is a jilam project !");
    }

    public abstract String encrypt(String paramString)
            throws Exception;

    public abstract String decrypt(String paramString)
            throws Exception;
}
