package controllers.admin.category;

import models.services.category.CategoryService;
import utils.CATEGORY_STATUS;
import view_models.categories.CategoryCreateRequest;
import view_models.categories.CategoryGetPagingRequest;
import view_models.categories.CategoryViewModel;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CategoryControllers", value = "/admin/categories")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class CategoryControllers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryGetPagingRequest req = new CategoryGetPagingRequest();
        req.setColumnName("categoryName");
        ArrayList<CategoryViewModel> categories = CategoryService.getInstance().retrieveAll(req);
        request.setAttribute("categories",categories);

        request.getRequestDispatcher("/views/admin/category/main-category.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part categoryLogo = request.getPart("category-logo");
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");


        CategoryCreateRequest req = new CategoryCreateRequest();
        req.setDescription(description);
        req.setName(categoryName);
        req.setImage(categoryLogo);
        req.setStatus(CATEGORY_STATUS.ACTIVE);

        int categoryId = CategoryService.getInstance().insert(req);

        doGet(request,response);
    }
}
