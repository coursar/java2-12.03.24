package org.example.example.https.service;

import com.google.common.primitives.Ints;
import org.example.example.https.exception.ItemNotFoundException;
import org.example.example.https.exception.OperationNotPermittedException;
import org.example.example.https.model.Item;

import java.security.Principal;
import java.time.Instant;
import java.util.*;

// TODO:
//  1. getAll - everybody
//  2. getById - everybody
//  3. save new - authenticated
//  4. save update - authenticated && owner = principal.owner
//  5. delete - authenticated && owner = principal.owner
public class CrudService {
    public static final int MAX_LIMIT = 100;
    private final List<Item> items;
    private long nextId = 1;

    public CrudService() {
         this.items = new ArrayList<>(256);
    }

    public CrudService(List<Item> items, long nextId) {
        this.items = items;
        this.nextId = nextId;
    }

    public List<Item> getAll(Optional<Principal> optPrincipal, int limit, int offset) {
        int effectiveOffset = Ints.min(offset, items.size() - 1); // TODO: add offset validation
        int effectiveLimit = Ints.min(limit, MAX_LIMIT, items.size() - effectiveOffset);

        List<Item> responseModel = this.items.subList(effectiveOffset, effectiveOffset + effectiveLimit);
        return responseModel;
    }

    public Item getById(Optional<Principal> optPrincipal, long id) throws ItemNotFoundException {
        for (Item item : this.items) {
            if (item.getId() == id) {
                return item;
            }
        }

        throw new ItemNotFoundException("item with id " + id + " not found");
    }

    public Item save(Optional<Principal> optPrincipal, Item requestModel) throws ItemNotFoundException, OperationNotPermittedException {
        Principal principal = optPrincipal.orElseThrow(() -> new OperationNotPermittedException("not permitted"));

        if (requestModel.getId() == 0) {
            requestModel.setId(this.nextId++);
            requestModel.setOwner(principal.getName());
            requestModel.setCreated(Instant.now());
            this.items.add(requestModel);

            return requestModel;
        }

        for (Item item : this.items) {
            if (item.getId() != requestModel.getId()) {
                continue;
            }

            if (!Objects.equals(item.getOwner(), principal.getName())) {
                throw new OperationNotPermittedException("...");
            }
            item.setValue(requestModel.getValue());
            return item;
        }

        throw new ItemNotFoundException("item with id " + requestModel.getId() + " not found");
    }

    public void deleteById(Optional<Principal> optPrincipal, long id) throws ItemNotFoundException, OperationNotPermittedException {
        Principal principal = optPrincipal.orElseThrow(() -> new OperationNotPermittedException("not permitted"));

        Iterator<Item> iterator = this.items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getId() != id) {
                continue;
            }

            if (!Objects.equals(item.getOwner(), principal.getName())) {
                throw new OperationNotPermittedException("...");
            }
            iterator.remove();
            return;
        }

        throw new ItemNotFoundException("item with id " + id + " not found");
    }
}
