package models.services.product;

import models.entities.Product;
import models.entities.ProductImage;
import models.repositories.product.ProductRepository;
import models.services.product_images.ProductImageService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.DateUtils;
import utils.FileUtil;
import utils.HibernateUtils;
import utils.HtmlClassUtils;
import utils.constants.PRODUCT_STATUS;
import models.view_models.product_images.ProductImageViewModel;
import models.view_models.products.ProductCreateRequest;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductUpdateRequest;
import models.view_models.products.ProductViewModel;

import java.util.*;

public class ProductService implements IProductService {
    private static ProductService instance = null;
    public static ProductService getInstance() {
        if(instance == null)
            instance = new ProductService();
        return instance;
    }
    @Override
    public int insertProduct(ProductCreateRequest request) {
        return ProductRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateProduct(ProductUpdateRequest request) {
        return ProductRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteProduct(Integer productId) {
        return ProductRepository.getInstance().delete(productId);
    }

    @Override
    public ProductViewModel retrieveProductById(Integer productId) {
        return ProductRepository.getInstance().retrieveById(productId);
    }

    @Override
    public ArrayList<ProductViewModel> retrieveAllProduct(ProductGetPagingRequest request) {
        return ProductRepository.getInstance().retrieveAll(request);
    }
}
