package com.hellionbots.Helpers;

import org.telegram.telegrambots.meta.api.objects.Update;

public class credHelper {
    // Check's if the username is present
    public boolean isUPresent(Update update){
        if(new credentials(update).username == null)
            return false;
        return true;
    }

    // Check's if the password is present
    public boolean isPPresent(Update update){
        if(new credentials(update).password == null) 
            return false;
        return true;
    }
}