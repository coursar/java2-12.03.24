package org.example.example.rest.api;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.example.example.rest.dto.GetItemByIdRS;
import org.example.example.rest.model.Item;
import org.example.example.rest.service.CrudService;
import org.example.framework.handler.FrameworkRequest;
import org.example.framework.handler.FrameworkResponse;
import org.modelmapper.ModelMapper;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

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
        byte[] responseBody = ("Save item").getBytes(StandardCharsets.UTF_8);

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
}
