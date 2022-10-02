package models.services.review;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.reviews.ReviewCreateRequest;
import view_models.reviews.ReviewGetPagingRequest;
import view_models.reviews.ReviewUpdateRequest;
import view_models.reviews.ReviewViewModel;

public interface IReviewService extends IModifyEntity<ReviewCreateRequest, ReviewUpdateRequest, Integer>,
        IRetrieveEntity<ReviewViewModel, ReviewGetPagingRequest, Integer> {
    void ChangeStatus(int reviewId);
}
