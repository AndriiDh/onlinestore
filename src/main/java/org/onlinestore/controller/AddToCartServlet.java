package org.onlinestore.controller;

import org.onlinestore.dao.ItemDao;
import org.onlinestore.entity.Item;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        int id = Integer.parseInt(req.getParameter("id"));
        List<Item> cart;
        List<Item> unRegCart = (List) req.getSession().getAttribute("cart");
        Item item = null;
        BigDecimal sum = BigDecimal.ZERO;
        try {
            item = ItemDao.getInstance().get(id);
            if (user != null) {
                cart = user.getCart();
                if (cart == null) {
                    user.setCart(new ArrayList<>());
                    cart = user.getCart();
                }
                if (unRegCart != null) {
                    cart.addAll(unRegCart);
                    req.getSession().removeAttribute("cart");
                }
                if (!cart.contains(item)) {
                    cart.add(item);
                    sum = ItemDao.getInstance().getItemsPrice(cart);
                }
            } else {
                if (unRegCart == null) {
                    unRegCart = new ArrayList<>();
                    req.getSession().setAttribute("cart", unRegCart);
                }
                if (!unRegCart.contains(item)) {
                    unRegCart.add(item);
                    sum = ItemDao.getInstance().getItemsPrice(unRegCart);
                }
            }
            req.getSession().setAttribute("total", sum);
            resp.sendRedirect("catalog");
        } catch (SQLException | NamingException throwables) {
            // todo: error handling
            throwables.printStackTrace();
        }
    }
}
