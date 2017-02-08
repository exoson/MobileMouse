
package com.example.lime.mousecontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable{
    
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean running;

    public void stop()
    {
        running = false;
        sendMsg("qt");
        try {
            client.close();
            out.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendMsg(String msg)
    {
        out.println(msg);
        out.flush();
    }
    @Override
    public void run() {
        try {
            client = new Socket("192.168.0.101",8000);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        running = true;
        while(running && !client.isClosed())
        {
            String input;
            try {
                input = in.readLine();
            } catch (IOException ex) {
                
            }
        }
    }
}
