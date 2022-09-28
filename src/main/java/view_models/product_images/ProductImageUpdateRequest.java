package view_models.product_images;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.Part;

public class ProductImageUpdateRequest {
    public int productImageId;

    public Part image;

    public int getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(int productImageId) {
        this.productImageId = productImageId;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }
}
