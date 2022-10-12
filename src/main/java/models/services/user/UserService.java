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
import java.util.HashMap;
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
        user.setDateUpdated(DateUtils.dateTimeNow());
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
                for(int roleId: request.getRoleIds()){
                    UserRole ur = new UserRole();
                    ur.setUserId(userId);
                    ur.setRoleId(roleId);
                    session.persist(ur);
                }
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
        user.setPassword(request.getPassword());
        if(request.getAvatar()!= null && !request.getAvatar().getSubmittedFileName().equals("")){
            user.setAvatar(FileUtil.encodeBase64(request.getAvatar()));
        }
        if(request.getRoleIds() != null && request.getRoleIds().size() > 0){
            boolean res = updateUserRole(session, user, request.getRoleIds());
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
        user.setStatus(USER_STATUS.IN_ACTIVE);
        return HibernateUtils.merge(user);
    }
    private UserViewModel getUserViewModel(User user, Session session){
        UserViewModel userViewModel = new UserViewModel();

        userViewModel.setId(user.getUserId());
        userViewModel.setAddress(user.getAddress());
        userViewModel.setDateCreated(DateUtils.dateTimeToStringWithFormat(user.getDateCreated(),"yyyy-MM-dd HH:mm:ss"));
        userViewModel.setStatus(user.getStatus());
        userViewModel.setDateUpdated(DateUtils.dateTimeToStringWithFormat(user.getDateUpdated(),"yyyy-MM-dd HH:mm:ss"));
        userViewModel.setFirstName(user.getFirstName());
        userViewModel.setLastName(user.getLastName());
        userViewModel.setGender(user.getGender());
        if(user.getLastLogin() != null)
            userViewModel.setLastLogin(DateUtils.dateTimeToStringWithFormat(user.getLastLogin(),"yyyy-MM-dd HH:mm:ss"));
        userViewModel.setEmail(user.getEmail());
        userViewModel.setPhone(user.getPhone());
        userViewModel.setUsername(user.getUsername());
        userViewModel.setDateOfBirth(user.getDateOfBirth());
        userViewModel.setAvatar(user.getAvatar());
        userViewModel.setPassword(user.getPassword());
        Query q1 = session.createQuery("select sum(oi.quantity) from User u inner join Order o on u.userId = o.userId " +
                "inner join OrderItem oi on o.orderId = oi.orderId");
        Object res1 = q1.getSingleResult();
        userViewModel.setTotalBought(res1 != null ? (long)res1 : 0);
        userViewModel.setStatusCode(getUserStatus(user.getStatus()));
        userViewModel.setGenderCode(getUserGender(user.getGender()));
        Query q2 = session.createQuery("select count(*) from WishItem wi inner join WishList wl on wi.wishId = wl.wishListId where wl.user.userId =:s1");
        q2.setParameter("s1",user.getUserId());
        Object res2 = q2.getSingleResult();
        userViewModel.setTotalWishListItem(res2 != null ? (long)res2 : 0);

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
        try{
            return (int)q1.getSingleResult();
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public boolean updateUserRole(Session session, User user, ArrayList<Integer> roleIDs) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            HashMap<Integer, Integer> userRoleIds = new HashMap<>();
            for(UserRole ur: user.getUserRoles()){
                userRoleIds.put(ur.getUserRoleId(), ur.getRoleId());
            }
            ArrayList<Integer> addRoleIds = new ArrayList<>();
            for(int roleId:roleIDs){
                if(!userRoleIds.values().contains(roleId)){
                    addRoleIds.add(roleId);
                }
            }
            HashMap<Integer, Integer> removeRoleIds = new HashMap<>();
            for(int userRoleId:userRoleIds.keySet()){
                if(!roleIDs.contains(userRoleIds.get(userRoleId))){
                    removeRoleIds.put(userRoleId, userRoleIds.get(userRoleId));
                }
            }

            removeRoleIds.forEach((userRoleId, roleId) -> {
                session.remove(session.find(UserRole.class, userRoleId));
            });
            addRoleIds.forEach(id -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(id);
                userRole.setUserId(user.getUserId());
                session.persist(userRole);
            });
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkUsername(String username) {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("select count(*) from User where username=:s1");
        q.setParameter("s1", username);
        Object o = q.getSingleResult();
        if(o == null || (long)o == 0)
            return false;
        return true;
    }

}
