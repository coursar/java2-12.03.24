package org.example.example.https.service;

import org.example.example.https.exception.ItemNotFoundException;
import org.example.example.https.exception.OperationNotPermittedException;
import org.example.example.https.model.Item;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public List<Item> getAll(int limit, int offset) {
        // TODO: handle limits
        int effectiveFrom = Math.min(offset, this.items.size() - 1);
        int effectivePageSize = Math.min(limit, MAX_LIMIT);
        int effectiveTo = Math.min(this.items.size() - effectiveFrom, effectiveFrom + effectivePageSize);
        List<Item> page = this.items.subList(effectiveFrom, effectiveTo);
        return page;
    }

    public Item getById(long id) throws ItemNotFoundException {
        for (Item item : this.items) {
            if (item.getId() == id) {
                return item;
            }
        }

        throw new ItemNotFoundException("item with id " + id + " not found");
    }

    public Item save(Optional<Principal> optionalPrincipal, Item itemSaveModel) throws ItemNotFoundException, OperationNotPermittedException {
        Principal principal = optionalPrincipal.orElseThrow(() -> new OperationNotPermittedException("not permitted"));

        if (itemSaveModel.getId() == 0) {
            itemSaveModel.setId(this.nextId++);
            itemSaveModel.setOwner(principal.getName());
            itemSaveModel.setCreated(Instant.now());
            this.items.add(itemSaveModel);

            return itemSaveModel;
        }

        for (Item item : this.items) {
            if (item.getId() == itemSaveModel.getId()) {
                if (!Objects.equals(item.getOwner(), principal.getName())) {
                    throw new OperationNotPermittedException("...");
                }
                item.setValue(itemSaveModel.getValue());
                return item;
            }
        }

        throw new ItemNotFoundException("item with id " + itemSaveModel.getId() + " not found");
    }

    public void deleteById(long id) throws ItemNotFoundException {
        boolean removed = this.items.removeIf(o -> o.getId() == id);

        if (!removed) {
            throw new ItemNotFoundException("item with id " + id + " not found");
        }
    }
}
