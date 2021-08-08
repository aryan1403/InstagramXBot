package com.hellionbots;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.hellionbots.Helpers.configuration;
import com.hellionbots.Plugins.post;
import com.hellionbots.Plugins.setCredentials;

public class InstaX extends TelegramLongPollingBot{
    @Override
    public void onUpdateReceived(Update update) {
        String cmd = update.getMessage().getText();

        sendRequest(update, cmd);
    }

    public void sendRequest(Update update, String cmd) {
        new setCredentials().handleRequests(update, cmd);
        new post().handleRequests(update, cmd);
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

        try {
            m = execute(sMessage);
            return m;
        } catch (TelegramApiException e) {
            e.getMessage();
        }

        return null;
    }

    public String support(){
        return "";
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
