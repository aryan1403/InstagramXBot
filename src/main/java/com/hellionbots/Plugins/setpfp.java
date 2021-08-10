package com.hellionbots.Plugins;

import java.io.File;
import java.util.List;
import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credentials;
import com.github.instagram4j.instagram4j.IGClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class setpfp extends InstaX implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"setpfp") && update.getMessage().getReplyToMessage().hasPhoto()){
            String username = new credentials().getUsername(update);
            String password = new credentials().getPass(update);

            Message m = sendMessage(update, "Downloading...");

            List<PhotoSize> arr = update.getMessage().getReplyToMessage().getPhoto();

            PhotoSize biggSize = null;
            for (int i = 0; i < arr.size(); i++) {
                for (int j = 0; j < arr.size(); j++) {
                    if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                        biggSize = arr.get(i);
                }
            }
            PhotoSize photos = biggSize;
            GetFile getFiled = new GetFile();
            getFiled.setFileId(photos.getFileId());

            org.telegram.telegrambots.meta.api.objects.File file;

            IGClient client;
            try {
                file = execute(getFiled);
                File res = downloadFile(file);

                editMessage(update, m.getMessageId(), "Uploading...");

                long start = System.nanoTime();
                
                client = IGClient.builder().username(username).password(password).login();
                client.actions().account().setProfilePicture(res).thenAccept(e -> {
                    long end = System.nanoTime();
                    editMessage(update, m.getMessageId(), "Profile Pic set Successfully in "+((end-start*Math.pow(10, -9)+"").substring(0, 3))+"s");
                }).join();

            } catch (IGLoginException | TelegramApiException e) {
                editMessage(update, m.getMessageId(), "Incorrect Username/Password\nType /setcred to set your Username, Password");
            }
        }
        
    }
    
}
