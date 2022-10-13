package models.services.order;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.orders.OrderCreateRequest;
import models.view_models.orders.OrderGetPagingRequest;
import models.view_models.orders.OrderUpdateRequest;
import models.view_models.orders.OrderViewModel;

import java.util.ArrayList;

public interface IOrderService {
    int insertOrder(OrderCreateRequest request);
    boolean updateOrder(OrderUpdateRequest request);
    boolean deleteOrder(Integer orderId);
    OrderViewModel retrieveOrderById(Integer orderId);
    ArrayList<OrderViewModel> retrieveAllOrder(OrderGetPagingRequest request);


    ArrayList<OrderViewModel> retrieveDeliveredOrder(OrderGetPagingRequest request);
    ArrayList<OrderViewModel> retrieveNewOrder(OrderGetPagingRequest request);
}
