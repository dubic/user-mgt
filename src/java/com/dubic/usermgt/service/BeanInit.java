/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.usermgt.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 *
 * @author DUBIC
 */
@Named
public class BeanInit implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object o, String string) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String string) throws BeansException {
//        if(bean instanceof UserServiceImpl) {
//            System.out.println("bean initialiazed - ".toUpperCase()+bean.getClass().getName());
//            UserServiceImpl us = (UserServiceImpl) bean;
//            try {
//                us.loadUserList();
//            } catch (Exception ex) {
//                Logger.getLogger(BeanInit.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        return bean;
    }
    
}
