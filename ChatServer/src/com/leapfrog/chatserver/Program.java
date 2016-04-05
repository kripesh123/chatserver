/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.chatserver;

import com.leapfrog.chatserver.entity.User;
import com.leapfrog.chatserver.handler.ClientListener;
import com.leapfrog.chatserver.handler.UserHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port=9000;
        
        try{
        ServerSocket server=new ServerSocket(port);
            System.out.println("Server is running at port "+ port);
            UserHandler userHandler=new UserHandler();
            userHandler.start();
        while(true){
            Socket client=server.accept();
            System.out.println("connection request from :" + client.getInetAddress().getHostAddress());
            ClientListener listener=new ClientListener(client,userHandler);
            listener.start();
            
        }
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
    
}
