package model;


public class RevenueStatistics extends Order {



    public RevenueStatistics() {
        super();
    }

    public RevenueStatistics(Customer customer, double totalAmount) {

        super.setCustomer(customer);
        super.setTotal((float) totalAmount); 
    }




    @Override
    public String toString() {
    
        return "RevenueStatistics{" +
                "customer=" + getCustomer() +
                ", total=" + getTotal() +
                '}';
    }
}
