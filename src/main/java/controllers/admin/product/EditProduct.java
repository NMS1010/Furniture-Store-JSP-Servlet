package controllers.admin.product;

import models.services.product.ProductService;
import models.services.product_images.ProductImageService;
import utils.ServletUtils;
import utils.StringUtils;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.products.ProductUpdateRequest;
import models.view_models.products.ProductViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EditProduct", value = "/admin/product/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class EditProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = StringUtils.toInt(request.getParameter("productId"));

        ProductViewModel product = ProductService.getInstance().retrieveProductById(productId);

        request.setAttribute("product", product);

        ServletUtils.forward(request, response, "/admin/product/add");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part file = request.getPart("main-image");

        List<Part> subImages = new ArrayList<Part>();
        //int numberSubImage = StringUtils.toInt(request.getParameter("number-sub-image"));
        int minSubImage = StringUtils.toInt(request.getParameter("min-number-sub-image"));
        for(int i=minSubImage; i<= 6; i++){
            Part f = request.getPart("sub-image-" + i);
            subImages.add(f);
        }
        int productId = StringUtils.toInt(request.getParameter("productId"));

        ProductUpdateRequest req = new ProductUpdateRequest();
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        BigDecimal price = StringUtils.toBigDecimal(request.getParameter("price"));
        int quantity = StringUtils.toInt(request.getParameter("quantity"));
        String origin = request.getParameter("origin");
        int categoryId = StringUtils.toInt(request.getParameter("categories"));
        int brandId = StringUtils.toInt(request.getParameter("brands"));
        int status = StringUtils.toInt(request.getParameter("status"));
        req.setProductId(productId);
        req.setDescription(description);
        req.setOrigin(origin);
        req.setPrice(price);
        req.setProductName(productName);
        req.setQuantity(quantity);
        req.setImage(file);
        req.setCategoryId(categoryId);
        req.setBrandId(brandId);
        req.setStatus(status);

        boolean isSuccess = ProductService.getInstance().updateProduct(req);
        if(!isSuccess){
            request.setAttribute("error", "true");
            doGet(request, response);
            return;
        }
        if(subImages.size() > 0) {
            ProductImageCreateRequest productImageCreateRequest = new ProductImageCreateRequest();
            productImageCreateRequest.setProductId(productId);
            productImageCreateRequest.setImages(subImages);

            int id = ProductImageService.getInstance().insertProductImage(productImageCreateRequest);
            if(id < 1){
                request.setAttribute("error", "true");
                doGet(request, response);
                return;
            }
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/products");
    }
}
