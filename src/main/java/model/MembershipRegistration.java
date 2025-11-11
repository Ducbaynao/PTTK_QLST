package model;

public class MembershipRegistration {
    private int id;
    private Customer customer;
    private int membershipDurationYears;
    private String membershipLevel;

    public MembershipRegistration() {}

    public MembershipRegistration(Customer customer, int membershipDurationYears, String membershipLevel) {
        this.customer = customer;
        this.membershipDurationYears = membershipDurationYears;
        this.membershipLevel = membershipLevel;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public int getMembershipDurationYears() { return membershipDurationYears; }
    public void setMembershipDurationYears(int membershipDurationYears) { this.membershipDurationYears = membershipDurationYears; }

    public String getMembershipLevel() { return membershipLevel; }
    public void setMembershipLevel(String membershipLevel) { this.membershipLevel = membershipLevel; }
}
