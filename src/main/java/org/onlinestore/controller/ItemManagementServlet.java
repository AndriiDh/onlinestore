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
        String reqId = req.getParameter("id");
        int id = reqId != null ? Integer.parseInt(reqId) : 0;
        try {
            req.setAttribute("categories", ItemDao.CategoryDao.getInstance().getAll());
            switch (action) {
                case "edit":
                    ItemDao dao = ItemDao.getInstance();
                    Item item = dao.get(id);
                    req.setAttribute("item", item);
                    req.getRequestDispatcher("new-item.jsp").forward(req, resp);
                    break;
                case "new":
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
