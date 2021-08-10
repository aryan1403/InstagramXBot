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
            SendPhoto sendPhoto = new SendPhoto(chatId(update), new InputFile(new File("src/main/java/com/hellionbots/res/insta.jpg")));
            sendPhoto.setCaption("Welcome to the Help Section of InstaX\n"
                + "Available Commands :-\n"
                + "1. /setcred - Enter your Credential's for Login\n"
                + "2. /post - Reply to a photo/Video to Post it.\n"
                + "3. /info - To get information about an account\n%7Format : /info <Username>\n"
                + "4. /setbio - To set a Bio\n"
                + "5. /setname - To set a Name\n"
                + "6. /setpfp - Reply to a Photo to set as Profile Pic\n"
                + "7. /story - Reply to a Photo/Video to set it in Story\n\n"
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
