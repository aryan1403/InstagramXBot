package com.hellionbots.Plugins;

import java.io.File;

import com.hellionbots.InstaX;
import com.hellionbots.Master;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class help extends InstaX implements Master{

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"help")){
            InputFile file = new InputFile(new File("src/main/java/com/hellionbots/res/insta.jpg"), "instagram");
            
            SendPhoto sendPhoto = new SendPhoto(chatId(update), file);
            sendPhoto.setCaption("Welcome to the Help Section of InstaX\n"
            + "Available Commands :-\n"
            + "1. /setcred - Enter your Credential's for Login\n"
            + "2. /post - Reply to a photo to Post it.\n\n"
            + "For Any Feedback or Help Join "+support()
            + "\n\nPowered by "+channel());
            
            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        
    }
    
}
