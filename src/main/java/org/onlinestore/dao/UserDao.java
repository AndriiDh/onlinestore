package org.onlinestore.dao;

import org.onlinestore.entity.Role;
import org.onlinestore.entity.User;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM user WHERE id = (?)";
    private static final String SQL_GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login = (?)";
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_INSERT_USER = "INSERT INTO user(login, first_name, last_name, user_password, email, phone, role_title)" +
            "VALUE (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE user SET login = (?), first_name = (?), last_name = (?), " +
            "user_password = (?), email = (?), phone = (?), role_title = (?), banne = (?) WHERE id = (?)";
    private static UserDao instance;

    private UserDao() {

    }

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public User get(int id) throws SQLException, NamingException {
        User user = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_USER_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setLogin(rs.getString(2));
                    user.setFirstName(rs.getString(3));
                    user.setLastName(rs.getString(4));
                    user.setPassword(rs.getString(5));
                    user.setEmail(rs.getString(6));
                    user.setPhoneNumber(rs.getString(7));
                    user.setRole(Role.valueOf(rs.getString(8).toUpperCase()));
                    user.setBanned(rs.getBoolean(9));
                }
            }
        }
        return user;
    }

    public User getUserByLogin(String login) throws SQLException, NamingException {
        User user = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_GET_USER_BY_LOGIN)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setLogin(rs.getString(2));
                    user.setFirstName(rs.getString(3));
                    user.setLastName(rs.getString(4));
                    user.setPassword(rs.getString(5));
                    user.setEmail(rs.getString(6));
                    user.setPhoneNumber(rs.getString(7));
                    user.setRole(Role.valueOf(rs.getString(8).toUpperCase()));
                    user.setBanned(rs.getBoolean(9));
                }
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException, NamingException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(SQL_GET_ALL_USERS)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setFirstName(rs.getString(3));
                user.setLastName(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setPhoneNumber(rs.getString(7));
                user.setRole(Role.valueOf(rs.getString(8).toUpperCase()));
                user.setBanned(rs.getBoolean(9));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public void insert(User user) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_USER)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhoneNumber());
            ps.setString(7, Role.CUSTOMER.getValue());
            if (ps.executeUpdate() > 0) {
                try (ResultSet set = ps.getGeneratedKeys()) {
                    if (set.next()) {
                        user.setId(set.getInt(1));
                    }
                }
            }
        }

    }

    @Override
    public void update(User user) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhoneNumber());
            ps.setString(7, user.getRole().getValue());
            ps.setBoolean(8, user.isBanned());
            ps.executeUpdate();
        }

    }
}
