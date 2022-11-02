package controllers.client.cart;

import models.services.cart_item.CartItemService;
import models.services.product.ProductService;
import models.view_models.cart_items.CartItemCreateRequest;
import models.view_models.cart_items.CartItemUpdateRequest;
import models.view_models.cart_items.CartItemViewModel;
import models.view_models.products.ProductViewModel;
import models.view_models.users.UserViewModel;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddItemToCart", value = "/cart/add")
public class AddItemToCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        int productId = StringUtils.toInt(request.getParameter("productId"));
        int quantity = StringUtils.toInt(request.getParameter("quantity"));
        if(quantity == 0)
            quantity = 1;
        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel) session.getAttribute("user");
        if(user == null)
            return;
        int userId = user.getId();
        int cartId = CartItemService.getInstance().getCartIdByUserId(userId);

        int count = -1;
        CartItemViewModel cartItem = CartItemService.getInstance().getCartItemContain(cartId, productId);
        ProductViewModel product = ProductService.getInstance().retrieveProductById(productId);
        if(product.getQuantity() > 0) {
            if (cartItem != null) {
                CartItemUpdateRequest updateReq = new CartItemUpdateRequest();
                updateReq.setCartItemId(cartItem.getCartItemId());
                updateReq.setQuantity(cartItem.getQuantity() + 1);
                updateReq.setStatus(cartItem.getStatus());
                count = CartItemService.getInstance().updateCartItem(updateReq) ? 1 : 0;
                out.println("repeat");
            } else {
                CartItemCreateRequest createReq = new CartItemCreateRequest();
                createReq.setCartId(cartId);
                createReq.setProductId(productId);
                createReq.setQuantity(quantity);
                createReq.setStatus(1);

                count = CartItemService.getInstance().insertCartItem(createReq);
                if (count > 0) {
                    user.setTotalCartItem(user.getTotalCartItem() + 1);
                    session.setAttribute("user", user);
                    out.println(user.getTotalCartItem() + "success");
                }
            }
        }else{
            out.println("expired");
        }

        if(count <= 0){
            out.println("error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
