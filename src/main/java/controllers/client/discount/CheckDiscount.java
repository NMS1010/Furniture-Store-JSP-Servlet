package controllers.client.discount;

import com.google.gson.Gson;
import models.services.discount.DiscountService;
import models.view_models.discounts.DiscountViewModel;
import utils.DateUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CheckDiscount", value = "/discount/apply")
public class CheckDiscount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String discountCode = request.getParameter("discountCode");
        DiscountViewModel discount = DiscountService.getInstance().getDiscountByDiscountCode(discountCode);
        if(discount == null)
            out.println("error");
        else if (DateUtils.stringToLocalDateTime(discount.getStartDate()).isAfter(DateUtils.dateTimeNow()) ||
                DateUtils.stringToLocalDateTime(discount.getEndDate()).isBefore(DateUtils.dateTimeNow())){
            out.println("expired");
        }
        else
            DiscountService.getInstance().updateQuantity(discount.getDiscountId());
            out.println(new Gson().toJson(discount));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
