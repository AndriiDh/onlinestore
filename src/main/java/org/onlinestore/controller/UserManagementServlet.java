package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.dao.UserDao;
import org.onlinestore.entity.Role;
import org.onlinestore.entity.User;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserManagement")
public class UserManagementServlet extends HttpServlet  {
    private static final Logger LOG = LogManager.getLogger(UserManagementServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("catalog");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println(action);
        try {
            UserDao dao = UserDao.getInstance();
            User user = dao.get(Integer.parseInt(req.getParameter("id")));
            switch (action) {
                case "ban" :
                    user.setBanned(true);
                    break;
                case "unban" :
                    user.setBanned(false);
                    break;
                case "make-manager":
                    user.setRole(Role.MANAGER);
                    break;
                case "make-customer":
                    user.setRole(Role.CUSTOMER);
                    break;
            }
            dao.update(user);
            resp.sendRedirect("users");
        } catch (SQLException | NamingException throwables) {
            LOG.error("Can't update user", throwables);
            resp.sendRedirect("error.jsp");
        }
    }
}
