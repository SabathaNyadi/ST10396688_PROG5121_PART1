import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    private static int totalMessages = 0;

    private String messageId;
    private String recipient;
    private String messageText;
    private int messageNumber;
    private String messageHash;
    private String timestamp;

    public Message(String recipient, String messageText, int messageNumber) {
        this.messageId = generateMessageId();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.messageHash = createMessageHash(); 
        totalMessages++;
    }

    //
    public String getMessageText() { return messageText; }
    public String getRecipient() { return recipient; }
    public String getMessageId() { return messageId; }
    public String getMessageHash() { return messageHash; }

    // this is used to validate everything 
    public boolean checkMessageID() {
        return messageId != null && messageId.matches("\\d{10}");
    }

    public int checkRecipientCell() {
        if (recipient.startsWith("+") && recipient.length() >= 10 && recipient.length() <= 13) {
            JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
            return 1;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Cell phone number is incorrectly formatted or does not contain international code. "
                    + "Please correct the number and try again.");
            return 0;
        }
    }

    public String createMessageHash() {
        String words = extractFirstAndLastWords(messageText);
        String idPart = messageId.substring(0, Math.min(2, messageId.length()));
        return idPart + ":" + messageNumber + ":" + words.toUpperCase();
    }

    public boolean sendMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int choice = JOptionPane.showOptionDialog(null,
                "Recipient: " + recipient + "\nMessage Hash: " + messageHash + "\n\nSelect an option:",
                "Final Message Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        switch (choice) {
            case 0 -> {
                JOptionPane.showMessageDialog(null, "Message successfully sent.");
                return true;
            }
            case 1 -> {
                JOptionPane.showMessageDialog(null, "Press 0 to delete message.");
                totalMessages--;
                return false;
            }
            case 2 -> {
                storeMessage();
                JOptionPane.showMessageDialog(null, "Message successfully stored to 'messages_log.txt'.");
                return true;
            }
            default -> {
                totalMessages--;
                return false;
            }
        }
    }

    public String printMessages() {
        return "--- Message #" + messageNumber + " ---\n"
                + "Message ID: " + messageId + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText + "\n"
                + "Timestamp: " + timestamp;
    }

    public static int returnTotalMessages() { return totalMessages; }

    public void storeMessage() {
        try (FileWriter writer = new FileWriter("messages_log.txt", true)) {
            writer.write(printMessages() + System.lineSeparator() + System.lineSeparator());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving message: " + e.getMessage());
        }
    }

    //
    private String generateMessageId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String extractFirstAndLastWords(String text) {
        if (text == null || text.trim().isEmpty()) return "";

        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(text);

        String firstWord = "";
        String lastWord = "";

        if (matcher.find()) firstWord = matcher.group();
        while (matcher.find()) lastWord = matcher.group();

        return lastWord.isEmpty() ? firstWord : firstWord + lastWord;
    }
}
