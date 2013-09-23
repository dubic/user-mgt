/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.service;

import com.dubic.usermgt.enums.Status;
import com.dubic.usermgt.models.Role;
import com.dubic.usermgt.models.User;
import com.dubic.usermgt.models.UserLoginAttempts;
import com.dubic.usermgt.models.dto.UserDetail;
import com.dubic.usermgt.util.EncrypterUtil;
import com.dubic.usermgt.util.ResContants;
import com.dubic.usermgt.util.UserUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author DUBIC
 */
@Named(value = "userService")
@Scope(value="request")
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    @Inject
    private Database db;
    private UserDetail user = new UserDetail();
    private List<User> userList = new ArrayList<User>();

    public List<User> getUserList() {
        try {
            loadUserList();
        } catch (Exception ex) {
            log.error(ex);
        }
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public UserDetail getUser() {
        return user;
    }

    public void setUser(UserDetail user) {
        this.user = user;
    }

    public String saveNewUser() {
        User u = new User(user);
        u.getRoles().add(new Role(Role.ROLE_USER));
        log.debug("create User{} " + u.toString());
        try {
            if (emailExists(u)) {
                log.error("email already in use");
                UserUtils.addFacesMessage(null, FacesMessage.SEVERITY_ERROR, u.getEmail() + " already exists");

                return "login.xhtml";
            }

            EncrypterUtil.salt = u.getEmail();
            String epword = EncrypterUtil.encrypt(u.getPassword());
            u.setPassword(epword);
            db.persist(u);
            return "login.xhtml";
        } catch (Exception ex) {
            log.error(ex);
            UserUtils.addFacesMessage(null, FacesMessage.SEVERITY_ERROR, "Error occurred in saving user");
        }
        return "register.xhtml";
    }

    @Override
    public void createUser(UserDetail userDetails) throws Exception {
        log.info("create User{} " + userDetails.toString());
//        User u = new User(userDetails);
//        User u = userDetails;
//        log.debug(u);
    }

    @Override
    public void updateUser(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignRole(User user, String role) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Role> getUserRoles(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User getUser(String principal, String pword, Status status) throws AuthenticationException {
        try {
            User u = db.findUserByEmailPasswordStatus(principal, pword, status);
            return u;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("could not find unique user - "+ex.getMessage());
        }
    }

    @Override
    public User findUserByUsername(String email) throws Exception {
        return db.findUserByEmail(email);
    }

    @Override
    public void loadUserList() throws Exception {
        log.debug("Loading user list..");
        userList = db.getAll(User.class);

    }

    private boolean emailExists(User u) throws Exception {
        log.debug("checking email..." + u.getEmail());
        User us = db.findUserByEmail(u.getEmail());
        return us == null ? false : true;
    }

    @Override
    public void setLastLoginDate(User user) throws Exception {
        log.debug("set Last Login Date...");
        user.setLoginDate(new Date());
        db.merge(user);
    }

    @Override
    public void logFailedAttempt(User user) throws Exception {
        log.debug("logFailedAttempt...");
        UserLoginAttempts loginAttempts = db.getUserLoginAttempt(user);
        if (loginAttempts == null) {
            loginAttempts = new UserLoginAttempts();
        }
        loginAttempts.setAttemptDate(new Date());
        loginAttempts.incrementNumOfAttempts();
        loginAttempts.setUser(user);
        db.merge(loginAttempts);
    }
    
    @Transactional
    @Override
    public void saveFailedLogin(User user) throws Exception{
        log.info("saveFailedLogin..."+user.getEmail());
        UserLoginAttempts loginAttempts = new UserLoginAttempts();
        loginAttempts.setAttemptDate(new Date());
        loginAttempts.incrementNumOfAttempts();
        loginAttempts.setUser(user);
        db.persist(loginAttempts);
        if(ResContants.LOCK_USER_ON_FAILED_LOGIN){
            log.info("locking user on account of multiple failed logon");
            user.setStatus(Status.BLOCKED);
            user.setStatusChangeReason("blocked due to multiple failed login attempts");
            db.merge(user);
            log.info("user account blocked");
        }
        
    }
}
