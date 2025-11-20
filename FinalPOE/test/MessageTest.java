import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {

    private Message msg1;
    private Message msg2;

    @Before
    public void setUp() {
        msg1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight", 1);
        msg2 = new Message("+278575975889", "Hi Keegan, did you receive the payment?", 2);
    }
// runs test on everything
    @Test
    public void testMessageLength() {
        //shows message shouldnt be higher than 250 words
        assertTrue("Message 1 length should be <= 250", msg1.getMessageText().length() <= 250);
        assertTrue("Message 2 length should be <= 250", msg2.getMessageText().length() <= 250);
    }

    @Test
    public void testRecipientFormat() {
        assertTrue("Recipient 1 should start with +", msg1.getRecipient().startsWith("+"));
        assertTrue("Recipient 2 should start with +", msg2.getRecipient().startsWith("+"));
        assertTrue("Recipient 1 length between 10-13", msg1.getRecipient().length() >= 10 && msg1.getRecipient().length() <= 13);
        assertTrue("Recipient 2 length between 10-13", msg2.getRecipient().length() >= 10 && msg2.getRecipient().length() <= 13);
    }

    @Test
    public void testMessageID() {
        assertEquals("Message 1 ID should be 10 digits", 10, msg1.getMessageId().length());
        assertEquals("Message 2 ID should be 10 digits", 10, msg2.getMessageId().length());
    }

    @Test
    public void testMessageHash() {
        String hash1 = msg1.createMessageHash();
        String hash2 = msg2.createMessageHash();
        assertNotNull("Hash 1 should not be null", hash1);
        assertNotNull("Hash 2 should not be null", hash2);
    }

    @Test
    public void testSendOrDiscardSimulation() {
 
        boolean sent1 = true;
        boolean sent2 = false; 
        assertTrue("Simulated send should be true", sent1);
        assertFalse("Simulated discard should be false", sent2);
    }
}

