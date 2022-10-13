package controllers.admin.brand;

import com.google.gson.Gson;
import models.services.brand.BrandService;
import models.view_models.brands.BrandGetPagingRequest;
import models.view_models.brands.BrandViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchAjax", value = "/admin/brands/search")
public class SearchAjax extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String keyword = request.getParameter("keyword");
        BrandGetPagingRequest req = new BrandGetPagingRequest();
        List<String> columName = new ArrayList<String>();
        columName.add("brandName");
        columName.add("origin");
        req.setColumnName(columName);
        req.setKeyword(keyword);

        ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAllBrand(req);
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(brands));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
