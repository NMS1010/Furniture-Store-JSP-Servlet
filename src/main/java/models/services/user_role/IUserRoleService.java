package models.services.user_role;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.user_roles.UserRoleCreateRequest;
import models.view_models.user_roles.UserRoleGetPagingRequest;
import models.view_models.user_roles.UserRoleUpdateRequest;
import models.view_models.user_roles.UserRoleViewModel;

import java.util.ArrayList;

public interface IUserRoleService{
    ArrayList<UserRoleViewModel> getByUserId(int userId);
    int insert(UserRoleCreateRequest request);
    boolean update(UserRoleUpdateRequest request);
    boolean delete(Integer userRoleId);
    UserRoleViewModel retrieveById(Integer userRoleId);
    ArrayList<UserRoleViewModel> retrieveAll(UserRoleGetPagingRequest request);

}
