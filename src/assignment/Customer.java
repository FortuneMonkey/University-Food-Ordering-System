package Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends User {
    protected static String cusId;
    protected static String cusName;
    private static String cusEmail;
    private static String cusAddress;
    private static int cusContact;
    
    public Customer(String customerId, String customerName, String customerEmail, String customerAddress, int customerContact) {
        super(customerId, customerName, customerContact);
        this.cusId = customerId;
        this.cusName = customerName;
        this.cusEmail = customerEmail;
        this.cusAddress = customerAddress;
        this.cusContact = customerContact;
    }
    
    public static void getCusId(String cusId) {
        Customer.cusId = cusId;
    }

    // Create Scanner
    private static Scanner Sc = new Scanner(System.in);

    private static int getIntInput(String message) {
        int userInput = 0;
        boolean validInput = false;

        do {
            try {
                System.out.print(message);
                userInput = Integer.parseInt(Sc.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } while (!validInput);

        return userInput;
    }

    public static ArrayList<String> viewMenu() {
        //Print welcome menu
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Available restaurants: ");
        System.out.println("------------------------------------------------------------------------");

        //Declare Variables
        ArrayList<String> vendorData = FH.ReadFromFile("vendors.txt");
        ArrayList<String> vendorNames = new ArrayList();
        int count = 1;
        
        //Get vendor names through looping vendor data
        for(String line : vendorData){
            if (line == null){
                break;
            }

            String[] dataParts = line.split(",");
            if(dataParts.length == 4){
                String vendorName = dataParts[2];
                vendorNames.add(vendorName);
            }
        }

        //Print out all the vendors 
        for(String line : vendorNames){
            if (line == null){
                break;
            }
            System.out.println(count + ". " + line);
            count++;
        }

        //Prompt user choice
        int vendorChoice;
        
        do {
            vendorChoice = getIntInput("Pick your desired vendor or enter 0 to exit (enter a number): ");


            if (vendorChoice > vendorNames.size()) {
                System.out.println("Invalid choice. Please enter a number between 1 and " + vendorNames.size() + ".");
            }

        } while (vendorChoice > vendorNames.size());
        
        if (vendorChoice == 0){
            return null;
        } 
        
        String choice = "V" + vendorChoice;
        Vendor.VenID = choice;
        String venName = FH.GetNameFromFile("vendors.txt", Vendor.VenID);
        
        //Based on user's input, print out the chosen vendor menu
        System.out.println("");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(venName);
        System.out.println("------------------------------------------------------------------------");
        
        //Declare Items
        ArrayList<String> itemList = FH.ReadFromFile("menus.txt"); 
        ArrayList<String> vendorItems = new ArrayList();

        for(String line : itemList){
            if (line == null){
                break;
            }

            String[] dataParts = line.split(",");
            if(dataParts.length == 5){
                String vendorId = dataParts[0];
                if (vendorId.equals(choice)){
                    vendorItems.add(line);
                }
            }
        }

        count = 1;
        for(String line : vendorItems){
            if (line == null){
                break;
            }
            String[] dataParts = line.split(",");
            //assume dataparts 3 = name
            System.out.println(count + ". " + dataParts[2] + ", " + dataParts[3] + ", Price: " + dataParts[4]);
            count++;
        }

        return vendorItems;
    }

    public static void viewOrderStatus() {
        ArrayList<String> orderHistory = FH.ReadFromFile("orders.txt");
        System.out.print("Enter Order ID to view its status: ");
        String orderId = Sc.nextLine();

        boolean found = false;
        for (String order : orderHistory) {
            String[] dataParts = order.split(",");
            String fileOrderId = dataParts[0];
            if (fileOrderId.equals(orderId)) {
                found = true;
                System.out.println("Order ID: " + dataParts[0]);
                System.out.println("Status: " + dataParts[3]);
                break;
            }
        }

        if (!found) {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    public static void viewOrderHistory(String cusId) {
        ArrayList<String> orderHistoryData = FH.ReadFromFile("orders.txt");
        ArrayList<String> vendorData = FH.ReadFromFile("vendors.txt");
        ArrayList<String> itemData = FH.ReadFromFile("menus.txt");
        
        if (orderHistoryData.isEmpty()){
            System.out.println("You have not made any orders. Please place an order first.");
        } else { 
            System.out.println("Order History:");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-25s %-10s %-20s %-15s %-14s %-25s\n", "Order ID", "Customer Name", "Vendor Name", "Status", "Total Amount (RM)", "Order Type", "Date", "Items");

            for (String orderData : orderHistoryData) {
                String[] dataParts = orderData.split(",");
                String orderId = dataParts[0];
                String customerId = dataParts[1];
                String vendorId = dataParts[2];
                String status = dataParts[3];
                String totalAmount = dataParts[4];
                String orderType = dataParts[5];
                String date = dataParts[6];

                if (customerId.equals(cusId)) {
                    String venName = "";
                    for (String line : vendorData){
                        String vendorParts[] = line.split(",");
                        String idVendor = vendorParts[0];

                        if(vendorId.equals(idVendor)) {
                            venName = vendorParts[2];
                        }
                    }  

                    System.out.printf("%-10s %-25s %-25s %-10s %-20s %-15s %-15s", orderId, cusName, venName, status, totalAmount, orderType, date);

                    // Display item details
                    String[] Items = dataParts[7].split(";");
                    String itemName = "";
                    String itemPrice = "";
                    String quantity;
                    int printCount = 1;
                    for(String item : Items){
                        String[] itemParts = item.split(":");
                        quantity = itemParts[1].substring(0,1);
                        String itemID = itemParts[0];
                        for(String line : itemData){
                            String[] fileItemParts = line.split(",");
                            String fileItemId = fileItemParts[1];
                            String fileVendorId = fileItemParts[0];
                            if(fileItemId.equals(itemID) && fileVendorId.equals(vendorId)){
                               itemName = fileItemParts[2];
                               itemPrice = fileItemParts[4];
                            }
                        }
                        String itemOutput = itemName + " (RM " + itemPrice + ")" + " Amount: " + quantity;
                        if (printCount == 1) {
                            System.out.printf("%-25s\n" , itemOutput);
                            printCount++;
                        } else {
                        System.out.printf("%-125s %-25s\n" , "", itemOutput);
                        }
                        
                    }
                    System.out.println("");
                } 
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
      
    }
    
    public static void checkTransactionHistory(){
        ArrayList<String> receiptData = FH.ReadFromFile("receipt.txt");
        System.out.println("Transaction Type\t\tTransaction Amount\tBalance");
        System.out.println("--------------------------------------------------------------------");

        for (String line : receiptData) {
            String[] receiptParts = line.split(",");
            String fileCusId = receiptParts[1];

            if (fileCusId.equals(cusId)) {
                String transactionType = receiptParts[5];
                Double transactionAmount = Double.valueOf(receiptParts[3]);
                Double balance = Double.valueOf(receiptParts[2]);
                String orderId = "";

                if (transactionType.contains("PayOrder")) {
                    int endIndex = transactionType.length() - 8;
                    orderId = transactionType.substring(0, endIndex);
                } else if (transactionType.contains("RefundOrder")) {
                    int endIndex = transactionType.length() - 11;
                    orderId = transactionType.substring(0, endIndex);
                }

                String formattedLine;
                String RESET = "\u001B[0m";
                String RED = "\u001B[31m";
                String GREEN = "\u001B[32m";
                
                if (transactionType.equals("TopUp")) {
                    formattedLine = String.format("Top Up\t\t\t\t%.2f\t\t\t%.2f", transactionAmount, balance);
                    System.out.println(GREEN+formattedLine+RESET);
                } else if (transactionType.contains("PayOrder")) {
                    formattedLine = String.format("Payment for Order %s\t\t%.2f\t\t\t%.2f", orderId, transactionAmount, balance);
                    System.out.println(RED+formattedLine+RESET);
                } else if (transactionType.contains("RefundOrder")) {
                    formattedLine = String.format("Refund for Order %s\t\t%.2f\t\t\t%.2f", orderId, transactionAmount, balance);
                    System.out.println(GREEN+formattedLine+RESET);
                }
            }
        }
        System.out.println("--------------------------------------------------------------------");
    }
    
    private static void ReviewOptions(){
        System.out.println("Reviewing Order");
        System.out.println("1. Review Vendor");
        System.out.println("2. Review Runner");
        int choice = getIntInput("Enter you choice: ");
        switch(choice){
            case 1:
                VendorReview tempVenReview = new VendorReview();
                tempVenReview.validateOrder(cusId);
                break;
            case 2:
                RunnerReview tempRunReview = new RunnerReview();
                tempRunReview.validateOrder(cusId);
                break;
            default:
                System.out.println("Invalid Option. Please try again.");
                break;  
        }
    }
    
    @Override
    public void performAction() {
        printNotification();
        System.out.println("");
        printOrderUpdateNotification();
        System.out.println("");
        
        while (true) {
            System.out.println("---------------------------------------------------");
            System.out.println("Welcome " + cusName + "!");
            System.out.println("Please choose an option to start:");
            System.out.println("---------------------------------------------------");
            System.out.println("1. View Menu");
            System.out.println("2. Read Customer Reviews");
            System.out.println("3. Place Order");
            System.out.println("4. Cancel Order");
            System.out.println("5. Check Order Status");
            System.out.println("6. View Order History");
            System.out.println("7. Check Transaction History");
            System.out.println("8. Review Order");
            System.out.println("9. Reorder Previous Order");
            System.out.println("10. Exit");
            int choice = getIntInput("Enter your choice: ");
            System.out.println("");
            switch (choice) {
                case 1:
                    viewMenu();
                    System.out.println("");
                    break;
                case 2:
                    VendorReview tempVenReview = new VendorReview();
                    tempVenReview.viewReviews();
                    System.out.println("");
                    break;
                case 3:
                    Order.placeOrder();
                    System.out.println("");
                    break;
                case 4:
                    Order.cancelOrder(cusId);
                    System.out.println("");
                    break;
                case 5:
                    viewOrderStatus();
                    System.out.println("");
                    break;
                case 6:
                    viewOrderHistory(cusId);
                    System.out.println("");
                    break;
                case 7:
                    checkTransactionHistory();
                    System.out.println("");
                    break;
                case 8:
                    ReviewOptions();
                    System.out.println("");
                    break;
                case 9:
                    Order.reorder(cusId);
                    System.out.println("");
                    break;                    
                case 10:
                    System.out.println("Thank you! Hope to see you soon!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("");
            }
        }
    }
    
    public static void printNotification(){
        ArrayList<String> receiptData = FH.ReadFromFile("receipt.txt");
        ArrayList<String> cusReceipt = new ArrayList();
        for(String line : receiptData){
            String[] receiptParts = line.split(",");
            String fileCusId = receiptParts[1];
            String status = receiptParts[4];
            if(fileCusId.equals(cusId) && status.equals("Generated")) {
                cusReceipt.add(line);
            }
        }
        
        if(!cusReceipt.isEmpty()){
            System.out.println("");
            System.out.println("Transaction Notifications");
            System.out.println("--------------------------------------------------------------------");
            for(String line : cusReceipt){
                String[] receiptParts = line.split(",");
                Double amount = Double.valueOf(receiptParts[3]);
                String type = receiptParts[5];
                if(type.equals("TopUp")){
                    System.out.println("You have succesfully topped up RM " + amount);
                    System.out.println("");

                }
                if(type.contains("PayOrder")){
                    int endIndex = type.length() - 8;
                    System.out.println("Payment received. You paid RM " + amount + " for Order " + receiptParts[5].substring(0,endIndex));
                    System.out.println("");
                }
                if(type.contains("RefundOrder")){
                    int endIndex = type.length() - 11;
                    System.out.println("Order " + receiptParts[5].substring(0,endIndex) + " refunded. You received RM " + amount + ".");
                    System.out.println("");
                }
                FH.UpdateReceiptStatus("receipt.txt", receiptParts[0], "Printed", 4);
            }
            System.out.println("Check transaction history for more details.");
            System.out.println("--------------------------------------------------------------------");
            System.out.println("");
        }
    }
    
    public static void printOrderUpdateNotification(){
        ArrayList<String> orderData = FH.ReadFromFile("updated.txt");
        ArrayList<String> updatedOrder = new ArrayList();
        for(String line : orderData){
            String[] parts = line.split(",");
            
            if (parts[9].equals("Generated") && parts[1].equals(cusId)){
                updatedOrder.add(line);
            }
        }
        
        if(!updatedOrder.isEmpty()){
            System.out.println("Order Update Notifications");
            System.out.println("-----------------------------------------------------------------");
            for(String line : updatedOrder){
                String[] parts = line.split(",");
                if (parts[3].equals("Driver not found")){
                    Order.updateOrder(parts[0]);
                    return;
                }
                System.out.println("Order " + parts[0] + " is " + parts[3] + ".");
                FH.UpdateReceiptStatus("updated.txt", parts[0], "Printed", 9);
                
            }
            System.out.println("-----------------------------------------------------------------");
        }
    }
}