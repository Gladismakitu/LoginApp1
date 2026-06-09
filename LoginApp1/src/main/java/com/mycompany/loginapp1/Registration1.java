/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp1;

/**
 *
 * @author Student
 */
public class Registration1 {

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    public Registration1(String username, String password,
                        String cellPhoneNumber,
                        String firstName, String lastName) {
        this.username        = username;
        this.password        = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName       = firstName;
        this.lastName        = lastName;
    }

    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        boolean length  = password.length() >= 8;
        boolean capital = !password.equals(password.toLowerCase());
        boolean number  = password.matches(".*\\d.*");
        boolean special = !password.matches("[A-Za-z0-9]*");
        return length && capital && number && special;
    }

    public boolean checkCellPhoneNumber() {
        return cellPhoneNumber.matches("\\+27\\d{9}");
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username " +
                   "contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password " +
                   "contains at least eight characters, a capital letter, a number, and a special character.";
        }
        return "Welcome " + firstName + " ," + lastName + " it is great to see you.";
    }

    public String getUsername()   { return username; }
    public String getPassword()   { return password; }
    public String getFirstName()  { return firstName; }
    public String getLastName()   { return lastName; }
}