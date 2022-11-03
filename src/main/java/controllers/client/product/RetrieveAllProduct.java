package controllers.client.product;

import models.services.brand.BrandService;
import models.services.category.CategoryService;
import models.services.product.ProductService;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandViewModel;
import models.view_models.categories.CategoryGetPagingRequest;
import models.view_models.categories.CategoryViewModel;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductViewModel;
import utils.ServletUtils;
import utils.StringUtils;
import utils.constants.SORT_BY;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@WebServlet(name = "RetrieveAllProduct", value = "/products")
public class RetrieveAllProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        BrandGetPagingRequest req = new BrandGetPagingRequest();
        ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAllBrand(req);

        CategoryGetPagingRequest req2 = new CategoryGetPagingRequest();
        ArrayList<CategoryViewModel> categories = CategoryService.getInstance().retrieveAllCategory(req2);
        categories.removeIf(x -> x.getParentCategoryId() != 0);

        ProductGetPagingRequest req1 = new ProductGetPagingRequest();

        String keyword = request.getParameter("keyword");
        int categoryId = StringUtils.toInt(request.getParameter("categoryId"));
        int brandId = StringUtils.toInt(request.getParameter("brandId"));
        BigDecimal minPrice = StringUtils.toBigDecimal(request.getParameter("filter.v.price.gte"));
        BigDecimal maxPrice = StringUtils.toBigDecimal(request.getParameter("filter.v.price.lte"));
        String sortBy = request.getParameter("sortBy");
        if(keyword != null) {
            req1.setKeyword(keyword);
            ArrayList<String> columns = new ArrayList<>();
            columns.add("name");
            req1.setColumnName(columns);
        }
        if(categoryId != 0){
            req1.setCondition("categoryId = " + categoryId);
        }
        if(brandId != 0){
            req1.setCondition("brandId = " + brandId);
        }
        if(minPrice.compareTo(BigDecimal.valueOf(0)) != 0  && maxPrice.compareTo(BigDecimal.valueOf(0)) != 0){
            req1.setCondition("price >= " + minPrice + " and " + "price <= " + maxPrice);
        }
        if(sortBy != null){
            int s = StringUtils.toInt(sortBy);
            if(s == SORT_BY.BY_NAME_AZ){
                req1.setSortBy("name");
                req1.setTypeSort("ASC");
            }
            else if (s == SORT_BY.BY_NAME_ZA){
                req1.setSortBy("name");
                req1.setTypeSort("DESC");
            }
            else if (s == SORT_BY.BY_PRICE_AZ){
                req1.setSortBy("price");
                req1.setTypeSort("ASC");
            }
            else if(s == SORT_BY.BY_PRICE_ZA){
                req1.setSortBy("price");
                req1.setTypeSort("DESC");

            }
            request.setAttribute("sortBy", s);
        }
        ArrayList<ProductViewModel> products = ProductService.getInstance().retrieveAllProduct(req1);
        request.setAttribute("products", products);
        request.setAttribute("brands", brands);
        request.setAttribute("categories", categories);
        ServletUtils.forward(request,response,"/views/client/shop.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
