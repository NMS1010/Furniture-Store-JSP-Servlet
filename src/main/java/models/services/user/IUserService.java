package models.services.user;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.entities.Role;
import models.entities.User;
import org.hibernate.Session;
import view_models.users.UserCreateRequest;
import view_models.users.UserGetPagingRequest;
import view_models.users.UserUpdateRequest;
import view_models.users.UserViewModel;

import java.util.ArrayList;

public interface IUserService  extends IModifyEntity<UserCreateRequest, UserUpdateRequest, Integer>,
        IRetrieveEntity<UserViewModel, UserGetPagingRequest, Integer> {
    int getUserRoleId(int userId, int roleId);

    boolean updateUserRole(Session session, User user, ArrayList<Integer> roleIds);

    boolean checkUsername(String username);

}
