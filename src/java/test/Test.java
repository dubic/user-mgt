/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ResourceBundle;

/**
 *
 * @author DUBIC
 */
public class Test {
    public static void main(String[] jihad){
        new Test().testConfigLocation();
    }

    private void testConfigLocation() {
        ResourceBundle bundle = ResourceBundle.getBundle("pages");
        System.out.println("res = "+bundle.getString("page1"));
    }
}
