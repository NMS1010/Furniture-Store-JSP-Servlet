package controllers.client.wish_list;

import models.services.brand.BrandService;
import models.services.wish_item.WishItemService;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandViewModel;
import models.view_models.users.UserViewModel;
import models.view_models.wish_items.WishItemGetPagingRequest;
import models.view_models.wish_items.WishItemViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetWishList", value = "/wish-list")
public class GetWishList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserViewModel user = (UserViewModel) session.getAttribute("user");
        if(user == null)
            return;
        int userId = user.getId();
        ArrayList<WishItemViewModel> wishItems = WishItemService.getInstance().retrieveWishListByUserId(userId);
        request.setAttribute("wishItems", wishItems);

        ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAllBrand(new BrandGetPagingRequest());
        request.setAttribute("brands", brands);

        ServletUtils.forward(request,response,"/views/client/wishlist.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}