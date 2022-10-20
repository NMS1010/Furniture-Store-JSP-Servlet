package controllers.client;

import models.services.category.CategoryService;
import models.services.product.ProductService;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryViewModel;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ClientIndex", value = "/home")
public class ClientIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductGetPagingRequest req1 = new ProductGetPagingRequest();
        ArrayList<ProductViewModel> products = ProductService.getInstance().retrieveAllProduct(req1);

        CategoryGetPagingRequest req2 = new CategoryGetPagingRequest();
        ArrayList<CategoryViewModel> categories = CategoryService.getInstance().retrieveAllCategory(req2);

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        ServletUtils.forward(request,response,"/views/client/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
