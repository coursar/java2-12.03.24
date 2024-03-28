package org.example.example.https.api;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.example.example.https.dto.GetAllRS;
import org.example.example.https.dto.GetItemByIdRS;
import org.example.example.https.dto.SaveItemRQ;
import org.example.example.https.dto.SaveItemRS;
import org.example.example.https.exception.OperationNotPermittedException;
import org.example.example.https.model.Item;
import org.example.example.https.service.CrudService;
import org.example.framework.handler.FrameworkRequest;
import org.example.framework.handler.FrameworkResponse;
import org.example.security.X509AuthHandler;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CrudApi {
    private final CrudService service;
    private final JsonMapper jsonMapper;
    private final ModelMapper modelMapper;

    public void getAll(FrameworkRequest request, FrameworkResponse response) throws Exception {
        Optional<Principal> optPrincipal = ((Optional<Principal>) request.getAttributes().get(X509AuthHandler.X509_PRINCIPAL_ATTRIBUTE));
        String limitParam = request.getQuery().get("limit").getFirst();
        int limit = Integer.parseInt(limitParam);
        String offsetParam = request.getQuery().get("offset").getFirst();
        int offset = Integer.parseInt(offsetParam);

        List<Item> responseModel = this.service.getAll(optPrincipal, limit, offset);
        List<GetAllRS> responseDto = this.modelMapper.map(responseModel, new TypeToken<List<GetAllRS>>() {
        }.getType());

        byte[] responseBody = this.jsonMapper.writeValueAsBytes(responseDto);

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

    public void getById(FrameworkRequest request, FrameworkResponse response) throws Exception {
        Optional<Principal> optPrincipal = ((Optional<Principal>) request.getAttributes().get(X509AuthHandler.X509_PRINCIPAL_ATTRIBUTE));

        String idParam = request.getPathParam("id").orElseThrow(); // pass custom exception
        long id = Long.parseLong(idParam);

        Item responseModel = this.service.getById(optPrincipal, id);
        GetItemByIdRS responseDto = this.modelMapper.map(responseModel, GetItemByIdRS.class);
        byte[] responseBody = this.jsonMapper.writeValueAsBytes(responseDto);

        OutputStream out = response.getOut();
        out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: application/json\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    }

    public void save(FrameworkRequest request, FrameworkResponse response) throws Exception {
        // handler
        Optional<Principal> optPrincipal = ((Optional<Principal>) request.getAttributes().get(X509AuthHandler.X509_PRINCIPAL_ATTRIBUTE));
        SaveItemRQ requestDto = this.jsonMapper.readValue(request.getBody(), SaveItemRQ.class);
        Item requestModel = this.modelMapper.map(requestDto, Item.class);
        Item responseModel = this.service.save(optPrincipal, requestModel);
        SaveItemRS responseDto = this.modelMapper.map(responseModel, SaveItemRS.class);

        byte[] responseBody = this.jsonMapper.writeValueAsBytes(responseDto);

        OutputStream out = response.getOut();
        out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: application/json\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    }

    public void deleteById(FrameworkRequest request, FrameworkResponse response) throws Exception {
        Optional<Principal> optPrincipal = ((Optional<Principal>) request.getAttributes().get(X509AuthHandler.X509_PRINCIPAL_ATTRIBUTE));

        String idParam = request.getPathParam("id").orElseThrow(); // pass custom exception
        long id = Long.parseLong(idParam);

        this.service.deleteById(optPrincipal, id);

        OutputStream out = response.getOut();
        out.write("HTTP/1.1 204 No Content\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: 0\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
    }
}
