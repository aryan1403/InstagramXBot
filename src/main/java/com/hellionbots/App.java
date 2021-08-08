package com.hellionbots;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public final class App {
    static Update update;

    public App(Update update){
        App.update = update;
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new InstaX(update));
            System.out.println("Bot Started Successfully");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new App(update);
    }
}
