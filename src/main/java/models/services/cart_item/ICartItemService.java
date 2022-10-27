package models.services.cart_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.cart_items.CartItemCreateRequest;
import models.view_models.cart_items.CartItemGetPagingRequest;
import models.view_models.cart_items.CartItemUpdateRequest;
import models.view_models.cart_items.CartItemViewModel;

import java.util.ArrayList;

public interface ICartItemService {
    int insertCartItem(CartItemCreateRequest request);
    boolean updateCartItem(CartItemUpdateRequest request);
    boolean deleteCartItem(Integer cartItemId);
    CartItemViewModel retrieveCartItemById(Integer cartItemId);
    ArrayList<CartItemViewModel> retrieveAllCartItem(CartItemGetPagingRequest request);

    ArrayList<CartItemViewModel> retrieveCartByUserId(int userId);
    int getCartIdByUserId(int userId);
    CartItemViewModel getCartItemContain(int cartId, int productId);
    int canUpdateQuantity(int cartItemId, int quantity);

}
