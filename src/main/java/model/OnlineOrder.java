package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OnlineOrder extends Order {
    private String shippingAddress;
    private String notes;
    private String status;

    // Danh sách chi tiết đơn hàng online
    private List<OnlineOrderDetail> details = new ArrayList<>();

    // Mảng Product[] để tiện hiển thị (nếu bạn muốn)
    private Product[] products;

    // Constructors
    public OnlineOrder() {
        super();
    }

    public OnlineOrder(int id, Date createdAt, Integer createdBy, float total,
                       Customer customer, String paymentMethod,
                       String shippingAddress, String notes, String status) {
        super(id, createdAt, createdBy, total, customer, paymentMethod);
        this.shippingAddress = shippingAddress;
        this.notes = notes;
        this.status = status;
    }

    // Getters and Setters
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<OnlineOrderDetail> getDetails() { return details; }
    public void setDetails(List<OnlineOrderDetail> details) {
        this.details = details != null ? details : new ArrayList<>();
        updateProductsArray();
    }

    public void addDetail(OnlineOrderDetail detail) {
        if (this.details == null) this.details = new ArrayList<>();
        this.details.add(detail);
        updateProductsArray();
    }

    // Tự động tạo mảng Product[] từ danh sách chi tiết
    private void updateProductsArray() {
        if (details == null || details.isEmpty()) {
            this.products = new Product[0];
            return;
        }
        this.products = new Product[details.size()];
        for (int i = 0; i < details.size(); i++) {
            this.products[i] = details.get(i).getProduct();
        }
    }

    public Product[] getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "OnlineOrder{" +
                super.toString() +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", notes='" + notes + '\'' +
                ", status='" + status + '\'' +
                ", detailCount=" + details.size() +
                '}';
    }
}