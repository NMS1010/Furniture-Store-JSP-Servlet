package controllers.admin.brand;

import models.services.brand.BrandService;
import view_models.brands.BrandCreateRequest;
import view_models.brands.BrandGetPagingRequest;
import view_models.brands.BrandViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddBrand", value = "/admin/brand/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AddBrand extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part filePart = request.getPart("brand-logo");
        BrandCreateRequest brandReq = new BrandCreateRequest();
        brandReq.setBrandName(request.getParameter("brandName"));
        brandReq.setOrigin(request.getParameter("brandOrigin"));
        brandReq.setImage(filePart);

        int brandId = BrandService.getInstance().insert(brandReq);

        response.sendRedirect(request.getContextPath() + "/admin/brands");
    }
}
