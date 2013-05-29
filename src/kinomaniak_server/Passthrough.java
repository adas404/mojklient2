/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

/**
 *
 * @author qbass
 */
public class Passthrough {
//    private static final int PORT = 8888;
//    private static final int MAXCONNS = 5;
//    private boolean logged;
//    private String user;
//    private String password;
//    private int type;
////    private Server[] activeServers;
//    private ServerSocket sockfd;
//    private Socket[] activeConns;
//    private Thread[] activeThreads;
//    private int connectionCount; 
  //  private Socket acceptsockfd;
    
    public static void main(String[] args){
        final int PORT = 8888;
        final int MAXCONNS = 5;
        //boolean logged;
        //String user;
        //String password;
       // int type;
        ServerSocket sockfd;
        Socket[] activeConns;
        Thread[] activeThreads;
         int connectionCount; 
        System.out.println("Waiting for connection");
            try{
                sockfd = new ServerSocket(PORT);
                connectionCount = 0;
                activeConns = new Socket[MAXCONNS+1];// +1 for sending error message to 'not connected' client
                activeThreads = new Thread[MAXCONNS];
                while(1==1){ 
                if(connectionCount<MAXCONNS){
                    //wait for client to connect
                    try{
                        activeConns[connectionCount] = sockfd.accept();
                        System.out.println("Connected");
                        activeThreads[connectionCount] = new Thread(new Server(activeConns[connectionCount]));
                        activeThreads[connectionCount].start();
                        connectionCount++;
                    }catch(IOException e ){
                        System.err.println("Could not connect Client.");
                    }

                }else{
                    try{
                        activeConns[MAXCONNS+1] = sockfd.accept();
                        PrintWriter out = new PrintWriter(activeConns[MAXCONNS+1].getOutputStream(),true);  //out for data
                        out.write("Sorry, this server cannot accept more than "+MAXCONNS+" connections");
                    }catch(IOException e){
                        System.err.println("Could not connect Client.");
                    }
                }
            }
            }catch(IOException e){
                System.err.println("Could not listen on port: "+PORT);  
                System.exit(1); 
            }
            
    }
}
