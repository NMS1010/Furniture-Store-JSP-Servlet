package models.repositories.wish_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.wish_items.WishItemCreateRequest;
import models.view_models.wish_items.WishItemGetPagingRequest;
import models.view_models.wish_items.WishItemUpdateRequest;
import models.view_models.wish_items.WishItemViewModel;

import java.util.ArrayList;

public interface IWishItemRepository extends IModifyEntity<WishItemCreateRequest, WishItemUpdateRequest, Integer>,
        IRetrieveEntity<WishItemViewModel, WishItemGetPagingRequest, Integer> {
    int getWishIdFromUserId(int userId);
    ArrayList<WishItemViewModel> retrieveWishListByUserId(int userId);
}
