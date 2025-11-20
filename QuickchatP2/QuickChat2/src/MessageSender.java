import javax.swing.*;
import java.util.ArrayList;
import java.awt.Dimension;

public class MessageSender {

    // Arrays for Part 3
    private final ArrayList<Message> sentMessages = new ArrayList<>();
    private final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private final ArrayList<Message> storedMessages = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> messageIDs = new ArrayList<>();

    // ======== Main Menu ========
    public void run() {
        boolean running = true;

        while (running) {
            String menu = """
                    === QuickChat ===
                    1. Send Messages
                    2. Show Recently Sent Messages
                    3. Display Senders & Recipients
                    4. Display Longest Message
                    5. Search Message by ID
                    6. Search Messages by Recipient
                    7. Delete Message by Hash
                    8. Display Sent Report
                    9. Quit
                    """;

            String choice = JOptionPane.showInputDialog(menu);
            if (choice == null) { running = false; continue; }

            switch (choice.trim()) {
                case "1" -> sendMessages();
                case "2" -> showAllMessages();
                case "3" -> displaySentSendersAndRecipients();
                case "4" -> displayLongestMessage();
                case "5" -> {
                    String id = JOptionPane.showInputDialog("Enter Message ID to search:");
                    if (id != null) searchByMessageID(id.trim());
                }
                case "6" -> {
                    String recipient = JOptionPane.showInputDialog("Enter recipient number:");
                    if (recipient != null) searchMessagesByRecipient(recipient.trim());
                }
                case "7" -> {
                    String hash = JOptionPane.showInputDialog("Enter Message Hash to delete:");
                    if (hash != null) deleteMessageByHash(hash.trim());
                }
                case "8" -> displaySentReport();
                case "9" -> { JOptionPane.showMessageDialog(null, "Goodbye!"); running = false; }
                default -> JOptionPane.showMessageDialog(null, "Invalid choice. Select 1-9.");
            }
        }
    }

    // ======== Send Messages ========
    private void sendMessages() {
        int count;
        try {
            String input = JOptionPane.showInputDialog("How many messages would you like to send?");
            if (input == null) return;
            count = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
            return;
        }

        for (int i = 0; i < count; i++) {
            String recipient = getValidRecipient(i + 1);
            if (recipient == null) continue;

            String text = JOptionPane.showInputDialog("Enter message text for message #" + (i + 1) + ":");
            if (text == null || text.length() > 250) {
                JOptionPane.showMessageDialog(null, "Message invalid or too long.");
                continue;
            }

            Message msg = new Message(recipient, text, sentMessages.size() + disregardedMessages.size() + storedMessages.size() + 1);

            int action = JOptionPane.showOptionDialog(null,
                    "Recipient: " + recipient + "\nMessage Hash: " + msg.getMessageHash(),
                    "Select an Action",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Send", "Disregard", "Store"},
                    "Send");

            switch (action) {
                case 0 -> { // Sent
                    sentMessages.add(msg);
                    messageHashes.add(msg.getMessageHash());
                    messageIDs.add(msg.getMessageId());
                    JOptionPane.showMessageDialog(null, "Message sent.");
                }
                case 1 -> { // Disregard
                    disregardedMessages.add(msg);
                    JOptionPane.showMessageDialog(null, "Message disregarded.");
                }
                case 2 -> { // Stored
                    storedMessages.add(msg);
                    messageHashes.add(msg.getMessageHash());
                    messageIDs.add(msg.getMessageId());
                    msg.storeMessage();
                    JOptionPane.showMessageDialog(null, "Message stored.");
                }
            }
        }
    }

    // ======== Recipient Validation ========
    private String getValidRecipient(int messageNum) {
        while (true) {
            String recipient = JOptionPane.showInputDialog("Enter recipient number for message #" + messageNum + " (+countrycodexxxxxxxx):");
            if (recipient == null) return null;
            recipient = recipient.trim();
            if (recipient.startsWith("+") && recipient.length() >= 10 && recipient.length() <= 13) {
                return recipient;
            }
            JOptionPane.showMessageDialog(null, "Invalid recipient format.");
        }
    }

    // ======== Display Recently Sent Messages ========
    private void showAllMessages() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Message msg : sentMessages) {
            sb.append(msg.printMessages()).append("\n\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Recently Sent Messages", JOptionPane.INFORMATION_MESSAGE);
    }

    // Required features as methods
    public void displaySentSendersAndRecipients() {
        if (sentMessages.isEmpty()) { JOptionPane.showMessageDialog(null, "No sent messages."); return; }
        StringBuilder sb = new StringBuilder();
        for (Message msg : sentMessages) {
            sb.append("Sender: You\nRecipient: ").append(msg.getRecipient()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void displayLongestMessage() {
        if (sentMessages.isEmpty()) return;
        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) longest = msg;
        }
        JOptionPane.showMessageDialog(null, "Longest Sent Message:\n" + longest.getMessageText());
    }

    public void searchByMessageID(String id) {
        for (Message msg : sentMessages) {
            if (msg.getMessageId().equals(id)) {
                JOptionPane.showMessageDialog(null,
                        "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMessageText());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    public void searchMessagesByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) sb.append(msg.getMessageText()).append("\n");
        }
        if (sb.isEmpty()) sb.append("No messages for this recipient.");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void deleteMessageByHash(String hash) {
        for (Message msg : sentMessages) {
            if (msg.getMessageHash().equals(hash)) {
                sentMessages.remove(msg);
                messageHashes.remove(hash);
                messageIDs.remove(msg.getMessageId());
                JOptionPane.showMessageDialog(null,
                        "Message \"" + msg.getMessageText() + "\" successfully deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message hash not found.");
    }

    public void displaySentReport() {
        if (sentMessages.isEmpty()) { JOptionPane.showMessageDialog(null, "No sent messages."); return; }
        StringBuilder sb = new StringBuilder();
        for (Message msg : sentMessages) {
            sb.append("Hash: ").append(msg.getMessageHash())
              .append("\nRecipient: ").append(msg.getRecipient())
              .append("\nMessage: ").append(msg.getMessageText())
              .append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Get methods for unit testing 
    public ArrayList<Message> getSentMessages() { return sentMessages; }
    public ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<Message> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIDs() { return messageIDs; }
}
