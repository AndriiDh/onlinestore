package org.onlinestore.dao;

import org.onlinestore.entity.Category;
import org.onlinestore.entity.Item;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemDao implements Dao<Item> {
    private static ItemDao instance;

    private static final String SQL_GET_ITEM_BY_ID = "SELECT * FROM item WHERE id = (?)";
    private static final String SQL_GET_ALL_ITEMS = "SELECT * FROM item";

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
                    item.setPrice(rs.getBigDecimal(3));
                    item.setAmount(rs.getInt(5));
                    item.setAddedAt(rs.getDate(6));
                    item.setCategory(cd.get(rs.getInt(7)));
                }
            }
        }
        return item;
    }

    @Override
    public List<Item> getAll() throws SQLException, NamingException {
        List<Item> items = new ArrayList<>();
        CategoryDao cd = CategoryDao.getInstance();
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL_GET_ALL_ITEMS)) {
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt(1));
                item.setTitle(rs.getString(2));
                item.setPrice(rs.getBigDecimal(3));
                item.setAmount(rs.getInt(5));
                item.setAddedAt(rs.getDate(6));
                item.setCategory(cd.get(rs.getInt(7)));
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public void insert(Item item) {
    }

    @Override
    public void update(Item item) {

    }

    private static class CategoryDao implements Dao<Category> {
        private static CategoryDao instance;

        private static final String SQL_GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = (?)";
        private static final String SQL_GET_ALL_CATEGORIES = "SELECT * FROM category";
        private static final String SQL_INSERT_INTO_CATEGORY = "INSERT INTO category(name) VALUE (?)";
        private static final String SQL_UPDATE_CATEGORY_BY_ID = "UPDATE category SET name = (?) WHERE id = (?)";

        private CategoryDao() {

        }

        public static synchronized CategoryDao getInstance() {
            if (instance == null) {
                instance = new CategoryDao();
            }
            return instance;
        }

        @Override
        public Category get(int id) throws SQLException, NamingException {
            Category category = null;
            try (Connection connection = DBManager.getConnection();
                 PreparedStatement ps = connection.prepareStatement(SQL_GET_CATEGORY_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        category = new Category();
                        category.setId(rs.getInt(1));
                        category.setName(rs.getString(2));
                    }
                }
            }
            return category;
        }

        @Override
        public List<Category> getAll() throws SQLException, NamingException {
            List<Category> categories = new ArrayList<>();
            try (Connection connection = DBManager.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(SQL_GET_ALL_CATEGORIES)) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt(1));
                    category.setName(rs.getString(2));
                    categories.add(category);
                }
            }
            return categories;
        }

        @Override
        public void insert(Category category) throws SQLException, NamingException {
            try (Connection connection = DBManager.getConnection();
                 PreparedStatement ps = connection.prepareStatement(SQL_INSERT_INTO_CATEGORY, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, category.getName());
                if (ps.executeUpdate() > 1) {
                    try (ResultSet set = ps.getGeneratedKeys()) {
                        if (set.next()) {
                            category.setId(set.getInt(1));
                        }
                    }
                }
            }
        }

        @Override
        public void update(Category category) throws SQLException, NamingException {
            try (Connection connection = DBManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CATEGORY_BY_ID)) {
                statement.setString(1, category.getName());
                statement.setInt(2, category.getId());
                statement.executeUpdate();
            }
        }
    }
}
