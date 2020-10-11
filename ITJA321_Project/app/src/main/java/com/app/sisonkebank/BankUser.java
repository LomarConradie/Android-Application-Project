// Author: Lomar Ungerer Conradie | DQ61ZP3G5 | ITJA321 | Project
package com.app.sisonkebank;

public class BankUser {

    private String name, surname, email, password, gender;
    private Integer mobile, balance, savings, id;

    /**
     * This is the BankUser class that implements constructors and getters and setter methods to handle the transfer of data between the SQLite database
     * and the Login/Registration classes.
     * @param id - This is the ID of the user that uniquely identifies them in the SQLite database
     * @param name - This is the name of the user
     * @param surname - This is the surname of the user
     * @param email - This is the users' email
     * @param password - This is the password used to log into the application
     * @param mobile - This is the users' mobile number
     * @param gender - This is the gender of the user
     * @param balance - This is the balance the user has in their account. The Registration class assigns R2500 to this variable for testing purposes
     * @param savings - This is the savings balance the user has in their account. The Registration class assigns R0 to this variable for testing purposes
     * Following below is the getter and setter methods for each variable and the constructor and toString methods.
     */
    public BankUser(Integer id, String name, String surname, String email, String password, Integer mobile, String gender, Integer balance, Integer savings) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.gender = gender;
        this.balance = balance;
        this.savings = savings;
    }

    //toString method
    @Override
    public String toString() {
        return "BankUser{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", mobile=" + mobile +
                ", balance=" + balance +
                ", savings=" + savings +
                ", id=" + id +
                '}';
    }

    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getSavings() {
        return savings;
    }

    public void setSavings(Integer savings) {
        this.savings = savings;
    }
}
