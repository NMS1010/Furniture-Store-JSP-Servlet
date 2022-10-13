package models.repositories.product_image;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.product_images.ProductImageGetPagingRequest;
import models.view_models.product_images.ProductImageUpdateRequest;
import models.view_models.product_images.ProductImageViewModel;

public interface IProductImageRepository  extends IModifyEntity<ProductImageCreateRequest, ProductImageUpdateRequest, Integer>,
        IRetrieveEntity<ProductImageViewModel, ProductImageGetPagingRequest, Integer> {
}
