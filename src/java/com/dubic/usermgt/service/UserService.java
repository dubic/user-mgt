/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.service;

import com.dubic.usermgt.enums.Status;
import com.dubic.usermgt.models.Role;
import com.dubic.usermgt.models.User;
import com.dubic.usermgt.models.dto.UserDetail;
import java.util.List;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author DUBIC
 */
public interface UserService {
    public void createUser(UserDetail user) throws Exception;
    public void updateUser(User user) throws Exception;
    public void delete(Long id) throws Exception;
    public void assignRole(User user, String role) throws Exception;
    public List<Role> getUserRoles(User user) throws Exception;
    public User getUser(String uname, String pword, Status status) throws AuthenticationException;

    public User findUserByUsername(String uname) throws Exception;
    
    public void loadUserList() throws Exception;

    public void setLastLoginDate(User user) throws Exception;

    public void logFailedAttempt(User user) throws Exception;

    public void saveFailedLogin(User user) throws Exception;
}
