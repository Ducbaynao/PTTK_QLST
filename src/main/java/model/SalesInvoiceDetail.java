package model;

public class SalesInvoiceDetail {
    private int id;
    private int quantity;
    private double price;
    private Product product;

    // Constructors
    public SalesInvoiceDetail() {}

    public SalesInvoiceDetail(int quantity, double price, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public double getLineTotal() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return "SalesInvoiceDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", product=" + (product != null ? product.getName() : "null") +
                '}';
    }
}