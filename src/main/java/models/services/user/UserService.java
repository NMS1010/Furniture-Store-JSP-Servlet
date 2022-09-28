package models.services.user;

import models.entities.*;
import models.entities.User;
import models.entities.User;
import models.entities.User;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import view_models.products.ProductViewModel;
import view_models.users.UserViewModel;
import view_models.users.UserViewModel;
import view_models.users.UserViewModel;
import view_models.users.UserCreateRequest;
import view_models.users.UserGetPagingRequest;
import view_models.users.UserUpdateRequest;
import view_models.users.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService{
    private static UserService instance = null;
    public static UserService getInstance(){
        if(instance == null)
            instance = new UserService();
        return instance;
    }
    @Override
    public int insert(UserCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        User user = new User();
        user.setAddress(request.getAddress());
        user.setDateCreated(DateUtils.dateNow());
        user.setStatus(request.getStatus());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());

        int userId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(user);
            userId = user.getId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return userId;
    }

    @Override
    public boolean update(UserUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        User user = session.find(User.class, request.getUserId());
        user.setAddress(request.getAddress());
        user.setDateUpdated(DateUtils.dateNow());
        user.setStatus(request.getStatus());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setUsername(request.getUsername());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());

        return HibernateUtils.merge(user);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        User user = session.find(User.class, entityId);
        return HibernateUtils.remove(user);
    }
    private UserViewModel getUserViewModel(User user, Session session){
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setId(user.getId());
        userViewModel.setAddress(user.getAddress());
        userViewModel.setDateCreated(user.getDateCreated());
        userViewModel.setStatus(user.getStatus());
        userViewModel.setDateUpdated(user.getDateUpdated());
        userViewModel.setFirstName(user.getFirstName());
        userViewModel.setLastName(user.getLastName());
        userViewModel.setGender(user.getGender());
        userViewModel.setLastLogin(user.getLastLogin());
        userViewModel.setEmail(user.getEmail());
        userViewModel.setPhone(user.getPhone());
        userViewModel.setUsername(user.getUsername());
        userViewModel.setDateOfBirth(user.getDateOfBirth());

        return userViewModel;
    }
    @Override
    public UserViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        User user = session.find(User.class, entityId);

        UserViewModel userViewModel = getUserViewModel(user, session);
        session.close();

        return userViewModel;
    }

    @Override
    public ArrayList<UserViewModel> retrieveAll(UserGetPagingRequest request) {
        ArrayList<UserViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("User", request.getColumnName(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<User> users = q.list();

        for(User user:users){
            UserViewModel v = getUserViewModel(user, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
