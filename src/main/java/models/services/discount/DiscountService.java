package models.services.discount;

import models.entities.Discount;
import models.entities.Discount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;
import view_models.discounts.DiscountViewModel;
import view_models.discounts.DiscountCreateRequest;
import view_models.discounts.DiscountGetPagingRequest;
import view_models.discounts.DiscountUpdateRequest;
import view_models.discounts.DiscountViewModel;

import java.util.ArrayList;
import java.util.List;

public class DiscountService implements IDiscountService{

    private static DiscountService instance = null;
    public static DiscountService getInstance(){
        if(instance == null)
            instance = new DiscountService();
        return instance;
    }
    @Override
    public int insert(DiscountCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Discount discount = new Discount();
        discount.setDiscountCode(request.getDiscountCode());
        discount.setDiscountValue(request.getDiscountValue());
        discount.setStatus(request.getStatus());
        discount.setDateStart(request.getStartDate());
        discount.setDateEnd(request.getEndDate());
        discount.setQuantity(request.getQuantity());

        int discountId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(discount);
            discountId = discount.getDiscountId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return discountId;
    }

    @Override
    public boolean update(DiscountUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Discount discount = session.find(Discount.class, request.getDiscountId());

        discount.setDiscountCode(request.getDiscountCode());
        discount.setDiscountValue(request.getDiscountValue());
        discount.setStatus(request.getStatus());
        discount.setDateStart(request.getStartDate());
        discount.setDateEnd(request.getEndDate());
        discount.setQuantity(request.getQuantity());
        return HibernateUtils.merge(discount);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Discount discount = session.find(Discount.class, entityId);
        String cmd = "update Order set discountId = null, totalPrice = totalItemPrice + shipping where discountId =:s1";
        Query q = session.createQuery(cmd);
        q.setParameter("s1", discount.getDiscountId());
        q.executeUpdate();
        return HibernateUtils.remove(discount);
    }
    private DiscountViewModel getDiscountViewModel(Discount discount, Session session){
        DiscountViewModel discountViewModel = new DiscountViewModel();

        discountViewModel.setDiscountId(discount.getDiscountId());
        discountViewModel.setDiscountCode(discount.getDiscountCode());
        discountViewModel.setDiscountValue(discount.getDiscountValue());
        discountViewModel.setStartDate(discount.getDateStart());
        discountViewModel.setEndDate(discount.getDateEnd());
        discountViewModel.setStatus(discount.getStatus());
        discountViewModel.setQuantity(discount.getQuantity());

        return discountViewModel;
    }
    @Override
    public DiscountViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Discount discount = session.find(Discount.class, entityId);

        DiscountViewModel discountViewModel = getDiscountViewModel(discount, session);
        session.close();

        return discountViewModel;
    }

    @Override
    public ArrayList<DiscountViewModel> retrieveAll(DiscountGetPagingRequest request) {
        ArrayList<DiscountViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Discount", request.getColumnName(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Discount> discounts = q.list();

        for(Discount discount:discounts){
            DiscountViewModel v = getDiscountViewModel(discount, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
