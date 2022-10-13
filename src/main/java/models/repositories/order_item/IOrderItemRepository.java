package models.repositories.order_item;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.order_items.OrderItemCreateRequest;
import models.view_models.order_items.OrderItemGetPagingRequest;
import models.view_models.order_items.OrderItemUpdateRequest;
import models.view_models.order_items.OrderItemViewModel;

import java.util.ArrayList;

public interface IOrderItemRepository extends IModifyEntity<OrderItemCreateRequest, OrderItemUpdateRequest, Integer>,
        IRetrieveEntity<OrderItemViewModel, OrderItemGetPagingRequest, Integer> {
    ArrayList<OrderItemViewModel> getByOrderId(int orderId);
}
