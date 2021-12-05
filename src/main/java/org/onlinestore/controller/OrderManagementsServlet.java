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

@WebServlet("/order-management")
public class OrderManagementsServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(OrderManagementsServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "change-status":
                String status = req.getParameter("status");
                try {
                    Order order = OrderDao.getInstance().get(Integer.parseInt(req.getParameter("id")));
                    System.out.println(order);
                    order.setStatus(status);
                    System.out.println(order);
                    OrderDao.getInstance().update(order);
                } catch (SQLException | NamingException throwables) {
                    LOG.error("Cannot manage orders", throwables);
                    resp.sendRedirect("error.jsp");
                }
                break;
        }
        resp.sendRedirect(req.getHeader("Referer"));
    }
}
