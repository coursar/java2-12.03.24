package org.example.learning.serialization.xml.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.learning.serialization.xml.model.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class JaxbMain {
    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Marshaller marshaller = context.createMarshaller();
        Message original = new Message(1, "content");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(original, out);
        String encoded = out.toString(StandardCharsets.UTF_8);
        System.out.println(encoded);

        ByteArrayInputStream in = new ByteArrayInputStream(encoded.getBytes(StandardCharsets.UTF_8));
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Message decoded = (Message) unmarshaller.unmarshal(in);
    }
}
