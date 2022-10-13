package models.services.order_item;

import models.entities.OrderItem;
import models.repositories.order_item.OrderItemRepository;
import models.services.product.ProductService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;
import models.view_models.order_items.OrderItemCreateRequest;
import models.view_models.order_items.OrderItemGetPagingRequest;
import models.view_models.order_items.OrderItemUpdateRequest;
import models.view_models.order_items.OrderItemViewModel;
import models.view_models.products.ProductViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderItemService implements IOrderItemService{
    private static OrderItemService instance = null;
    public static OrderItemService getInstance(){
        if(instance == null)
            instance = new OrderItemService();
        return instance;
    }
    @Override
    public int insertOrderItem(OrderItemCreateRequest request) {
        return OrderItemRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateOrderItem(OrderItemUpdateRequest request) {
        return OrderItemRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteOrderItem(Integer orderItemId) {
        return OrderItemRepository.getInstance().delete(orderItemId);
    }
    @Override
    public OrderItemViewModel retrieveOrderItemById(Integer orderItemId) {
        return OrderItemRepository.getInstance().retrieveById(orderItemId);
    }

    @Override
    public ArrayList<OrderItemViewModel> retrieveAllOrderItem(OrderItemGetPagingRequest request) {
        return OrderItemRepository.getInstance().retrieveAll(request);
    }

    @Override
    public ArrayList<OrderItemViewModel> getByOrderId(int orderId) {
        return OrderItemRepository.getInstance().getByOrderId(orderId);
    }
}
