package org.example.learning.serialization.json.gson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.learning.serialization.json.model.Message;

import java.util.List;

public class GsonMain {
    public static void main(String[] args) throws JsonProcessingException {
        Gson gson = new Gson();
        Message original = new Message(1L, "value");
        String serialized = gson.toJson(original);
        System.out.println("serialized = " + serialized);

        Message deserialized = gson.fromJson(serialized, Message.class);
        System.out.println("deserialized = " + deserialized);

        List<Message> originalList = List.of(original);
        String serializedList = gson.toJson(originalList);
        System.out.println("serializedList = " + serializedList);

        // List<Message> deserializedList = gson.fromJson(serializedList, List.class);
        List<Message> deserializedList = gson.fromJson(serializedList, new TypeToken<List<Message>>() {
        }.getType());
        System.out.println("deserializedList = " + deserializedList);
    }
}
