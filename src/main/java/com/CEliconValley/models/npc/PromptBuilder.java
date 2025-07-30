package com.CEliconValley.models.npc;

public class PromptBuilder {
    public static String buildPrompt(String npcName,
                                     String npcPersonality, String playerMessage) {
        return String.format(
                "You are %s, a character who is %s. Respond to the player naturally ( " +
                        "just give the conversation not anything else" +
                        " that means don't model the situation with" +
                        " things like *Glances at you*, simply give the answer ): \"%s\"",
                npcName, npcPersonality, playerMessage
        );
    }
}
