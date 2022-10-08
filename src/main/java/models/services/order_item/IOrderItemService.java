package models.services.order_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.order_items.OrderItemCreateRequest;
import view_models.order_items.OrderItemGetPagingRequest;
import view_models.order_items.OrderItemUpdateRequest;
import view_models.order_items.OrderItemViewModel;

import java.util.ArrayList;

public interface IOrderItemService  extends IModifyEntity<OrderItemCreateRequest, OrderItemUpdateRequest, Integer>,
        IRetrieveEntity<OrderItemViewModel, OrderItemGetPagingRequest, Integer> {
    ArrayList<OrderItemViewModel> getByOrderId(int orderId);
}
