package com.project.demo.models;

import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @NotBlank
    @Column(nullable = false, length = 100)
    private String firstName;
//    @NotBlank
    @Column(nullable = false, length = 100)
    private String lastName;
//    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
//    @NotBlank
    private String password;
    private Boolean locked;
    private Boolean enabled;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShippingAddress> shippingAddresses;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName=lastName;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public List<ShippingAddress> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(List<ShippingAddress> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}