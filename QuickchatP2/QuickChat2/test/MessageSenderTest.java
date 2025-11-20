import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class MessageSenderTest {

    private MessageSender sender;

    @Before
    public void setUp() {
        sender = new MessageSender();

        // Populate test data
        // Message 1 (Sent)
        Message msg1 = new Message("+27834557896", "Did you get the cake?", 1);
        sender.getSentMessages().add(msg1);
        sender.getMessageHashes().add(msg1.getMessageHash());
        sender.getMessageIDs().add(msg1.getMessageId());

        // Message 2 (Disregarded)
        Message msg2 = new Message("+27834484567", "Yohoooo, I am at your gate.", 2);
        sender.getDisregardedMessages().add(msg2);

        // Message 3 (Stored)
        Message msg3 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 3);
        sender.getStoredMessages().add(msg3);
        sender.getMessageHashes().add(msg3.getMessageHash());
        sender.getMessageIDs().add(msg3.getMessageId());

        // Message 4 (Sent)
        Message msg4 = new Message("0838884567", "It is dinner time !", 4);
        sender.getSentMessages().add(msg4);
        sender.getMessageHashes().add(msg4.getMessageHash());
        sender.getMessageIDs().add(msg4.getMessageId());

        // Message 5 (Stored)
        Message msg5 = new Message("+27838884567", "Ok, 1 am leaving without you.", 5);
        sender.getStoredMessages().add(msg5);
        sender.getMessageHashes().add(msg5.getMessageHash());
        sender.getMessageIDs().add(msg5.getMessageId());
    }

    @Test
    public void testSentMessagesArrayPopulation() {
        ArrayList<Message> sent = sender.getSentMessages();
        assertEquals(2, sent.size());
        assertEquals("Did you get the cake?", sent.get(0).getMessageText());
        assertEquals("It is dinner time !", sent.get(1).getMessageText());
    }

    @Test
    public void testLongestMessage() {
        Message longest = sender.getSentMessages().get(0);
        for (Message m : sender.getSentMessages()) {
            if (m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        assertEquals("It is dinner time !", longest.getMessageText());
    }

    @Test
    public void testSearchByMessageID() {
        Message target = sender.getSentMessages().get(0);
        String id = target.getMessageId();
        boolean found = false;
        for (Message m : sender.getSentMessages()) {
            if (m.getMessageId().equals(id)) {
                assertEquals(target.getRecipient(), m.getRecipient());
                assertEquals(target.getMessageText(), m.getMessageText());
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testSearchByRecipient() {
        String recipient = "+27838884567";
        ArrayList<String> results = new ArrayList<>();
        for (Message m : sender.getSentMessages()) {
            if (m.getRecipient().equals(recipient)) {
                results.add(m.getMessageText());
            }
        }
        // Should be empty because messages to this recipient are stored, not sent
        assertEquals(0, results.size());
    }

    @Test
    public void testDeleteByHash() {
        Message target = sender.getSentMessages().get(0);
        String hash = target.getMessageHash();
        sender.deleteMessageByHash(hash);
        // After deletion, it should no longer exist
        boolean exists = sender.getSentMessages().stream()
                .anyMatch(m -> m.getMessageHash().equals(hash));
        assertFalse(exists);
    }

    @Test
    public void testDisplaySentReport() {
        // Just check the sentMessages array is not empty
        assertFalse(sender.getSentMessages().isEmpty());
        // Validate that the hashes match
        for (Message m : sender.getSentMessages()) {
            assertTrue(sender.getMessageHashes().contains(m.getMessageHash()));
        }
    }
}
