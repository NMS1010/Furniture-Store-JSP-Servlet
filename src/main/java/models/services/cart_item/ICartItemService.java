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

}
