package model;

import java.util.Date;
import java.util.List;

public class SalesInvoice extends Order {
    private SalesStaff staff;                    // Nhân viên bán hàng
    private List<SalesInvoiceDetail> details;    // Chi tiết hóa đơn

    public SalesInvoice() {
        super();
    }

    public SalesInvoice(int id, Date createdAt, Integer createdBy, float total,
                        Customer customer, String paymentMethod, SalesStaff staff) {
        super(id, createdAt, createdBy, total, customer, paymentMethod);
        this.staff = staff;
    }

    public SalesStaff getStaff() { return staff; }
    public void setStaff(SalesStaff staff) { this.staff = staff; }

    public List<SalesInvoiceDetail> getDetails() { return details; }
    public void setDetails(List<SalesInvoiceDetail> details) { this.details = details; }

    @Override
    public String toString() {
        return "SalesInvoice{" +
                super.toString() +
                ", salesStaff=" + (staff != null ? staff.getName() + " (" + staff.getPosition() + ")" : "null") +
                ", detailCount=" + (details != null ? details.size() : 0) +
                '}';
    }
}