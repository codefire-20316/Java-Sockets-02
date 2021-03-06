/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author human
 */
public class Main {

    private static final int SERVER_PORT = 7812;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("SERVER STARTED ON " + SERVER_PORT);

            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                String senderAddress = acceptedSocket.getInetAddress().getHostAddress();
                System.out.println("CONNECTED: " + senderAddress);

                DataInputStream dis = new DataInputStream(acceptedSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(acceptedSocket.getOutputStream());

                String command = dis.readUTF();
                System.out.println("COMMAND: " + command);

                switch (command) {
                    case "PING":
                        dos.writeUTF("PONG");
                        dos.flush();
                        break;
                    case "MSG": {
                        String recipient = dis.readUTF();
                        String message = dis.readUTF();

                        System.out.printf("Send message:\n  address: %s\n  message: %s\n\n", recipient, message);

                        boolean sent = sendMessage(senderAddress, recipient, message);

                        dos.writeUTF(sent ? "OK" : "ERROR");
                        dos.flush();

                        break;
                    }
                }
                // 
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static boolean sendMessage(String sender, String recipient, String message) {
        try (Socket socket = new Socket(recipient, SERVER_PORT + 1)) {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            System.out.println("SEND MSG: " + sender + " " + recipient + " " + message);

            dos.writeUTF("MSG");
            dos.flush();
            dos.writeUTF(sender);
            dos.flush();
            dos.writeUTF(message);
            dos.flush();

            if ("OK".equals(dis.readUTF())) {
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
