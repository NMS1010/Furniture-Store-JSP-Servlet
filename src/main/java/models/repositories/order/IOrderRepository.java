package models.repositories.order;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.orders.OrderCreateRequest;
import models.view_models.orders.OrderGetPagingRequest;
import models.view_models.orders.OrderUpdateRequest;
import models.view_models.orders.OrderViewModel;

import java.util.ArrayList;

public interface IOrderRepository  extends IModifyEntity<OrderCreateRequest, OrderUpdateRequest, Integer>,
        IRetrieveEntity<OrderViewModel, OrderGetPagingRequest, Integer> {
    ArrayList<OrderViewModel> retrieveDeliveredOrder(OrderGetPagingRequest request);
    ArrayList<OrderViewModel> retrieveNewOrder(OrderGetPagingRequest request);
}
