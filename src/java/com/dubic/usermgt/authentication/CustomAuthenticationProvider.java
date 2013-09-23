/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.authentication;

import com.dubic.usermgt.enums.Status;
import com.dubic.usermgt.models.Role;
import com.dubic.usermgt.models.User;
import com.dubic.usermgt.pages.UserSession;
import com.dubic.usermgt.service.UserService;
import com.dubic.usermgt.util.EncrypterUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author DUBIC
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    Logger log = Logger.getLogger(CustomAuthenticationProvider.class);
    @Inject
    private UserService userService;
    @Inject
    private UserSession userSession;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        log.debug("username = " + a.getName());
        log.debug("password = " + a.getCredentials().toString());
        EncrypterUtil.salt = a.getName();
        String enc_password = EncrypterUtil.encrypt(a.getCredentials().toString());
        log.debug("enc password = " + enc_password);
        User user = userService.getUser(a.getName(), enc_password, Status.ACTIVE);
        if (user != null) {
            //check if password has expired
            
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            for (Role role : user.getRoles()) {
                grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(a.getName(), a.getCredentials().toString(), grantedAuths);
            SecurityContextHolder.getContext().setAuthentication(auth);
            try {
                //set last login date
                userService.setLastLoginDate(user);
            } catch (Exception ex) {
                log.error(ex,ex);
            }
            return auth;
        } else {
            try {
                //loginfailed attempts
                userSession.incrementFailedLogins(user);
            } catch (Exception ex) {
                log.error(ex,ex);
            }
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public void setAuthentication(User user) {
        if (user != null) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            for (Role role : user.getRoles()) {
                grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword().toString(), grantedAuths);
            SecurityContextHolder.getContext().setAuthentication(auth);

        }
    }
}
