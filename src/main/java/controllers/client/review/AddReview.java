package controllers.client.review;

import models.services.review_item.ReviewItemService;
import models.view_models.review_items.ReviewItemCreateRequest;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddReview", value = "/my-account/order/review/add")
public class AddReview extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int productId = StringUtils.toInt(request.getParameter("productId"));
        int userId = SessionUtils.getUserIdLogin(request);
        ReviewItemCreateRequest createReq = new ReviewItemCreateRequest();
        int reviewId = ReviewItemService.getInstance().getReviewIdByUserId(userId);

        createReq.setReviewId(reviewId);
        createReq.setRating(StringUtils.toInt(request.getParameter("rating")));
        createReq.setContent(request.getParameter("content"));
        createReq.setProductId(productId);
        createReq.setStatus(1);

        int reviewItemId = ReviewItemService.getInstance().insertReviewItem(createReq);

        ServletUtils.redirect(response, request.getContextPath() + "/my-account/order/reviews?productId=" + productId);
    }
}
