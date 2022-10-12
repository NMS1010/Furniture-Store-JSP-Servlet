package controllers.admin.user;

import models.services.role.RoleService;
import models.services.user.UserService;
import utils.ServletUtils;
import view_models.roles.RoleGetPagingRequest;
import view_models.roles.RoleViewModel;
import view_models.users.UserGetPagingRequest;
import view_models.users.UserViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetUsers", value = "/admin/users")
public class GetUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserGetPagingRequest reqUser = new UserGetPagingRequest();
        ArrayList<UserViewModel> users = UserService.getInstance().retrieveAll(reqUser);

        request.setAttribute("users",users);

        RoleGetPagingRequest reqRole = new RoleGetPagingRequest();
        ArrayList<RoleViewModel> roles = RoleService.getInstance().retrieveAll(reqRole);
        request.setAttribute("roles",roles);


        ServletUtils.forward(request, response, "/views/admin/user/list-user.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
