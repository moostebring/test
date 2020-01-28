package nl.quintor.app.dto;

import nl.quintor.app.model.RoleModel;

import java.util.Set;

public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private String country;
    private String city;
    private String street;
    private String number;
    private Boolean enabled;
    private Set<RoleModel> roleModels;

    public UserRegistrationDTO(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RoleModel> getRoleModels() {
        return roleModels;
    }

    public void setRoleModels(Set<RoleModel> roleModels) {
        this.roleModels = roleModels;
    }
}
