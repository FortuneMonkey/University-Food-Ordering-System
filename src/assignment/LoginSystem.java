
package Assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem {
    public static void login() {
        Scanner Sc = new Scanner(System.in);

        System.out.println("Welcome to Tech Delivery");
        System.out.print("Enter your ID (V/C/D/A): ");
        String inputId = Sc.nextLine();
            
        System.out.print("Enter your password: ");
        String inputPassword = Sc.nextLine();
        
        boolean isAuthenticated = false;
        
        User user = null;
        
        switch (inputId.substring(0,1).toUpperCase()) {
            case "V":
                isAuthenticated = FH.verifyDataToFile("vendors.txt", inputId, inputPassword);
                if (isAuthenticated) {
                    String inputName = null;
                    int inputContact = 0;
                    
                    ArrayList<String> data = FH.ReadFromFile2("vendors.txt", inputId);
                    
                    for (String line : data) {
                        String[] dataParts = line.split(",");
                        inputName = dataParts[2];
                        String strContact = dataParts[3];
                        inputContact = Integer.parseInt(strContact);
                    }
                    user = new Vendor(inputId, inputName, inputContact);
                }
                break;
            case "C":
                isAuthenticated = FH.verifyDataToFile("customers.txt", inputId, inputPassword);
                if (isAuthenticated) {
                    String inputName = null;
                    String inputEmail = null;
                    String inputAddress = null;
                    int inputContact = 0;
                    
                    ArrayList<String> data = FH.ReadFromFile2("customers.txt", inputId);
                    
                    for (String line : data) {
                        String[] dataParts = line.split(",");
                        inputName = dataParts[2];
                        inputEmail = dataParts[3];
                        inputAddress = dataParts[4];
                        String strContact = dataParts[5];
                        inputContact = Integer.parseInt(strContact);
                    }
                    user = new Customer(inputId, inputName, inputEmail, inputAddress, inputContact);
                }
                break;
            case "D":
                isAuthenticated = FH.verifyDataToFile("runners.txt", inputId, inputPassword);
                if (isAuthenticated) {
                    String inputName = null;
                    int inputContact = 0;
                    
                    ArrayList<String> data = FH.ReadFromFile2("runners.txt", inputId);
                    
                    for (String line : data) {
                        String[] dataParts = line.split(",");
                        inputName = dataParts[2];
                        String strContact = dataParts[3];
                        inputContact = Integer.parseInt(strContact);
                    }
                    user = new DeliveryRunner(inputId, inputName, inputContact);
                }
                break;
            case "A":
                isAuthenticated = FH.verifyDataToFile("admin.txt", inputId, inputPassword);
                if (isAuthenticated) {
                    String inputName = null;
                    int inputContact = 0;
                    
                    ArrayList<String> data = FH.ReadFromFile2("admin.txt", inputId);
                    
                    for (String line : data) {
                        String[] dataParts = line.split(",");
                        inputName = dataParts[2];
                        String strContact = dataParts[3];
                        inputContact = Integer.parseInt(strContact);
                    }
                    user = new Admin(inputId, inputName, inputContact);
                }
                break;
            default:
                System.out.println("Unknown role.");
        }

        if (isAuthenticated && user != null) {
            System.out.println("Login Successful!");
            user.performAction(); // Polymorphism: Calls specific user's action
        } else {
            System.out.println("Login failed. Please check your ID and password.");
        }

            
        Sc.close();
    }
}

abstract class User {
    protected String userID;
    protected String userName;
    protected int userContact;

    public User(String userID, String userName, int userContact) {
        this.userID = userID;
        this.userName = userName;
        this.userContact = userContact;
    }

    public void performAction() {
        // Common action for all users (can be overridden by subclasses)
        System.out.println("Performing a generic user action.");
    }
}