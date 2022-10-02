package models.services.category;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.categories.CategoryCreateRequest;
import view_models.categories.CategoryGetPagingRequest;
import view_models.categories.CategoryUpdateRequest;
import view_models.categories.CategoryViewModel;

import java.util.HashMap;

public interface ICategoryService extends IModifyEntity<CategoryCreateRequest, CategoryUpdateRequest, Integer>,
        IRetrieveEntity<CategoryViewModel, CategoryGetPagingRequest, Integer> {
    HashMap<Integer, String> getParentCategory();
}
