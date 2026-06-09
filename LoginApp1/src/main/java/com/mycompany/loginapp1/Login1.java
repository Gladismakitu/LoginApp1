/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp1;

/**
 *
 * @author Student
 */
public class Login1 {

    private String        enteredUsername;
    private String        enteredPassword;
    private Registration1 registeredUser;

    public Login1(String enteredUsername, String enteredPassword,
                 Registration1 registeredUser) {
        this.enteredUsername = enteredUsername;
        this.enteredPassword = enteredPassword;
        this.registeredUser  = registeredUser;
    }

    public boolean loginUser() {
        boolean usernameMatch = enteredUsername.equals(registeredUser.getUsername());
        boolean passwordMatch = enteredPassword.equals(registeredUser.getPassword());
        return usernameMatch && passwordMatch;
    }

    public String returnLoginStatus() {
        if (loginUser()) {
            return "Welcome " + registeredUser.getFirstName() + " " +
                   registeredUser.getLastName() + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}