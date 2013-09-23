/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.models;

import com.dubic.usermgt.util.ResContants;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author DUBIC
 */
@Entity
@Table(name = "um_user_login_attempts")
public class UserLoginAttempts implements Serializable {
    
    private Long id;
    private User user;
    private int numOfAttempts = 0;
    private boolean countReached = false;
    private Date attemptDate;
    
@OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumOfAttempts() {
        return numOfAttempts;
    }

    public void setNumOfAttempts(int numOfAttempts) {
        this.numOfAttempts = numOfAttempts;
    }

    
    public boolean isCountReached() {
        return countReached;
    }

    public void setCountReached(boolean countReached) {
        this.countReached = countReached;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }
    
      @Override
    public String toString() {
        return new Gson().toJson(this);
    }
@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void incrementNumOfAttempts() {
        this.numOfAttempts++;
    }
    
}
