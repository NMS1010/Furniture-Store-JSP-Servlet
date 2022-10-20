package controllers.admin.product;

import models.services.product.ProductService;
import models.services.review_item.ReviewItemService;
import utils.ServletUtils;
import utils.StringUtils;
import models.view_models.products.ProductViewModel;
import models.view_models.review_items.ReviewItemViewModel;

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

        ProductViewModel product = ProductService.getInstance().retrieveProductById(productId);
        request.setAttribute("product", product);

        ServletUtils.forward(request, response, "/views/admin/product/product-detail.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
