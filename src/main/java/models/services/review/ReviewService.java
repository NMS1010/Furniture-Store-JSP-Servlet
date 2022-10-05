package models.services.review;

import models.entities.*;
import models.entities.Review;
import models.entities.Review;
import models.entities.Review;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import view_models.products.ProductViewModel;
import view_models.reviews.ReviewViewModel;
import view_models.reviews.ReviewCreateRequest;
import view_models.reviews.ReviewGetPagingRequest;
import view_models.reviews.ReviewUpdateRequest;
import view_models.reviews.ReviewViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewService implements IReviewService{
    private static ReviewService instance = null;
    public static ReviewService getInstance(){
        if(instance == null)
            instance = new ReviewService();
        return instance;
    }
    @Override
    public int insert(ReviewCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Review review = new Review();
        review.setContent(request.getContent());
        review.setStatus(request.getStatus());
        review.setRating(request.getRating());
        review.setCreatedAt(DateUtils.dateNow());
        review.setUpdatedAt(DateUtils.dateNow());
        review.setUserId(request.getUserId());
        review.setProductId(request.getProductId());

        int reviewId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(review);
            reviewId = review.getReviewId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return reviewId;
    }

    @Override
    public boolean update(ReviewUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Review review = session.find(Review.class, request.getReviewId());

        review.setContent(request.getContent());
        review.setStatus(request.getStatus());
        review.setRating(request.getRating());
        review.setUpdatedAt(DateUtils.dateNow());
        return HibernateUtils.merge(review);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Review review = session.find(Review.class, entityId);
        return HibernateUtils.remove(review);
    }
    private ReviewViewModel getReviewViewModel(Review review, Session session){
        ReviewViewModel reviewViewModel = new ReviewViewModel();
        ProductViewModel product = ProductService.getInstance().retrieveById(review.getProductId());
        reviewViewModel.setReviewId(review.getReviewId());
        reviewViewModel.setContent(review.getContent());
        reviewViewModel.setProductId(review.getProductId());
        reviewViewModel.setDateCreated(review.getCreatedAt());
        reviewViewModel.setRating(review.getRating());
        reviewViewModel.setUserId(review.getUserId());
        reviewViewModel.setStatus(review.getStatus());
        reviewViewModel.setProductImage(product.getImage());
        reviewViewModel.setProductName(product.getName());
        reviewViewModel.setDateUpdated(review.getUpdatedAt());

        Query q1 = session.createQuery("select username from User where id = :s1");
        q1.setParameter("s1",review.getUserId());

        reviewViewModel.setUserName(q1.getSingleResult().toString());
        Query q2 = session.createQuery("select avatar from User where id = :s1");
        q2.setParameter("s1",review.getUserId());

        reviewViewModel.setUserAvatar(q2.getSingleResult().toString());

        return reviewViewModel;
    }
    @Override
    public ReviewViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Review review = session.find(Review.class, entityId);

        ReviewViewModel reviewViewModel = getReviewViewModel(review, session);
        session.close();

        return reviewViewModel;
    }

    @Override
    public ArrayList<ReviewViewModel> retrieveAll(ReviewGetPagingRequest request) {
        ArrayList<ReviewViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Review", request.getColumnName(), request.getSortBy(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Review> reviews = q.list();

        for(Review review:reviews){
            ReviewViewModel v = getReviewViewModel(review, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public void ChangeStatus(int reviewId) {
        Session session = HibernateUtils.getSession();
        Review review = session.find(Review.class, reviewId);
        review.setStatus(review.getStatus() == 1 ? 0 : 1);
        session.close();
        HibernateUtils.merge(review);
    }

    @Override
    public ArrayList<ReviewViewModel> retrieveByProductId(Integer productId) {
        Session session = HibernateUtils.getSession();
        ArrayList<ReviewViewModel> reviews = new ArrayList<>();
        Query q = session.createQuery("select reviewId from Review  where productId =:s1");
        q.setParameter("s1",productId);
        List<Integer> l = q.list();
        if(l!= null)
            l.forEach(reviewId -> {
                reviews.add(retrieveById((Integer)reviewId));
            });
        return reviews;
    }
}
