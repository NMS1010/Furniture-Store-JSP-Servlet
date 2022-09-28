package models.services.wish_list_item;

import models.entities.WishListItem;
import models.entities.WishListItem;
import models.entities.WishListItem;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import view_models.products.ProductViewModel;
import view_models.wish_list_items.WishListItemCreateRequest;
import view_models.wish_list_items.WishListItemGetPagingRequest;
import view_models.wish_list_items.WishListItemUpdateRequest;
import view_models.wish_list_items.WishListItemViewModel;

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
    public int insert(WishListItemCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        WishListItem wishListItem = new WishListItem();
        wishListItem.setProductId(request.getProductId());
        wishListItem.setUserId(request.getUserId());
        wishListItem.setDateAdded(DateUtils.dateNow());
        wishListItem.setStatus(request.getStatus());

        int wishListItemId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(wishListItem);
            wishListItemId = wishListItem.getWishListItemId();
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
    public boolean update(WishListItemUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        WishListItem wishListItem = session.find(WishListItem.class, request.getWishListItemId());

        wishListItem.setStatus(request.getStatus());

        return HibernateUtils.merge(wishListItem);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        WishListItem wishListItem = session.find(WishListItem.class, entityId);
        return HibernateUtils.remove(wishListItem);
    }
    private WishListItemViewModel getWishListItemViewModel(WishListItem wishListItem, Session session){
        WishListItemViewModel wishListItemViewModel = new WishListItemViewModel();
        ProductViewModel product = ProductService.getInstance().retrieveById(wishListItem.getProductId());

        wishListItemViewModel.setWishListItemId(wishListItem.getWishListItemId());
        wishListItemViewModel.setProductId(wishListItem.getProductId());
        wishListItemViewModel.setUserId(wishListItem.getUserId());
        wishListItemViewModel.setStatus(wishListItem.getStatus());
        wishListItemViewModel.setProductImage(product.getImage());
        wishListItemViewModel.setProductName(product.getName());
        wishListItemViewModel.setDateAdded(wishListItem.getDateAdded());
        wishListItemViewModel.setUnitPrice(product.getPrice());
        Query q = session.createQuery("select username from User where id = :s1");
        q.setParameter("s1",wishListItem.getUserId());

        wishListItemViewModel.setUserName(q.getSingleResult().toString());

        return wishListItemViewModel;
    }
    @Override
    public WishListItemViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        WishListItem wishListItem = session.find(WishListItem.class, entityId);

        WishListItemViewModel wishListItemViewModel = getWishListItemViewModel(wishListItem, session);
        session.close();

        return wishListItemViewModel;
    }

    @Override
    public ArrayList<WishListItemViewModel> retrieveAll(WishListItemGetPagingRequest request) {
        ArrayList<WishListItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("WishListItem", request.getColumnName(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<WishListItem> wishListItems = q.list();

        for(WishListItem wishListItem:wishListItems){
            WishListItemViewModel v = getWishListItemViewModel(wishListItem, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
