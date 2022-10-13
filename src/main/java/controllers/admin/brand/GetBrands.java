package controllers.admin.brand;

import models.entities.Brand;
import models.entities.Category;
import models.services.brand.BrandService;
import utils.ServletUtils;
import view_models.brands.BrandCreateRequest;
import view_models.brands.BrandGetPagingRequest;
import view_models.brands.BrandViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetBrands", value = "/admin/brands")
public class GetBrands extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BrandGetPagingRequest req = new BrandGetPagingRequest();

        req.setTypeSort("brandName");
        ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAll(req);

        request.setAttribute("brands", brands);
        String error = request.getParameter("error");
        if(error != null && !error.equals("")){
            request.setAttribute("error",error);
        }
        ServletUtils.forward(request, response, "/views/admin/brand/brand.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("edit","true");
        doGet(request, response);
    }
}
