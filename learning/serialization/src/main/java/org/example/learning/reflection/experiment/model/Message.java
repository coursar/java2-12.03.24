package org.example.learning.reflection.experiment.model;

import lombok.*;

// TODO: JavaBeans + no-logic -> DTO

//@NoArgsConstructor
//@AllArgsConstructor
//@Data
@Getter
public class Message {
    private long id;
    private String value;

    private Message(String secretValue) {

    }

    public static Message of(long id, String value) {
        Message message = new Message("...");
        message.id = id;
        message.value = value;
        return message;
    }
}
