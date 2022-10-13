package models.services.order_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.order_items.OrderItemCreateRequest;
import models.view_models.order_items.OrderItemGetPagingRequest;
import models.view_models.order_items.OrderItemUpdateRequest;
import models.view_models.order_items.OrderItemViewModel;

import java.util.ArrayList;

public interface IOrderItemService  {
    int insertOrderItem(OrderItemCreateRequest request);
    boolean updateOrderItem(OrderItemUpdateRequest request);
    boolean deleteOrderItem(Integer orderItemId);
    OrderItemViewModel retrieveOrderItemById(Integer orderItemId);
    ArrayList<OrderItemViewModel> retrieveAllOrderItem(OrderItemGetPagingRequest request);

    ArrayList<OrderItemViewModel> getByOrderId(int orderId);
}
