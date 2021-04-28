package com.edafa;


import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    final static Integer PORT_NUM =5002;
    final static String SERVER ="localhost";

    public static void main(String[] args)  {
        System.setProperty("javax.net.ssl.trustStore","src/main/myTrustStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword","123456");
        System.out.println("Client Started");

        SSLSocketFactory sf =
                (SSLSocketFactory) SSLSocketFactory.getDefault();

        try(Socket socket = sf.createSocket(SERVER, PORT_NUM)){

            BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output=new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner=new Scanner(System.in);
            String clientMessage;
            beginSession(scanner,input,output);
            clientMessage=scanner.nextLine();

            while(!clientMessage.equals("Bye Bye")){
                output.println(clientMessage);
                System.out.println("Client sent "+clientMessage);
                clientMessage=scanner.nextLine();
                }
            }

        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean beginSession(Scanner scanner,BufferedReader input,PrintWriter output) throws IOException {

        try{
            String serverResponse=input.readLine();
            String clientMessage;

            while(!serverResponse.equals("success")) {

                    System.out.println(serverResponse);
                    clientMessage=scanner.nextLine();
                    output.println(clientMessage);
                    serverResponse=input.readLine();

            }

            System.out.println(serverResponse);
            System.out.println("Type your Message :");

            return true;
            }

        catch (IOException e){
            System.out.println("Server Failed");

            return false;
            }
    }
}
