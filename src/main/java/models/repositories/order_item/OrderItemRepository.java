package models.repositories.order_item;

import models.entities.OrderItem;
import models.services.product.ProductService;
import models.view_models.order_items.OrderItemCreateRequest;
import models.view_models.order_items.OrderItemGetPagingRequest;
import models.view_models.order_items.OrderItemUpdateRequest;
import models.view_models.order_items.OrderItemViewModel;
import models.view_models.products.ProductViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository implements IOrderItemRepository{
    private static OrderItemRepository instance = null;
    public static OrderItemRepository getInstance(){
        if(instance == null)
            instance = new OrderItemRepository();
        return instance;
    }
    @Override
    public int insert(OrderItemCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(request.getOrderId());
        orderItem.setProductId(request.getProductId());
        orderItem.setQuantity(request.getQuantity());
        orderItem.setUnitPrice(request.getUnitPrice());
        orderItem.setTotalPrice(request.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

        int orderItemId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(orderItem);
            orderItemId = orderItem.getOrderItemId();
            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return orderItemId;
    }

    @Override
    public boolean update(OrderItemUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        OrderItem orderItem = session.find(OrderItem.class, request.getOrderItemId());

        return HibernateUtils.merge(orderItem);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        OrderItem orderItem = session.find(OrderItem.class, entityId);
        return HibernateUtils.remove(orderItem);
    }
    private OrderItemViewModel getOrderItemViewModel(OrderItem orderItem, Session session){
        OrderItemViewModel orderItemViewModel = new OrderItemViewModel();
        ProductViewModel product = ProductService.getInstance().retrieveProductById(orderItem.getProductId());

        orderItemViewModel.setProductId(orderItem.getProductId());
        orderItemViewModel.setOrderId(orderItem.getOrderId());
        orderItemViewModel.setProductImage(product.getImage());
        orderItemViewModel.setProductName(product.getName());
        orderItemViewModel.setOrderItemId(orderItem.getOrderItemId());
        orderItemViewModel.setUnitPrice(orderItem.getUnitPrice());
        orderItemViewModel.setQuantity(orderItem.getQuantity());
        orderItemViewModel.setTotalPrice(orderItem.getTotalPrice());

        return orderItemViewModel;
    }
    @Override
    public OrderItemViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        OrderItem orderItem = session.find(OrderItem.class, entityId);

        OrderItemViewModel orderItemViewModel = getOrderItemViewModel(orderItem, session);
        session.close();

        return orderItemViewModel;
    }

    @Override
    public ArrayList<OrderItemViewModel> retrieveAll(OrderItemGetPagingRequest request) {
        ArrayList<OrderItemViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("OrderItem", request.getColumnName(),request.getSortBy(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<OrderItem> orderItems = q.list();

        for(OrderItem orderItem:orderItems){
            OrderItemViewModel v = getOrderItemViewModel(orderItem, session);
            list.add(v);
        }
        session.close();
        return list;
    }

    @Override
    public ArrayList<OrderItemViewModel> getByOrderId(int orderId) {
        Session session = HibernateUtils.getSession();
        ArrayList<OrderItemViewModel> orders = new ArrayList<>();
        Query q = session.createQuery("select orderItemId from OrderItem where orderId=:s1");
        q.setParameter("s1", orderId);

        List<Integer> l = q.list();
        l.forEach(id -> {
            orders.add(retrieveById(id));
        });

        return orders;
    }
}
