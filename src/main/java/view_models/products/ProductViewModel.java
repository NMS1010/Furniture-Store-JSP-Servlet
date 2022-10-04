package view_models.products;

import org.apache.commons.fileupload.FileItem;
import view_models.product_images.ProductImageViewModel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public class ProductViewModel {
    private int productId;
    private String name;
    private String description;

    private BigDecimal price;

    private int quantity;

    private int status;

    private String origin;

    private Date dateCreated;

    private String image;
    private int categoryId;
    private int brandId;
    private String categoryName;
    private String brandName;

    private long totalPurchased;

    private List<ProductImageViewModel> productImages;

    public long getTotalPurchased() {
        return totalPurchased;
    }

    public void setTotalPurchased(long totalPurchased) {
        this.totalPurchased = totalPurchased;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public List<ProductImageViewModel> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImageViewModel> productImages) {
        this.productImages = productImages;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
