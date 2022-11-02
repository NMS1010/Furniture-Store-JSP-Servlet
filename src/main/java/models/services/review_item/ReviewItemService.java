package models.services.review_item;

import models.entities.ReviewItem;
import models.repositories.review_item.IReviewItemRepository;
import models.repositories.review_item.ReviewItemRepository;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import models.view_models.products.ProductViewModel;
import models.view_models.review_items.ReviewItemCreateRequest;
import models.view_models.review_items.ReviewItemGetPagingRequest;
import models.view_models.review_items.ReviewItemUpdateRequest;
import models.view_models.review_items.ReviewItemViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReviewItemService implements IReviewItemService {
    private static ReviewItemService instance = null;
    public static ReviewItemService getInstance(){
        if(instance == null)
            instance = new ReviewItemService();
        return instance;
    }
    @Override
    public int insertReviewItem(ReviewItemCreateRequest request) {
        return ReviewItemRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateReviewItem(ReviewItemUpdateRequest request) {
        return ReviewItemRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteReviewItem(Integer reviewItemId) {
        return ReviewItemRepository.getInstance().delete(reviewItemId);
    }
    @Override
    public ReviewItemViewModel retrieveReviewItemById(Integer reviewItemId) {
        return ReviewItemRepository.getInstance().retrieveById(reviewItemId);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveAllReviewItem(ReviewItemGetPagingRequest request) {
        return ReviewItemRepository.getInstance().retrieveAll(request);
    }

    @Override
    public boolean ChangeReviewItemStatus(int reviewItemId) {
        return ReviewItemRepository.getInstance().ChangeStatus(reviewItemId);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveReviewItemByProductId(Integer productId) {
        return ReviewItemRepository.getInstance().retrieveByProductId(productId);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveReviewItemByUserId(Integer userId) {
        return ReviewItemRepository.getInstance().retrieveByUserId(userId);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveUserReviewByProductId(Integer userId, Integer productId) {
        return ReviewItemRepository.getInstance().retrieveUserReviewByProductId(userId,productId);
    }

    @Override
    public int getReviewIdByUserId(int userId) {
        return ReviewItemRepository.getInstance().getReviewIdByUserId(userId);
    }


}
