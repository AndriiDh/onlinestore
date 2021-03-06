package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.dao.ItemDao;
import org.onlinestore.entity.Category;
import org.onlinestore.entity.Item;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet("/newItemServlet")
public class NewItemServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(NewItemServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("catalog").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String price = req.getParameter("price");
        String category = req.getParameter("category");
        String image = req.getParameter("image");
        String amount = req.getParameter("amount");
        String description = req.getParameter("description");
        String id = req.getParameter("id");

        Category categoryToInsert;
       try {
           ItemDao dao = ItemDao.getInstance();
           List<Category> categories = ItemDao.CategoryDao.getInstance().getAll();
           Optional<Category> c = categories
                   .stream()
                   .filter(cat -> cat.getName().equals(category))
                   .findAny();
           categoryToInsert = c.orElse(null);

           Item item = new Item();
           if (id == null) {
               item.setTitle(title)
                       .setPrice(BigDecimal.valueOf(Double.parseDouble(price)))
                       .setImage(image)
                       .setAmount(Integer.parseInt(amount))
                       .setDescription(description)
                       .setCategory(categoryToInsert)
                       .setAddedAt(new Date(System.currentTimeMillis()));
               dao.insert(item);
           } else {
               item.setId(Integer.parseInt(id))
                       .setTitle(title)
                       .setPrice(BigDecimal.valueOf(Double.parseDouble(price)))
                       .setImage(image)
                       .setAmount(Integer.parseInt(amount))
                       .setDescription(description)
                       .setCategory(categoryToInsert)
                       .setAddedAt(new Date(System.currentTimeMillis()));
               dao.update(item, Arrays.stream(req.getCookies()).filter(s -> s.getName()
                       .equals("lang_id"))
                       .findAny()
                       .orElse(new Cookie("lang_id", "en"))
                       .getValue());
           }
           resp.sendRedirect("catalog");
       } catch (SQLException | NamingException throwables) {
           LOG.error("Cannot add new item", throwables);
           resp.sendRedirect("error.jsp");
        }
    }
}
