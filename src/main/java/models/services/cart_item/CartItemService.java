package models.services.cart_item;

import models.entities.CartItem;
import models.repositories.cart_item.CartItemRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;
import models.view_models.cart_items.CartItemCreateRequest;
import models.view_models.cart_items.CartItemGetPagingRequest;
import models.view_models.cart_items.CartItemUpdateRequest;
import models.view_models.cart_items.CartItemViewModel;

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
    public int insertCartItem(CartItemCreateRequest request) {
        return CartItemRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateCartItem(CartItemUpdateRequest request) {
        return CartItemRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteCartItem(Integer cartItemId) {
        return CartItemRepository.getInstance().delete(cartItemId);
    }
    @Override
    public CartItemViewModel retrieveCartItemById(Integer cartItemId) {
        return CartItemRepository.getInstance().retrieveById(cartItemId);
    }

    @Override
    public ArrayList<CartItemViewModel> retrieveAllCartItem(CartItemGetPagingRequest request) {
        return CartItemRepository.getInstance().retrieveAll(request);
    }
}
