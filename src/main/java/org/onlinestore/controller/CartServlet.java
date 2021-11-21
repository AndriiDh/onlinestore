package org.onlinestore.controller;

import org.onlinestore.dao.ItemDao;
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
import java.util.Map;

@WebServlet("/cartProcessing")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));
        Map<Item, Integer> cartItems;
        BigDecimal sum = BigDecimal.ZERO;
        if (req.getSession().getAttribute("user") == null) {
            cartItems = (Map<Item, Integer>) req.getSession().getAttribute("cart");
        } else {
            User user = (User) req.getSession().getAttribute("user");
            cartItems = user.getCart();
        }
        try {
            Item item = ItemDao.getInstance().get(id);
            switch (action) {
                case "delete":
                    cartItems.remove(item);
                    break;
                case "increase":
                    Integer increase = cartItems.get(item);
                    cartItems.replace(item, ++increase);
                    break;
                case "decrease":
                    Integer decrease = cartItems.get(item);
                    decrease = decrease == 1 ? 1 : decrease - 1;
                    cartItems.replace(item, decrease);
                    break;
            }
            sum = ItemDao.getInstance().getItemsPrice(cartItems);
        } catch (SQLException | NamingException e) {
            // errors
            e.printStackTrace();
        }
        req.getSession().setAttribute("total", sum);
        resp.sendRedirect("cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
