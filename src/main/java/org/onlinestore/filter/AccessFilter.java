package org.onlinestore.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinestore.entity.Role;
import org.onlinestore.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@WebFilter("/*")
public class AccessFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(AccessFilter.class);

    private static final Map<Role, List<String>> map = new EnumMap<>(Role.class);
    private static final List<String> UNAUTHORISED = Arrays.asList("cart.jsp", "catalog.jsp", "login.jsp", "signup.jsp",
            "cartProcessing", "catalog", "login", "SetLocaleServlet", "signup", "addToCart");

    static {
        map.put(Role.CUSTOMER, Arrays.asList("buy", "logout", "orders", "buy.jsp", "cabinet.jsp", "order.jsp"));
        map.put(Role.ADMIN, Arrays.asList("delete-servlet", "itemManagement", "newItemServlet", "UserManagement",
                "users", "buy", "logout", "orders", "buy.jsp", "cabinet.jsp", "order.jsp", "order-management"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        String source = uri.substring(uri.lastIndexOf('/') + 1);
        LOG.info("Resource has been accessed: " + source);
        if (!(source.endsWith(".css") || source.endsWith(".jpg"))) {
            User user = (User) httpRequest.getSession().getAttribute("user");
            if (user != null) {
                if (user.isBanned()) {
                    request.setAttribute("Ban", true);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
                if (!(map.get(user.getRole()).contains(source) || UNAUTHORISED.contains(source))) {
                    httpResponse.sendRedirect("catalog");
                    return;
                }
            } else if (!UNAUTHORISED.contains(source)) {
                httpResponse.sendRedirect("catalog");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
