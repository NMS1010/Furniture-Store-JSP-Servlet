package controllers.admin.user;

import models.entities.Role;
import models.services.user.UserService;
import utils.DateUtils;
import utils.FileUtil;
import utils.ServletUtils;
import utils.StringUtils;
import utils.constants.USER_STATUS;
import view_models.users.UserCreateRequest;

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
        request.setCharacterEncoding("UTF-8");
        boolean error = false;
        UserCreateRequest reqCreate = new UserCreateRequest();
        reqCreate.setAvatar(request.getPart("avatar"));
        reqCreate.setUsername(request.getParameter("username"));
        reqCreate.setFirstName(request.getParameter("firstName"));
        reqCreate.setLastName(request.getParameter("lastName"));
        reqCreate.setEmail(request.getParameter("email"));
        reqCreate.setPhone(request.getParameter("phone"));
        reqCreate.setDateOfBirth(DateUtils.stringToLocalDate(request.getParameter("dob"), "MM/dd/yyyy"));
        reqCreate.setAddress(request.getParameter("address"));
        reqCreate.setGender(StringUtils.toInt(request.getParameter("gender")));
        reqCreate.setPassword(request.getParameter("password"));
        reqCreate.setStatus(StringUtils.toInt(request.getParameter("status")));
        String[] values = request.getParameterValues("roleCheckBox");
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (String v : values) {
            roleIds.add(StringUtils.toInt(v));
        }
        reqCreate.setRoleIds(roleIds);

        int userId = UserService.getInstance().insert(reqCreate);
        ServletUtils.redirect(response, request.getContextPath() + "/admin/users");

    }
}
