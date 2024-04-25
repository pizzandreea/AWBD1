package com.project.demo.dtos.user;


import com.project.demo.models.User;

public class UserResponseDto {
    private Integer Id;
    private String firstName;
    private String lastName;
    private String email;

    public UserResponseDto fromUser(User user){
        if (user != null) {
            setId(user.getId());
            setEmail(user.getEmail());
            setFirstName(user.getFirstName());
            setLastName(user.getLastName());
        }
        return this;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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
}
