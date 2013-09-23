/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author DUBIC
 */
public class UserUtils {

    public static void addFacesMessage(String uid, Severity severity, String msg) {
        FacesContext ci = FacesContext.getCurrentInstance();
        ci.addMessage(uid, new FacesMessage(severity, "", msg));
    }
}
