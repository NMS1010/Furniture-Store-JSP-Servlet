package models.repositories.user_role;

import models.entities.UserRole;
import models.view_models.user_roles.UserRoleCreateRequest;
import models.view_models.user_roles.UserRoleGetPagingRequest;
import models.view_models.user_roles.UserRoleUpdateRequest;
import models.view_models.user_roles.UserRoleViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.ArrayList;
import java.util.List;

public class UserRoleRepository implements IUserRoleRepository{
    private static UserRoleRepository instance = null;
    public static UserRoleRepository getInstance(){
        if(instance == null)
            instance = new UserRoleRepository();
        return instance;
    }
    @Override
    public int insert(UserRoleCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        UserRole userRole = new UserRole();
        userRole.setUserId(request.getUserId());
        userRole.setRoleId(request.getRoleId());

        int userRoleId = -1;

        try {
            tx = session.beginTransaction();
            session.persist(userRole);
            userRoleId = userRole.getUserRoleId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return userRoleId;
    }

    @Override
    public boolean update(UserRoleUpdateRequest request) {
        Session session = HibernateUtils.getSession();

        UserRole userRole = session.find(UserRole.class, request.getUserRoleId());
        userRole.setRoleId(request.getRoleId());
        userRole.setUserId(request.getUserId());

        return HibernateUtils.merge(userRole);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        UserRole userRole = session.find(UserRole.class, entityId);
        return HibernateUtils.remove(userRole);
    }
    private UserRoleViewModel getUserRoleViewModel(UserRole userRole, Session session){
        UserRoleViewModel userRoleViewModel = new UserRoleViewModel();
        Query q1 = session.createQuery("select username from User where id = :s1");
        q1.setParameter("s1",userRole.getUserId());

        Query q2 = session.createQuery("select roleName from Role where roleId = :s1");
        q2.setParameter("s1",userRole.getRoleId());
        userRoleViewModel.setRoleId(userRole.getRoleId());
        userRoleViewModel.setUserId(userRole.getUserId());
        userRoleViewModel.setUserName(q1.getSingleResult().toString());
        userRoleViewModel.setRoleName(q2.getSingleResult().toString());

        return userRoleViewModel;
    }

    @Override
    public UserRoleViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        UserRole userRole = session.find(UserRole.class, entityId);

        UserRoleViewModel userRoleViewModel = getUserRoleViewModel(userRole, session);
        session.close();

        return userRoleViewModel;
    }

    @Override
    public ArrayList<UserRoleViewModel> retrieveAll(UserRoleGetPagingRequest request) {
        ArrayList<UserRoleViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("UserRole", request);
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<UserRole> userRoles = q.list();

        for(UserRole userRole:userRoles){
            UserRoleViewModel v = getUserRoleViewModel(userRole, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public ArrayList<UserRoleViewModel> getByUserId(int userId) {
        ArrayList<UserRoleViewModel> userRoles = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        Query q1 = session.createQuery("select userRoleId from UserRole where userId=:s1");
        q1.setParameter("s1",userId);
        List<Integer> userRoleIds = q1.list();
        if(userRoleIds != null){

            for(Integer userRoleId:userRoleIds){
                userRoles.add(retrieveById(userRoleId));
            }
        }
        return userRoles;
    }
}
