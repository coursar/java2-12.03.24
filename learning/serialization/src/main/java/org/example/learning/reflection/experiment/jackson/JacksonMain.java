package org.example.learning.reflection.experiment.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.learning.reflection.experiment.model.Message;

import java.util.List;

public class JacksonMain {
    public static void main(String[] args) throws JsonProcessingException {
        // ObjectMapper objectMapper = new ObjectMapper();
        JsonMapper jsonMapper = new JsonMapper();
        Message original = Message.of(1L, "value");
        String serialized = jsonMapper.writeValueAsString(original);
        System.out.println("serialized = " + serialized);

        Message deserialized = jsonMapper.readValue(serialized, Message.class);
        System.out.println("deserialized = " + deserialized);
    }
}
