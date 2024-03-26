package org.example.learning.mapping.mapstruct;

import org.example.learning.mapping.dto.MessageDto;
import org.example.learning.mapping.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {
    MessageMapper instance = Mappers.getMapper(MessageMapper.class);

    // result -> to | arg -> from
    MessageDto dtoFromModel(Message message);
    List<MessageDto> dtoListFromModelList(List<Message> messages);
    Map<String, MessageDto> dtoMapFromModelMap(Map<String, Message> map);

    Message modelFromDto(MessageDto mapped, Instant created);
}
