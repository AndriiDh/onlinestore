package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.dao.OrderDao;
import org.onlinestore.entity.Order;
import org.onlinestore.entity.User;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(LogOutServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            if (!user.getCart().isEmpty()) {
                Order order = new Order();
                order.setUser(user);
                order.setStatus("cart");
                order.setItems(user.getCart());
                order.setPriceOfOrder((BigDecimal) req.getSession().getAttribute("total"));
                OrderDao.getInstance().insert(order);
            }
        } catch (NamingException | SQLException e) {
            LOG.error("Cannot perform logout", e);
            resp.sendRedirect("error.jsp");
        }
        req.getSession().invalidate();
        resp.sendRedirect("catalog");
    }
}
