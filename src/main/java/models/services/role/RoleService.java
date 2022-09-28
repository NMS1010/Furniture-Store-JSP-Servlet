package models.services.role;

import models.entities.*;
import models.entities.Role;
import models.entities.Role;
import models.entities.Role;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import view_models.products.ProductViewModel;
import view_models.roles.RoleViewModel;
import view_models.roles.RoleViewModel;
import view_models.roles.RoleViewModel;
import view_models.roles.RoleCreateRequest;
import view_models.roles.RoleGetPagingRequest;
import view_models.roles.RoleUpdateRequest;
import view_models.roles.RoleViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoleService implements IRoleService{
    private static RoleService instance = null;
    public static RoleService getInstance(){
        if(instance == null)
            instance = new RoleService();
        return instance;
    }
    @Override
    public int insert(RoleCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Role role = new Role();
        role.setRoleName(request.getRoleName());
        int roleId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(role);
            roleId = role.getRoleId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return roleId;
    }

    @Override
    public boolean update(RoleUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Role role = session.find(Role.class, request.getRoleId());
        role.setRoleName(request.getRoleName());
        return HibernateUtils.merge(role);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Role role = session.find(Role.class, entityId);
        return HibernateUtils.remove(role);
    }
    private RoleViewModel getRoleViewModel(Role role, Session session){
        RoleViewModel roleViewModel = new RoleViewModel();

        roleViewModel.setRoleId(role.getRoleId());
        roleViewModel.setRoleName(role.getRoleName());

        return roleViewModel;
    }
    @Override
    public RoleViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Role role = session.find(Role.class, entityId);

        RoleViewModel roleViewModel = getRoleViewModel(role, session);
        session.close();

        return roleViewModel;
    }

    @Override
    public ArrayList<RoleViewModel> retrieveAll(RoleGetPagingRequest request) {
        ArrayList<RoleViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Role", request.getColumnName(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Role> roles = q.list();

        for(Role role:roles){
            RoleViewModel v = getRoleViewModel(role, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
