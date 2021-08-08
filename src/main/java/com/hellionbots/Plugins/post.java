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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class post extends InstaX implements Master {

    credHelper c = new credHelper();

    @Override
    public void handleRequests(Update update, String cmd) {
        if (update.getMessage().getReplyToMessage().hasPhoto() && cmd.equalsIgnoreCase(getHandler() + "post")) {
            if (c.isUPresent(update) && c.isPPresent(update)) {
                upload(update);
            } else
                sendMessage(update,
                    "Please set your Username and Password in order to use the Bot.\nType /setcred to enter your credential's");
        }
    }

    public void upload(Update update) {
        Message m = sendMessage(update, "Uploading...");
        long startTime = System.nanoTime();
        List<PhotoSize> arr = update.getMessage().getReplyToMessage().getPhoto();

        PhotoSize biggSize = null;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.size(); j++) {
                if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                    biggSize = arr.get(i);
            }
        }
        String caption = update.getMessage().getReplyToMessage().getText();
        PhotoSize photos = biggSize;
        GetFile getFiled = new GetFile();
        getFiled.setFileId(photos.getFileId());

        org.telegram.telegrambots.meta.api.objects.File file;

        try {
            file = execute(getFiled);
            File res = downloadFile(file);

            if (postNow(new credentials(update).username, new credentials(update).password, res, caption, update, m) != false)
            {
                long endTime = System.nanoTime();

                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(chatId(update));
                editMessageText.setMessageId(m.getMessageId());
                editMessageText.setText(
                        "Uploaded Succesfullly in " + (((endTime - startTime) * Math.pow(10, -9)) + "").substring(0, 3)
                                + "s\n" + "File Size : " + (file.getFileSize() / 0.001 + "").substring(0, 3) + " mb\n"
                                + "File Dimension : " + photos.getWidth() + "x" + photos.getHeight());

                execute(editMessageText);
            }
        } catch (TelegramApiException e) {
            sendMessage(update, "Input file is corrupt!");
        }
    }

    public boolean postNow(String username, String password, File res, String caption, Update update, Message m) {
        try {
            IGClient client = IGClient.builder().username(username).password(password).login();

            client.actions().timeline().uploadPhoto(res, caption).thenAccept(response -> {
            }).join();
            return true;
        } catch (IGLoginException e) {
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(chatId(update));
            editMessageText.setMessageId(m.getMessageId());
            editMessageText.setText("Incorrect Username/Password\nType /setcred to re-enter your credentials");

            try {
                execute(editMessageText);
            } catch (TelegramApiException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

}