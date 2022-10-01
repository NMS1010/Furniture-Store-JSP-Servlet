package models.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "products")
public class Product{
    @Id
    @Column(name = "productId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private int status;
    @Column
    private int brandId;
    @Column
    private int categoryId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private List<ProductImage> productImages;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private List<OrderItem> orderItems;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private List<CartItem> cartItems;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private List<WishListItem> wishListItems;


    public List<WishListItem> getWishListItems() {
        return wishListItems;
    }

    public void setWishListItems(List<WishListItem> wishListItems) {
        this.wishListItems = wishListItems;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}