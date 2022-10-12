package models.services.review_item;

import models.entities.Review;
import models.entities.ReviewItem;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import view_models.products.ProductViewModel;
import view_models.review_items.ReviewItemCreateRequest;
import view_models.review_items.ReviewItemGetPagingRequest;
import view_models.review_items.ReviewItemUpdateRequest;
import view_models.review_items.ReviewItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewItemService implements IReviewItemService {
    private static ReviewItemService instance = null;
    public static ReviewItemService getInstance(){
        if(instance == null)
            instance = new ReviewItemService();
        return instance;
    }
    @Override
    public int insert(ReviewItemCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        ReviewItem reviewItem = new ReviewItem();

        reviewItem.setContent(request.getContent());
        reviewItem.setStatus(request.getStatus());
        reviewItem.setRating(request.getRating());
        reviewItem.setCreatedAt(DateUtils.dateTimeNow());
        reviewItem.setUpdatedAt(DateUtils.dateTimeNow());
        reviewItem.setProductId(request.getProductId());
        reviewItem.setReviewId(reviewItem.getReviewId());

        int reviewItemId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(reviewItem);
            reviewItemId = reviewItem.getReviewItemId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return reviewItemId;
    }

    @Override
    public boolean update(ReviewItemUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        ReviewItem reviewItem = session.find(ReviewItem.class, request.getReviewItemId());

        reviewItem.setContent(request.getContent());
        reviewItem.setStatus(request.getStatus());
        reviewItem.setRating(request.getRating());
        reviewItem.setUpdatedAt(DateUtils.dateTimeNow());

        return HibernateUtils.merge(reviewItem);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ReviewItem reviewItem = session.find(ReviewItem.class, entityId);
        session.close();
        return HibernateUtils.remove(reviewItem);
    }
    private ReviewItemViewModel getReviewItemViewModel(ReviewItem reviewItem, Session session){
        ReviewItemViewModel reviewItemViewModel = new ReviewItemViewModel();
        ProductViewModel product = ProductService.getInstance().retrieveById(reviewItem.getProductId());

        reviewItemViewModel.setReviewItemId(reviewItem.getReviewItemId());
        reviewItemViewModel.setReviewId(reviewItem.getReviewId());
        reviewItemViewModel.setContent(reviewItem.getContent());
        reviewItemViewModel.setProductId(reviewItem.getProductId());
        reviewItemViewModel.setDateCreated(DateUtils.dateTimeToStringWithFormat(reviewItem.getCreatedAt(),"yyyy-MM-dd HH:mm:ss"));
        reviewItemViewModel.setRating(reviewItem.getRating());
        Query q = session.createQuery("select r.user.userId from Review r where reviewId=:s1");
        int userId = (int)q.getSingleResult();
        reviewItemViewModel.setUserId(reviewItem.getReviewId());
        reviewItemViewModel.setStatus(reviewItem.getStatus());
        reviewItemViewModel.setProductImage(product.getImage());
        reviewItemViewModel.setProductName(product.getName());
        reviewItemViewModel.setDateUpdated(DateUtils.dateTimeToStringWithFormat(reviewItem.getUpdatedAt(),"yyyy-MM-dd HH:mm:ss"));

        Query q1 = session.createQuery("select username from User where id = :s1");
        q1.setParameter("s1",userId);

        reviewItemViewModel.setUserName(q1.getSingleResult().toString());
        Query q2 = session.createQuery("select avatar from User where id = :s1");
        q2.setParameter("s1",userId);

        reviewItemViewModel.setUserAvatar(q2.getSingleResult().toString());

        return reviewItemViewModel;
    }
    @Override
    public ReviewItemViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ReviewItem reviewItem = session.find(ReviewItem.class, entityId);

        ReviewItemViewModel reviewItemViewModel = getReviewItemViewModel(reviewItem, session);
        session.close();

        return reviewItemViewModel;
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveAll(ReviewItemGetPagingRequest request) {
        ArrayList<ReviewItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("ReviewItem", request.getColumnName(), request.getSortBy(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<ReviewItem> reviewItems = q.list();

        for(ReviewItem reviewItem:reviewItems){
            ReviewItemViewModel v = getReviewItemViewModel(reviewItem, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public boolean ChangeStatus(int reviewItemId) {
        Session session = HibernateUtils.getSession();
        ReviewItem reviewItem = session.find(ReviewItem.class, reviewItemId);
        reviewItem.setStatus(reviewItem.getStatus() == 1 ? 0 : 1);
        session.close();
        return HibernateUtils.merge(reviewItem);
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveByProductId(Integer productId) {
        Session session = HibernateUtils.getSession();
        ArrayList<ReviewItemViewModel> reviewItems = new ArrayList<>();
        Query q = session.createQuery("select reviewItemId from ReviewItem where productId =:s1");
        q.setParameter("s1",productId);
        List<Integer> l = q.list();
        if(l!= null)
            l.forEach(reviewItemId -> {
                reviewItems.add(retrieveById(reviewItemId));
            });
        session.close();
        return reviewItems;
    }

    @Override
    public ArrayList<ReviewItemViewModel> retrieveByUserId(Integer userId) {
        Session session = HibernateUtils.getSession();
        ArrayList<ReviewItemViewModel> reviewItems = new ArrayList<>();
        Query q = session.createQuery("select ri.reviewItemId from Review r inner join ReviewItem ri on r.reviewId = ri.reviewId where r.user.userId =:s1");
        q.setParameter("s1",userId);
        List<Integer> l = q.list();
        if(l!= null)
            l.forEach(reviewItemId -> {
                reviewItems.add(retrieveById(reviewItemId));
            });
        session.close();
        return reviewItems;
    }
}
