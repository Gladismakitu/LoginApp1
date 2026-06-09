/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest1 {

   
    private Message1 msg1, msg2, msg3, msg4, msg5;

    @BeforeEach
    void setUp() throws Exception {
        resetField("totalMessages",       0);
        resetField("sentMessages",        new ArrayList<>());
        resetField("disregardedMessages", new ArrayList<>());
        resetField("storedMessages",      new ArrayList<>());
        resetField("messageHashes",       new ArrayList<>());
        resetField("messageIDs",          new ArrayList<>());

        msg1 = new Message1(0, "+27834557896", "Did you get the cake?");
        msg2 = new Message1(1, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg3 = new Message1(2, "+27834484567", "Yohoooo, I am at your gate.");
        msg4 = new Message1(3, "0838884567",   "It is dinner time!");
        msg5 = new Message1(4, "+27838884567", "Ok, I am leaving without you.");

        msg1.sentMessage(1); // Sent
        msg2.sentMessage(3); // Stored
        msg3.sentMessage(2); // Disregard
        msg4.sentMessage(1); // Sent
        msg5.sentMessage(3); // Stored
    }

    private void resetField(String name, Object value) throws Exception {
        Field f = Message1.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(null, value);
    }

    // ==================== checkMessageID() ====================

    @Test
    void testMessageID_IsNotNull() {
        assertNotNull(msg1.getMessageID());
    }

    @Test
    void testMessageID_LengthIsAtMost10() {
        assertTrue(msg1.checkMessageID());
    }

    @Test
    void testMessageID_IsNumeric() {
        assertTrue(msg1.getMessageID().matches("\\d+"));
    }

    // ==================== checkRecipientCell() ====================

    @Test
    void testCheckRecipientCell_ValidNumber() {
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCell_MissingCode_Message4() {
       
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. " +
            "Please correct the number and try again.",
            msg4.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCell_EmptyString() {
        Message1 msg = new Message1(0, "", "Test");
        assertFalse(msg.checkRecipientCell().equals("Cell phone number successfully captured."));
    }

    // ==================== checkMessageLength() ====================

    @Test
    void testCheckMessageLength_WithinLimit() {
        assertEquals("Message ready to send.", msg1.checkMessageLength());
    }

    @Test
    void testCheckMessageLength_ExactlyAtLimit() {
        Message1 msg = new Message1(0, "+27831234567", "A".repeat(250));
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    void testCheckMessageLength_ExceedsLimit() {
        Message1 msg = new Message1(0, "+27831234567", "A".repeat(260));
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                msg.checkMessageLength());
    }

    // ==================== createMessageHash() ====================

    @Test
    void testMessageHash_IsUpperCase() {
        String hash = msg1.getMessageHash();
        assertEquals(hash.toUpperCase(), hash);
    }

    @Test
    void testMessageHash_Message1_ContainsFirstAndLastWord() {
        
        String hash = msg1.getMessageHash();
        assertTrue(hash.contains("DID"));
        assertTrue(hash.contains("CAKE"));
    }

    @Test
    void testMessageHash_ContainsMessageNumber() {
        
        assertTrue(msg2.getMessageHash().contains(":1:"));
    }

    // ==================== sentMessage() ====================

    @Test
    void testSentMessage_SendChoice() {
        Message1 msg = new Message1(0, "+27831234567", "Hello");
        assertEquals("Message successfully sent.", msg.sentMessage(1));
    }

    @Test
    void testSentMessage_StoreChoice() {
        Message1 msg = new Message1(0, "+27831234567", "Hello");
        assertEquals("Message successfully stored.", msg.sentMessage(3));
    }

    @Test
    void testSentMessage_DisregardChoice() {
        Message1 msg = new Message1(0, "+27831234567", "Hello");
        assertEquals("Press 0 to delete the message.", msg.sentMessage(2));
    }

    @Test
    void testSentMessage_InvalidChoice() {
        Message1 msg = new Message1(0, "+27831234567", "Hello");
        assertEquals("Invalid option. Please select 1, 2, or 3.", msg.sentMessage(99));
    }

    // ==================== returnTotalMessages() ====================

    @Test
    void testReturnTotalMessages_TwoMessagesSent() {
       
        assertEquals(2, Message1.returnTotalMessages());
    }

    @Test
    void testReturnTotalMessages_StoredNotCounted() {
        
        assertEquals(2, Message1.returnTotalMessages());
    }

    // ==================== Sent Messages array ====================

    @Test
    void testSentMessagesArray_ContainsSentMessages() {
        String result = Message1.getAllSentMessages();
        assertTrue(result.contains("Did you get the cake?"));
        assertTrue(result.contains("It is dinner time!"));
    }

    @Test
    void testSentMessagesArray_DoesNotContainStoredMessages() {
        String result = Message1.getAllSentMessages();
        assertFalse(result.contains("Where are you? You are late!"));
    }

    @Test
    void testSentMessagesArray_Size() {
        assertEquals(2, Message1.getSentMessages().size());
    }

    // ==================== Stored Messages array ====================

    @Test
    void testStoredMessagesArray_Size() {
        assertEquals(2, Message1.getStoredMessages().size());
    }

    // ==================== Disregarded Messages array ====================

    @Test
    void testDisregardedMessagesArray_Size() {
        assertEquals(1, Message1.getDisregardedMessages().size());
    }

    // ==================== Message Hashes & IDs arrays ====================

    @Test
    void testMessageHashesArray_Size() {
        
        assertEquals(4, Message1.getMessageHashes().size());
    }

    @Test
    void testMessageIDsArray_Size() {
        assertEquals(4, Message1.getMessageIDs().size());
    }

    // ==================== PART 3a: getStoredMessagesDetails() ====================

    @Test
    void testGetStoredMessagesDetails_ContainsMsg2() {
        assertTrue(Message1.getStoredMessagesDetails().contains("Where are you? You are late!"));
    }

    @Test
    void testGetStoredMessagesDetails_ContainsMsg5() {
        assertTrue(Message1.getStoredMessagesDetails().contains("Ok, I am leaving without you."));
    }

    @Test
    void testGetStoredMessagesDetails_ContainsRecipient() {
        assertTrue(Message1.getStoredMessagesDetails().contains("+27838884567"));
    }

    // ==================== PART 3b: getLongestMessage() ====================

    @Test
    void testGetLongestMessage_ReturnsMsg2() {
       
        String result = Message1.getLongestMessage();
        assertTrue(result.contains("Where are you? You are late!"));
    }

    @Test
    void testGetLongestMessage_WhenEmpty() throws Exception {
        resetField("sentMessages",   new ArrayList<>());
        resetField("storedMessages", new ArrayList<>());
        assertEquals("No messages found.", Message1.getLongestMessage());
    }

    // ==================== PART 3c: searchByMessageID() ====================

    @Test
    void testSearchByMessageID_FindsSentMessage() {
        String result = Message1.searchByMessageID(msg1.getMessageID());
        assertTrue(result.contains("Did you get the cake?"));
    }

    @Test
    void testSearchByMessageID_FindsStoredMessage() {
        String result = Message1.searchByMessageID(msg2.getMessageID());
        assertTrue(result.contains("Where are you?"));
    }

    @Test
    void testSearchByMessageID_NotFound() {
        assertEquals("Message ID not found.", Message1.searchByMessageID("0000000000"));
    }

    // ==================== PART 3d: searchByRecipient() ====================

    @Test
    void testSearchByRecipient_Found_BothMessages() {
       
        String result = Message1.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late!"));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    void testSearchByRecipient_NotFound() {
        assertEquals("No messages found for this recipient.",
                Message1.searchByRecipient("+27999999999"));
    }

    // ==================== PART 3e: deleteMessageByHash() ====================

    @Test
    void testDeleteMessageByHash_SuccessfulDeletion() {
        String hash = msg1.getMessageHash();
        String result = Message1.deleteMessageByHash(hash);
        assertTrue(result.contains("successfully deleted"));
    }

    @Test
    void testDeleteMessageByHash_MessageRemovedFromList() {
        String hash = msg1.getMessageHash();
        Message1.deleteMessageByHash(hash);
        assertFalse(Message1.getAllSentMessages().contains("Did you get the cake?"));
    }

    @Test
    void testDeleteMessageByHash_NotFound() {
        assertEquals("Message hash not found.", Message1.deleteMessageByHash("FAKEHASH123"));
    }

    // ==================== PART 3f: displayReport() ====================

    @Test
    void testDisplayReport_ContainsMsg1() {
        assertTrue(Message1.displayReport().contains("Did you get the cake?"));
    }

    @Test
    void testDisplayReport_ContainsMsg4() {
        assertTrue(Message1.displayReport().contains("It is dinner time!"));
    }

    @Test
    void testDisplayReport_ContainsHashLabel() {
        assertTrue(Message1.displayReport().contains("Message Hash:"));
    }

    @Test
    void testDisplayReport_ContainsRecipientLabel() {
        assertTrue(Message1.displayReport().contains("Recipient:"));
    }

    @Test
    void testDisplayReport_WhenEmpty() throws Exception {
        resetField("sentMessages", new ArrayList<>());
        assertEquals("No sent messages to report.", Message1.displayReport());
    }
}