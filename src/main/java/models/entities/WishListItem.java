package models.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "wish_list_items",uniqueConstraints =
@UniqueConstraint(columnNames = {"productId","userId"}))
public class WishListItem {
    @Id
    @Column(name = "wishListItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishListItemId;

    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private int productId;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private Date dateAdded;

    public int getWishListItemId() {
        return wishListItemId;
    }

    public void setWishListItemId(int wishListItemId) {
        this.wishListItemId = wishListItemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
