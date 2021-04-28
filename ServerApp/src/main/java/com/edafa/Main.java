package com.edafa;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    static final Integer PORT_NUM =5002;

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "src/main/myKeyStore.jks"); //setting the key store
        System.setProperty("javax.net.ssl.keyStorePassword","123456"); //setting key store password
        SSLServerSocketFactory ssf =  (SSLServerSocketFactory)
                SSLServerSocketFactory.getDefault();
        try( ServerSocket socket =
                     ssf.createServerSocket(PORT_NUM)){
            while(true){
                new Server(socket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
