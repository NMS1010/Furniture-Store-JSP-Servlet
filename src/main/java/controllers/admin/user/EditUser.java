package controllers.admin.user;

import models.services.user.UserService;
import models.view_models.user_roles.UserRoleViewModel;
import models.view_models.users.UserViewModel;
import utils.DateUtils;
import utils.ServletUtils;
import utils.StringUtils;
import models.view_models.users.UserUpdateRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "EditUser", value = "/admin/user/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class EditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        UserUpdateRequest reqUpdate = new UserUpdateRequest();

        reqUpdate.setAvatar(request.getPart("avatar"));

        reqUpdate.setUserId(StringUtils.toInt(request.getParameter("userId")));
        reqUpdate.setFirstName(request.getParameter("firstName"));
        reqUpdate.setLastName(request.getParameter("lastName"));
        reqUpdate.setEmail(request.getParameter("email"));
        reqUpdate.setPhone(request.getParameter("phone"));
        reqUpdate.setDateOfBirth(DateUtils.stringToLocalDate(request.getParameter("dob"), "MM/dd/yyyy"));
        reqUpdate.setAddress(request.getParameter("address"));
        reqUpdate.setGender(StringUtils.toInt(request.getParameter("gender")));
        reqUpdate.setPassword(request.getParameter("newPassword"));
        reqUpdate.setStatus(StringUtils.toInt(request.getParameter("status")));
        reqUpdate.setUsername(request.getParameter("username"));
        String[] values = request.getParameterValues("roleCheckBox");
        ArrayList<Integer> roleIds = new ArrayList<>();
        for(String v:values){
            roleIds.add(StringUtils.toInt(v));
        }
        reqUpdate.setRoleIds(roleIds);

        boolean isSuccess = UserService.getInstance().updateUser(reqUpdate);
        String error = "";
        if(!isSuccess){
            error = "&error=true";
        }else{
            UserViewModel user = UserService.getInstance().getUserByUserName(request.getParameter("username"));
            HttpSession session = request.getSession();
            UserViewModel currUser = (UserViewModel) session.getAttribute("admin");
            if(Objects.equals(currUser.getUsername(), user.getUsername())){
                session.setAttribute("admin",user);
            }
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/user/detail?userId=" + reqUpdate.getUserId() + error);
    }
}
