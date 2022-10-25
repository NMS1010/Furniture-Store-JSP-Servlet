package controllers.client.wish_list;

import models.services.wish_item.WishItemService;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RemoveWish", value = "/remove-wish")
public class RemoveWish extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int wishItemId = StringUtils.toInt(request.getParameter("wishItemId"));
        boolean success = WishItemService.getInstance().deleteWishItem(wishItemId);
        PrintWriter out = response.getWriter();
        if(!success){
            out.println("error");
        }else{
            HttpSession session = request.getSession();
            UserViewModel user = (UserViewModel) session.getAttribute("user");
            if(user == null)
                return;
            user.setTotalWishListItem(user.getTotalWishListItem() - 1);
            session.setAttribute("user", user);
            //ServletUtils.redirect(response, request.getContextPath() + "/wish-list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
