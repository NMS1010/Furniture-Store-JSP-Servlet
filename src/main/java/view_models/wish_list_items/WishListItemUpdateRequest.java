package view_models.wish_list_items;

public class WishListItemUpdateRequest {
    private int wishListItemId;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getWishListItemId() {
        return wishListItemId;
    }

    public void setWishListItemId(int wishListItemId) {
        this.wishListItemId = wishListItemId;
    }
}
