package org.onlinestore.dao;

import org.onlinestore.entity.Item;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDao implements Dao<Item> {
    private static ItemDao instance;

    private static final String SQL_GET_ITEM_BY_ID = "SELECT * FROM item WHERE id = (?)";

    private ItemDao() {

    }

    public static ItemDao getInstance() {
        if (instance == null) {
            instance = new ItemDao();
        }
        return instance;
    }

    @Override
    public Item get(int id) throws SQLException, NamingException {
        Item item = null;
        CategoryDao cd = CategoryDao.getInstance();
        try (Connection connection = DBManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_GET_ITEM_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    item = new Item();
                    item.setId(rs.getInt(1));
                    item.setTitle(rs.getString(2));
                    item.setPrice(rs.getDouble(3));
                    item.setAmount(rs.getInt(5));
                    item.setAddedAt(rs.getDate(6));
                    item.setCategory(cd.get(rs.getInt(7)));
                }
            }
        }
        return item;
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
