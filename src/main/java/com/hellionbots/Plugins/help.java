package com.hellionbots.Plugins;

import com.hellionbots.InstaX;
import com.hellionbots.Master;
import org.telegram.telegrambots.meta.api.objects.Update;

public class help extends InstaX implements Master{
    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"help")){
            sendMessage(update, "Welcome to the Help Section of InstaX\n"
                + "Available Commands :-\n"
                + "1. /setcred - Enter your Credential's for Login\n"
                + "2. /post - Reply to a photo to Post it.\n"
                + "3. /info - To get information about an account\nFormat : /info <Username>\n\n"
                + "For Any Feedback or Help Join "+support()
                + "\n\nPowered by "+channel());
        }
        
    }
    
}
