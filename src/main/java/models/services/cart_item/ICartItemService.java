package models.services.cart_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.cart_items.CartItemCreateRequest;
import view_models.cart_items.CartItemGetPagingRequest;
import view_models.cart_items.CartItemUpdateRequest;
import view_models.cart_items.CartItemViewModel;

public interface ICartItemService extends IModifyEntity<CartItemCreateRequest, CartItemUpdateRequest, Integer>,
        IRetrieveEntity<CartItemViewModel, CartItemGetPagingRequest, Integer> {
}
