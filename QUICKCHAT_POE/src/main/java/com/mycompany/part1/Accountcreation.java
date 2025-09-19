import java.util.Scanner;

public class Accountcreation {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Ask for username
        System.out.print("Enter username: ");
        String username = input.nextLine();

        // Ask for password (not validated yet, just stored)
        System.out.print("Enter password: ");
        String password = input.nextLine();

        // Ask for cell number (not validated yet, just stored)
        System.out.print("Enter South African cell number: ");
        String cellNumber = input.nextLine();

        // Check username conditions
        if (username.contains("_") && username.length() <= 5) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username unsuccessfully captured");
        }

        input.close();
    }
}

