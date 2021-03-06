package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.dao.OrderDao;
import org.onlinestore.entity.Order;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(OrderServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        int id = Integer.parseInt(userId);
        List<Order> orders;
        try {
           orders = OrderDao.getInstance().getByUserId(id);
           req.setAttribute("orders", orders);
           req.setAttribute("userId", userId);
           req.getRequestDispatcher("order.jsp").forward(req,resp);
        } catch (SQLException | NamingException throwables) {
            LOG.error("Cannot process order", throwables);
            resp.sendRedirect("error.jsp");
        }
    }
}
