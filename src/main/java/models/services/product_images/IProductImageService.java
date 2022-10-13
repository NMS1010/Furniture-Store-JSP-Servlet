package models.services.product_images;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.product_images.ProductImageGetPagingRequest;
import models.view_models.product_images.ProductImageUpdateRequest;
import models.view_models.product_images.ProductImageViewModel;

import java.util.ArrayList;

public interface IProductImageService {
    int insertProductImage(ProductImageCreateRequest request);
    boolean updateProductImage(ProductImageUpdateRequest request);
    boolean deleteProductImage(Integer productImageId);
    ProductImageViewModel retrieveProductImageById(Integer productImageId);
    ArrayList<ProductImageViewModel> retrieveAllProductImage(ProductImageGetPagingRequest request);
}
