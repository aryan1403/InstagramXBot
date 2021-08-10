package com.hellionbots.Plugins;

import java.io.File;
import java.util.List;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credentials;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class story extends InstaX implements Master {
    Message m;

    @Override
    public void handleRequests(Update update, String cmd) {
        if (cmd.equalsIgnoreCase(getHandler() + "story") && update.getMessage().isReply()) {
            String username = new credentials().getUsername(update);
            String password = new credentials().getPass(update);

            if (update.getMessage().getReplyToMessage().hasPhoto()) {
                m = sendMessage(update, "Downloading...");
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
                File res = null;
                try {
                    file = execute(getFiled);
                    res = downloadFile(file);
                    long start = System.nanoTime();
                    editMessage(update, m.getMessageId(), "Uploading...");
                    if (uploadPhoto(username, password, update, res)) {
                        long end = System.nanoTime();
                        editMessage(update, m.getMessageId(), "Set Story Succesfully in "
                                + (((end - start) * Math.pow(10, -9) + "").substring(0, 3)) + "s");
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if(update.getMessage().getReplyToMessage().hasVideo()){
                m = sendMessage(update, "Downloading...");
                Video video = update.getMessage().getReplyToMessage().getVideo();
                GetFile getFiled = new GetFile();
                getFiled.setFileId(video.getFileId());
                org.telegram.telegrambots.meta.api.objects.File file;
                try {
                    file = execute(getFiled);
                    File res = downloadFile(file);
                    long start = System.nanoTime();
                    editMessage(update, m.getMessageId(), "Uploading...");
                    if (uploadVideo(username, password, update, res)) {
                        long end = System.nanoTime();
                        editMessage(update, m.getMessageId(), "Set Story Succesfully in "
                                + (((end - start) * Math.pow(10, -9) + "").substring(0, 3)) + "s");
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean uploadPhoto(String username, String password, Update update, File file) {
        IGClient client;
        try {
            client = IGClient.builder().username(username).password(password).login();
            client.actions().story().uploadPhoto(file);
            return true;
        } catch (IGLoginException e) {
            sendMessage(update, "Incorrect Username/Password\nType /setcred to enter your credentials");
            return false;
        }
    }

    public boolean uploadVideo(String username, String password, Update update, File file) {
        IGClient client;
        try {
            client = IGClient.builder().username(username).password(password).login();
            client.actions().story().uploadVideo(file, new File("src/main/java/com/hellionbots/res/insta.jpg"));
            return true;
        } catch (IGLoginException e) {
            editMessage(update, m.getMessageId(), "Incorrect Username/Password\nType /setcred to enter your credentials");
            return false;
        }
    }

}
