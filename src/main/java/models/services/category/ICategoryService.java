package models.services.category;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.categories.CategoryCreateRequest;
import view_models.categories.CategoryGetPagingRequest;
import view_models.categories.CategoryUpdateRequest;
import view_models.categories.CategoryViewModel;

public interface ICategoryService extends IModifyEntity<CategoryCreateRequest, CategoryUpdateRequest, Integer>,
        IRetrieveEntity<CategoryViewModel, CategoryGetPagingRequest, Integer> {
}
