package models.services.role;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.roles.RoleCreateRequest;
import view_models.roles.RoleGetPagingRequest;
import view_models.roles.RoleUpdateRequest;
import view_models.roles.RoleViewModel;

public interface IRoleService  extends IModifyEntity<RoleCreateRequest, RoleUpdateRequest, Integer>,
        IRetrieveEntity<RoleViewModel, RoleGetPagingRequest, Integer> {
}
