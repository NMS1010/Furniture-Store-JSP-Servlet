package models.services.user;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.entities.User;
import org.hibernate.Session;
import models.view_models.users.UserCreateRequest;
import models.view_models.users.UserGetPagingRequest;
import models.view_models.users.UserUpdateRequest;
import models.view_models.users.UserViewModel;

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
}
