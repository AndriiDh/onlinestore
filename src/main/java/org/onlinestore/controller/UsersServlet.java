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

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
           /* if (((User) req.getSession().getAttribute("user")).getRole() == Role.ADMIN) {
                //not
            }*/
            req.setAttribute("users_list", UserDao.getInstance().getAll());
            req.getRequestDispatcher("users.jsp").forward(req,resp);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
