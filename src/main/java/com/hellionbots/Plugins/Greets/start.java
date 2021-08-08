package com.hellionbots.Plugins.Greets;

import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credentials;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class start extends InstaX implements Master {
    @Override
    public void handleRequests(Update update, String cmd) {
        if (cmd.equalsIgnoreCase(getHandler() + "start")) {
            String s = "Hello " + update.getMessage().getFrom().getFirstName()
                    + "\nI can Help you Managing your Instagram Account."
                    + "\nType /help to see all the available Commands.";
            Message m = sendMessage(update, s);
            if (new credentials().getUsername(update) == null && new credentials().getPass(update) == null) {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(chatId(update));
                editMessageText.setMessageId(m.getMessageId());
                editMessageText.setText(s + "\nType /setcred to enter your credential's\nFormat : <Username> <Password>\nExample : /setcred abcdefgh@gmail.com 12345");

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}