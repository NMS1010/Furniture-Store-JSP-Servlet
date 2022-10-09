package controllers.admin.review;

import models.services.review_item.ReviewItemService;
import utils.ServletUtils;
import view_models.review_items.ReviewItemGetPagingRequest;
import view_models.review_items.ReviewItemViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetReviews", value = "/admin/reviews")
public class GetReviews extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReviewItemGetPagingRequest req = new ReviewItemGetPagingRequest();

        ArrayList<ReviewItemViewModel> reviews = ReviewItemService.getInstance().retrieveAll(req);

        request.setAttribute("reviews",reviews);
        ServletUtils.forward(request, response, "/views/admin/review/review.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
