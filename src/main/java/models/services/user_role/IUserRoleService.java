package models.services.user_role;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.roles.RoleViewModel;
import view_models.user_roles.UserRoleCreateRequest;
import view_models.user_roles.UserRoleGetPagingRequest;
import view_models.user_roles.UserRoleUpdateRequest;
import view_models.user_roles.UserRoleViewModel;

import java.util.ArrayList;

public interface IUserRoleService extends IModifyEntity<UserRoleCreateRequest, UserRoleUpdateRequest, Integer>,
        IRetrieveEntity<UserRoleViewModel, UserRoleGetPagingRequest, Integer> {
    ArrayList<UserRoleViewModel> getByUserId(int userId);
}
