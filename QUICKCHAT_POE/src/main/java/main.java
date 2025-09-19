import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        login login = new login(); 

        System.out.println("=== QuickChat Registration ===");
//registration 
        System.out.print("First name: ");
        String firstName = sc.nextLine().trim();

        System.out.print("Last name: ");
        String lastName = sc.nextLine().trim();

        System.out.print("Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Cell Number (+27xxxxxxxxx): ");
        String cell = sc.nextLine().trim();

        // Pass all 5 parameters
        String regMsg = login.registerUser(username, password, cell, firstName, lastName);
        System.out.println(regMsg);
//login password and username
        System.out.println("\n=== QuickChat Login ===");
        System.out.print("Enter username: ");
        String loginUser = sc.nextLine().trim();

        System.out.print("Enter password: ");
        String loginPass = sc.nextLine();

        boolean success = login.loginUser(loginUser, loginPass);
        System.out.println("Login success boolean: " + success);
        System.out.println(login.returnLoginStatus());

        sc.close();
    }
}
