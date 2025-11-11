package model;

// 1. Vẫn giữ kế thừa từ Order
public class RevenueStatistics extends Order {

    // 2. XÓA các trường bị trùng lặp ở đây
    //    (Không cần "private Customer customer" hay "private double totalAmount")
    //    Chúng ta sẽ dùng "customer" và "total" kế thừa từ Order.

    public RevenueStatistics() {
        super();
    }

    public RevenueStatistics(Customer customer, double totalAmount) {
        // 3. Dùng setter của lớp cha (Order) để gán giá trị
        //    cho các trường được kế thừa.
        super.setCustomer(customer);
        super.setTotal((float) totalAmount); // 'total' của Order là float
    }

    // 4. Xóa tất cả các getter/setter mà bạn đã viết trong file này
    //    (getCustomer, setCustomer, gettotalAmount, setTotal)
    //    vì chúng ta sẽ dùng các getter/setter của lớp Order.


    @Override
    public String toString() {
        // Dùng getter của lớp cha (Order)
        return "RevenueStatistics{" +
                "customer=" + getCustomer() +
                ", total=" + getTotal() +
                '}';
    }
}