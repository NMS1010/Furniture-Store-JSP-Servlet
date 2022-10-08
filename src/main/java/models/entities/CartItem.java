package models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items",uniqueConstraints =
@UniqueConstraint(columnNames = {"productId","userId"}))
public class CartItem {
    @Id
    @Column(name = "cartItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime dateAdded;

    @Column(nullable = false)
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }
}
