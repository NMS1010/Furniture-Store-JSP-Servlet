package models.services.user_role;

import models.entities.UserRole;
import models.repositories.user_role.UserRoleRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;
import models.view_models.user_roles.UserRoleCreateRequest;
import models.view_models.user_roles.UserRoleGetPagingRequest;
import models.view_models.user_roles.UserRoleUpdateRequest;
import models.view_models.user_roles.UserRoleViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserRoleService implements IUserRoleService{
    private static UserRoleService instance = null;
    public static UserRoleService getInstance(){
        if(instance == null)
            instance = new UserRoleService();
        return instance;
    }
    @Override
    public int insert(UserRoleCreateRequest request) {
        return UserRoleRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(UserRoleUpdateRequest request) {
        return UserRoleRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(Integer userRoleId) {
        return UserRoleRepository.getInstance().delete(userRoleId);
    }

    @Override
    public UserRoleViewModel retrieveById(Integer userRoleId) {
        return UserRoleRepository.getInstance().retrieveById(userRoleId);
    }

    @Override
    public ArrayList<UserRoleViewModel> retrieveAll(UserRoleGetPagingRequest request) {
       return UserRoleRepository.getInstance().retrieveAll(request);
    }

    @Override
    public ArrayList<UserRoleViewModel> getByUserId(int userId) {
        return UserRoleRepository.getInstance().getByUserId(userId);
    }
}
