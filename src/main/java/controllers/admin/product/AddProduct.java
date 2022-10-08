package controllers.admin.product;

import models.services.brand.BrandService;
import models.services.category.CategoryService;
import models.services.product.ProductService;
import models.services.product_images.ProductImageService;
import utils.DateUtils;
import utils.ServletUtils;
import utils.StringUtils;
import utils.constants.PRODUCT_STATUS;
import view_models.brands.BrandGetPagingRequest;
import view_models.brands.BrandViewModel;
import view_models.categories.CategoryGetPagingRequest;
import view_models.categories.CategoryViewModel;
import view_models.product_images.ProductImageCreateRequest;
import view_models.products.ProductCreateRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddProduct", value = "/admin/product/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AddProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryGetPagingRequest req1 = new CategoryGetPagingRequest();

        ArrayList<CategoryViewModel> categories = CategoryService.getInstance().retrieveAll(req1);
        BrandGetPagingRequest req2 = new BrandGetPagingRequest();

        ArrayList<BrandViewModel> brands = BrandService.getInstance().retrieveAll(req2);
        request.setAttribute("categories",categories);
        request.setAttribute("brands",brands);
        ServletUtils.forward(request, response, "/views/admin/product/add-product.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part file = request.getPart("main-image");

        List<Part> subImages = new ArrayList<Part>();
        int numberSubImage = StringUtils.toInt(request.getParameter("number-sub-image"));
        for(int i=1; i<= numberSubImage; i++){
            Part f = request.getPart("sub-image-" + i);
            subImages.add(f);
        }

        ProductCreateRequest req = new ProductCreateRequest();
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        int quantity = StringUtils.toInt(request.getParameter("quantity"));

        int categoryId = StringUtils.toInt(request.getParameter("categories"));
        String origin = request.getParameter("origin");
        int brandId = StringUtils.toInt(request.getParameter("brands"));
        int status = StringUtils.toInt(request.getParameter("status"));

        req.setDescription(description);
        req.setOrigin(origin);
        req.setPrice(StringUtils.toBigDecimal(price));
        req.setProductName(productName);
        req.setQuantity(quantity);
        req.setImage(file);
        req.setCategoryId(categoryId);
        req.setBrandId(brandId);
        req.setStatus(status);
        int productId = ProductService.getInstance().insert(req);

        ProductImageCreateRequest productImageCreateRequest = new ProductImageCreateRequest();
        productImageCreateRequest.setProductId(productId);

        productImageCreateRequest.setImages(subImages);

        ProductImageService.getInstance().insert(productImageCreateRequest);

        ServletUtils.redirect(response, request.getContextPath() + "/admin/products");

    }
}
