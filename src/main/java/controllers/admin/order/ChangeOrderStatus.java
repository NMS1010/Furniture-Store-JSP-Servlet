package controllers.admin.order;

import models.services.order.OrderService;
import models.services.order.OrderService;
import utils.ServletUtils;
import utils.StringUtils;
import view_models.orders.OrderViewModel;
import view_models.orders.OrderUpdateRequest;
import view_models.orders.OrderViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangeOrderStatus", value = "/admin/order/editStatus")
public class ChangeOrderStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");

        OrderViewModel order = OrderService.getInstance().retrieveById(Integer.parseInt(orderId));
        request.setAttribute("currOrder", order);
        ServletUtils.forward(request, response, "/admin/orders");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = StringUtils.toInt(request.getParameter("orderId"));
        int status = StringUtils.toInt(request.getParameter("orderStatus"));
        OrderUpdateRequest req = new OrderUpdateRequest();
        req.setOrderId(orderId);
        req.setStatus(status);

        boolean isSuccess = OrderService.getInstance().update(req);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/orders" + error);
    }
}
