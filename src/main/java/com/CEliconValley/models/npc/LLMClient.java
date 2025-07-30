package com.CEliconValley.models.npc;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.function.Consumer;

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
}