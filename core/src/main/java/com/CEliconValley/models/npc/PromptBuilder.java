package com.CEliconValley.models.npc;

import com.CEliconValley.common.Talk;
import com.CEliconValley.models.Season;
import com.CEliconValley.models.WeatherType;

import java.util.ArrayList;

public class PromptBuilder {
    public static String buildPrompt(String npcName,
                                     String npcPersonality, WeatherType weatherType, Season season,
                                     String playerMessage) {
        return String.format(
                "You are %s, a character who is %s.the current weather is %s and current season is " +
                        "%s , sometimes relate your answer unintentionally to these . Respond to the player naturally ( " +
                        "just give the conversation not anything else" +
                        " that means don't model the situation with" +
                        " things like *Glances at you*, simply give the answer ): \"%s\"",
                npcName, weatherType, season,  npcPersonality, playerMessage
        );
    }
    public static String buildPrompt(String npcName,
                                     String npcPersonality,
                                     WeatherType weatherType,
                                     Season season,
                                     String playerMessage,
                                     Talk talk) {

        return String.format(
                "You are %s, a character who is %s. The current weather is %s and the current season is %s. " +
                        "Sometimes relate your answer unintentionally to these. Respond to the player naturally — " +
                        "just give the reply, not any narration or actions like *glances at you*. " +
                        "Here is the previous chat history:\n%s\nNow respond to this message: \"%s\"",
                npcName, npcPersonality, weatherType, season,
                talk.toString(5).trim(), playerMessage
        );
    }

}
