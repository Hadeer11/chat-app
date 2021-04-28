package com.edafa;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class Server extends Thread {
    private Socket socket;
    private StringBuilder fullMessage=new StringBuilder(); /*contains the current conversation*/

    public Server(Socket socket){
        this.socket=socket;
    }

    public void run() {
        try{
            BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output=new PrintWriter(socket.getOutputStream(),true);
            HashMap<String, String> userIn=UserHandler.handShake(output,input);        // handling userName and password Authentication
            String clientMessage;
            while (true){
                clientMessage=input.readLine();
                if(clientMessage==null || clientMessage.equals("Bye Bye")){
                    System.out.println("client ended the session");
                    EndConversationHandler.saveConversationState(userIn.get("user"),fullMessage);  // handling end of conversation like saving conversation text and stats
                    break;}
                System.out.println(clientMessage);
                fullMessage.append(clientMessage);
                fullMessage.append("\n");
            }

        }
        catch (IOException e){
            System.out.println("Client Connection Lost");
        }
        finally {
            try {
                //System.out.println(fullMessage);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
