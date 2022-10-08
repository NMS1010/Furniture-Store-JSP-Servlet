package models.services.user;

import controllers.admin.brand.SearchAjax;
import models.entities.*;
import models.entities.User;
import models.entities.User;
import models.entities.User;
import models.services.product.ProductService;
import models.services.user_role.UserRoleService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.FileUtil;
import utils.HibernateUtils;
import utils.constants.USER_GENDER;
import utils.constants.USER_STATUS;
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
        user.setDateCreated(DateUtils.dateTimeNow());
        user.setStatus(request.getStatus());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        if(request.getAvatar()!= null && !request.getAvatar().getSubmittedFileName().equals("")){
            user.setAvatar(FileUtil.encodeBase64(request.getAvatar()));
        }

        int userId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(user);
            userId = user.getUserId();

            if(userId != -1){

            }
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
        user.setDateUpdated(DateUtils.dateTimeNow());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPhone(request.getPhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setStatus(request.getStatus());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        if(request.getAvatar()!= null && !request.getAvatar().getSubmittedFileName().equals("")){
            user.setAvatar(FileUtil.encodeBase64(request.getAvatar()));
        }
        if(request.getRoles() != null && request.getRoles().size() > 0){
            boolean res = updateUserRole(session, request.getUserId(), request.getRoles());
            if(!res)
                return false;
        }

        session.close();
        return HibernateUtils.merge(user);
    }
    private String getUserStatus(int i){
        String status = "";
        switch (i){
            case USER_STATUS.IN_ACTIVE:
                status = "Không hoạt động";
                break;
            case USER_STATUS.ACTIVE:
                status = "Đang hoạt động";
                break;
            default:
                status = "Không xác định";
                break;
        }
        return status;
    }
    private String getUserGender(int i){
        String gender = "";
        switch (i){
            case USER_GENDER.MALE:
                gender = "Nam";
                break;
            case USER_GENDER.FEMALE:
                gender = "Nữ";
                break;
            case USER_GENDER.OTHER:
                gender = "Khác";
                break;
            default:
                gender = "Không xác định";
                break;
        }
        return gender;
    }
    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        User user = session.find(User.class, entityId);
        return HibernateUtils.remove(user);
    }
    private UserViewModel getUserViewModel(User user, Session session){
        UserViewModel userViewModel = new UserViewModel();

        userViewModel.setId(user.getUserId());
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
        userViewModel.setAvatar(user.getAvatar());

        Query q1 = session.createQuery("select sum(oi.quantity) from User u inner join Order o on u.userId = o.userId " +
                "inner join OrderItem oi on o.orderId = oi.orderId");
        Object res1 = q1.getSingleResult();
        userViewModel.setTotalBought(res1 != null ? (long)q1.getSingleResult() : 0);
        userViewModel.setStatusCode(getUserStatus(user.getStatus()));
        userViewModel.setGenderCode(getUserGender(user.getGender()));
        Query q2 = session.createQuery("select count(*) from WishListItem where userId =:s1");
        q2.setParameter("s1",user.getUserId());
        Object res2 = q2.getSingleResult();
        userViewModel.setTotalWishListItem(res2 != null ? (long)q1.getSingleResult() : 0);

        userViewModel.setRoles(UserRoleService.getInstance().getByUserId(user.getUserId()));
        ArrayList<Integer> roleIds = new ArrayList<>();
        userViewModel.getRoles().forEach(s -> roleIds.add(s.getRoleId()));
        userViewModel.setRoleIds(roleIds);


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
        String cmd = HibernateUtils.getRetrieveAllQuery("User", request.getColumnName(),request.getSortBy(), request.getKeyword(), request.getTypeSort());
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


    @Override
    public int getUserRoleId(int userId, int roleId) {
        Session session = HibernateUtils.getSession();
        Query q1 = session.createQuery("select userRoleId from UserRole where userId=:s1 and roleId=:s2");
        q1.setParameter("s1",userId);
        q1.setParameter("s2",roleId);
        Object res = q1.getSingleResult();
        if(res != null){
            return (int)res;
        }
        return -1;
    }

    @Override
    public boolean updateUserRole(Session session, int userId, ArrayList<Role> roles) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            for (Role r: roles){
                int roleId = r.getRoleId();
                int userRoleId = getUserRoleId(userId, roleId);
                if(userRoleId != -1){
                    session.remove(session.find(UserRole.class, userRoleId));
                }else{
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(userId);

                    session.persist(userRole);
                }
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
