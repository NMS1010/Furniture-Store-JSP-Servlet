package controllers.admin;

import models.services.order.OrderService;
import models.services.user.UserService;
import models.view_models.orders.OrderGetPagingRequest;
import models.view_models.orders.OrderViewModel;
import models.view_models.users.UserGetPagingRequest;
import models.view_models.users.UserViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet(name = "AdminIndex", value = "/admin/home")
public class AdminIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserGetPagingRequest req1 = new UserGetPagingRequest();

        ArrayList<UserViewModel> users = UserService.getInstance().retrieveAllUser(req1);
        long totalUsers = users.size();
        users.sort((o1, o2) -> (int) (o1.getTotalOrders() - o2.getTotalOrders()));
        ArrayList<UserViewModel> customers = new ArrayList<>();
        for (int i = 0; i< users.size();i++){
            if(i < 10){
                customers.add(users.get(i));
            }
        }
        request.setAttribute("customers", customers);

        ArrayList<OrderViewModel> orders = OrderService.getInstance().retrieveAllOrder(new OrderGetPagingRequest());
        long totalOrders = orders.size();
        BigDecimal totalRevenue = BigDecimal.valueOf(0);
        for(OrderViewModel o: orders){
            totalRevenue = totalRevenue.add(o.getTotalPrice());
        }
        request.setAttribute("totalUsers",totalUsers);
        request.setAttribute("totalOrders",totalOrders);
        request.setAttribute("totalRevenue",totalRevenue);
        request.setAttribute("orders", orders);


        ServletUtils.forward(request,response,"/views/admin/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
