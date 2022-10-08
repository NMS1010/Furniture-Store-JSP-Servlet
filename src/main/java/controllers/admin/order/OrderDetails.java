package controllers.admin.order;

import models.services.order.OrderService;
import models.services.order_item.OrderItemService;
import utils.ServletUtils;
import utils.StringUtils;
import view_models.order_items.OrderItemViewModel;
import view_models.orders.OrderGetPagingRequest;
import view_models.orders.OrderViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "OrderDetails", value = "/admin/order/detail")
public class OrderDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = StringUtils.toInt(request.getParameter("orderId"));
        OrderViewModel order = OrderService.getInstance().retrieveById(orderId);

        request.setAttribute("order", order);
        ArrayList<OrderItemViewModel> orderItems = OrderItemService.getInstance().getByOrderId(orderId);
        request.setAttribute("orderItems", orderItems);

        ServletUtils.forward(request, response, "/views/admin/order/order-detail.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
