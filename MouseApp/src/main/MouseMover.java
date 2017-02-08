
package main;


import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lime
 */
public class MouseMover
{
    
    
    
    public static void main(String[] args) {
        System.out.println("Main Method Entry");

        TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            Image image = Toolkit.getDefaultToolkit().getImage("textures/ammo.png");
            // create a action listener to listen for default action executed on the tray icon
            ActionListener listener = (ActionEvent e) -> {
                System.out.println("Exiting...");
                System.exit(0);
            };
            // create a popup menu
            PopupMenu popup = new PopupMenu();
            // create menu item for the default action
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(listener);
            popup.add(defaultItem);
            /// ... add other items
            // construct a TrayIcon
            trayIcon = new TrayIcon(image, "MouseApp", popup);
            // set the TrayIcon properties
            trayIcon.addActionListener(listener);
            // ...
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        } else {
            // disable tray option in your application or
            // perform other actions
        }
        Server s = new Server();
        Thread t = new Thread(s);
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MouseMover.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        String[] asd = new String[]{"","10","10"};
        for(int i = 0; i < 10; i++) {
            s.mouseDown();
            s.move(asd);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MouseMover.class.getName()).log(Level.SEVERE, null, ex);
            }
            s.mouseUp();
        }*/
        
        
        System.out.println("Main Method Exit");
    }
}
