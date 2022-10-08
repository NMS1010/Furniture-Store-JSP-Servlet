package controllers.admin.order;

import models.services.order.OrderService;
import utils.ServletUtils;
import utils.StringUtils;
import view_models.orders.OrderUpdateRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangeOrderStatus", value = "/admin/order/editStatus")
public class ChangeOrderStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = StringUtils.toInt(request.getParameter("orderId"));
        int status = StringUtils.toInt(request.getParameter("orderStatus"));
        OrderUpdateRequest req = new OrderUpdateRequest();
        req.setOrderId(orderId);
        req.setStatus(status);

        OrderService.getInstance().update(req);

        ServletUtils.redirect(response, request.getContextPath() + "/admin/orders");
    }
}
