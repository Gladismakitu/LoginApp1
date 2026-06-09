/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Message1 {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    private static int totalMessages = 0;
    private static ArrayList<Message1> sentMessages      = new ArrayList<>();
    private static ArrayList<Message1> disregardedMessages = new ArrayList<>();
    private static ArrayList<Message1> storedMessages    = new ArrayList<>();
    private static ArrayList<String>   messageHashes     = new ArrayList<>();
    private static ArrayList<String>   messageIDs        = new ArrayList<>();

    public Message1(int messageNumber, String recipient, String message) {
        this.messageID     = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.message       = message;
        this.messageHash   = createMessageHash();
    }

    // ==================== CORE METHODS ====================

    private String generateMessageID() {
        Random rand = new Random();
        long id = (long)(rand.nextDouble() * 9000000000L) + 1000000000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.matches("^\\+[0-9]{1,3}[0-9]{1,10}$") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. " +
               "Please correct the number and try again.";
    }

    public String createMessageHash() {
        String firstTwo  = messageID.substring(0, 2);
        String[] words   = message.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord  = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int over = message.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

    public String sentMessage(int choice) {
        if (choice == 1) {
            totalMessages++;
            sentMessages.add(this);
            messageHashes.add(this.messageHash);
            messageIDs.add(this.messageID);
            return "Message successfully sent.";
        } else if (choice == 2) {
            disregardedMessages.add(this);
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storedMessages.add(this);
            messageHashes.add(this.messageHash);
            messageIDs.add(this.messageID);
            storeMessage();
            return "Message successfully stored.";
        }
        return "Invalid option. Please select 1, 2, or 3.";
    }

    public String printMessages() {
        return "Message ID: "   + messageID   + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: "    + recipient   + "\n" +
               "Message: "      + message;
    }

    public void storeMessage() {
        try {
            java.io.FileWriter file = new java.io.FileWriter("storedMessages.json", true);
            file.write("{\n");
            file.write("  \"messageID\": \""   + messageID   + "\",\n");
            file.write("  \"messageHash\": \"" + messageHash + "\",\n");
            file.write("  \"recipient\": \""   + recipient   + "\",\n");
            file.write("  \"message\": \""     + message     + "\"\n");
            file.write("},\n");
            file.flush();
            file.close();
        } catch (java.io.IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // ==================== STATIC DISPLAY METHODS ====================

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public static String getAllSentMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("--- Message ").append(i + 1).append(" ---\n");
            sb.append(sentMessages.get(i).printMessages()).append("\n\n");
        }
        return sb.toString();
    }

    // ==================== PART 3: NEW METHODS ====================

    // a) Display sender and recipient of all stored messages
    public static String getStoredMessagesDetails() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder("=== Stored Messages ===\n");
        for (Message1 msg : storedMessages) {
            sb.append("Recipient: ").append(msg.recipient).append("\n");
            sb.append("Message: ").append(msg.message).append("\n\n");
        }
        return sb.toString();
    }

    // b) Display the longest message
    public static String getLongestMessage() {
        ArrayList<Message1> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);
        if (all.isEmpty()) {
            return "No messages found.";
        }
        Message1 longest = all.stream()
                .max(Comparator.comparingInt(m -> m.message.length()))
                .orElse(null);
        return longest != null ? longest.printMessages() : "No messages found.";
    }

    // c) Search for a message by ID
    public static String searchByMessageID(String id) {
        for (Message1 msg : sentMessages) {
            if (msg.messageID.equals(id)) {
                return "Recipient: " + msg.recipient + "\nMessage: " + msg.message;
            }
        }
        for (Message1 msg : storedMessages) {
            if (msg.messageID.equals(id)) {
                return "Recipient: " + msg.recipient + "\nMessage: " + msg.message;
            }
        }
        return "Message ID not found.";
    }

    // d) Search all messages for a particular recipient
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message1 msg : sentMessages) {
            if (msg.recipient.equals(recipient)) {
                sb.append(msg.message).append("\n");
            }
        }
        for (Message1 msg : storedMessages) {
            if (msg.recipient.equals(recipient)) {
                sb.append(msg.message).append("\n");
            }
        }
        return sb.isEmpty() ? "No messages found for this recipient." : sb.toString().trim();
    }

    // e) Delete a message using its hash
    public static String deleteMessageByHash(String hash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).messageHash.equals(hash)) {
                String deletedMsg = sentMessages.get(i).message;
                sentMessages.remove(i);
                messageHashes.remove(hash);
                return "Message: \"" + deletedMsg + "\" successfully deleted.";
            }
        }
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).messageHash.equals(hash)) {
                String deletedMsg = storedMessages.get(i).message;
                storedMessages.remove(i);
                messageHashes.remove(hash);
                return "Message: \"" + deletedMsg + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // f) Display full report of all sent messages
    public static String displayReport() {
        if (sentMessages.isEmpty()) {
            return "No sent messages to report.";
        }
        StringBuilder sb = new StringBuilder("=== Full Message Report ===\n\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("--- Message ").append(i + 1).append(" ---\n");
            sb.append(sentMessages.get(i).printMessages()).append("\n\n");
        }
        return sb.toString();
    }

    // ==================== GETTERS ====================

    public String getMessageID()   { return messageID; }
    public String getRecipient()   { return recipient; }
    public String getMessage()     { return message; }
    public String getMessageHash() { return messageHash; }
    public int    getMessageNumber() { return messageNumber; }

    public static ArrayList<Message1> getSentMessages()        { return sentMessages; }
    public static ArrayList<Message1> getStoredMessages()      { return storedMessages; }
    public static ArrayList<Message1> getDisregardedMessages() { return disregardedMessages; }
    public static ArrayList<String>   getMessageHashes()       { return messageHashes; }
    public static ArrayList<String>   getMessageIDs()          { return messageIDs; }
}