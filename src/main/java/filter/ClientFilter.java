package filter;

import models.view_models.users.UserViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "ClientFilter",
        urlPatterns = {
            "/my-account/*", "/my-account?*", "/my-account",
            "/wish-list",  "/wish-list/*",  "/wish-list?*",
            "/add-wish","/add-wish/*", "/add-wish?*",
            "/remove-wish", "/remove-wish?*", "/remove-wish/"
    }
)
public class ClientFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession(false);
        httpReq.setCharacterEncoding("UTF-8");
        httpResp.setCharacterEncoding("UTF-8");
        PrintWriter out = httpResp.getWriter();
        UserViewModel user = null;
        if(session != null)
            user = (UserViewModel) session.getAttribute("user");


        if(user != null){
            chain.doFilter(request, response);
        }else{
            if(httpReq.getRequestURL().toString().contains("add-wish")){
                out.println("must-login");
            }
            else {
                ServletUtils.redirect(httpResp, httpReq.getContextPath() + "/signin");
            }
        }
    }
}
