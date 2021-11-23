package org.onlinestore.controller;

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

@WebServlet("/itemManagement")
public class ItemManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            switch (action) {
                case "edit":
                    ItemDao dao = ItemDao.getInstance();
                    Item item = dao.get(id);
                    req.setAttribute("item", item);
                    req.getRequestDispatcher("new-item.jsp").forward(req, resp);
                    break;
                case "new":
                    req.setAttribute("categories", ItemDao.CategoryDao.getInstance().getAll());
                    req.getRequestDispatcher("new-item.jsp").forward(req, resp);
                    break;
                case "delete":
                    req.setAttribute("id", id);
                    req.setAttribute("action", "delete");
                    req.getRequestDispatcher("confirm.jsp").forward(req,resp);
                    return;
            }
            resp.sendRedirect("catalog");
        } catch (SQLException | NamingException throwables) {
            // todo: errors handling
            throwables.printStackTrace();
        }
    }
}
