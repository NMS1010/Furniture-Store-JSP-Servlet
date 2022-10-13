package models.repositories.user;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.entities.User;
import models.view_models.user_roles.UserRoleCreateRequest;
import models.view_models.user_roles.UserRoleGetPagingRequest;
import models.view_models.user_roles.UserRoleUpdateRequest;
import models.view_models.user_roles.UserRoleViewModel;
import models.view_models.users.UserCreateRequest;
import models.view_models.users.UserGetPagingRequest;
import models.view_models.users.UserUpdateRequest;
import models.view_models.users.UserViewModel;
import org.hibernate.Session;

import java.util.ArrayList;

public interface IUserRepository extends IModifyEntity<UserCreateRequest, UserUpdateRequest, Integer>,
        IRetrieveEntity<UserViewModel, UserGetPagingRequest, Integer> {

    boolean checkUsername(String username);
    boolean checkEmail(String email);
    boolean checkPhone(String phone);
    boolean checkPassword(int userId, String password);
}
