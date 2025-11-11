package model;

public class Staff extends User {
    private String position; // SalesStaff, ManagementStaff, DeliveryStaff, WarehouseStaff...

    public Staff() {
        super();
    }

    // Getters & Setters
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", position='" + position + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}