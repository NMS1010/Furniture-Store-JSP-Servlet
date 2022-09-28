package models.services.discount;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import view_models.discounts.DiscountCreateRequest;
import view_models.discounts.DiscountGetPagingRequest;
import view_models.discounts.DiscountUpdateRequest;
import view_models.discounts.DiscountViewModel;

public interface IDiscountService extends IModifyEntity<DiscountCreateRequest, DiscountUpdateRequest, Integer>,
        IRetrieveEntity<DiscountViewModel, DiscountGetPagingRequest, Integer> {
}
