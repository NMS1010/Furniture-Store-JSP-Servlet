package controllers.client.authentication;

import common.authentication.AuthenticationUtils;
import models.services.role.RoleService;
import models.services.user.UserService;
import models.view_models.roles.RoleGetPagingRequest;
import models.view_models.roles.RoleViewModel;
import models.view_models.users.UserCreateRequest;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Register", value = "/register")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.forward(request,response, "/views/client/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserCreateRequest createReq = AuthenticationUtils.CreateRegisterRequest(request);

        RoleGetPagingRequest reqRole = new RoleGetPagingRequest();
        ArrayList<RoleViewModel> roles = RoleService.getInstance().retrieveAllRole(reqRole);

        roles.removeIf(x -> !x.getRoleName().equalsIgnoreCase("khách hàng"));
        createReq.setRoleIds(new ArrayList<Integer>(){
            {
                add(roles.get(0).getRoleId());
            }
        });

        int userId = UserService.getInstance().insertUser(createReq);
        String error = "";
        if(userId < 1){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/signin" + error);
    }
}
