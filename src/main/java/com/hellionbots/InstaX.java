package com.hellionbots;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.hellionbots.Helpers.configuration;
import com.hellionbots.Plugins.help;
import com.hellionbots.Plugins.info;
import com.hellionbots.Plugins.post;
import com.hellionbots.Plugins.setCredentials;
import com.hellionbots.Plugins.Greets.start;

public class InstaX extends TelegramLongPollingBot implements Runnable{

    Update update;
    String cmd;

    public InstaX(Update update){
        this.update = update;
    }

    //public InstaX(){}

    @Override
    public void onUpdateReceived(Update update) {
        //String cmd = update.getMessage().getText();

        Thread t = new Thread();
        t.start();
        //sendRequest(update, cmd);
    }

    public void sendRequest(Update update, String cmd) {
        new start(update).handleRequests(update, cmd);
        new help(update).handleRequests(update, cmd);
        new info(update).handleRequests(update, cmd);
        new setCredentials(update).handleRequests(update, cmd);
        new post(update).handleRequests(update, cmd);
    }

    @Override
    public void run() {
        sendRequest(update, update.getMessage().getText().toString());
    }

    public String getHandler() {
        return configuration.handler;
    }

    public String chatId(Update update) {
        return update.getMessage().getChatId().toString();
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
