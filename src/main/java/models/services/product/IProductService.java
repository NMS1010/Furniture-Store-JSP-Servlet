package models.services.product;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.products.ProductCreateRequest;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductUpdateRequest;
import models.view_models.products.ProductViewModel;

import java.util.ArrayList;

public interface IProductService {
    int insertProduct(ProductCreateRequest request);
    boolean updateProduct(ProductUpdateRequest request);
    boolean deleteProduct(Integer productId);
    ProductViewModel retrieveProductById(Integer productId);
    ArrayList<ProductViewModel> retrieveAllProduct(ProductGetPagingRequest request);
    boolean updateQuantity(int productId, int quantity);
}
