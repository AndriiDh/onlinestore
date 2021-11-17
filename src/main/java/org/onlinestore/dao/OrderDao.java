package org.onlinestore.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.entity.Item;
import org.onlinestore.entity.Order;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements Dao<Order> {
    private static OrderDao instance;
    private static Logger logger = LogManager.getLogger(OrderDao.class);

    private static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM bill WHERE id = (?)";
    private static final String SQL_GET_ALL_ORDERS = "SELECT * FROM bill";
    private static final String SQL_GET_ALL_USER_ORDERS = "SELECT * FROM bill WHERE user_id=(?)";
    private static final String SQL_INSERT_ORDER = "INSERT INTO bill(user_id, order_price, status, comment) VALUE (?, ?, ?, ?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE bill SET user_id = (?), " +
            "order_price = (?), status = (?), comment(?) WHERE id = (?)";
    private static final String SQL_INSERT_ITEMS_ORDERS = "INSERT INTO item_order(item_id, order_id) VALUE (?,?)";
    private static final String SQL_GET_ITEMS_TO_ORDER = "SELECT item_id FROM item_order WHERE order_id = (?)";
    private static final String SQL_GET_USER_CART = "SELECT id FROM bill WHERE user_id = (?) AND status = 'cart'";
    private static final String SQL_DELETE_CART = "DELETE FROM bill WHERE id = (?)";
    private static final String SQL_DELETE_ITEMS = "DELETE FROM item_order WHERE order_id = (?)";

    private OrderDao() {

    }

    public static synchronized OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDao();
        }
        return instance;
    }

    public List<Order> getByUserId(int id) throws SQLException, NamingException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_ALL_USER_ORDERS)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt(1));
                    order.setUser(UserDao.getInstance().get(rs.getInt(2)));
                    order.setPriceOfOrder(rs.getBigDecimal(3));
                    order.setStatus(rs.getString(4));
                    order.setComment(rs.getString(5));
                    order.setItems(getItemOrder(order.getId()));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    @Override
    public Order get(int id) throws SQLException, NamingException {
        Order order;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_ORDER_BY_ID)) {
            ps.setInt(1, id);
            order = new Order();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                    UserDao userDao = UserDao.getInstance();
                    order.setUser(userDao.get(rs.getInt(2)));
                    order.setPriceOfOrder(rs.getBigDecimal(3));
                    order.setStatus(rs.getString(4));
                    order.setComment(rs.getString(5));
                    order.setItems(getItemOrder(order.getId()));
                }
            }
        }
        return order;
    }

    @Override
    public List<Order> getAll() throws SQLException, NamingException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL_GET_ALL_ORDERS)) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setUser(UserDao.getInstance().get(rs.getInt(2)));
                order.setPriceOfOrder(rs.getBigDecimal(3));
                order.setStatus(rs.getString(4));
                order.setComment(rs.getString(5));
                order.setItems(getItemOrder(order.getId()));
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public void insert(Order order) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUser().getId());
            ps.setBigDecimal(2, order.getPriceOfOrder());
            ps.setString(3, order.getStatus());
            ps.setString(4, order.getComment());
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getInt(1));
                    }
                }
            }
            setItemsForOrder(order);
        }
    }

    public void setItemsForOrder(Order order) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ITEMS_ORDERS)) {
            for (Item item : order.getItems()) {
                ps.setInt(1, item.getId());
                ps.setInt(2, order.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public List<Item> getItemOrder(int id) throws SQLException, NamingException {
        List<Item> items = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_ITEMS_TO_ORDER)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(ItemDao.getInstance().get(rs.getInt(1)));
                }
            }
        }
        return items;
    }

    public int getCartId(int userId) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_USER_CART)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    @Override
    public void update(Order order) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ORDER)) {
            ps.setInt(1, order.getUser().getId());
            ps.setBigDecimal(2, order.getPriceOfOrder());
            ps.setString(3, order.getStatus());
            ps.setString(4, order.getComment());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_DELETE_CART);
             PreparedStatement ps1 = connection.prepareStatement(SQL_DELETE_ITEMS)) {
            ps1.setInt(1, id);
            ps1.executeUpdate();
            ps.setInt(1, id);
            if (ps.executeUpdate() != 1) {
                logger.warn("Cart wasn't deleted!");
            }
        }
    }
}
