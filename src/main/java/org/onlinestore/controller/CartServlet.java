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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cartProcessing")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("edit");
        int id = Integer.parseInt(req.getParameter("id"));
        List<Item> cartItems;
        if (req.getSession().getAttribute("user") == null) {
            cartItems = (List<Item>) req.getSession().getAttribute("cart");
        } else {
            User user = (User) req.getSession().getAttribute("user");
            cartItems = user.getCart();
        }
        try {
            switch (action) {
                case "delete":
                    Item item = ItemDao.getInstance().get(id);
                    cartItems.remove(item);
                    BigDecimal sum = (BigDecimal) req.getSession().getAttribute("total");
                    sum = sum.subtract(item.getPrice());
                    req.getSession().setAttribute("total", sum);
                    resp.sendRedirect("cart.jsp");
                    break;
                case "buy":
                    Order order = new Order();
                    order.setItems(cartItems);


            }
        } catch (SQLException | NamingException e) {
            // errors
            e.printStackTrace();
        }
    }
}
