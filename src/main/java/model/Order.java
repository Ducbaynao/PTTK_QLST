package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private Date createdAt;
    private Integer createdBy; // có thể null nếu là đơn online
    private float total;
    private Customer customer;
    private String paymentMethod;


    // Constructors
    public Order() {
        // Không khởi tạo details ở đây
    }

    public Order(int id, Date createdAt, Integer createdBy, float total,
                 Customer customer, String paymentMethod) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.total = total;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        // Không khởi tạo details
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }



}