package com.example.ecommerce.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_information")
public class UserInformation {

    @Column(unique = true)
    private String mail;
    private String firstName;
    private String lastName;
    private String middleName;

    private  Boolean isActive;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    public UserInformation(Long id, String mail, String lastName, String firstName, String middleName, boolean b) {
//    }

    public Boolean getActive() {
        return isActive;
    }



    public UserInformation(String mail, String firstName, String lastName, String middleName, Boolean isActive, Long id) {
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.isActive = isActive;
        this.id = id;
    }
    public UserInformation(String mail, String firstName, String lastName, String middleName, Boolean isActive) {
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.isActive=isActive;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInformation that = (UserInformation) o;
        return Objects.equals(mail, that.mail) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(middleName, that.middleName) && Objects.equals(isActive, that.isActive) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, firstName, lastName, middleName, isActive, id);
    }

    public UserInformation(Long id, String mail, String lastName, String firstName, String middleName) {
    }


    public UserInformation() {

    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }
}
