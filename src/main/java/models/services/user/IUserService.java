package models.services.user;

import models.view_models.users.*;

import java.util.ArrayList;

public interface IUserService {
    int insertUser(UserCreateRequest request);
    boolean updateUser(UserUpdateRequest request);
    boolean deleteUser(Integer userId);
    UserViewModel retrieveUserById(Integer userId);
    ArrayList<UserViewModel> retrieveAllUser(UserGetPagingRequest request);
    boolean checkUsername(String username);
    boolean checkEmail(String email);
    boolean checkPhone(String phone);
    boolean checkPassword(int userId, String password);
    boolean login(UserLoginRequest request);
    UserViewModel getUserByUserName(String username);
}
