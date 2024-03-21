package org.example.learning.serialization.json.fastjson2;

import com.alibaba.fastjson2.JSON;
import org.example.learning.serialization.json.model.Message;

import java.util.List;

public class FastJson2 {
    public static void main(String[] args) {
        Message original = new Message(1L, "value");
        String serialized = JSON.toJSONString(original);
        System.out.println("serialized = " + serialized);

        Message deserialized = JSON.parseObject(serialized, Message.class);
        System.out.println("deserialized = " + deserialized);

        List<Message> originalList = List.of(original);
        String serializedList = JSON.toJSONString(originalList);
        System.out.println("serializedList = " + serializedList);

        // List<Message> deserializedList = JSON.parseObject(serializedList, List.class);
        List<Message> deserializedList = JSON.parseArray(serializedList, Message.class);
        System.out.println("deserializedList = " + deserializedList);
    }
}
