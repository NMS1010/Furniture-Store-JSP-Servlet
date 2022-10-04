package view_models.product_images;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.Part;
import java.util.HashMap;

public class ProductImageUpdateRequest {

    private HashMap<Integer, Part> productImages;

    public HashMap<Integer, Part> getProductImages() {
        return productImages;
    }

    public void setProductImages(HashMap<Integer, Part> productImages) {
        this.productImages = productImages;
    }
}
