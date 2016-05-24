/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.codefire.chat;

import java.util.logging.Level;
import java.util.logging.Logger;
import ua.com.codefire.chat.net.ChatClient;

/**
 *
 * @author human
 */
public class Main {

    private static final String SERVER_ADDRESS = "192.168.1.99";
    private static final int SERVER_PORT = 7812;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (ChatClient chatClient = new ChatClient(SERVER_ADDRESS, SERVER_PORT)) {

            if (chatClient.connect()) {
                chatClient.sendMessage("192.168.1.121", "Hello, World!");
            }

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
