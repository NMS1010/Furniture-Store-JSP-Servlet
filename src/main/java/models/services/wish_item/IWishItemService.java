package models.services.wish_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.wish_items.WishItemCreateRequest;
import models.view_models.wish_items.WishItemGetPagingRequest;
import models.view_models.wish_items.WishItemUpdateRequest;
import models.view_models.wish_items.WishItemViewModel;

import java.util.ArrayList;

public interface IWishItemService  {
    int insertWishItem(WishItemCreateRequest request);
    boolean updateWishItem(WishItemUpdateRequest request);
    boolean deleteWishItem(Integer wishItemId);
    WishItemViewModel retrieveWishItemById(Integer wishItemId);
    ArrayList<WishItemViewModel> retrieveAllWishItem(WishItemGetPagingRequest request);
    int getWishIdFromUserId(int userId);

    ArrayList<WishItemViewModel> retrieveWishListByUserId(int userId);
}
