package com.hellionbots.Plugins;

import java.io.File;
import java.io.IOException;
import com.github.instagram4j.instagram4j.IGClient;
import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credentials;
import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class uploadVideo extends InstaX implements Master {

    public void handleRequests(Update update, String cmd) {
        if (update.getMessage().getFrom().getIsBot() != true && update.getMessage().getReplyToMessage().hasVideo() && cmd.equalsIgnoreCase(getHandler() + "post")) {
            String username = new credentials().getUsername(update);
            String password = new credentials().getPass(update);

            if (username != null && password != null) {
                upload(update, username, password);
            } else
                sendMessage(update,
                    "Please set your Username and Password in order to use the Bot.\nType /setcred to enter your credential's");
        }
    }

    public void upload(Update update, String username, String password) {
        Message m = sendMessage(update, "Downloading...");
        Video video = update.getMessage().getReplyToMessage().getVideo();

        GetFile getFiled = new GetFile();
        getFiled.setFileId(video.getFileId());

        org.telegram.telegrambots.meta.api.objects.File file;

        try {
            file = execute(getFiled);
            File res = downloadFile(file);

            editMessage(update, m.getMessageId(), "Uploading...");
            long startTime = System.nanoTime();

            if (postNow(username, password, res, new File("src/main/java/com/hellionbots/res/insta.jpg"), file.getFileSize(),"caption", update, m) != false)
            {
                long endTime = System.nanoTime();

                editMessage(update, m.getMessageId(),
                        "Uploaded Succesfullly in " + (((endTime - startTime) * Math.pow(10, -9)) + "").substring(0, 3)
                        + "s\n" + "File Size : " + (file.getFileSize() / 0.001 + "").substring(0, 3) + " mb\n"
                        + "File Dimension : " + video.getWidth() + "x" + video.getHeight());
            }
        } catch (TelegramApiException e) {
            sendMessage(update, "Input file is corrupt!");
        }
    }

    public boolean postNow(String username, String password, File res, File cover, int size, String caption, Update update, Message m) {
        try {
            IGClient client = IGClient.builder().username(username).password(password).login();

            byte[] data = FileUtils.readFileToByteArray(res);
            byte[] coverdata = FileUtils.readFileToByteArray(cover);

            client.actions().upload().chunkedVideoWithCover(data, coverdata, size, "1").join();
            //client.actions().timeline().uploadVideo(res, cover, caption);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            editMessage(update, m.getMessageId(), "Incorrect Username/Password\nType /setcred to re-enter your credentials");
            return false;
        }
    }

    
}
