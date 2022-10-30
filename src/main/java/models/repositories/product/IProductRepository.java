package models.repositories.product;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.products.ProductCreateRequest;
import models.view_models.products.ProductGetPagingRequest;
import models.view_models.products.ProductUpdateRequest;
import models.view_models.products.ProductViewModel;

import java.math.BigDecimal;

public interface IProductRepository extends IModifyEntity<ProductCreateRequest, ProductUpdateRequest, Integer>,
        IRetrieveEntity<ProductViewModel, ProductGetPagingRequest, Integer> {
    boolean updateQuantity(int productId, int quantity);
}
