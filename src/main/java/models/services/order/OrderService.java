package models.services.order;

import models.entities.Order;
import models.services.discount.DiscountService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import view_models.discounts.DiscountViewModel;
import view_models.orders.OrderCreateRequest;
import view_models.orders.OrderGetPagingRequest;
import view_models.orders.OrderUpdateRequest;
import view_models.orders.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService{
    private static OrderService instance = null;
    public static OrderService getInstance(){
        if(instance == null)
            instance = new OrderService();
        return instance;
    }
    @Override
    public int insert(OrderCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Order order = new Order();
        order.setAddress(request.getAddress());
        order.setDateCreated(DateUtils.dateNow());
        order.setStatus(request.getStatus());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setName(request.getName());
        order.setDiscountId(request.getDiscountId());
        order.setUserId(request.getUserId());
        order.setDateDone(DateUtils.dateNow());
        order.setShipping(request.getShipping());
        order.setTotalItemPrice(request.getTotalItemPrice());
        order.setTotalPrice(request.getTotalItemPrice() + request.getShipping());

        int orderId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(order);
            orderId = order.getOrderId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return orderId;
    }

    @Override
    public boolean update(OrderUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Order order = session.find(Order.class, request.getOrderId());

        order.setStatus(request.getStatus());
        return HibernateUtils.merge(order);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Order order = session.find(Order.class, entityId);
        return HibernateUtils.remove(order);
    }
    private OrderViewModel getOrderViewModel(Order order, Session session){
        OrderViewModel orderViewModel = new OrderViewModel();
        DiscountViewModel discount = DiscountService.getInstance().retrieveById(order.getDiscountId());
        Query q = session.createQuery("select username from User where id =:s1");
        q.setParameter("s1",order.getUserId());

        orderViewModel.setUserName(q.getSingleResult().toString());
        orderViewModel.setOrderId(order.getOrderId());
        orderViewModel.setAddress(order.getAddress());
        orderViewModel.setDateCreated(order.getDateCreated());
        orderViewModel.setStatus(order.getStatus());
        orderViewModel.setEmail(order.getEmail());
        orderViewModel.setPhone(order.getPhone());
        orderViewModel.setName(order.getName());
        orderViewModel.setDiscountId(order.getDiscountId());
        orderViewModel.setDiscountCode(discount.getDiscountCode());
        orderViewModel.setDiscountValue(discount.getDiscountValue());
        orderViewModel.setUserId(order.getUserId());
        orderViewModel.setDateDone(order.getDateDone());
        orderViewModel.setShipping(order.getShipping());
        orderViewModel.setTotalItemPrice(order.getTotalItemPrice());
        orderViewModel.setTotalPrice(order.getTotalPrice());

        return orderViewModel;
    }
    @Override
    public OrderViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Order order = session.find(Order.class, entityId);

        OrderViewModel orderViewModel = getOrderViewModel(order, session);
        session.close();

        return orderViewModel;
    }

    @Override
    public ArrayList<OrderViewModel> retrieveAll(OrderGetPagingRequest request) {
        ArrayList<OrderViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Order", request.getColumnName(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Order> orders = q.list();

        for(Order order:orders){
            OrderViewModel v = getOrderViewModel(order, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
