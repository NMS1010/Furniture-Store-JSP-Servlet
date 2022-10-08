package models.services.wish_list_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.wish_items.WishItemCreateRequest;
import view_models.wish_items.WishItemGetPagingRequest;
import view_models.wish_items.WishItemUpdateRequest;
import view_models.wish_items.WishItemViewModel;

public interface IWishListItemService  extends IModifyEntity<WishItemCreateRequest, WishItemUpdateRequest, Integer>,
        IRetrieveEntity<WishItemViewModel, WishItemGetPagingRequest, Integer> {
}
