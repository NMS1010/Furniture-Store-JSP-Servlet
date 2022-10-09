package controllers.admin.product;

import models.services.product.ProductService;
import models.services.review_item.ReviewItemService;
import utils.ServletUtils;
import utils.StringUtils;
import view_models.products.ProductViewModel;
import view_models.review_items.ReviewItemViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProductDetails", value = "/admin/product/detail")
public class ProductDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = StringUtils.toInt(request.getParameter("productId"));

        ProductViewModel product = ProductService.getInstance().retrieveById(productId);
        request.setAttribute("product", product);

        ArrayList<ReviewItemViewModel> productReviews = ReviewItemService.getInstance().retrieveByProductId(productId);
        request.setAttribute("productReviews", productReviews);
        int totalRating = productReviews.stream().mapToInt(ReviewItemViewModel::getRating).sum();
        long avgRating = Math.round((totalRating * 1.0)/productReviews.size());

        request.setAttribute("avgRating",avgRating);
        ServletUtils.forward(request, response, "/views/admin/product/product-detail.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
