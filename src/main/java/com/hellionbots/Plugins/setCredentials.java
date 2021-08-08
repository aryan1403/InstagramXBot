package com.hellionbots.Plugins;

import org.telegram.telegrambots.meta.api.objects.Update;
import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.DB.db;
import org.bson.Document;

public class setCredentials extends InstaX implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        //String helpMsg = "Enter your Instagram Credentials\nFormat :\n<Username> <Password>\nExample : abcdefg@gmail.com 12345";
        String s = update.getMessage().getText().replace(getHandler()+"setcred ", "");
        if (cmd.equals(getHandler() + "setcred "+s)) {
            String id = update.getMessage().getFrom().getId().toString(); 

            String[] splitStr = s.split("\\s+");

            String username = splitStr[0];
            String password = splitStr[1];

            Document document = new Document("key", id)
                .append("username", username)
                .append("password", password);

            db database = new db();

            database.alreadyPresent(id);

            if(database.add(document))
                sendMessage(update, "Successfully Uploaded Credentials");
            else 
                sendMessage(update, "Error Occured Uploading Credentials\nJoin "+support()+" for more Info!");
        }
    }
}
