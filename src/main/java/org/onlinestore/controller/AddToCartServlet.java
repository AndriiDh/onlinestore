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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        Map<Item, Integer> cart;
        Map<Item, Integer> unRegCart = (Map<Item, Integer>) req.getSession().getAttribute("cart");
        BigDecimal sum = BigDecimal.ZERO;

        try {
            Item item = ItemDao.getInstance().get(Integer.parseInt(req.getParameter("id")));
            if (user != null) {
                cart = user.getCart();
                if (unRegCart != null) {
                    cart.putAll(unRegCart);
                    req.getSession().removeAttribute("cart");
                }
                if (!cart.containsKey(item)) {
                    cart.put(item, 1);
                } else {
                    Integer increase = cart.get(item);
                    increase = increase == item.getAmount() ? increase : ++increase;
                    cart.replace(item, increase);
                }
                sum = ItemDao.getInstance().getItemsPrice(cart);
            } else {
                if (unRegCart == null) {
                    unRegCart = new HashMap<>();
                    req.getSession().setAttribute("cart", unRegCart);
                }

                if (!unRegCart.containsKey(item)) {
                    unRegCart.put(item, 1);
                } else {
                    Integer increase = unRegCart.get(item);
                    unRegCart.replace(item, ++increase);
                }
                sum = ItemDao.getInstance().getItemsPrice(unRegCart);
            }
            req.getSession().setAttribute("total", sum);

            req.getRequestDispatcher("catalog").forward(req, resp);
        } catch (SQLException | NamingException throwables) {
            // todo: error handling
            throwables.printStackTrace();
        }
    }
}
