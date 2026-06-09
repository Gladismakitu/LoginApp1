/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp1;

import org.junit.Test;
import static org.junit.Assert.*;


public class RegistrationTest1 {

    @Test
    public void testRegisterUser_correctUsername_returnsSuccessMessage() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(
                "Username successfully captured.\n"
              + "Password successfully captured.",
                reg.registerUser());
    }

    @Test
    public void testRegisterUser_incorrectUsername_returnsError() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(
                "Username is not correctly formatted; please ensure"
              + " that your username contains an underscore and is"
              + " no more than 5 characters long.",
                reg.registerUser());
    }

    @Test
    public void testRegisterUser_correctPassword_returnsSuccessMessage() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(
                "Username successfully captured.\n"
              + "Password successfully captured.",
                reg.registerUser());
    }

    @Test
    public void testRegisterUser_incorrectPassword_returnsError() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "password", "+27838968976");
        assertEquals(
                "Password is not correctly formatted; please ensure"
              + " that the password contains at least 8 characters,"
              + " a capital letter, a number and a special character.",
                reg.registerUser());
    }

   
    @Test
    public void testCheckUserName_correct_returnsTrue() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(reg.checkUserName());
    }

    @Test
    public void testCheckUserName_incorrect_returnsFalse() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(reg.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexity_correct_returnsTrue() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(reg.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexity_incorrect_returnsFalse() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "password", "+27838968976");
        assertFalse(reg.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumber_correct_returnsTrue() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(reg.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumber_incorrect_returnsFalse() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(reg.checkCellPhoneNumber());
    }

  
    @Test
    public void testLoginUser_correctCredentials_returnsTrue() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        Login1 login = new Login1("kyl_1", "Ch&&sec@ke99!", reg);
        assertTrue(login.loginUser());
    }

    @Test
    public void testLoginUser_incorrectCredentials_returnsFalse() {
        Registration1 reg = new Registration1("Kyle", "Smith",
                "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        Login1 login = new Login1("wrong", "wrong", reg);
        assertFalse(login.loginUser());
    }
}