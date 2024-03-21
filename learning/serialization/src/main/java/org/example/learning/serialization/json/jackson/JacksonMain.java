package org.example.learning.serialization.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.learning.serialization.json.model.Message;
import org.example.learning.serialization.json.model.MessageList;

import java.util.List;

public class JacksonMain {
    public static void main(String[] args) throws JsonProcessingException {
        // ObjectMapper objectMapper = new ObjectMapper();
        JsonMapper jsonMapper = new JsonMapper();
        Message original = new Message(1L, "value");
        String serialized = jsonMapper.writeValueAsString(original);
        System.out.println("serialized = " + serialized);

        Message deserialized = jsonMapper.readValue(serialized, Message.class);
        System.out.println("deserialized = " + deserialized);

        List<Message> originalList = List.of(original);
        String serializedList = jsonMapper.writeValueAsString(originalList);
        System.out.println("serializedList = " + serializedList);

        // List<Message> deserializedList = jsonMapper.readValue(serializedList, List.class);
        List<Message> deserializedList = jsonMapper.readValue(serializedList, new TypeReference<List<Message>>() {
        });
        System.out.println("deserializedList = " + deserializedList);

        String wrappedSerializedList = "{\"items\": [{\"id\":1,\"value\":\"value\"}]}";
        MessageList messageList = jsonMapper.readValue(wrappedSerializedList, MessageList.class);
        System.out.println("messageList = " + messageList);
    }
}
