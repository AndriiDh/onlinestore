package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.dao.UserDao;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UserManagementServlet extends HttpServlet  {
    private static final Logger LOG = LogManager.getLogger(UserManagementServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("catalog");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            UserDao dao = UserDao.getInstance();
            dao.get(Integer.parseInt(req.getParameter("id")));

        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
    }
}
