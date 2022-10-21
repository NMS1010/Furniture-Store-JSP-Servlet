package controllers.admin.user;

import common.authentication.AuthenticationUtils;
import models.services.user.UserService;
import utils.DateUtils;
import utils.ServletUtils;
import utils.StringUtils;
import models.view_models.users.UserCreateRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AddUser", value = "/admin/user/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserCreateRequest reqCreate = AuthenticationUtils.CreateRegisterRequest(request);

        int userId = UserService.getInstance().insertUser(reqCreate);
        String error = "";
        if(userId < 1){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/users" + error);

    }
}
