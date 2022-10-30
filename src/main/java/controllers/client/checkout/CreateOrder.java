package controllers.client.checkout;

import models.entities.CartItem;
import models.services.cart_item.CartItemService;
import models.services.order.OrderService;
import models.services.order_item.OrderItemService;
import models.services.product.ProductService;
import models.view_models.cart_items.CartItemViewModel;
import models.view_models.order_items.OrderItemCreateRequest;
import models.view_models.orders.OrderCreateRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.StringUtils;
import utils.constants.ORDER_STATUS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CreateOrder", value = "/order/create")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int userId = SessionUtils.getUserIdLogin(request);
        if(userId == -1)
            return;
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        String totalItemPrice = request.getParameter("totalItemPrice");
        String shipping = request.getParameter("shipping");
        String discountId = request.getParameter("discountId");
        String totalPrice = request.getParameter("totalPrice");

        String payment = request.getParameter("payment");

        OrderCreateRequest createOrderReq = new OrderCreateRequest();
        createOrderReq.setAddress(address);
        createOrderReq.setDiscountId(StringUtils.toInt(discountId));
        createOrderReq.setEmail(email);
        createOrderReq.setName(name);
        createOrderReq.setPayment(StringUtils.toInt(payment));
        createOrderReq.setPhone(phone);
        createOrderReq.setStatus(ORDER_STATUS.PENDING);
        createOrderReq.setShipping(StringUtils.toBigDecimal(shipping));
        createOrderReq.setTotalPrice(StringUtils.toBigDecimal(totalPrice));
        createOrderReq.setTotalItemPrice(StringUtils.toBigDecimal(totalItemPrice));
        createOrderReq.setUserId(userId);

        int orderId = OrderService.getInstance().insertOrder(createOrderReq);
        if(orderId < 0){
            request.setAttribute("error", "error");
            ServletUtils.forward(request,response, "/checkout");
            return;
        }
        ArrayList<CartItemViewModel> cartItems = CartItemService.getInstance().retrieveCartByUserId(userId);
        for(CartItemViewModel c: cartItems){
            OrderItemCreateRequest createOrderItemReq = new OrderItemCreateRequest();
            createOrderItemReq.setOrderId(orderId);
            createOrderItemReq.setQuantity(c.getQuantity());
            createOrderItemReq.setUnitPrice(c.getUnitPrice());
            createOrderItemReq.setProductId(c.getProductId());
            int orderItemId = OrderItemService.getInstance().insertOrderItem(createOrderItemReq);
            ProductService.getInstance().updateQuantity(c.getProductId(), c.getQuantity());
        }

        boolean success = CartItemService.getInstance().deleteCartByUserId(userId);
        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel) session.getAttribute("user");
        user.setTotalCartItem(user.getTotalCartItem() - cartItems.size());
        session.setAttribute("user", user);
        ServletUtils.redirect(response, request.getContextPath() + "/cart/items");
    }
}
