package org.example.learning.mapping.mapstruct;

import org.example.learning.mapping.dto.MessageDto;
import org.example.learning.mapping.model.Message;

import java.time.Instant;
import java.util.List;

public class MapStructMain {
    public static void main(String[] args) {
        MessageMapper mapper = MessageMapper.instance;
        Message original = new Message(1, "value", Instant.now());
        MessageDto mapped = mapper.dtoFromModel(original);
        System.out.println("mapped = " + mapped);

        Message model = mapper.modelFromDto(mapped, Instant.now());
        System.out.println("model = " + model);

        List<Message> originalList = List.of(original);
        List<MessageDto> mappedList = mapper.dtoListFromModelList(originalList);
        System.out.println("mappedList = " + mappedList);
    }
}
