package com.hellionbots.Plugins;

import java.io.IOException;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.models.direct.Inbox;
import com.github.instagram4j.instagram4j.requests.direct.DirectInboxRequest;
import com.github.instagram4j.instagram4j.responses.direct.DirectInboxResponse;
import com.hellionbots.InstaX;
import com.hellionbots.Master;
import com.hellionbots.Helpers.credentials;
import org.telegram.telegrambots.meta.api.objects.Update;

public class test extends InstaX implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"unseen")){
            String username = new credentials().getUsername(update);
            String password = new credentials().getPass(update);
            IGClient client;
            try {
                client = IGClient.builder().username(username).password(password).login();
                
                //sendMessage(update, "Bio Set Successfully!");
                DirectInboxResponse response = new DirectInboxRequest().execute(client).join();
                Inbox inbox = response.getInbox();

                sendMessage(update, "Unseen Messages : "+inbox.getUnseen_count());
                
            

            } catch (IOException e) {
                sendMessage(update, "Incorrect Username/Password\nType /setcred to set your Username, Password");
            }
            
        }
        
    }

    
}
