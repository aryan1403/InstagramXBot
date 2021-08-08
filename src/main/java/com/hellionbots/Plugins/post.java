package com.hellionbots.Plugins;

import java.io.File;
import java.util.List;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credHelper;
import com.hellionbots.Helpers.credentials;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class post extends InstaX implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if (cmd.equalsIgnoreCase(getHandler() + "post")) {
            credHelper cred = new credHelper();
            if (cred.isUPresent() && cred.isPPresent()) {
                sendMessage(update, "Send a Reply Image to Post to your Account");
            }
            else
                sendMessage(update, "Please set your Username and Password in order to use the Bot.");
        }
        if(update.getMessage().hasPhoto() && update.getMessage().getReplyToMessage().getText().equals("Send a Reply Image to Post to your Account")){
            List<PhotoSize> arr = update.getMessage().getPhoto();

            PhotoSize biggSize = null;
            for (int i = 0; i < arr.size(); i++) {
                for (int j = 0; j < arr.size(); j++) {
                    if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                        biggSize = arr.get(i);
                }
            }
            String caption = update.getChannelPost().getCaption();
            PhotoSize photos = biggSize;
            GetFile getFiled = new GetFile();
            getFiled.setFileId(photos.getFileId());

            org.telegram.telegrambots.meta.api.objects.File file;

            try {
                file = execute(getFiled);
                File res = downloadFile(file);

                postNow(credentials.username, credentials.password, res, caption, update);

            } catch (TelegramApiException e) {
                sendMessage(update, "Input file is corrupt!");
            }
        }
    }

    public void postNow(String username, String password, File res, String caption, Update update) {
        username = credentials.username;
        password = credentials.password;
        try {
            IGClient client = IGClient.builder().username(username).password(password).login();

            client.actions().timeline().uploadPhoto(res, caption).thenAccept(response -> {
                    sendMessage(update, "Succesfully Uploaded Photo!");
                }).join();
        } catch (IGLoginException e) {
            e.printStackTrace();
        }
    }

}
