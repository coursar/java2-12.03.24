package org.example.example.rest.service;

import lombok.RequiredArgsConstructor;
import org.example.example.rest.exception.ItemNotFoundException;
import org.example.example.rest.model.Item;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    public Item save(Item toSave) throws ItemNotFoundException {
        if (toSave.getId() == 0) {
            toSave.setId(this.nextId++);
            toSave.setCreated(Instant.now());
            this.items.add(toSave);

            return toSave;
        }

        for (Item item : this.items) {
            if (item.getId() == toSave.getId()) {
                item.setValue(toSave.getValue());
                return item;
            }
        }

        throw new ItemNotFoundException("item with id " + toSave.getId() + " not found");
    }

    public void deleteById(long id) throws ItemNotFoundException {
        boolean removed = this.items.removeIf(o -> o.getId() == id);

        if (!removed) {
            throw new ItemNotFoundException("item with id " + id + " not found");
        }
    }
}
