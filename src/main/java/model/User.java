package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public User() {}

    public User(User u) {
        this.id = u.id;
        this.username = u.username;
        this.password = u.password;
        this.role = u.role;
        this.name = u.name;
        this.email = u.email;
        this.phoneNumber = u.phoneNumber;
        this.address = u.address;
    }
    
    public User(int id, String username, String password, String role, String name, String email, String phoneNumber, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
