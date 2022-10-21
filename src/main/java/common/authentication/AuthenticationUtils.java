package common.authentication;

import models.view_models.users.UserCreateRequest;
import models.view_models.users.UserLoginRequest;
import utils.DateUtils;
import utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AuthenticationUtils {
    public static UserCreateRequest CreateRegisterRequest(HttpServletRequest request) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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
        if(values == null)
            return reqCreate;
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (String v : values) {
            roleIds.add(StringUtils.toInt(v));
        }
        reqCreate.setRoleIds(roleIds);

        return reqCreate;
    }

    public static UserLoginRequest CreateLoginRequest(HttpServletRequest request) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        return loginRequest;
    }
}
