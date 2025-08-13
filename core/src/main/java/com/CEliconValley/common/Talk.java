package com.CEliconValley.common;

import com.CEliconValley.common.messages.Messagenpc;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;


@Embedded
public class Talk {
    String playername;
    ArrayList<Messagenpc> talks;

    public Talk(String playername, ArrayList<Messagenpc> talks) {
        this.playername = playername;
        this.talks = talks;
    }

    public Talk(String playername) {
        this.playername = playername;
        this.talks = new ArrayList<>();
    }

    public String getPlayername() {
        return playername;
    }

    public ArrayList<Messagenpc> getTalks() {
        return talks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Messagenpc talk : talks) {
            if(talk.isNPC){
                builder.append("NPC: ");
            }else{
                builder.append("Player: ");
            }
            builder.append(talk.message);
            builder.append("\n");
        }
        return builder.toString();
    }
}
