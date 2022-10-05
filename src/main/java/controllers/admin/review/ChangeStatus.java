package controllers.admin.review;

import models.services.review.ReviewService;
import utils.ServletUtils;
import utils.StringUtils;
import view_models.reviews.ReviewUpdateRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangeStatus", value = "/admin/review/editStatus")
public class ChangeStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewId = request.getParameter("reviewId");

        ReviewService.getInstance().ChangeStatus(StringUtils.toInt(reviewId));

        ServletUtils.redirect(response, request.getContextPath() + "/admin/reviews");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
