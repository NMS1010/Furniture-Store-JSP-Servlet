package models.services.order;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.orders.OrderCreateRequest;
import view_models.orders.OrderGetPagingRequest;
import view_models.orders.OrderUpdateRequest;
import view_models.orders.OrderViewModel;

public interface IOrderService extends IModifyEntity<OrderCreateRequest, OrderUpdateRequest, Integer>,
        IRetrieveEntity<OrderViewModel, OrderGetPagingRequest, Integer> {
}
