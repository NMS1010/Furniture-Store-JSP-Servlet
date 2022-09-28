package models.services.wish_list_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.wish_list_items.WishListItemCreateRequest;
import view_models.wish_list_items.WishListItemGetPagingRequest;
import view_models.wish_list_items.WishListItemUpdateRequest;
import view_models.wish_list_items.WishListItemViewModel;

public interface IWishListItemService  extends IModifyEntity<WishListItemCreateRequest, WishListItemUpdateRequest, Integer>,
        IRetrieveEntity<WishListItemViewModel, WishListItemGetPagingRequest, Integer> {
}
