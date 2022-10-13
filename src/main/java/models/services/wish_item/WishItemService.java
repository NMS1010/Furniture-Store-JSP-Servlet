package models.services.wish_item;

import models.entities.WishItem;
import models.repositories.wish_item.WishItemRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import models.view_models.wish_items.WishItemCreateRequest;
import models.view_models.wish_items.WishItemGetPagingRequest;
import models.view_models.wish_items.WishItemUpdateRequest;
import models.view_models.wish_items.WishItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class WishItemService implements IWishItemService {
    private static WishItemService instance = null;
    public static WishItemService getInstance(){
        if(instance == null)
            instance = new WishItemService();
        return instance;
    }
    @Override
    public int insertWishItem(WishItemCreateRequest request) {
        return WishItemRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateWishItem(WishItemUpdateRequest request) {
        return WishItemRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteWishItem(Integer wishItemId) {
        return WishItemRepository.getInstance().delete(wishItemId);
    }
    @Override
    public WishItemViewModel retrieveWishItemById(Integer wishItemId) {
        return WishItemRepository.getInstance().retrieveById(wishItemId);
    }

    @Override
    public ArrayList<WishItemViewModel> retrieveAllWishItem(WishItemGetPagingRequest request) {
        return WishItemRepository.getInstance().retrieveAll(request);
    }
}
