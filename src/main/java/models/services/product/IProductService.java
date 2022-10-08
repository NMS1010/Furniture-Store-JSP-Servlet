package models.services.product;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.entities.Product;
import org.hibernate.Session;
import view_models.products.ProductCreateRequest;
import view_models.products.ProductGetPagingRequest;
import view_models.products.ProductUpdateRequest;
import view_models.products.ProductViewModel;

public interface IProductService extends IModifyEntity<ProductCreateRequest, ProductUpdateRequest, Integer>,
        IRetrieveEntity<ProductViewModel, ProductGetPagingRequest, Integer> {

}
