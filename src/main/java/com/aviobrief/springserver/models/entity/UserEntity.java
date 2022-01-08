package com.aviobrief.springserver.models.entity;


import javax.persistence.*;

@Entity
@Table(name = "users")
@Access(AccessType.PROPERTY)
public class UserEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public UserEntity() {
    }

    @Column(name = "first_name", unique = false, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(name = "last_name", unique = false, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(name = "password", unique = false, nullable = false)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Column(name = "email", unique = true, nullable = true)//todo - email must not be empty, set like this for dev process
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
