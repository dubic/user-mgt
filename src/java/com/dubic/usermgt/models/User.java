/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.models;

import com.dubic.usermgt.enums.Status;
import com.dubic.usermgt.models.dto.UserDetail;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author DUBIC
 */
@Entity
@Table(name = "um_user")
public class User implements Serializable {

    protected Long id;
    protected String username;
    protected String firstname;
    protected String lastname;
    protected String othername;
    protected String email;
    protected String phone;
    protected String password;
    private Date passwordChangedDate;
    private Date loginDate;
    private Status status = Status.REGISTERED;
    private boolean passwordExpired;
    
    protected Date created = new Date();
    protected Date updated = new Date();
    private List<Role> roles = new ArrayList<Role>(); 
    private String statusChangeReason;

    public User() {
    }

    public User(UserDetail userDetails) {
        this.email = userDetails.getEmail();
        this.firstname = userDetails.getFirstname();
        this.lastname = userDetails.getLastname();
        this.id = userDetails.getId();
        this.othername = userDetails.getOthername();
        this.password = userDetails.getPassword();
        this.phone = userDetails.getPhone();
        this.username = userDetails.getUsername();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }
@Column(unique=true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
@Column(unique=true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

  

    @OneToMany(cascade={CascadeType.ALL},fetch= FetchType.EAGER)
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> authorities) {
        this.roles = authorities;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getPasswordChangedDate() {
        return passwordChangedDate;
    }

    public void setPasswordChangedDate(Date passwordChangedDate) {
        this.passwordChangedDate = passwordChangedDate;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
@Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }
    
      @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 47 * hash + (this.password != null ? this.password.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        return true;
    }

    public void setStatusChangeReason(String reason) {
        this.statusChangeReason = reason;
    }

    public String getStatusChangeReason() {
        return statusChangeReason;
    }
    
    
      
}
