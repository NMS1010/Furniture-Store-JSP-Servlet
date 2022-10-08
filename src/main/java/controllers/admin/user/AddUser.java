package controllers.admin.user;

import utils.DateUtils;
import view_models.users.UserCreateRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

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
        UserCreateRequest reqCreate = new UserCreateRequest();
        reqCreate.setAvatar(request.getPart("avatar"));
        reqCreate.setFirstName(request.getParameter("firstName"));
        reqCreate.setLastName(request.getParameter("lastName"));
        reqCreate.setEmail(request.getParameter("email"));
        reqCreate.setPhone(request.getParameter("phone"));
        reqCreate.setDateOfBirth(DateUtils.stringToLocalDate(request.getParameter("dob"), "MM/dd/yyyy"));

    }
}
