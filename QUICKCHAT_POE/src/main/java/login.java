import java.util.Objects;
import java.util.regex.Pattern;
//registered (
public class login {
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCell;
    private String registeredFirstName;
    private String registeredLastName;
    
    private boolean lastLoginSuccessful;
    //username should contain '_' and less than 5 characers
    public boolean checkUsername(String username) {
        return username != null
             && username.contains("_")
             && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password){
        if (password == null) return false;
        boolean longEnough = password.length() >= 8;
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigits = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^A-Za-z0-9].*"); // fixed for real special char
        return longEnough && hasUpper && hasDigits && hasSpecial;
    }   
    //cellphone should contain a '+27'
    public boolean checkCellPhoneNumber(String cell){
        if (cell == null) return false;
        return Pattern.matches("^\\+27\\d{9}$", cell);    
    }
    //the output if rules are not met
    public String registerUser(String username, String password, String cell, String firstName, String lastName){
        if (!checkUsername(username)){
            return "Username is not correctly formatted. Please ensure your username contains an underscore and is no more than five characters in length.";
        }
        //if validility not met
        if (!checkPasswordComplexity(password)){
            return "Password is not correctly formatted. Please ensure that the password has at least eight characters, a capital letter, a number, and a special character.";
        }
        //validility not met
        if (!checkCellPhoneNumber(cell)){
            return "Cellphone number is incorrectly formatted. It must start with +27 followed by 9 digits.";
        }
        
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCell = cell;
        this.registeredFirstName = firstName;
        this.registeredLastName = lastName;
          
        return "Username successfully captured.\nPassword successfully captured.\nCellphone successfully captured.\nFirst name and Last name successfully captured.";
    }
       //checks if everything matches   
    public boolean loginUser(String username, String password){
        this.lastLoginSuccessful = 
                Objects.equals(username, this.registeredUsername)
             && Objects.equals(password, this.registeredPassword);
        return this.lastLoginSuccessful;
    }
    //if everything is met a mesage will be displayed to welcome you
    public String returnLoginStatus(){
        if (this.lastLoginSuccessful){
            return "Welcome " + registeredFirstName + ", " + registeredLastName + ", it is great to see you again.";
        } else {
            return "Login failed. Please check your username and password.";
        }
    }
}
