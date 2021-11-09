package org.onlinestore.dao;

import org.onlinestore.entity.Category;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDao implements Dao<Category> {
    private static CategoryDao instance;

    private static final String SQL_GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = (?)";
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
                    category.setDescription(rs.getString(3));
                }
            }
        }
        return category;
    }

    @Override
    public List<Category> getAll() throws Exception {
        return null;
    }

    @Override
    public void insert(Category category) throws Exception {

    }

    @Override
    public void update(Category category) throws Exception {

    }
}
