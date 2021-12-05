package org.onlinestore.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SetLocaleServlet")
public class SetLocaleServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(SetLocaleServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        lang = lang == null ? "en" : lang;
        resp.addCookie(new Cookie("lang", lang));
        resp.addCookie(new Cookie("lang_id", lang.equals("en") ? "1" : "2"));
        LOG.info("Language has been set");
        resp.sendRedirect(req.getHeader("Referer"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    }
}
