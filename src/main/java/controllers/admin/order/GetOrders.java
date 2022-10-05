package controllers.admin.order;

import models.services.order.OrderService;
import utils.ServletUtils;
import view_models.orders.OrderGetPagingRequest;
import view_models.orders.OrderViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetOrders", value = "/admin/orders")
public class GetOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderGetPagingRequest req = new OrderGetPagingRequest();

        String newOrder = request.getParameter("new");
        String delivered = request.getParameter("delivered");
        ArrayList<OrderViewModel> orders;
        if(newOrder != null){
            orders = OrderService.getInstance().retrieveNewOrder(req);
        }else if(delivered != null){
            orders = OrderService.getInstance().retrieveDeliveredOrder(req);
        }else{
            orders = OrderService.getInstance().retrieveAll(req);
        }

        request.setAttribute("orders", orders);

        ServletUtils.forward(request, response, "/views/admin/order/order.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
