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
import java.util.Arrays;
import java.util.List;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    public static final Logger log = LogManager.getLogger(CatalogServlet.class);
    private static final List<String> sort = Arrays.asList("title", "name", "add_time", "price");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        String sortBy = req.getParameter("sort-by");
        String page = req.getParameter("page");

        query = query == null || query.isEmpty() ? "." : query;
        sortBy = sort.contains(sortBy) ? sortBy : "title";

        try {
            log.info("DoGet Method is executed");
            List<Item> items = getItems(query, sortBy, page);

            req.setAttribute("items", items);
            req.setAttribute("amount_of_items", countPages(query));

            req.getRequestDispatcher("catalog.jsp").forward(req, resp);
        } catch (SQLException | NamingException throwables) {
            log.warn("Troubles with ItemDao", throwables);
        }
    }

    private static List<Item> getItems(String query, String sortBy, String page) throws SQLException, NamingException {
        int pageToGo;
        try {
            pageToGo = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            pageToGo = 1;
        }
        return ItemDao.getInstance().getAll(query, sortBy, pageToGo);
    }

    private static int countPages(String query) throws SQLException, NamingException {
        int countItems = ItemDao.getInstance().countItems(query);
        return countItems % 8 == 0 ? countItems / 8 : countItems / 8 + 1;
    }

}
