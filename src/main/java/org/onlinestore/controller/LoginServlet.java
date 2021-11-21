package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.onlinestore.dao.OrderDao;
import org.onlinestore.dao.UserDao;
import org.onlinestore.entity.User;
import org.onlinestore.entity.Item;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("doGet");
        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = UserDao.getInstance().getUserByLogin(req.getParameter("login"));
            LOGGER.info("doPost");
            if (user == null) {
                req.getSession().setAttribute("invalid-login", true);
                resp.sendRedirect("login.jsp");
                return;
            }
            if (BCrypt.checkpw(req.getParameter("password"),user.getPassword())) {
                req.getSession().removeAttribute("invalid-login");
                req.getSession().removeAttribute("invalid-password");

                OrderDao orderDao = OrderDao.getInstance();
                Map<Item, Integer> cart = (Map<Item, Integer>) req.getSession().getAttribute("cart");
                cart = cart == null ? new HashMap<>() : cart;
                int cartId = orderDao.getCartId(user.getId());
                Map<Item, Integer> cartDao = orderDao.getItemOrder(cartId);
                cart.putAll(cartDao);
                user.setCart(cart);

                req.getSession().setAttribute("user", user);

                orderDao.delete(cartId);
                req.getSession().removeAttribute("cart");

                resp.sendRedirect("catalog");
            } else {
                req.getSession().setAttribute("invalid-password", true);
                resp.sendRedirect("login.jsp");
            }

        } catch (SQLException | NamingException throwables) {
            // todo : error handling
            LOGGER.error("Can`t process data");
            throwables.printStackTrace();
        }
    }

}
