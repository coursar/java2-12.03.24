package org.example.example.https.api;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.example.example.https.dto.GetItemByIdRS;
import org.example.example.https.dto.SaveItemRQ;
import org.example.example.https.exception.OperationNotPermittedException;
import org.example.example.https.model.Item;
import org.example.example.https.service.CrudService;
import org.example.framework.handler.FrameworkRequest;
import org.example.framework.handler.FrameworkResponse;
import org.example.security.X509AuthHandler;
import org.modelmapper.ModelMapper;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
public class CrudApi {
    private final CrudService service;
    private final JsonMapper jsonMapper;
    private final ModelMapper modelMapper;

    public void getAll(FrameworkRequest request, FrameworkResponse response) throws Exception {
        byte[] responseBody = "List of items".getBytes(StandardCharsets.UTF_8);

        // TODO: if you open -> you close it -> libraries also follow
        //  in your case we don't close out
        OutputStream out = response.getOut();
        out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    }

    public void getById(FrameworkRequest request, FrameworkResponse response) throws Exception {
        String idParam = request.getPathParam("id").orElseThrow(); // pass custom exception
        long id = Long.parseLong(idParam);

        Item responseItem = this.service.getById(id);
        // TODO: model -> dto
        //  1. Manually (code - slow, speed - fast)
        //  2. Reflection (code - fast, speed - slow)
        //  3. Code Generation (code - middle, speed - fast)
        GetItemByIdRS responseData = this.modelMapper.map(responseItem, GetItemByIdRS.class);
        byte[] responseBody = this.jsonMapper.writeValueAsBytes(responseData);

        // TODO: if you open -> you close it -> libraries also follow
        //  in your case we don't close out
        OutputStream out = response.getOut();
        out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    }

    public void save(FrameworkRequest request, FrameworkResponse response) throws Exception {
        // handler
        Optional<Principal> principal = ((Optional<Principal>) request.getAttributes().get(X509AuthHandler.X509_PRINCIPAL_ATTRIBUTE));
        SaveItemRQ saveItemRQ = this.jsonMapper.readValue(request.getBody(), SaveItemRQ.class);
        Item saveItemModel = this.modelMapper.map(saveItemRQ, Item.class);
        Item savedItemModel = this.service.save(principal, saveItemModel);

        byte[] responseBody = this.jsonMapper.writeValueAsBytes(savedItemModel);

        // TODO: if you open -> you close it -> libraries also follow
        //  in your case we don't close out
        OutputStream out = response.getOut();
        out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: application/json\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    }
}
