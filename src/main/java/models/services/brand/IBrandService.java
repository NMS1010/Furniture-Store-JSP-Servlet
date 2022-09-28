package models.services.brand;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.brands.BrandCreateRequest;
import view_models.brands.BrandGetPagingRequest;
import view_models.brands.BrandUpdateRequest;
import view_models.brands.BrandViewModel;

public interface IBrandService extends IModifyEntity<BrandCreateRequest, BrandUpdateRequest, Integer>,
        IRetrieveEntity<BrandViewModel, BrandGetPagingRequest, Integer> {
}
