package com.hellionbots.Helpers;

import com.hellionbots.DB.db;

import org.telegram.telegrambots.meta.api.objects.Update;

public class credentials {
    static db database = new db();

    public static String username;
    public static String password;

    public credentials(Update update){
        username = getUsername(update);
        password = getPass(update);
    }

    public String getUsername(Update update){
        return database.findUsername(update.getMessage().getFrom().getId().toString());
    }

    public String getPass(Update update){
        return database.findPassword(update.getMessage().getFrom().getId().toString());
    }

}
