package controllers.admin.user;

import com.google.gson.Gson;
import models.services.user.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CheckUser", value = "/admin/users/check")
public class CheckUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        boolean exist = false;
        if(UserService.getInstance().checkUsername(request.getParameter("username"))){
            exist = true;
        }
        PrintWriter out = response.getWriter();
        out.println(exist);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
