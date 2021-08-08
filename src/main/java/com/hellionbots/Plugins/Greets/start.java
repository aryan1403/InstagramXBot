package com.hellionbots.Plugins.Greets;

import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credHelper;

import org.telegram.telegrambots.meta.api.objects.Update;

public class start extends InstaX implements Master{

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"start")){
            if(new credHelper().isUPresent(update) && new credHelper().isPPresent(update))
                sendMessage(update, "Hello "+update.getMessage().getFrom().getFirstName()+"\nI can Help you Managing your Instagram Account.\nType /help to see all the available Commands.");
            else 
                sendMessage(update, "Hello "+update.getMessage().getFrom().getFirstName()+"\nI can Help you Managing your Instagram Account.\nType /setcred to enter your credential's\nFormat : <Username> <Password>\nExample : /setcred abcdefgh@gmail.com 12345");
        }
        
    }
    
}