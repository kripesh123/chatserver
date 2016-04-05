/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.chatserver.handler;

import com.leapfrog.chatserver.dao.UserDAO;
import com.leapfrog.chatserver.dao.impl.UserDAOImpl;
import com.leapfrog.chatserver.entity.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ClientListener extends Thread {

    private Socket client;
    private UserHandler userHandler;
    private UserDAO userDAO = new UserDAOImpl();

    public ClientListener(Socket client, UserHandler userHandler) {
        this.client = client;
        this.userHandler = userHandler;

    }

    private void sendMessage(Socket client, String message) throws IOException {
        PrintWriter writer = new PrintWriter(client.getOutputStream());
        writer.write(message + "\r\n");
        writer.flush();
    }

    public void broadcastMessage(String message) throws IOException {

        for (User user : userHandler.getUsers()) {
            if (!user.getSocket().equals(client)) {
                PrintWriter writer = new PrintWriter(user.getSocket().getOutputStream());
                writer.write(message);
                writer.flush();
            }
        }
    }
    
    

    private void checkLogin(BufferedReader reader) throws IOException {
        while (true) {
            sendMessage(client,"Enter your User Name:");
            String username = reader.readLine();
            sendMessage(client,"Enter your Password:");
            String password = reader.readLine();
            User user = userDAO.login(username, password);
            if (user == null) {
                sendMessage(client,"Invalid user name and password\r\n");
            } else {
                user.setSocket(client);
                userHandler.add(user);
                broadcastMessage(user.getUserName() + "  has joined");
                sendMessage(client,"Thank you you can continue now\r\n");
                break;
            }
        }
    }

    @Override
    public void run() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            checkLogin(reader);
            while(true){
                String line=reader.readLine();
                if(line.startsWith("/list")){
                    sendMessage(client,listUsers());
                }
                else if(line.startsWith("/pm")){
                    String[] tokens=line.split(";");
                    User friend=userHandler.getUserByUserName(tokens[1]);
                    sendMessage(friend.getSocket(), tokens[2]);
                }
                else{
                User user=userHandler.getUserBySocket(client);
                broadcastMessage(user.getUserName() +  " says:>" + line + "\r\n");
                }
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());

        }
    }
    
    private String listUsers(){
        StringBuilder builder=new StringBuilder();
        for(User u: userHandler.getUsers()){
            builder.append(u.getUserName() + "\r\n");
        }
        return builder.toString();
    }

}
