package com.hellionbots.Plugins;

import java.util.concurrent.ExecutionException;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credentials;
import org.telegram.telegrambots.meta.api.objects.Update;

public class setName extends InstaX implements Master {
    @Override
    public void handleRequests(Update update, String cmd) {
        String name = cmd.replace(getHandler()+"setname ", "");
        if(cmd.equalsIgnoreCase(getHandler()+"setname "+name)){
            String username = new credentials().getUsername(update);
            String password = new credentials().getPass(update);

            IGClient client;
            try {
                client = IGClient.builder().username(username).password(password).login();
                client.actions().account().currentUser().get().getUser().setFull_name(name);
                
                sendMessage(update, "Name Set Successfully!");

            } catch (IGLoginException | InterruptedException | ExecutionException e) {
                sendMessage(update, "Incorrect Username/Password\nType /setcred to set your Username, Password");
            }
        }
        
    }
}
