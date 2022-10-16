package controllers.admin.user;

import models.services.user.UserService;
import models.view_models.user_roles.UserRoleViewModel;
import models.view_models.users.UserLoginRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Login", value = "/admin/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.forward(request,response,"/views/admin/signin/signin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        boolean isAdmin = false;
        if(UserService.getInstance().login(loginRequest)){
            UserViewModel user = UserService.getInstance().getUserByUserName(username);
            for(UserRoleViewModel role:user.getRoles()){
                if(role.getRoleName().equalsIgnoreCase("admin")){
                    Cookie c = new Cookie("admin", username);
                    response.addCookie(c);
                    isAdmin = true;

                    HttpSession session = request.getSession();
                    session.setAttribute("admin",user);
                    break;
                }
            }
        }

        if(!isAdmin){
            out.println("error");
        }else{
            ServletUtils.redirect(response, request.getContextPath() + "/admin/home");
        }
    }
}
