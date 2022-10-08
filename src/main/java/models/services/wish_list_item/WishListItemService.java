package models.services.wish_list_item;

import models.entities.WishItem;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import view_models.products.ProductViewModel;
import view_models.wish_items.WishItemCreateRequest;
import view_models.wish_items.WishItemGetPagingRequest;
import view_models.wish_items.WishItemUpdateRequest;
import view_models.wish_items.WishItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class WishListItemService implements IWishListItemService{
    private static WishListItemService instance = null;
    public static WishListItemService getInstance(){
        if(instance == null)
            instance = new WishListItemService();
        return instance;
    }
    @Override
    public int insert(WishItemCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        WishItem wishItem = new WishItem();
        wishItem.setProductId(request.getProductId());
        wishItem.setUserId(request.getUserId());
        wishItem.setDateAdded(DateUtils.dateTimeNow());
        wishItem.setStatus(request.getStatus());

        int wishListItemId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(wishItem);
            wishListItemId = wishItem.getWishListItemId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return wishListItemId;
    }

    @Override
    public boolean update(WishItemUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        WishItem wishItem = session.find(WishItem.class, request.getWishListItemId());

        wishItem.setStatus(request.getStatus());

        return HibernateUtils.merge(wishItem);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        WishItem wishItem = session.find(WishItem.class, entityId);
        return HibernateUtils.remove(wishItem);
    }
    private WishItemViewModel getWishListItemViewModel(WishItem wishItem, Session session){
        WishItemViewModel wishListItemViewModel = new WishItemViewModel();
        ProductViewModel product = ProductService.getInstance().retrieveById(wishItem.getProductId());

        wishListItemViewModel.setWishListItemId(wishItem.getWishListItemId());
        wishListItemViewModel.setProductId(wishItem.getProductId());
        wishListItemViewModel.setUserId(wishItem.getUserId());
        wishListItemViewModel.setStatus(wishItem.getStatus());
        wishListItemViewModel.setProductImage(product.getImage());
        wishListItemViewModel.setProductName(product.getName());
        wishListItemViewModel.setDateAdded(wishItem.getDateAdded());
        wishListItemViewModel.setUnitPrice(product.getPrice());
        Query q = session.createQuery("select username from User where id = :s1");
        q.setParameter("s1", wishItem.getUserId());

        wishListItemViewModel.setUserName(q.getSingleResult().toString());

        return wishListItemViewModel;
    }
    @Override
    public WishItemViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        WishItem wishItem = session.find(WishItem.class, entityId);

        WishItemViewModel wishListItemViewModel = getWishListItemViewModel(wishItem, session);
        session.close();

        return wishListItemViewModel;
    }

    @Override
    public ArrayList<WishItemViewModel> retrieveAll(WishItemGetPagingRequest request) {
        ArrayList<WishItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("WishItem", request.getColumnName(),request.getSortBy(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<WishItem> wishItems = q.list();

        for(WishItem wishItem : wishItems){
            WishItemViewModel v = getWishListItemViewModel(wishItem, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
