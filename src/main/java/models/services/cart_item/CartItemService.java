package models.services.cart_item;

import models.entities.*;
import models.entities.CartItem;
import models.entities.CartItem;
import models.entities.CartItem;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import view_models.cart_items.CartItemCreateRequest;
import view_models.cart_items.CartItemGetPagingRequest;
import view_models.cart_items.CartItemUpdateRequest;
import view_models.cart_items.CartItemViewModel;
import view_models.products.ProductViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartItemService implements ICartItemService{
    private static CartItemService instance = null;
    public static CartItemService getInstance(){
        if(instance == null)
            instance = new CartItemService();
        return instance;
    }
    @Override
    public int insert(CartItemCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        CartItem cartItem = new CartItem();
//        cartItem.setProductId(request.getProductId());
//        cartItem.setUserId(request.getUserId());
//        cartItem.setQuantity(request.getQuantity());
//        cartItem.setDateAdded(DateUtils.dateTimeNow());
//        Query q = session.createQuery("select price from Product where productId =:s1");
//        q.setParameter("s1",request.getProductId());
//        BigDecimal unitPrice = (BigDecimal)q.getSingleResult();
//        cartItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(request.getQuantity())) );
//        cartItem.setStatus(request.getStatus());

        int cartItemId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(cartItem);
//            cartItemId = cartItem.getCartItemId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return cartItemId;
    }

    @Override
    public boolean update(CartItemUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        CartItem cartItem = session.find(CartItem.class, request.getCartItemId());

//        cartItem.setQuantity(request.getQuantity());
//        cartItem.setStatus(request.getStatus());

        return HibernateUtils.merge(cartItem);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        CartItem cartItem = session.find(CartItem.class, entityId);
        return HibernateUtils.remove(cartItem);
    }
    private CartItemViewModel getCartItemViewModel(CartItem cartItem, Session session){
        CartItemViewModel cartItemViewModel = new CartItemViewModel();
//        ProductViewModel product = ProductService.getInstance().retrieveById(cartItem.getProductId());
//
//        cartItemViewModel.setCartItemId(cartItem.getCartItemId());
//        cartItemViewModel.setDateAdded(cartItem.getDateAdded());
//        cartItemViewModel.setQuantity(cartItem.getQuantity());
//        cartItemViewModel.setProductImage(product.getImage());
//        cartItemViewModel.setProductName(product.getName());
//
//        Query q1 = session.createQuery("select price from Product where productId =:s1");
//        q1.setParameter("s1",cartItem.getProductId());
//        BigDecimal unitPrice = (BigDecimal)q1.getSingleResult();
//
//        cartItemViewModel.setUnitPrice(unitPrice);
//        cartItemViewModel.setTotalPrice(cartItem.getTotalPrice());
//        cartItemViewModel.setProductId(cartItem.getProductId());
//        cartItemViewModel.setUserId(cartItem.getUserId());
//        cartItemViewModel.setStatus(cartItem.getStatus());
//
//        Query q2 = session.createQuery("select username from User where id = :s1");
//        q2.setParameter("s1",cartItem.getUserId());
//
//        cartItemViewModel.setUserName(q2.getSingleResult().toString());

        return cartItemViewModel;
    }
    @Override
    public CartItemViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        CartItem cartItem = session.find(CartItem.class, entityId);

        CartItemViewModel cartItemViewModel = getCartItemViewModel(cartItem, session);
        session.close();

        return cartItemViewModel;
    }

    @Override
    public ArrayList<CartItemViewModel> retrieveAll(CartItemGetPagingRequest request) {

        ArrayList<CartItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("CartItem", request.getColumnName(), request.getSortBy(),request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<CartItem> cartItems = q.list();

        for(CartItem cartItem:cartItems){
            CartItemViewModel v = getCartItemViewModel(cartItem, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
