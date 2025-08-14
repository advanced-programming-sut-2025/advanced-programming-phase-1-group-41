package com.CEliconValley.models.npc;

import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.Messagenpc;
import com.CEliconValley.models.*;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class LLMClient {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;

    public LLMClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public void sendMessageAsync(String messageContent, Consumer<String> onResult, Consumer<Exception> onError) {
        JsonObject payload = new JsonObject();
        payload.addProperty("model", "deepseek/deepseek-chat-v3-0324:free");

        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", messageContent);
        messages.add(userMessage);

        payload.add("messages", messages);

        RequestBody body = RequestBody.create(
                new Gson().toJson(payload),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError.accept(e);
//                Gdx.app.postRunnable(() -> onError.accept(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
//                    Gdx.app.postRunnable(() -> onError.accept(new IOException("Unexpected code: " + response)));
                    onError.accept(new IOException("unexpected code: "+response));
                    return;
                }

                JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
                String reply = jsonResponse.getAsJsonArray("choices")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("message")
                        .get("content").getAsString();

//                Gdx.app.postRunnable(() -> onResult.accept(reply));
                onResult.accept(reply);
            }
        });
    }

    public String sendMessageSync(String messageContent) throws IOException {
        JsonObject payload = new JsonObject();
        payload.addProperty("model", "deepseek/deepseek-chat-v3-0324:free");

        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", messageContent);
        messages.add(userMessage);

        payload.add("messages", messages);

        RequestBody body = RequestBody.create(
                new Gson().toJson(payload),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }

            JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
            return jsonResponse.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();
        }
    }

    public void meetNpcAsync(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        String npcName = matcher.group(1);
        String input = matcher.group(2);

        NPC npc = App.getGame().getVillage().getNPCs().stream()
            .filter(n -> n.getName().equals(npcName))
            .findFirst()
            .orElse(null);

        if (npc == null) return;

        if (!npc.isTalkedToday(player)) {
            npc.incFriendShip(player, 100);
            npc.setTalkedToday(player, true);
            npc.getQuests().get(0).setLocked(player, false);
        }

        String fallbackText = determineFallbackDialogue(npc, player);
        if (fallbackText == null) return;

        String prompt = PromptBuilder.buildPrompt(
            npc.getName(),
            npc.getPersonality(),
            App.getGame().getWeatherType(),
            App.getGame().getTime().getSeason(),
            input,
            npc.getTalkByName(playername)
        );

        npc.getTalkByName(playername).getTalks().add(new Messagenpc(false, input));
        if (!npc.canSendLLMRequest()) {
            npc.getTalkByName(playername).getTalks().add(new Messagenpc(true, fallbackText));
            System.out.println("Rate limit: NPC is thinking too fast.");
            return;
        }
        npc.getLlmClient().sendMessageAsync(
            prompt,
            npcReply -> {
                npc.getTalkByName(playername).getTalks().add(new Messagenpc(true, npcReply));
                GameMessage<GameCommand> npcDialogue = new GameMessage<>("game-command",
                    new GameCommand("npc-dialogue", npc.getName()));
                App.getServer().sendToPlayer(player, new Gson().toJson(npcDialogue));
            }
            ,
            error -> {
                npc.getTalkByName(playername).getTalks().add(new Messagenpc(true, fallbackText));
//                npc.getTalkByName(playername).getTalks().add(new Messagenpc(true, "(NPC failed to respond properly)"));
                GameMessage<GameCommand> npcDialogue = new GameMessage<>("game-command",
                    new GameCommand("npc-dialogue", npc.getName()));
                App.getServer().sendToPlayer(player, new Gson().toJson(npcDialogue));
                System.out.println("LLM error: " + error.getMessage());
                error.printStackTrace();

            }
        );
    }
    private String determineFallbackDialogue(NPC npc, Player player) {
        WeatherType weather = App.getGame().getWeatherType();
        Season season = App.getGame().getTime().getSeason();
        int hour = App.getGame().getTime().getHour();

        if (!weather.equals(WeatherType.Sunny)) {
            return switch (weather) {
                case Rainy -> npc.getDialogues(4);
                case Snowy -> npc.getDialogues(5);
                case Stormy -> npc.getDialogues(6);
                default -> null;
            };
        } else if (hour > 18) {
            return npc.getDialogues(7);
        } else if (npc.getFriendShip(player) >= 600) {
            return npc.getDialogues(8);
        } else {
            return switch (season) {
                case Spring -> npc.getDialogues(0);
                case Summer -> npc.getDialogues(1);
                case Autumn -> npc.getDialogues(2);
                case Winter -> npc.getDialogues(3);
                default -> npc.getDialogues(0);
            };
        }
    }

}
