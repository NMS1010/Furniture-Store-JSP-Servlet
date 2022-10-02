package controllers.admin.category;

import models.services.category.CategoryService;
import view_models.categories.CategoryCreateRequest;
import view_models.categories.CategoryGetPagingRequest;
import view_models.categories.CategoryViewModel;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AddCategory", value = "/admin/category/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AddCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part categoryLogo = request.getPart("category-logo");
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");
        String parentCategoryId = request.getParameter("parent-category");

        CategoryCreateRequest req = new CategoryCreateRequest();
        req.setDescription(description);
        req.setName(categoryName);
        req.setImage(categoryLogo);
        if(parentCategoryId != null && !parentCategoryId.equals(""))
            req.setParentCategoryId(Integer.parseInt(parentCategoryId));

        int categoryId = CategoryService.getInstance().insert(req);
        if(parentCategoryId == null || parentCategoryId.equals(""))
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        else
            response.sendRedirect(request.getContextPath() + "/admin/categories?sub-categories=true");
    }
}
