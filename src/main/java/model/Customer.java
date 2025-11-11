package model;

public class Customer extends User {


    public Customer() {
        super();
        setRole("customer");
    }

    public Customer(User u) {
    	super(u);
    }
    
}

