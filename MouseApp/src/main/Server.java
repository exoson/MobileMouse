
package main;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{
    private Robot r;
    
    private final ArrayList<ClientServer> cs = new ArrayList<>();
    private int clientamt = 0;
    private boolean running;
    
    public void message(String msg, int clientIdx) {
        getCs().get(clientIdx).sendMsg(msg);
    }
    public void broadcast(String msg)
    {
        for(ClientServer c : cs){
            c.sendMsg(msg);
        }
    }
    public void stop()
    {
        running = false;
        for(ClientServer c : cs) {
            c.stop();
        }
    }
    @Override
    public void run()
    {
        try {
            r = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (ServerSocket ss = new ServerSocket(8000)) {
            ss.setSoTimeout(1000);
            System.out.println(Arrays.toString(ss.getInetAddress().getAddress()));
            running = true;
            while(running) {
                Socket client = null;
                try {
                    client = ss.accept();
                }
                catch(IOException e){
                    
                }
                if(client != null){
                    cs.add(new ClientServer(this, client));
                    System.out.println("Client connected " + client.getInetAddress().toString());
                    clientamt++;
                    new Thread(getCs().get(clientamt - 1)).start();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void remove(ClientServer cs) {
        this.cs.remove(cs);
        clientamt--;
    }
    /**
     * @return the cs
     */
    public ArrayList<ClientServer> getCs() {
        return cs;
    }
    public void move(String[] s) {
        int x = MouseInfo.getPointerInfo().getLocation().x + Integer.parseInt(s[1]);
        int y = MouseInfo.getPointerInfo().getLocation().y + Integer.parseInt(s[2]);
        r.mouseMove(x, y);
    }
    public void mouseDown(int button) {
        r.mousePress(button);
    }
    public void mouseUp(int button) {
        r.mouseRelease(button);
    }
}
