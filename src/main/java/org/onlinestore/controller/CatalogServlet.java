package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.dao.ItemDao;
import org.onlinestore.entity.Item;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    public static final Logger log = LogManager.getLogger(CatalogServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("DoGet Method is executed");
            List<Item> items = ItemDao.getInstance().getAll();
            req.setAttribute("items", items);
            req.getRequestDispatcher("catalog.jsp").forward(req, resp);
        } catch (SQLException | NamingException throwables) {
            log.warn("Troubles with ItemDao");
            throwables.printStackTrace();
        }
    }
}
