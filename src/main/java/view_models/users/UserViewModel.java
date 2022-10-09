package view_models.users;

import view_models.roles.RoleViewModel;
import view_models.user_roles.UserRoleViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserViewModel {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private int gender;
    private String genderCode;
    private String phone;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private int status;
    private String statusCode;
    private String username;
    private String password;
    private String email;
    private LocalDateTime lastLogin;
    private String avatar;

    private long totalBought;
    private long totalWishListItem;
    private ArrayList<UserRoleViewModel> roles;
    private ArrayList<Integer> roleIds;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(ArrayList<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public ArrayList<UserRoleViewModel> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<UserRoleViewModel> roles) {
        this.roles = roles;
    }

    public long getTotalWishListItem() {
        return totalWishListItem;
    }

    public void setTotalWishListItem(long totalWishListItem) {
        this.totalWishListItem = totalWishListItem;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public long getTotalBought() {
        return totalBought;
    }

    public void setTotalBought(long totalBought) {
        this.totalBought = totalBought;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
