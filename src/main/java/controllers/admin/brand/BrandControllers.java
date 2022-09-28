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

@WebServlet(name = "BrandControllers", value = "/admin/brands")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class BrandControllers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer pageIndex = 1;
        Integer pageSize = 10;
        String keyword = null;
        String columnName = "brandName";
        String typeSort = "ASC";
        BrandGetPagingRequest req = new BrandGetPagingRequest();
        req.setColumnName(columnName);
        req.setKeyword(keyword);
        req.setPageIndex(pageIndex);
        req.setPageSize(pageSize);
        req.setTypeSort(typeSort);
        List<BrandViewModel> brandViewModelList = BrandService.getInstance().retrieveAll(req);
        request.setAttribute("brands", brandViewModelList);
        request.getRequestDispatcher("/views/admin/brand/display-brand.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part filePart = request.getPart("brand-logo");
        BrandCreateRequest brandReq = new BrandCreateRequest();
        brandReq.setBrandName(request.getParameter("brand-name"));
        brandReq.setOrigin(request.getParameter("brand-origin"));
        brandReq.setImage(filePart);

        int brandId = BrandService.getInstance().insert(brandReq);

        doGet(request, response);
    }
}
