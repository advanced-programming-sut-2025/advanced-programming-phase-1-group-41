package com.CEliconValley.common.messages;

import com.CEliconValley.models.PlayerMessage;

import java.util.ArrayList;

public class MessageCred {
    public ArrayList<PlayerMessage> playerMessages;

    public MessageCred(ArrayList<PlayerMessage> playerMessages) {
        this.playerMessages = new ArrayList<>(playerMessages);
    }
}
