/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.chatserver.dao.impl;

import com.leapfrog.chatserver.dao.UserDAO;
import com.leapfrog.chatserver.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserDAOImpl implements UserDAO {
    private List<User> userList=new ArrayList<>();
    @Override
    public List<User> getAll() {
        if(userList.size()==0){
            userList.add(new User("anup","anup"));
            userList.add(new User("jeevan","jeevan"));
            userList.add(new User("aayush","aayush"));
        }
        return userList;
    }

    @Override
    public User login(String username, String password) {
        System.out.println(username +  " " + password);
        for(User u:getAll()){
            System.out.println(u.toString());
            if(u.getUserName().equalsIgnoreCase(username) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }
    
}
