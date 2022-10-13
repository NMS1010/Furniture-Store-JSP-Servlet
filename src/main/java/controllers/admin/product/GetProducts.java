package controllers.admin.product;

import models.services.brand.BrandService;
import models.services.category.CategoryService;
import models.services.product.ProductService;
import utils.HibernateUtils;
import utils.ServletUtils;
import utils.StringUtils;
import utils.constants.PAGING_PARAM;
import view_models.brands.BrandGetPagingRequest;
import view_models.brands.BrandViewModel;
import view_models.categories.CategoryGetPagingRequest;
import view_models.categories.CategoryViewModel;
import view_models.products.ProductGetPagingRequest;
import view_models.products.ProductViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetProducts", value = "/admin/products")
public class GetProducts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductGetPagingRequest req = new ProductGetPagingRequest();
        int pageIndex = StringUtils.toInt(request.getParameter("pageIndex"));
        int pageSize = StringUtils.toInt(request.getParameter("pageSize"));
        if(pageIndex > 0){
            req.setPageIndex(pageIndex);
            if(pageSize > 0)
                req.setPageSize(pageSize);
        }
        long totalPage = (long)Math.ceil((HibernateUtils.count("Product","") *1.0 / (pageSize > 0 ? req.getPageSize() : PAGING_PARAM.PAGE_SIZE)));
        request.setAttribute("totalPage",totalPage);
        request.setAttribute("pageIndex",req.getPageIndex());
        ArrayList<ProductViewModel> products = ProductService.getInstance().retrieveAll(req);

        request.setAttribute("products", products);
        String error = request.getParameter("error");
        if(error != null && !error.equals("")){
            request.setAttribute("error",error);
        }
        String grid = request.getParameter("grid");

        if(grid == null || grid.equals(""))
            ServletUtils.forward(request, response, "/views/admin/product/list-product.jsp");
        else {

            BrandGetPagingRequest req2 = new BrandGetPagingRequest();

            ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAll(req2);
            CategoryGetPagingRequest req1 = new CategoryGetPagingRequest();

            ArrayList<CategoryViewModel> categories = CategoryService.getInstance().retrieveAll(req1);
            request.setAttribute("categories",categories);
            request.setAttribute("brands",brands);

            ServletUtils.forward(request, response, "/views/admin/product/grid-product.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
