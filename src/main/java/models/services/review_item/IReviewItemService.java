package models.services.review_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.review_items.ReviewItemCreateRequest;
import view_models.review_items.ReviewItemGetPagingRequest;
import view_models.review_items.ReviewItemUpdateRequest;
import view_models.review_items.ReviewItemViewModel;

import java.util.ArrayList;

public interface IReviewItemService extends IModifyEntity<ReviewItemCreateRequest, ReviewItemUpdateRequest, Integer>,
        IRetrieveEntity<ReviewItemViewModel, ReviewItemGetPagingRequest, Integer> {
    boolean ChangeStatus(int reviewItemId);

    ArrayList<ReviewItemViewModel> retrieveByProductId(Integer productId);
    ArrayList<ReviewItemViewModel> retrieveByUserId(Integer userId);
}
