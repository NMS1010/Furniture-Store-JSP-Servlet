package controllers.client.authentication;

import common.user.UserUtils;
import models.services.user.UserService;
import models.view_models.users.UserLoginRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignIn", value = "/signin")
public class SignIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.forward(request,response, "/views/client/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        UserLoginRequest loginRequest = UserUtils.CreateLoginRequest(request);

        if(UserService.getInstance().login(loginRequest)){
            UserViewModel user = UserService.getInstance().getUserByUserName(loginRequest.getUsername());
            Cookie c = new Cookie("user", loginRequest.getUsername());
            response.addCookie(c);

            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            ServletUtils.redirect(response, request.getContextPath() + "/home");
        }else{
            out.println("error");
        }

    }
}
