package models.services.user;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.users.UserCreateRequest;
import view_models.users.UserGetPagingRequest;
import view_models.users.UserUpdateRequest;
import view_models.users.UserViewModel;

public interface IUserService  extends IModifyEntity<UserCreateRequest, UserUpdateRequest, Integer>,
        IRetrieveEntity<UserViewModel, UserGetPagingRequest, Integer> {
}
