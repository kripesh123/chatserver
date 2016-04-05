/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.chatserver.handler;

import com.leapfrog.chatserver.entity.User;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserHandler extends Thread{
    private List<User> userList;
    
    public UserHandler(){
        userList=new ArrayList<>();
    }
    
    public void add(User u){
        userList.add(u);
    }
    
    public List<User> getUsers(){
        return userList;
    }
    
    public User getUserBySocket(Socket s){
        for(User u:userList){
            if(u.getSocket().equals(s)){
                return u;
            }
        }
        return null;
    }

    public User getUserByUserName(String username){
        for(User u:userList){
            if(u.getUserName().equals(username)){
                return u;
            }
        }
        return null;
    }    

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
