package com.hellionbots.Plugins.Greets;

import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credentials;
import org.telegram.telegrambots.meta.api.objects.Update;

public class start extends InstaX implements Master {
    credentials c = new credentials();

    @Override
    public void handleRequests(Update update, String cmd) {
        if (cmd.equalsIgnoreCase(getHandler() + "start")) {
            String s = "Hello " + update.getMessage().getFrom().getFirstName()
                    + "\nI can Help you Managing your Instagram Account."
                    + "\nType /help to see all the available Commands."
                    + "\nType /setcred to enter your credential's\nFormat : <Username> <Password>\nExample : /setcred abcdefgh 12345";
            sendMessage(update, s);
        }

    }

}