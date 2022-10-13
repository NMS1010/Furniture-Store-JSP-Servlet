package models.repositories.user_role;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.user_roles.UserRoleCreateRequest;
import models.view_models.user_roles.UserRoleGetPagingRequest;
import models.view_models.user_roles.UserRoleUpdateRequest;
import models.view_models.user_roles.UserRoleViewModel;

import java.util.ArrayList;

public interface IUserRoleRepository extends IModifyEntity<UserRoleCreateRequest, UserRoleUpdateRequest, Integer>,
        IRetrieveEntity<UserRoleViewModel, UserRoleGetPagingRequest, Integer> {
    ArrayList<UserRoleViewModel> getByUserId(int userId);
}
