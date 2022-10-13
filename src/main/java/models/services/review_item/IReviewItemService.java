package models.services.review_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.review_items.ReviewItemCreateRequest;
import models.view_models.review_items.ReviewItemGetPagingRequest;
import models.view_models.review_items.ReviewItemUpdateRequest;
import models.view_models.review_items.ReviewItemViewModel;

import java.util.ArrayList;

public interface IReviewItemService {
    int insertReviewItem(ReviewItemCreateRequest request);
    boolean updateReviewItem(ReviewItemUpdateRequest request);
    boolean deleteReviewItem(Integer reviewItemId);
    ReviewItemViewModel retrieveReviewItemById(Integer reviewItemId);
    ArrayList<ReviewItemViewModel> retrieveAllReviewItem(ReviewItemGetPagingRequest request);
    boolean ChangeReviewItemStatus(int reviewItemId);
    ArrayList<ReviewItemViewModel> retrieveReviewItemByProductId(Integer productId);
    ArrayList<ReviewItemViewModel> retrieveReviewItemByUserId(Integer userId);
}
