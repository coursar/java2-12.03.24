package org.example.learning.mapping.beanmapper;

import io.beanmapper.BeanMapper;
import io.beanmapper.config.BeanMapperBuilder;
import org.example.learning.mapping.dto.MessageDto;
import org.example.learning.mapping.model.Message;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.Instant;
import java.util.List;

public class BeanMapperMain {
    public static void main(String[] args) {
        BeanMapper mapper = new BeanMapperBuilder().build();
        Message original = new Message(1, "value", Instant.now());
        MessageDto mapped = mapper.map(original, MessageDto.class);
        System.out.println("mapped = " + mapped);

        Message model = mapper.map(mapped, Message.class);
        model.setCreated(Instant.now());
        System.out.println("model = " + model);

        List<Message> originalList = List.of(original);
        List<MessageDto> mappedList = mapper.map(originalList, MessageDto.class);
        System.out.println("mappedList = " + mappedList);
    }
}
