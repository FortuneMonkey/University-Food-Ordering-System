
package Assignment;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;


public class Vendor extends User {    
    public static String VenID;
    private static String VenName;
    private static int VenContact;
    
    
    public Vendor(String VenID, String VenName, int VenContact) {
        super(VenID, VenName, VenContact);
        this.VenID = VenID;
        this.VenName = VenName;
        this.VenContact = VenContact;
    }

    public static String getVenID(){
        return VenID;
    }
    
    public static String getVenName(){
        return VenName;
    }
    
    public static int getVenContact(){
        return VenContact;
    }
     
    public static void printOrder(){
        System.out.println("Orders for " + VenName);
        FH.ShowOrder("orders.txt", VenID);
    }
    
    public static void decisionOrder(){        
        Scanner Sc = new Scanner(System.in);
        System.out.println("Showing pending order...");
        FH.ShowPendingOrder("orders.txt", VenID);
        
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Accept Order");
        System.out.println("2. Cancel Order");
        System.out.println("0. Return to vendor menu");
        System.out.println("--------------------------------------------------------");
        System.out.print("Enter choice: ");
        int choice = Sc.nextInt();
        Sc.nextLine();  
        
        switch(choice){
            case 1: 
                while(true){
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Enter OrderID to accept (0 to stop): ");
                    String tempOrdID = Sc.nextLine();
                    
                    if(tempOrdID.equals("0")){
                        break;
                    } else if(tempOrdID.equals("Finished")) {
                        System.out.println("Order is finished, cannot be accepted");
                    }
                     
                    
                    String newOrdStatus = "Accepted";
                    FH.UpdateOrdStatusFromFile("orders.txt", tempOrdID, newOrdStatus);
                    
                }    
                break;
                case 2:   
                    while(true){
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Enter OrderID to cancel (0 to stop): ");
                    String tempOrdID = Sc.nextLine();
                    
                    if(tempOrdID.equals("0")){
                        break;
                    } else if(tempOrdID.equals("Delivered")) {
                        System.out.println("Order is delivered, cannot be cancelled");
                    }
                    
                    String newOrdStatus = "Cancelled";
                    FH.UpdateOrdStatusFromFile("orders.txt", tempOrdID, newOrdStatus);
                    
                    String cusId = FH.GetCusIdFromFile("orders.txt", tempOrdID);
                    Payment tempPayment = Order.createPaymentObj(cusId);
                    tempPayment.refundOrders(tempOrdID, cusId);
                }    
                break;
            
            default: 
                
        } 
    }    
   
    public static void finishOrder(){
        Scanner Sc = new Scanner(System.in);
        FH.GetDineInTakeawayFromFile("orders.txt", VenID);
        
        System.out.println("--------------------------------------------------------");
        System.out.print("Select which order ID to finish: ");
        String ordID = Sc.nextLine();
        
        FH.UpdateOrdTypeFromFile("orders.txt", ordID);
    }
    
    public static void orderHistory(){
        Scanner Sc = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
        
        System.out.println("--------------------------------------------------------");        
        System.out.println("View order history for... (today's date " + currentDate + ")");
        System.out.println("1. Daily");
        System.out.println("2. Weekly");
        System.out.println("3. Monthly");
        System.out.println("4. Yearly");
        System.out.println("0. Return to vendor menu");
        System.out.println("--------------------------------------------------------");
        
        int choice = -1;

        while (choice != 0){
            System.out.print("Enter your choice: ");
            choice = Sc.nextInt();

            switch(choice){
                case 1:
                    viewDailyHistory();
                    break;
                case 2:
                    viewWeeklyHistory();
                    break;
                case 3:
                    viewMonthlyHistory();
                    break;
                case 4:
                    viewYearlyHistory();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } 
    }
    
    public static void viewDailyHistory() {
        Scanner Sc = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
        
        String Date = currentDate.toString();
        String YearMonth = Date.substring(0, Math.min(Date.length(), 8));
        
        System.out.print("Enter the date of the day you want to view ("+ YearMonth + "dd): ");
        String Day = Sc.nextLine();
        
        String combine = YearMonth + Day;
        
        System.out.println("Displaying daily order history for " + combine);
        FH.ShowOrderByDayVendor("orders.txt", combine, VenID);
    }

    //not sure of the logic
    public static void viewWeeklyHistory() {
        Scanner Sc = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
        
        String Date = currentDate.toString();
        String Month = Date.substring(5, Math.min(Date.length(), 7));
        String YearMonth = Date.substring(0, Math.min(Date.length(), 8));
        
        System.out.print("Enter what week of the month you want to view (1,2,3,4): ");
        int choice = Sc.nextInt();
        
        switch(choice){
            case 1:
                System.out.println("Displaying first week order history for month " + Month);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "01", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "02", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "03", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "04", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "05", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "06", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "07", VenID);
                break;
            case 2:
                System.out.println("Displaying second week order history for month " + Month);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "08", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "09", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "10", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "11", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "12", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "13", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "14", VenID);
                break;
            case 3:
                System.out.println("Displaying third week order history for month " + Month);   
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "15", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "16", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "17", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "18", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "19", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "20", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "21", VenID);
                break;
            case 4:
                System.out.println("Displaying fourth week order history for month " + Month);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "22", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "23", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "24", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "25", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "26", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "27", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "28", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "29", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "30", VenID);
                FH.ShowOrderByDayVendor("orders.txt", YearMonth + "31", VenID);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    public static void viewMonthlyHistory() {
        Scanner Sc = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
        
        String Date = currentDate.toString();
        String Year = Date.substring(0, Math.min(Date.length(), 5));
        
        System.out.print("Enter the month of the year you want to view (mm): ");
        String Month = Sc.nextLine();
        
        String combine = Year + Month;
        
        System.out.println("Displaying order history for " + Month + " month of this year.");
        FH.ShowOrderByMonthVendor("orders.txt", combine, VenID);
    }

    public static void viewYearlyHistory() {
        Scanner Sc = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
        
        String Date = currentDate.toString();
        
        System.out.print("Enter the month of the year you want to view (yyyy): ");
        String Year = Sc.nextLine();
        
        System.out.println("Displaying daily order history for " + Year + " year.");
        FH.ShowOrderByYearVendor("orders.txt", Year, VenID);
    }
    
    public static void revenueDashboard(){
        LocalDate currentDate = LocalDate.now();
        String Date = currentDate.toString();
        
        System.out.println("--------------------------------------------------------");    
        System.out.println("Showing revenue dashboard for " + VenName);    
        System.out.println("--------------------------------------------------------"); 
        
        double totalRevenue = FH.CalculateTotalRevenue("orders.txt", VenID);
        System.out.println("Total revenue overtime = " + totalRevenue); 
        
        
        System.out.println("--------------------------------------------------------");    
        System.out.println("Showing today's revenue (" + currentDate + ")");    
        System.out.println("--------------------------------------------------------"); 
        
        double todayRevenue = FH.CalculateTodayRevenue("orders.txt", VenID, Date);
        System.out.println("Today revenue = " + todayRevenue); 
    }
    
    public static void customerReview(){
        System.out.println("--------------------------------------------------------");    
        System.out.println("Showing " + VenName + " customers review!");    
        System.out.println("--------------------------------------------------------"); 
        FH.getReview("reviews.txt", VenID);
    }    
    
    public static void showBest(){
        FH.findMaxValuesVendors("payment.txt", "V", "vendors.txt", "orders.txt");
    }    
    
    @Override
    public void performAction(){
        Scanner Sc = new Scanner(System.in);
        boolean continueLoop = true;
        
        int orderCount = FH.CountOrder("orders.txt", VenID);
        System.out.println("--------------------------------------------------------");
        System.out.println("Welcome " + VenName + "!");
        System.out.println("| CURRENT STATUS |");
        System.out.println("You have " + orderCount + " new orders: ");
        
        while (continueLoop == true) {
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Create item");
            System.out.println("2. Read item");
            System.out.println("3. Update item");
            System.out.println("4. Delete item");
            System.out.println("5. View order list");
            System.out.println("6. Accept/Cancel order");
            System.out.println("7. Finish Dine in & Takeaway order");
            System.out.println("8. Check order history");
            System.out.println("9. Read customer review");
            System.out.println("10. Revenue dashboard");
            System.out.println("11. Show best vendor");
            System.out.println("0. Exit");
            System.out.println("--------------------------------------------------------");
            System.out.print("Select an action: ");
            int choice = Sc.nextInt();
            Sc.nextLine();  
            
            switch (choice) {
                case 1:
                    Item.createItem();
                    break;
                case 2:
                    Item.readItem();
                    break;
                case 3:
                    Item.updateItem();
                    break;
                case 4:
                    Item.deleteItem();
                    break;
                case 5:
                    printOrder();
                    break;
                case 6:
                    decisionOrder();
                    break;
                case 7:
                    finishOrder();
                    break;
                case 8:
                    orderHistory();
                    break;
                case 9:
                    customerReview();
                    break;
                case 10:
                    revenueDashboard();
                    break;
                case 11:
                    showBest();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    continueLoop = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    
    
}
