package org.example.learning.reflection.experiment.gson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.example.learning.reflection.experiment.model.Message;


import java.util.List;

public class GsonMain {
    public static void main(String[] args) throws JsonProcessingException {
        Gson gson = new Gson();
        Message original = Message.of(1L, "value");
        String serialized = gson.toJson(original);
        System.out.println("serialized = " + serialized);

        Message deserialized = gson.fromJson(serialized, Message.class);
        System.out.println("deserialized = " + deserialized);
    }
}
