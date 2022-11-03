package models.repositories.order;

import models.entities.Order;
import models.entities.User;
import models.services.discount.DiscountService;
import models.view_models.discounts.DiscountViewModel;
import models.view_models.orders.OrderCreateRequest;
import models.view_models.orders.OrderGetPagingRequest;
import models.view_models.orders.OrderUpdateRequest;
import models.view_models.orders.OrderViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.HibernateUtils;
import utils.HtmlClassUtils;
import utils.constants.ORDER_PAYMENT;
import utils.constants.ORDER_STATUS;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository{
    private static OrderRepository instance = null;
    public static OrderRepository getInstance(){
        if(instance == null)
            instance = new OrderRepository();
        return instance;
    }
    @Override
    public int insert(OrderCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Order order = new Order();
        order.setAddress(request.getAddress());
        order.setDateCreated(DateUtils.dateTimeNow());
        order.setPayment(request.getPayment());
        order.setStatus(request.getStatus());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setName(request.getName());
        if(request.getDiscountId() > 0)
            order.setDiscountId(request.getDiscountId());
        order.setUserId(request.getUserId());
        if(request.getPayment() == ORDER_PAYMENT.PAID)
            order.setDateDone(DateUtils.dateTimeNow());
        order.setShipping(request.getShipping());
        order.setTotalItemPrice(request.getTotalItemPrice());

        order.setTotalPrice(request.getTotalPrice());

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
        if(request.getStatus() == ORDER_STATUS.DELIVERED && order.getPayment() == ORDER_PAYMENT.COD){
            order.setPayment(ORDER_PAYMENT.PAID);
            order.setDateDone(DateUtils.dateTimeNow());
        }
        session.close();
        return HibernateUtils.merge(order);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Order order = session.find(Order.class, entityId);
        session.close();
        return HibernateUtils.remove(order);
    }

    private String getStatus(int i){
        String status = "";
        switch (i){
            case ORDER_STATUS.PENDING:
                status = "Đang đợi";
                break;
            case ORDER_STATUS.READY_TO_SHIP:
                status = "Sẵn sàng chuyển đi";
                break;
            case ORDER_STATUS.ON_THE_WAY:
                status = "Đang giao";
                break;
            case ORDER_STATUS.DELIVERED:
                status = "Đã giao thành công";
                break;
            case ORDER_STATUS.CANCEL:
                status = "Đã huỷ";
                break;
            case ORDER_STATUS.RETURN:
                status = "Hoàn trả";
                break;
            default:
                status = "Undefined";
                break;
        }
        return status;
    }
    private String getPayment(int i){
        String payment = "";
        switch (i){
            case ORDER_PAYMENT.PAID:
                payment = "PAID";
                break;
            case ORDER_PAYMENT.COD:
                payment = "COD";
                break;
            default:
                payment = "Undefined";
                break;
        }
        return payment;
    }
    private OrderViewModel getOrderViewModel(Order order, Session session){
        OrderViewModel orderViewModel = new OrderViewModel();
        DiscountViewModel discount = null;
        if(order.getDiscountId() != null)
            discount = DiscountService.getInstance().retrieveDiscountById(order.getDiscountId());
        Query q = session.createQuery("from User where id =:s1");
        q.setParameter("s1",order.getUserId());
        User user = (User)q.getSingleResult();

        orderViewModel.setUserName(user.getUsername());
        orderViewModel.setUserAddress(user.getAddress());
        orderViewModel.setUserPhone(user.getPhone());

        orderViewModel.setOrderId(order.getOrderId());
        orderViewModel.setAddress(order.getAddress());
        if(order.getDateCreated() != null)
            orderViewModel.setDateCreated(DateUtils.dateTimeToStringWithFormat(order.getDateCreated(),"yyyy-MM-dd HH:mm:ss"));
        orderViewModel.setStatus(order.getStatus());
        orderViewModel.setStatusCode(getStatus(order.getStatus()));
        orderViewModel.setEmail(order.getEmail());
        orderViewModel.setPhone(order.getPhone());
        orderViewModel.setName(order.getName());
        if(order.getDiscountId() != null) {
            orderViewModel.setDiscountId(order.getDiscountId());
            orderViewModel.setDiscountCode(discount.getDiscountCode());
            orderViewModel.setDiscountValue(discount.getDiscountValue());
        }
        orderViewModel.setUserId(order.getUserId());
        if(order.getDateDone() != null)
            orderViewModel.setDateDone(DateUtils.dateTimeToStringWithFormat(order.getDateDone(),"yyyy-MM-dd HH:mm:ss"));
        orderViewModel.setShipping(order.getShipping());
        orderViewModel.setTotalItemPrice(order.getTotalItemPrice());
        orderViewModel.setTotalPrice(order.getTotalPrice());
        orderViewModel.setPayment(order.getPayment());
        orderViewModel.setPaymentMethod(getPayment(order.getPayment()));
        orderViewModel.setTotalItem(HibernateUtils.count("OrderItem","orderId = " + order.getOrderId()));
        orderViewModel.setStatusClass(HtmlClassUtils.generateOrderStatusClass(order.getStatus()));
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
        String cmd = HibernateUtils.getRetrieveAllQuery("Order", request);
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

    @Override
    public ArrayList<OrderViewModel> retrieveDeliveredOrder(OrderGetPagingRequest request) {
        ArrayList<OrderViewModel> all = retrieveAll(request);

        all.removeIf(x -> x.getStatus() != ORDER_STATUS.DELIVERED);

        return all;
    }

    @Override
    public ArrayList<OrderViewModel> retrieveNewOrder(OrderGetPagingRequest request) {
        ArrayList<OrderViewModel> all = retrieveAll(request);

        all.removeIf(x -> x.getStatus() != ORDER_STATUS.PENDING);

        return all;
    }

    @Override
    public ArrayList<OrderViewModel> retrieveOrderByUserId(int userId) {
        Session session = HibernateUtils.getSession();
        Query q1 = session.createQuery("from Order where userId=:s1");
        q1.setParameter("s1",userId);
        ArrayList<OrderViewModel> list = new ArrayList<>();
        List<Order> orders = q1.list();

        for(Order order:orders){
            OrderViewModel v = getOrderViewModel(order, session);
            list.add(v);
        }
        return list;
    }
}
