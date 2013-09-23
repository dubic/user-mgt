/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.pages;

import com.dubic.usermgt.models.User;
import com.dubic.usermgt.models.dto.UserDetail;
import com.dubic.usermgt.service.UserService;
import com.dubic.usermgt.util.ResContants;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author DUBIC
 */
@Named("userSession")
@Scope(value="request")
public class UserSession {
    @Inject private UserService userService;
    private UserDetail userDetail;

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public void incrementFailedLogins(User user) throws Exception {
        int failedLogins = userDetail.getFailedLogins();
        userDetail.setFailedLogins(failedLogins++);
        if(failedLogins == ResContants.MAX_FAILED_LOGIN_COUNT){
            userService.saveFailedLogin(user);
        }
    }
    
    
}
