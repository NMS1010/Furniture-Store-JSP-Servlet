package models.services.product_images;

import models.repositories.product_image.ProductImageRepository;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.product_images.ProductImageGetPagingRequest;
import models.view_models.product_images.ProductImageUpdateRequest;
import models.view_models.product_images.ProductImageViewModel;

import java.util.ArrayList;

public class ProductImageService implements  IProductImageService{
    private static ProductImageService instance = null;
    public static ProductImageService getInstance(){
        if(instance == null){
            instance = new ProductImageService();
        }
        return instance;
    }
    @Override
    public int insertProductImage(ProductImageCreateRequest request) {
        return ProductImageRepository.getInstance().insert(request);
    }

    @Override
    public boolean updateProductImage(ProductImageUpdateRequest request) {
        return ProductImageRepository.getInstance().update(request);
    }

    @Override
    public boolean deleteProductImage(Integer productImageId) {
        return ProductImageRepository.getInstance().delete(productImageId);
    }
    @Override
    public ProductImageViewModel retrieveProductImageById(Integer productImageId) {
        return ProductImageRepository.getInstance().retrieveById(productImageId);
    }

    @Override
    public ArrayList<ProductImageViewModel> retrieveAllProductImage(ProductImageGetPagingRequest request) {
        return ProductImageRepository.getInstance().retrieveAll(request);
    }
}
