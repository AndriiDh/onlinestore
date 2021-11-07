package org.onlinestore.dao;

import org.onlinestore.entity.Item;

import java.util.List;

public class ItemDao implements Dao<Item> {
    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public void insert(Item item) {

    }

    @Override
    public void update(Item item) {

    }

    @Override
    public void delete(int id) {
        Dao.super.delete(id);
    }
}
