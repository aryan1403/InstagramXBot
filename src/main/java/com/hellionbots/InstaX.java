package com.hellionbots;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.hellionbots.Helpers.configuration;
import com.hellionbots.Plugins.help;
import com.hellionbots.Plugins.info;
import com.hellionbots.Plugins.post;
import com.hellionbots.Plugins.setBio;
import com.hellionbots.Plugins.setCredentials;
import com.hellionbots.Plugins.setpfp;
import com.hellionbots.Plugins.story;
import com.hellionbots.Plugins.test;
import com.hellionbots.Plugins.uploadVideo;
import com.hellionbots.Plugins.Greets.start;

public class InstaX extends TelegramLongPollingBot { 
    
    @Override
    public void onUpdateReceived(Update update) {
        String cmd = update.getMessage().getText();
    
        ExecutorService executorService = Executors.newFixedThreadPool(11); 
        executorService.execute(new Runnable() {  
            @Override  
            public void run() {  
                GetChatMember getChatMember = new GetChatMember("@HellionBotSupport",
                        update.getMessage().getFrom().getId());
                try {
                    ChatMember c = execute(getChatMember);
                    if (!c.getStatus().equals("left")) {
                        if(cmd.startsWith(getHandler()))
                            sendRequest(update, cmd);
                    } else {
                        sendMessage(update, "Join @HellionBots\nJoin @HellionBotSupport\n\nIn order to use me :)");
                    }
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
            }  
        }); 
        executorService.shutdown();
    }

    public void sendRequest(Update update, String cmd) {
        new test().handleRequests(update, cmd);
        new start().handleRequests(update, cmd);
        new help().handleRequests(update, cmd);
        new setpfp().handleRequests(update, cmd);
        //new setName().handleRequests(update, cmd);
        new info().handleRequests(update, cmd);
        new setCredentials().handleRequests(update, cmd);
        new setBio().handleRequests(update, cmd);
        new story().handleRequests(update, cmd);
        new uploadVideo().handleRequests(update, cmd);
        new post().handleRequests(update, cmd);
    }

    public String getHandler() {
        return configuration.handler;
    }

    public String chatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    public void editMessage(Update update, int mid, String text){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId(update));
        editMessageText.setMessageId(mid);
        editMessageText.setText(text);

        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Message sendMessage(Update update, String text) {
        Message m;
        SendMessage sMessage = new SendMessage(chatId(update), text);
        sMessage.enableMarkdown(true);

        try {
            m = execute(sMessage);
            return m;
        } catch (TelegramApiException e) {
            e.getMessage();
        }

        return null;
    }

    public String support(){
        return "@HellionBotSupport";
    }

    public String channel(){
        return "@HellionBots";
    }

    @Override
    public String getBotUsername() {
        String s = configuration.botUserName;
        return s;
    }

    @Override
    public String getBotToken() {
        String s = configuration.botToken;
        return s;
    }
}
