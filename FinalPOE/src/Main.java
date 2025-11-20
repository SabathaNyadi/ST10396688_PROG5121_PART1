import javax.swing.JOptionPane;
// main welcomes user
public class Main {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");
        MessageSender sender = new MessageSender();
        sender.run();
    }
}

