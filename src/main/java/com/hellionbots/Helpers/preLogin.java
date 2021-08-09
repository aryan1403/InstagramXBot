package com.hellionbots.Helpers;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;

public class preLogin {
    public IGClient Login(){
        try {
            IGClient client = IGClient.builder().username("hellion_coder").password("Aaryan@1403").login();
            return client;
        } catch (IGLoginException e) {
            e.printStackTrace();
        }
        return null;
    }
}
