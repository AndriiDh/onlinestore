package org.onlinestore.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.entity.Category;
import org.onlinestore.entity.Item;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemDao implements Dao<Item> {
    private static ItemDao instance;
    private static final Logger LOG = LogManager.getLogger(ItemDao.class);

    private static final String SQL_GET_ITEM_BY_ID = "SELECT * FROM item LEFT JOIN item_description ON id = item_description.item_id WHERE id = (?)";
    private static final String SQL_GET_ALL_ITEMS = "SELECT * FROM item LEFT JOIN item_description id ON item.id = id.item_id " +
            "WHERE title REGEXP (?) ORDER BY %s DESC LIMIT ? OFFSET ?";
    private static final String SQL_GET_ITEMS_BY_ORDER_ID = "SELECT item_id FROM item_order WHERE order_id = (?)";
    private static final String SQL_GET_ITEM_PRICE = "SELECT price FROM item WHERE id=(?)";
    private static final String SQL_GET_ITEMS_BY_NAME = "SELECT * FROM item LEFT JOIN item_description " +
            "ON item.id = item_description.item_id WHERE item.title REGEXP (?)";
    private static final String SQL_INSERT_ITEM = "INSERT INTO item(title, price, image, amount, category_id, add_time) VALUE (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_ITEM = "UPDATE item SET title=(?), price=(?), image=(?), amount=(?), add_time=(?), category_id=(?)" +
            "WHERE id=(?)";
    private static final String SQL_DELETE_ITEM = "DELETE FROM item WHERE id = (?)";
    private static final String SQL_COUNT_ITEMS = "SELECT count(*) FROM item WHERE title REGEXP (?)";
    private static final String SQL_INSERT_ITEM_DESCRIPTION = "INSERT INTO item_description(item_id, language_id, description) VALUE " +
            "(?,?,?)";
    private static final String SQL_UPDATE_ITEM_DESCRIPTION = "UPDATE item_description SET language_id = (?), description = (?) " +
            "WHERE item_id = (?)";
    private static final String SQL_GET_ITEM_DESCRIPTION = "SELECT * FROM item_description WHERE item_id = (?)";
    private static final int PRODUCTS_PER_PAGE = 8;


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
                    item.setImage(rs.getString(4));
                    item.setAmount(rs.getInt(5));
                    item.setAddedAt(rs.getDate(6));
                    item.setCategory(cd.get(rs.getInt(7)));
                    item.setDescription(rs.getString(10));
                }
            }
        }
        return item;
    }

    public List<Item> searchItems(String name) throws SQLException, NamingException {
        List<Item> items = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_ITEMS_BY_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt(1));
                    item.setTitle(rs.getString(2));
                    item.setPrice(rs.getBigDecimal(3));
                    item.setImage(rs.getString(4));
                    item.setAmount(rs.getInt(5));
                    item.setAddedAt(rs.getDate(6));
                    item.setCategory(CategoryDao.getInstance().get(rs.getInt(7)));
                    item.setDescription(rs.getString(10));
                    items.add(item);
                }
            }
        }
        return items;
    }

    public String getItemDescription(int id) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_ITEM_DESCRIPTION)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(3);
                }
            }
        }
        return null;
    }

    @Override
    public List<Item> getAll() {
        throw new UnsupportedOperationException();
    }

    public List<Item> getAll(String query, String sort, int page) throws SQLException, NamingException {
        ArrayList<Item> items = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(String.format(SQL_GET_ALL_ITEMS, sort))) {
            ps.setString(1, query);
            ps.setInt(2, PRODUCTS_PER_PAGE);
            ps.setInt(3, PRODUCTS_PER_PAGE * (page - 1));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt(1));
                    item.setTitle(rs.getString(2));
                    item.setPrice(rs.getBigDecimal(3));
                    item.setImage(rs.getString(4));
                    item.setAmount(rs.getInt(5));
                    item.setAddedAt(rs.getDate(6));
                    item.setCategory(CategoryDao.getInstance().get(rs.getInt(7)));
                    item.setDescription(rs.getString(10));
                    items.add(item);
                }
            }
        }
        return items;
    }

    public int countItems(String query) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_COUNT_ITEMS)) {
            ps.setString(1, query);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public void setItemDescription(Item item) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ITEM_DESCRIPTION)) {
            ps.setInt(1, item.getId());
            ps.setInt(2, 1); // I18N!!!!
            ps.setString(3, item.getDescription());
            if (ps.executeUpdate() < 1) {
                LOG.error("Description wasn't inserted");
            }
        }
    }

    private void updateItemDescription(Item item) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ITEM_DESCRIPTION)) {
            ps.setInt(1, 1); // i18n !!!!
            ps.setString(2, item.getDescription());
            ps.setInt(3, item.getId());
            if (ps.executeUpdate() < 1) {
                LOG.error("Description wasn't updated");
            }
        }
    }


    public BigDecimal getItemsPrice(Map<Item, Integer> list) throws SQLException, NamingException {
        BigDecimal sum = BigDecimal.ZERO;
        if (list.size() > 0) {
            for (Map.Entry<Item, Integer> item : list.entrySet()) {
                try (Connection connection = DBManager.getConnection();
                     PreparedStatement ps = connection.prepareStatement(SQL_GET_ITEM_PRICE)) {
                    ps.setInt(1, item.getKey().getId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            sum = sum.add(rs.getBigDecimal(1)
                                    .multiply(BigDecimal.valueOf(list.get(item.getKey()))));
                        }
                    }
                }
            }
        }
        return sum;
    }

    public List<Integer> getItemsId(int orderId) throws SQLException, NamingException {
        List<Integer> items = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_ITEMS_BY_ORDER_ID)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(rs.getInt(1));
                }
            }
        }
        return items;
    }

    public void updateItems(Map<Item, Integer> items) throws NamingException, SQLException {
        if (items == null) {
            throw new NamingException("Given structure is null");
        }
        for (Map.Entry<Item, Integer> item : items.entrySet()) {
            Item currentItem = item.getKey();
            currentItem.setAmount(currentItem.getAmount() - item.getValue());
            update(currentItem);
        }
    }

    @Override
    public void delete(int id) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_ITEM)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new SQLException("Current item doesn't exist");
            }
        }
    }


    @Override
    public void insert(Item item) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ITEM)) {
            ps.setString(1, item.getTitle());
            ps.setBigDecimal(2, item.getPrice());
            ps.setString(3, item.getImage());
            ps.setInt(4, item.getAmount());
            ps.setInt(5, item.getCategory().getId());
            ps.setDate(6, item.getAddedAt());
            if (ps.executeUpdate() < 1) {
                LOG.warn("Insert wasn't executed");
                return;
            }
            setItemDescription(item);
        }

    }

    @Override
    public void update(Item item) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ITEM)) {
            ps.setString(1, item.getTitle());
            ps.setBigDecimal(2, item.getPrice());
            ps.setString(3, item.getImage());
            ps.setInt(4, item.getAmount());
            ps.setDate(5, item.getAddedAt());
            ps.setInt(6, item.getCategory().getId());
            ps.setInt(7, item.getId());
            ps.executeUpdate();
            if (getItemDescription(item.getId()) == null) {
                setItemDescription(item);
            }
            updateItemDescription(item);
        }
    }

    public static class CategoryDao implements Dao<Category> {
        private static CategoryDao instance;

        private static final String SQL_GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = (?)";
        private static final String SQL_GET_ALL_CATEGORIES = "SELECT * FROM category";
        private static final String SQL_INSERT_INTO_CATEGORY = "INSERT INTO category(category) VALUE (?)";
        private static final String SQL_UPDATE_CATEGORY_BY_ID = "UPDATE category SET category = (?) WHERE id = (?)";

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
