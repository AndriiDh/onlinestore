package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.onlinestore.dao.UserDao;
import org.onlinestore.entity.Role;
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

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    public static final Logger log = LogManager.getLogger(SignUpServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("signup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        // todo : Validation : already in use login!
        user.setLogin(req.getParameter("login"));
        user.setFirstName(req.getParameter("first_name"));
        user.setLastName(req.getParameter("last_name"));
        user.setPassword(BCrypt.hashpw(req.getParameter("password"), BCrypt.gensalt()));
        user.setEmail(req.getParameter("email"));
        user.setPhoneNumber(req.getParameter("phone"));
        user.setRole(Role.CUSTOMER);
        user.setMoney(BigDecimal.ZERO);

        try {
            UserDao.getInstance().insert(user);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("catalog");
        } catch (SQLException | NamingException throwables) {
            log.error("User wasn't inserted", throwables);
        }
    }
}
