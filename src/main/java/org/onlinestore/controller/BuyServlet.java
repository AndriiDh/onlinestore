package org.onlinestore.controller;

import org.onlinestore.dao.ItemDao;
import org.onlinestore.dao.OrderDao;
import org.onlinestore.entity.Item;
import org.onlinestore.entity.Order;
import org.onlinestore.entity.User;

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

@WebServlet("/buy")
public class BuyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("catalog").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("catalog");
            return;
        }
        Map<Item, Integer> items = user.getCart();
        try {
            if (items != null && !items.isEmpty()) {
                Order order = new Order();
                order.setUser(user);
                order.setItems(items);
                order.setStatus("registered");
                order.setComment(req.getParameter("comment"));
                order.setPriceOfOrder(ItemDao.getInstance().getItemsPrice(items));
                OrderDao.getInstance().insert(order);
            }
            user.setCart(new HashMap<>());
            req.getSession().removeAttribute("total");
            resp.sendRedirect("catalog");
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        }
    }
}
