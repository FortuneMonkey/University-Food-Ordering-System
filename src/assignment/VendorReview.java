package Assignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class VendorReview extends Task{
    private String details;
    
    // Constructor
    public VendorReview(String taskId, String customerId, String vendorId, String location,
                        String status, String runnerId, LocalDate creationDate, String orderId, ArrayList<OrderItem> items) {
        super(taskId, customerId, vendorId, location, status, runnerId, creationDate, orderId, items);
    }
    
    public VendorReview(){
    }

    @Override
    public void validateOrder(String cusId) {
        System.out.println("--------Reviewing Vendor--------");
        Order orderToReview = getOrderToReview(cusId);
        if(orderToReview == null){
            return;
        }
        if (!((orderToReview.getStatus().equals("Finished")))){
            System.out.println("Order is not in a 'finished' state.");
            return;
        }
        review(orderToReview);
    }

    @Override
    public void review(Order orderToReview) {
        String vendorName = FH.GetNameFromFile("vendors.txt", orderToReview.getVendorId());
        
        // Overall review for the entire order
        System.out.println("---------------------------------------------------");
        System.out.println("Enter overall review for vendor: " + vendorName);
        int overallRating = getValidRating();
        System.out.print("Message: ");
        String overallReviewText = Sc.nextLine();
        addReview(orderToReview.getCusId(), orderToReview.getVendorId(), "Overall", overallRating, overallReviewText);

        System.out.println("");

        System.out.println("Rate the item(s) in your order");

        // Iterate through order items for item-specific reviews
        for (OrderItem orderItem : orderToReview.getOrderItems()) {
            // Print item-specific review prompt
            System.out.println("Item Name: " + orderItem.getItemName());
            int itemRating = getValidRating();
            System.out.print("Message: ");
            String itemReviewText = Sc.nextLine();
            System.out.println("");
            addReview(orderToReview.getCusId(), orderToReview.getVendorId(), orderItem.getItemId(), itemRating, itemReviewText);
        }
        updateFile();

        System.out.println("Thank you for your reviews!");
    }
    
    
    // New method to read menu items from the file and create Item objects
    public ArrayList<Item> readMenuItems(String filePath) {
        ArrayList<Item> menuItems = new ArrayList<>();
        ArrayList<String> lines = FH.ReadFromFile(filePath);

        for (String line : lines) {
            if (line == null) {
                break;
            }
            menuItems.add(new Item(line));
        }

        return menuItems;
    }
    
    public void addReview(String customerId, String vendorId, String itemId, int rating, String message) {
        ArrayList<String> File = FH.ReadFromFile("reviews.txt");
        String currentReviewId = "R0";
        
        for(String line : File){
            if(line == null){
                break;
            }
            
            String[] dataParts = line.split(",");
            currentReviewId = dataParts[0];
        }
        int numericPart = Integer.parseInt(currentReviewId.substring(1)) + 1;
        currentReviewId = String.format("R%01d", numericPart);
        
        String reviewData = String.format("%s,%s,%s,%s,%d,%s", currentReviewId,customerId, vendorId, itemId, rating, message);
        FH.Write2File(reviewData, "reviews.txt", new ArrayList<>());
        System.out.println("Review added");
        System.out.println("");
    }
    
    public void viewReviews() {
        System.out.println("View Reviews");
        System.out.println("1. View Vendor Ratings");
        System.out.println("2. Exit");
        System.out.print("");

        int choice = getValidChoice("Enter your choice: ", 1, 2);

        switch (choice) {
            case 1:
                printVendorsWithRatings();
                System.out.print("Select a vendor or enter 0 to exit: ");
                String vendorId = Sc.nextLine();
                boolean exist = FH.checkIdExists("vendors.txt", vendorId, 0);
                if (exist && (!vendorId.equals("0"))) {
                    System.out.println("1. View overall messages");
                    System.out.println("2. View item ratings");
                    System.out.println("3. Exit");

                    int vendorChoice = getValidChoice("Enter your choice: ", 1, 3);

                    switch (vendorChoice) {
                        case 1:
                            printOverallMessages(vendorId);
                            break;
                        case 2:
                            printItemRatings(vendorId);
                            break;
                        case 3:
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid choice. Exiting...");
                            break;
                    }
                } else {
                    System.out.println("Invalid Vendor. Try again.");
                }
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                break;
        } 
    }
    
    private static int getValidChoice(String message, int min, int max) {
        int choice;

        while (true) {
            try {
                System.out.print(message);
                choice = Integer.parseInt(Sc.nextLine());
                if (choice >= min && choice <= max) {
                    break; // Break the loop if the input is valid
                } else {
                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
            }
        }

        return choice;
    }
    
    public static void printVendorsWithRatings() {
        ArrayList<String> reviews = FH.ReadFromFile("reviews.txt");

        if (reviews.isEmpty()) {
            System.out.println("No reviews found.");
            return;
        }

        // Create a map to store total ratings and review counts for each vendor
        HashMap<String, Integer> totalRatings = new HashMap<>();
        HashMap<String, Integer> reviewCounts = new HashMap<>();
        int count = 1;
        for (String line : reviews) {
            String[] lineParts = line.split(",");
            String vendorId = lineParts[2];
            int rating = Integer.parseInt(lineParts[4]);
            count++;
            // Update the total rating and review count for the vendor
            totalRatings.merge(vendorId, rating, Integer::sum);
            reviewCounts.merge(vendorId, 1, Integer::sum);
        }

        // Sort the vendor IDs
        ArrayList<String> sortedVendors = new ArrayList<>(reviewCounts.keySet());
        Collections.sort(sortedVendors);

        // Print vendor IDs, vendor names, and their overall average ratings
        System.out.println("------------------------------------------------------");

        for (String vendorId : sortedVendors) {
            String vendorName = FH.GetNameFromFile("vendors.txt", vendorId);
            int totalRating = totalRatings.get(vendorId);
            int reviewCount = reviewCounts.get(vendorId);
            double averageRating = (double) totalRating / reviewCount;

            System.out.printf("%s. %s: %.2f\n", vendorId, vendorName, averageRating);
        }
        System.out.println("------------------------------------------------------");
    }
    
    public static void printOverallMessages(String vendorId) {
        ArrayList<String> reviews = FH.ReadFromFile("reviews.txt");
        
        String vendorName = FH.GetNameFromFile("vendors.txt", vendorId);
        if (reviews.isEmpty()) {
            System.out.println("No reviews found.");
            return;
        }
        
        System.out.println("");
        System.out.println("Overall Messages for " + vendorName);

        System.out.printf("%-20s %-25s  %-10s  %s\n", "Customer Name", "Vendor Name", "Rating", "Message");
        for (String review : reviews) {
            String[] parts = review.split(",");
            String reviewVendorId = parts[2];

            if (reviewVendorId.equals(vendorId) && parts[3].equals("Overall")) {
                String customerId = parts[1];
                int rating = Integer.parseInt(parts[4]);
                String message = parts[5];
                
                String customerName = FH.GetNameFromFile("customers.txt", customerId);
                
                    
                System.out.printf("%-20s %-25s  %-11d %s\n", customerName, vendorName, rating, message);
            }
        }
    }

    public static void printItemRatings(String vendorId) {
        ArrayList<String> reviews = FH.ReadFromFile("reviews.txt");

        if (reviews.isEmpty()) {
            System.out.println("No reviews found.");
            return;
        }

        System.out.println("");
        String vendorName = FH.GetNameFromFile("vendors.txt", vendorId);
        System.out.println("Item Ratings for " + vendorName);

        // Create a set to store unique items for the selected vendor
        HashSet<String> vendorItems = new HashSet<>();

        for (String review : reviews) {
            String[] parts = review.split(",");
            String reviewVendorId = parts[2];

            if (reviewVendorId.equals(vendorId)) {
                String itemId = parts[3];
                vendorItems.add(itemId);
            }
        }

        // Print average ratings for each item
        for (String itemId : vendorItems) {
            double totalRating = 0;
            int reviewCount = 0;

            for (String review : reviews) {
                String[] parts = review.split(",");
                String reviewVendorId = parts[2];

                if (reviewVendorId.equals(vendorId) && parts[3].equals(itemId)) {
                    int rating = Integer.parseInt(parts[4]);
                    totalRating += rating;
                    reviewCount++;
                }
            }

            if (reviewCount > 0 && !(itemId.equals("Overall"))) {
                double averageRating = totalRating / reviewCount;

                // Retrieve item details
                String itemName = getItemName(itemId, vendorId);

                System.out.printf("%s. %s: %.2f\n", itemId, itemName, averageRating);
            }
        }

        // Let the user choose one of the items to see detailed reviews
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;
        while(continueLoop){
            System.out.print("Enter Item ID to see detailed reviews (any other key to quit): ");
            String selectedItemId = scanner.nextLine();
            boolean exist = FH.checkIdExists("menus.txt", selectedItemId, 1);
            if (exist) {
                for (String review : reviews) {
                    String[] parts = review.split(",");
                    String reviewVendorId = parts[2];

                    if (reviewVendorId.equals(vendorId) && parts[3].equals(selectedItemId)) {
                        String customerId = parts[1];
                        int rating = Integer.parseInt(parts[4]);
                        String message = parts[5];

                        // Retrieve item details
                        String itemName = getItemName(selectedItemId, vendorId);
                        String customerName = FH.GetNameFromFile("customers.txt", customerId);
                        System.out.printf("%s: %s - %d - %s\n", customerName, itemName, rating, message);
                        System.out.println("");
                    }
                }
            }
            else {
                continueLoop = false;
            }
        }
    }

    // Helper method to get item name from the file
    private static String getItemName(String itemId, String vendorId) {
        ArrayList<String> menuItems = FH.ReadFromFile("menus.txt");

        for (String menuItem : menuItems) {
            String[] parts = menuItem.split(",");
            String itemVendorId = parts[0];

            if (itemVendorId.equals(vendorId) && parts[1].equals(itemId)) {
                return parts[2]; // Assuming item name is at index 2, adjust if needed
            }
        }

        return "Unknown Item";
    }

    @Override
    public void updateFile() {
        FH.UpdateOrdStatusFromFile("orders.txt", orderId, "Reviewed");
    }
    
}
