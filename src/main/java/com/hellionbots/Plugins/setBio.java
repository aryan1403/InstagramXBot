package com.hellionbots.Plugins;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credentials;
import org.telegram.telegrambots.meta.api.objects.Update;

public class setBio extends InstaX implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        String bio = cmd.replace(getHandler()+"setbio ", "");
        if(cmd.equalsIgnoreCase(getHandler()+"setbio "+bio)){
            String username = new credentials().getUsername(update);
            String password = new credentials().getPass(update);

            IGClient client;
            try {
                client = IGClient.builder().username(username).password(password).login();
                client.actions().account().setBio(bio).thenAccept(e -> {}).join();
                sendMessage(update, "Bio Set Successfully!");

            } catch (IGLoginException e) {
                sendMessage(update, "Incorrect Username/Password\nType /setcred to set your Username, Password");
            }
        }
        
    }

    
}
