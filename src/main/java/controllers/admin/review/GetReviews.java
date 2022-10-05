package controllers.admin.review;

import models.services.review.ReviewService;
import utils.ServletUtils;
import view_models.reviews.ReviewGetPagingRequest;
import view_models.reviews.ReviewViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetReviews", value = "/admin/reviews")
public class GetReviews extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReviewGetPagingRequest req = new ReviewGetPagingRequest();

        ArrayList<ReviewViewModel> reviews = ReviewService.getInstance().retrieveAll(req);

        request.setAttribute("reviews",reviews);
        ServletUtils.forward(request, response, "/views/admin/review/review.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
