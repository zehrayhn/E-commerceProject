package com.example.ecommerce.dto;

import java.util.Objects;

public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private  String mail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserRequest request = (CreateUserRequest) o;
        return Objects.equals(firstName, request.firstName) && Objects.equals(lastName, request.lastName) && Objects.equals(middleName, request.middleName) && Objects.equals(mail, request.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, middleName, mail);
    }

    public CreateUserRequest(String firstName, String lastName, String middleName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public CreateUserRequest() {
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
