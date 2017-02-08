
package main;

import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientServer implements Runnable
{
    private final Server server;
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;
    private boolean running;
    
    public ClientServer(Server s, Socket client) throws IOException{
        client.setSoTimeout(0);
        client.setKeepAlive(true);
        clientSocket = client;
        server = s;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        running = true;
        while(isRunning() && !clientSocket.isClosed()){
            String input;
            String[] splitted;
            try {
                input = in.readLine();
                splitted = input.split(":");
                //System.out.println(Arrays.toString(splitted));
                switch (splitted[0]) {
                    case "qt":
                        stop();
                        break;
                    case "mm":
                        server.move(splitted);
                        break;
                    case "md":
                        server.mouseDown(InputEvent.BUTTON1_DOWN_MASK);
                        break;
                    case "mu":
                        server.mouseUp(InputEvent.BUTTON1_DOWN_MASK);
                        break;
                    case "mD":
                        server.mouseDown(InputEvent.BUTTON3_DOWN_MASK);
                        break;
                    case "mU":
                        server.mouseUp(InputEvent.BUTTON3_DOWN_MASK);
                        break;
                    case "MD":
                        server.mouseDown(InputEvent.BUTTON2_DOWN_MASK);
                        break;
                    case "MU":
                        server.mouseUp(InputEvent.BUTTON2_DOWN_MASK);
                        break;
                }
                
            } catch (IOException ex) {
                //Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void sendMsg(String msg)
    {
        out.println(msg);
        out.flush();
    }
    public void stop()
    {
        running = false;
        server.remove(this);
        try {
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getInetAddress().toString());
        } catch (IOException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }
}
