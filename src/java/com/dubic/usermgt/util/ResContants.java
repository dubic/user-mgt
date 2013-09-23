/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.util;

import java.util.ResourceBundle;

/**
 *
 * @author DUBIC
 */
public class ResContants {
    public static final int MAX_FAILED_LOGIN_COUNT = Integer
            .parseInt(ResourceBundle.getBundle("resources.conf").getString("max.failed.login.attempts"));
    public static final boolean LOCK_USER_ON_FAILED_LOGIN = 
            Boolean.parseBoolean(ResourceBundle.getBundle("resources.conf").getString("lock.on.failed.login.attempts"));
    
}
