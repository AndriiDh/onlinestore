package org.onlinestore.dao;

import org.onlinestore.entity.Order;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDao implements Dao<Order>{
    private static OrderDao instance;

    public static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM order_description WHERE id = (?)";
    private OrderDao() {

    }

    public static synchronized OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDao();
        }
        return instance;
    }
    @Override
    public Order get(int id) throws SQLException, NamingException {
        Order order;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_ORDER_BY_ID)) {
            order = new Order();
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                    UserDao userDao = UserDao.getInstance();
                    order.setUser(userDao.get(rs.getInt(2)));
                    order.setPriceOfOrder(rs.getDouble(3));
                    order.setStatus(rs.getString(4));
                    order.setComment(rs.getString(5));
                }
            }
        }
        return order;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public void insert(Order order) {

    }

    @Override
    public void update(Order order) {

    }
}
