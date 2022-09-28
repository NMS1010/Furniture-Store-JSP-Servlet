package models.services.product_images;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.product_images.ProductImageCreateRequest;
import view_models.product_images.ProductImageGetPagingRequest;
import view_models.product_images.ProductImageUpdateRequest;
import view_models.product_images.ProductImageViewModel;
import view_models.products.ProductGetPagingRequest;
import view_models.products.ProductViewModel;

public interface IProductImageService extends IModifyEntity<ProductImageCreateRequest, ProductImageUpdateRequest, Integer>, IRetrieveEntity<ProductImageViewModel, ProductImageGetPagingRequest, Integer> {
}
