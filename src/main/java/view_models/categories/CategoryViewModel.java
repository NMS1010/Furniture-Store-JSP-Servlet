package view_models.categories;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class CategoryViewModel {
    private int categoryId;
    private String name;
    private String description;
    private int parentCategoryId;
    private String parentCategoryName;
    private List<Integer> subCategoryIds;
    
    private List<String> subCategoryNames;
    private String image;
    private long totalProduct;
    private long totalSell;

    public List<Integer> getSubCategoryIds() {
        return subCategoryIds;
    }

    public void setSubCategoryIds(List<Integer> subCategoryIds) {
        this.subCategoryIds = subCategoryIds;
    }

    public List<String> getSubCategoryNames() {
        return subCategoryNames;
    }

    public void setSubCategoryNames(List<String> subCategoryNames) {
        this.subCategoryNames = subCategoryNames;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public long getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(long totalProduct) {
        this.totalProduct = totalProduct;
    }

    public long getTotalSell() {
        return totalSell;
    }

    public void setTotalSell(long totalSell) {
        this.totalSell = totalSell;
    }
}
