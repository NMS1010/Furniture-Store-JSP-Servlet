package models.entities;


import javax.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @Column(name = "productImageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productImageId;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String image;

    @Column(nullable = false)
    private boolean isDefault;
    @Column(nullable = false)
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return productImageId;
    }

    public void setId(int productImageId) {
        this.productImageId = productImageId;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
