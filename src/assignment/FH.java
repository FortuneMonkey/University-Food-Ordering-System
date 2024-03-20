package Assignment;

import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;


public class FH {
    public static ArrayList<Object> vendorsList = new ArrayList<>();
    public static ArrayList<Object> menuList = new ArrayList<>();
    public static ArrayList<Object> customersList = new ArrayList<>();
    public static ArrayList<Object> ordersList = new ArrayList<>();
    public static ArrayList<Object> tasksList = new ArrayList<>();
    public static ArrayList<Object> receiptList = new ArrayList<>();
    public static ArrayList<Object> paymentList = new ArrayList<>();
    public static ArrayList<Object> runnerList = new ArrayList<>();
    public static ArrayList<Object> updateList = new ArrayList<>();
    public static ArrayList<Object> voucherList = new ArrayList<>();
    
    public static boolean verifyDataToFile(String filename, String inputId, String inputPassword) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Split the line into userID and password
                String[] userData = line.split(",");

                // Check if inputId and inputPassword match the current line
                if (inputId.equals(userData[0]) && inputPassword.equals(userData[1])) {
                    return true; // Authentication successful
                }
            }
        } catch (IOException e) {
            // Handle IO exception (e.g., file not found, unable to read)
            e.printStackTrace();
        }

        return false; // Authentication failed
    }
    
    public static <T> void Write2File(T o, String filename, ArrayList<T> list){
        list.add(o);
        
        try{
            File outFile = new File(filename);
            FileWriter fileWriter = new FileWriter(outFile,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(o.toString());
            
            System.out.println("Data written to the file");
            printWriter.close();
        } catch (IOException ex){
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return "Object details..." + "\n";
    }
    
    public static void printVendorsList() {
        System.out.println("Vendors List: " + vendorsList);
    }

    public static void printMenuList() {
        System.out.println("Menu List: " + menuList);
    }
    
    public static void printCustomersList() {
        System.out.println("Customers List: " + customersList);
    }
    
   
    public static ArrayList<String> ReadFromFile(String filename){
        ArrayList<String> data = new ArrayList();
        
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            
            while ((line = bufferedReader.readLine()) != null){
                
                data.add(line);
                
            }
            
            bufferedReader.close();
        } catch (IOException ex){
            System.out.println("Read Error");
        }
        
        return data;
    }
    
    public static ArrayList<String> ReadFromFile2(String filename, String inputId){
        ArrayList<String> data = new ArrayList();
        
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            
            while ((line = bufferedReader.readLine()) != null){
                String[] dataParts = line.split(",");
                String myId = dataParts[0].trim();
                if(myId.equalsIgnoreCase(inputId)){
                    data.add(line);
                } 
            }
            bufferedReader.close();
        } catch (IOException ex){
            System.out.println("Read Error");
        }
        
        return data;
    }
  
    
    public static void SearchFromFile(String FileName, String input){
        boolean itemFound = false;
        try{
            FileReader fileReader = new FileReader(FileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 5){
                    String myInput = dataParts[1].trim();
                    //System.out.println("myInput: " + myInput);
                    //System.out.println("input: " + input);
                    //System.out.println("Line: " + line);
                    
                    if(myInput.equalsIgnoreCase(input) && dataParts[0].equals(Vendor.VenID)){
                        System.out.println("Item found!");
                        System.out.println(line);
                        itemFound = true;
                    } 
                } 
            }
            bufferedReader.close();
        
            if (!itemFound) {
                System.out.println("Item not found.");
            }
            
        } catch (IOException ex) {
            System.out.println("Search Error");
        }
    }
    
    
    public static String GetCusIdFromFile(String FileName, String id){
        try{
            FileReader fileReader = new FileReader(FileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                String myId = dataParts[0].trim();
                if(myId.equals(id)){
                    String CusId = dataParts[1];
                    return CusId;
                }
            }
            bufferedReader.close();
        
        } catch (IOException ex) {
            System.out.println("Search Error");
        }
        
        return null;
    }
    
    public static String GetNameFromFile(String FileName, String id){
        try{
            FileReader fileReader = new FileReader(FileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                String myId = dataParts[0].trim();
                if(myId.equals(id)){
                    String Name = dataParts[2];
                    return Name;
                }
            }
            bufferedReader.close();
        
        } catch (IOException ex) {
            System.out.println("Search Error");
        }
        
        return null;
    }
    
    
    public static int GetContactFromFile(String FileName, String id){
        try{
            FileReader fileReader = new FileReader(FileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 4){
                    String myId = dataParts[0].trim();
                    if(myId.equals(id)){
                        int Contact = Integer.parseInt(dataParts[3]);
                        return Contact;
                    }
                }
            }
            bufferedReader.close();
        
        } catch (IOException ex) {
            System.out.println("Search Error");
        }
        
        return 0;
    }
    
    public static void GetIdFromFile(String FileName, String VendorId){
        try{
            FileReader fileReader = new FileReader(FileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            boolean found = false;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 5){
                    String myVendId = dataParts[0].trim();
                    if(myVendId.equals(VendorId)){
                        Item.currentItemId = dataParts[1].trim();
                        found = true;
                    } 
                }
            }
            if (!found){
                Item.currentItemId = "I0";
            }
            bufferedReader.close();
        
        } catch (IOException ex) {
            System.out.println("Search Error 2");
        }
        
    }
    
    
    public static boolean checkIdExists(String filename, String id, int location) { 
        ArrayList<String> File = FH.ReadFromFile(filename);

        for (String line : File){
            String[] parts = line.split(",");
            String fileId = parts[location];

            if(fileId.equals(id)){
                return true;
            }
        }
        return false;
    }
    
    public static void UpdateItemFromFile(String fileName, String itemIDToUpdate, String itemNameToUpdate, String newDescription, double newPrice) {
        ArrayList<String> updatedLines = new ArrayList<>();
        boolean itemUpdated = false; // Flag to track if the item was found and updated

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 5) {
                    String itemID = dataParts[0].trim();
                    String itemName = dataParts[2].trim();
                    if (itemID.equals(itemIDToUpdate) && itemName.equalsIgnoreCase(itemNameToUpdate)) {
                        dataParts[3] = newDescription;
                        dataParts[4] = String.valueOf(newPrice);
                        line = String.join(",", dataParts);
                        System.out.println("Item updated: " + line);
                        itemUpdated = true; // Set the flag to true
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the updated lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }
            if (itemUpdated) {
                System.out.println("File updated successfully.");
            } else {
                System.out.println("Wrong input: Item not found for the given ID and name.");
            }
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }
    
    public static void DeleteItemFromFile(String fileName, String itemIDToDelete, String itemNameToDelete) {
        ArrayList<String> remainingLines = new ArrayList<>();
        boolean itemDeleted = false; // Flag to track if the item was found and deleted

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 5) {
                    String itemID = dataParts[1].trim();
                    String itemName = dataParts[2].trim();

                    if (!(itemID.equals(itemIDToDelete) && itemName.equalsIgnoreCase(itemNameToDelete))) {
                        // Add lines for items not to be deleted to the list
                        remainingLines.add(line);
                    } else {
                        System.out.println("Item deleted: " + line);
                        itemDeleted = true; // Set the flag to true
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error deleting item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the remaining lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String remainingLine : remainingLines) {
                bufferedWriter.write(remainingLine);
                bufferedWriter.newLine();
            }
            if (itemDeleted) {
                System.out.println("File updated after deletion.");
            } else {
                System.out.println("Wrong input: Item not found for the given ID and name.");
            }
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }
        
    public static void ShowMenu(String filename, String VendorId){
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
           
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 5){
                    String myVendId = dataParts[0].trim();
                    if(myVendId.equals(VendorId)){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
    }
    
    //count only new coming orders
    public static int CountOrder(String filename, String Id){
        int Count = 0;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 9){
                    String myId = dataParts[2].trim();
                    String myStatus = dataParts[3].trim();
                    if(myId.equals(Id) && myStatus.equals("Placed")){
                        Count++;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
        
        return Count;
    }
    
    //count only new coming orders
    public static int CountAllOrder(String filename, String Id){
        int Count = 0;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 9){
                    String myId = dataParts[2].trim();
                    if(myId.equals(Id)){
                        Count++;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
        
        return Count;
    }
    
    //show all order
    public static void ShowOrder(String filename, String Id){
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 9){
                    String myId = dataParts[2].trim();
                    if(myId.equals(Id)){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
    }
    
    //show pending order only
    public static void ShowPendingOrder(String filename, String Id){
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 9){
                    String myId = dataParts[2].trim();
                    String myStatus = dataParts[3].trim();
                    if(myId.equals(Id) && myStatus.equals("Placed")){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
    }
    
    //show by date
    public static void ShowOrderByDayVendor(String filename, String desiredDay, String id){
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 9){
                    String myId = dataParts[2].trim();
                    String date = dataParts[6].trim();
                    if(myId.equals(id) && date.equals(desiredDay)){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
    }
    
    //show by month
    public static void ShowOrderByMonthVendor(String filename, String desiredMonth, String id) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String myId = dataParts[2].trim();
                    String date = dataParts[6].trim();
                    if (myId.equals(id) && date.startsWith(desiredMonth)) {
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println("Read Error");
        }
    }
    
    public static void ShowOrderByYearVendor(String filename, String desiredYear, String id) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String myId = dataParts[2].trim();
                    String date = dataParts[6].trim();
                    if (myId.equals(id) && date.startsWith(desiredYear)) {
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println("Read Error");
        }
    }
    
    //show by date
    public static void ShowOrderByDayRunner(String filename, String desiredDay, String id){
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 9){
                    String myId = dataParts[5].trim();
                    String date = dataParts[6].trim();
                    if(myId.equals(id) && date.equals(desiredDay)){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
    }
    
    //show by month
    public static void ShowOrderByMonthRunner(String filename, String desiredMonth, String id) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String myId = dataParts[5].trim();
                    String date = dataParts[6].trim();
                    if (myId.equals(id) && date.startsWith(desiredMonth)) {
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println("Read Error");
        }
    }
    
    public static void ShowOrderByYearRunner(String filename, String desiredYear, String id) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String myId = dataParts[5].trim();
                    String date = dataParts[6].trim();
                    if (myId.equals(id) && date.startsWith(desiredYear)) {
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println("Read Error");
        }
    }
    
    public static void UpdateOrdStatusFromFile(String fileName, String tempOrdId, String newOrdStatus) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String orderID = dataParts[0].trim();
                    if (orderID.equals(tempOrdId)) {
                        dataParts[3] = newOrdStatus;
                        line = String.join(",", dataParts);
                        String updateLines = line.concat(",").concat("Generated");
                        Write2File(updateLines,"updated.txt", updateList);
                        System.out.println("Status updated: " + line);
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the updated lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }
    
    public static double CalculateTotalRevenue(String filename, String tempId) {
        double totalRevenue = 0.0;

        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String venId = dataParts[2].trim();
                    if (venId.equals(tempId)) {
                        System.out.println(line);
                        double revenue = Double.parseDouble(dataParts[4]);
                        totalRevenue += revenue;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Error processing data: " + ex.getMessage());
        }

        return totalRevenue;
    }
    
    
    public static double CalculateTodayRevenue(String filename, String tempId, String date) {
        double todayRevenue = 0.0;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String venId = dataParts[2].trim();
                    String currDate = dataParts[6].trim();
                    if (venId.equals(tempId) && currDate.equals(date)) {
                        System.out.println(line);
                        double revenue = Double.parseDouble(dataParts[4]);
                        todayRevenue += revenue;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Error processing data: " + ex.getMessage());
    }
        return todayRevenue;
    }
    
    public static void getReview(String filename, String tempId) {
    try {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] dataParts = line.split(",");
            if (dataParts.length == 6) {
                String venId = dataParts[2].trim();
                if (venId.equals(tempId)) {
                    String title = dataParts[3].trim();
                    String rating = dataParts[4].trim();
                    String review = dataParts[5].trim();

                    String processedLine = String.format(title + " rating: " + rating + "*, " + review);
                    
                    System.out.println(processedLine);
                }
            }
        }
        bufferedReader.close();
    } catch (IOException | NumberFormatException ex) {
        System.out.println("Error processing data: " + ex.getMessage());
    }
    }

    public static Order createOrderFromData(String[] orderData) {
        String customerId = orderData[1];
        String vendorId = orderData[2];
        String status = orderData[3];
        String location = orderData[8];
        OrderType orderType = OrderType.valueOf(orderData[5]);

        // Parse item details and create OrderItem objects
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        String[] itemsArray = orderData[7].split(";");
        for (String item : itemsArray) {
            String[] itemParts = item.split(":");
            String itemId = itemParts[0];
            int quantity = Integer.parseInt(itemParts[1].substring(0, 1));
            String extraNotes = itemParts[1].substring(2, itemParts[1].length() - 1);

            // Use the newly added methods to get item details
            String itemName = Item.getItemName(itemId, vendorId);
            String itemDesc = Item.getItemDesc(itemId, vendorId);
            double itemPrice = Double.parseDouble(Item.getItemPrice(itemId, vendorId));

            OrderItem orderItem = new OrderItem(itemId, itemName, itemDesc, itemPrice, quantity, extraNotes);
            orderItems.add(orderItem);
        }

        return new Order(vendorId, customerId, orderItems, orderType, status, location, 0);
    }
    
    public static void UpdateReceiptStatus(String fileName, String receiptId, String newStatus, int location) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length > 1) {
                    String filereceiptId = dataParts[0].trim();
                    if (receiptId.equals(filereceiptId)) {
                        dataParts[location] = newStatus;
                        line = String.join(",", dataParts);
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the updated lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }
    
        
    public static void UpdateOrderUpdateStatus(String fileName, String receiptId, String newStatus) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 6) {
                    String filereceiptId = dataParts[0].trim();
                    if (receiptId.equals(filereceiptId)) {
                        dataParts[4] = newStatus;
                        line = String.join(",", dataParts);
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the updated lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }
    
    public static ArrayList<OrderItem> createOrderItems(String itemDetails, String vendorId){
        String[] itemsArray = itemDetails.split(";");
        ArrayList<OrderItem> orderItems = new ArrayList();
         for (String item : itemsArray) {
             String[] itemParts = item.split(":");
             String itemId = itemParts[0];
             int quantity = Integer.parseInt(itemParts[1].substring(0, 1));
             String extraNotes = itemParts[1].substring(2, itemParts[1].length() - 1);

             // Use the newly added methods to get item details
             String itemName = Item.getItemName(itemId, vendorId);
             String itemDesc = Item.getItemDesc(itemId, vendorId);
             double itemPrice = Double.parseDouble(Item.getItemPrice(itemId, vendorId));

             OrderItem orderItem = new OrderItem(itemId, itemName, itemDesc, itemPrice, quantity, extraNotes);
             orderItems.add(orderItem);
         }
         return orderItems;
    }
    
    public static void updateCustomer(String filename,String idToUpdate,String newPassword,String newName,String newEmail,String newAddress,String newContactNumber)
    {
        
        try{
            FileReader fileReader=new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            StringBuilder updatedContent=new StringBuilder();
            
            Boolean found=false;
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                String[] parts=line.split(",");
                
                if(parts.length==6 && parts[0].equals(idToUpdate))
                {
                    parts[1]=newPassword;
                    parts[2]=newName;
                    parts[3]=newEmail;
                    parts[4]=newAddress;
                    parts[5]=newContactNumber;
                    
                    found=true;
                }
                
                updatedContent.append(String.join(",", parts)).append("\n");
                
                
            }
            bufferedReader.close();
            
            if (!found) 
                {
                    System.out.println("\u001B[31mError: User ID not found. No record updated.\u001B[0m");
                    return;
                }
            
            FileWriter fileWriter=new FileWriter(filename);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(updatedContent.toString());
            bufferedWriter.close();
            
            System.out.println("\u001B[32mFile Updated Successfully.\u001B[0m");
            
        }catch(IOException ex){
            System.out.println("Update Error: "+ex.getMessage());
        }
        
    }
    
    public static void updateVendor(String filename,String idToUpdate,String newPassword,String newName,String newContact)
    {
        
        try{
            FileReader fileReader=new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            StringBuilder updatedContent=new StringBuilder();
            
            Boolean found=false;
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                String[] parts=line.split(",");
                
                if(parts.length==4 && parts[0].equals(idToUpdate))
                {
                    parts[1]=newPassword;
                    parts[2]=newName;
                    parts[3]=newContact;
                    found=true;
                    
                }
                
                updatedContent.append(String.join(",", parts)).append("\n");
                
                
            }
            bufferedReader.close();
            
            if (!found) 
                {
                    System.out.println("\u001B[31mError: User ID not found. No record updated.\u001B[0m");
                    return;
                }
            
            FileWriter fileWriter=new FileWriter(filename);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(updatedContent.toString());
            bufferedWriter.close();
            
            System.out.println("\u001B[32mFile Updated Successfully.\u001B[0m");
        
        }catch(IOException ex){
            System.out.println("Update Error: "+ex.getMessage());
        }
        
    }
    
    public static void updateRunner(String filename,String idToUpdate,String newPassword,String newName,String newContact)
    {
        
        try{
            FileReader fileReader=new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            StringBuilder updatedContent=new StringBuilder();
            
            Boolean found=false;
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                String[] parts=line.split(",");
                
                if(parts.length==4 && parts[0].equals(idToUpdate))
                {
                    parts[1]=newPassword;
                    parts[2]=newName;
                    parts[3]=newContact;
                    found=true;
                }
                
                updatedContent.append(String.join(",", parts)).append("\n");
                
                
            }
            bufferedReader.close();
            
            if (!found) 
                {
                    System.out.println("\u001B[31mError: User ID not found. No record updated.\u001B[0m");
                    return;
                }
            
            FileWriter fileWriter=new FileWriter(filename);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(updatedContent.toString());
            bufferedWriter.close();
            
            System.out.println("\u001B[32mFile Updated Successfully.\u001B[0m");
        
        }catch(IOException ex){
            System.out.println("Update Error: "+ex.getMessage());
        }
        
    }
        
    public static void UpdateOrdTypeFromFile(String fileName, String tempOrdId, String newOrdType) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String orderID = dataParts[0].trim();
                    if (orderID.equals(tempOrdId)) {
                        dataParts[5] = newOrdType;
                        line = String.join(",", dataParts);
                        String updateLines = line.concat(",").concat("Generated");
                        Write2File(updateLines,"updated.txt", updateList);
                        System.out.println("Status updated: " + line);
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the updated lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }
    
    public static void deleteRecord(String userId,String filename,ArrayList<Object>list)
    {
        try{
            // Read the file and update the content
            FileReader fileReader=new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            StringBuilder updatedContent=new StringBuilder();
            
            String line;
            boolean found=false;
            while((line=bufferedReader.readLine())!=null)
            {
                String[] parts=line.split(",");
                
                // check if the current line matches the user id to delete
                if(parts.length>0 && parts[0].equals(userId))
                {
                    //Skip the line if it matches
                    found=true;
                    continue;
                }
                // Rebuild the line with non-deleted information
                updatedContent.append(line).append("\n");
            }
                bufferedReader.close();
                
                if (!found) 
                {
                    System.out.println("\u001B[31mError: User ID not found. No record deleted.\u001B[0m");
                    return;
                }
                
                FileWriter fileWriter=new FileWriter(filename);
                BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
                bufferedWriter.write(updatedContent.toString());
                bufferedWriter.close();
                
                ArrayList<String> records = new ArrayList<>();
                records.removeIf(record -> startsWithUserId(record, userId));
        
                System.out.println("\u001B[32mRecord Deleted Successfully.\u001B[0m");  
        } catch(IOException ex) {
           System.out.println("Delete Error: " + ex.getMessage());
        }
    }
    
    private static boolean startsWithUserId(String record, String userId) {
        return record.startsWith(userId + ",");
    }
    
    public static void topup(String filename,String idToUpdate,Double credit)
    {
        try{
            FileReader fileReader=new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            StringBuilder updatedContent=new StringBuilder();
            
            String line;
            Boolean found=false;
            while ((line=bufferedReader.readLine())!=null)
            {
                String[] parts=line.split(",");
                
                if(parts.length==2 && parts[0].equals(idToUpdate))
                {
                    found=true;
                    // Parse the existing credit as a Double, add the top-up amount, and convert back to String
                    Double currentCredit = Double.parseDouble(parts[1]);
                    parts[1] = String.valueOf(currentCredit + credit);
                    
                    System.out.println("\u001B[32mTop-Up Successfully .\u001B[0m");
                    System.out.println("Current Credit Balance: " + parts[1]);
                }
                
                updatedContent.append(String.join(",", parts)).append("\n");
                
                
            }
            bufferedReader.close();
            
            if (!found) 
                {
                    System.out.println("\u001B[31mError: User ID not found .\u001B[0m");
                    return;
                }
            
            FileWriter fileWriter=new FileWriter(filename);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(updatedContent.toString());
            bufferedWriter.close();
        
        }catch(IOException ex){
            System.out.println("Top-Up Error: "+ex.getMessage());
        }
    }
    
    public static <T> void writeToFile(String id,T o, String filename, ArrayList<T> list){
        list.add(o);
        
        try{
            FileReader filereader=new FileReader(filename);
            BufferedReader bufferedreader=new BufferedReader(filereader);
            
            Boolean found=false;
            String line;
            while((line=bufferedreader.readLine())!=null){
                String [] parts=line.split(",");
                if(parts[0].equals(id)){
                    found=true;
                }   
            }
            
            bufferedreader.close();
            
            if(found){
                System.out.println("\u001B[31mError: Duplicate ID .\u001B[0m");
                return;
            }
            
            File outFile = new File(filename);
            FileWriter fileWriter = new FileWriter(outFile,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(o.toString());

            System.out.println("\u001B[32mData Written To The File .\u001B[0m");
            printWriter.close();
            
        } catch (IOException ex){
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    public static <T> void writePayment(String id,T o, String filename, ArrayList<T> list){
        list.add(o);
        
        try{
            FileReader filereader=new FileReader(filename);
            BufferedReader bufferedreader=new BufferedReader(filereader);
            
            Boolean found=false;
            String line;
            while((line=bufferedreader.readLine())!=null){
                String [] parts=line.split(",");
                if(parts[0].equals(id)){
                    found=true;
                }
               
            }
            bufferedreader.close();
            
            if(found){
                
                return;
            }
            
            File outFile = new File(filename);
            FileWriter fileWriter = new FileWriter(outFile,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(o.toString());

            
            printWriter.close();
            
        } catch (IOException ex){
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    public static double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
        public static int CountDeliRun(String filename, String Id){
        int Count = 0;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 9){
                    String myId = dataParts[5].trim();
                    String myStatus = dataParts[4].trim();
                    if(myId.equals(Id) && myStatus.equals("FINISHED")){
                        Count++;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
        
        return Count;
    }
    
    
public static void UpdateTaskStatusFromFile(String fileName, String newTaskId, String newTaskStatus, String newDeliId) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) { 
                    String taskId = dataParts[0].trim();
                    if(taskId.equals(newTaskId)){
                        dataParts[4] = newTaskStatus;
                        dataParts[5] = newDeliId;
                        line = String.join(",", dataParts);
                        System.out.println("Status updated: " + line);
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the updated lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }


    public static void getDeliRunReview(String filename, String tempId) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 4) {
                    String DeliId = dataParts[1].trim();
                    if (DeliId.equals(tempId)) {
                        String title = dataParts[0].trim();
                        String rating = dataParts[2].trim();
                        String review = dataParts[3].trim();

                        String processedLine = String.format(title + " rating: " + rating + "*, " + review);

                        System.out.println(processedLine);
                    }
                }
            }
            bufferedReader.close();
            } catch (IOException | NumberFormatException ex) {
                System.out.println("Error processing data: " + ex.getMessage());
            }
    }

    public static void ShowPendingTask(String filename, String DeliID){
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if(dataParts.length == 9){
                    String myStatus = dataParts[4].trim();
                    if(myStatus.equals("Pending") && dataParts[5].equals(DeliID)){
                        System.out.println(line);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
    }
    
    public static ArrayList<Integer> CountTask(String filename, String DeliID){
        ArrayList<Integer> Count = new ArrayList();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int newCount = 0;
            int activeCount = 0;
                
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");

                
                if(dataParts.length == 9){
                    String myStatus = dataParts[4].trim();
                    if(myStatus.equals("Pending") && dataParts[5].equals(DeliID)){
                        newCount++;
                    }
                    if(myStatus.equals("ON PROGRESS") && dataParts[5].equals(DeliID)){
                        activeCount++;
                    }
                }
            }
            Count.add(newCount);
            Count.add(activeCount);
            
            bufferedReader.close();
        } catch (IOException ex){
                System.out.println("Read Error");
        }
        
        return Count;
    }
    
     public static void UpdateRunner(String fileName, String receiptId, String newRunnerId) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length > 1) {
                    String filereceiptId = dataParts[7].trim();
                    if (receiptId.equals(filereceiptId)) {
                        dataParts[5] = newRunnerId;
                        line = String.join(",", dataParts);
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the updated lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }
     
    public static void findMaxValues(String filename, String type, String file2) {
        double maxDouble = Double.NEGATIVE_INFINITY;
        String maxId = null;
        
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 2) {
                    String idType = dataParts[0].substring(0, 1);
                    
                    if (idType.equals(type)) {
                        double currentDouble = Double.parseDouble(dataParts[1].trim());

                        if (currentDouble > maxDouble) {
                            maxDouble = currentDouble;
                            maxId = dataParts[0];
                        }
                    }
                }
            }

            if (maxId != null) {
                String name = FH.GetNameFromFile(file2, maxId);
                int ordCount = (int) (maxDouble / 5);
                System.out.println("--------------------------------------------------------"); 
                System.out.println("The best: " + name + ", with total earning of " + maxDouble + " (" + ordCount + " orders)");
                
            } else {
                System.out.println("No matching entries found for type: " + type);
            }

        } catch (IOException ex) {
            System.err.println("Read Error: " + ex.getMessage());
        }
    }
    
    public static void findMaxValuesVendors(String filename, String type, String file2, String file3) {
        double maxDouble = Double.NEGATIVE_INFINITY;
        String maxId = null;
        
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 2) {
                    String idType = dataParts[0].substring(0, 1);
                    
                    if (idType.equals(type)) {
                        double currentDouble = Double.parseDouble(dataParts[1].trim());

                        if (currentDouble > maxDouble) {
                            maxDouble = currentDouble;
                            maxId = dataParts[0];
                        }
                    }
                }
            }

            if (maxId != null) {
                String name = FH.GetNameFromFile(file2, maxId);
                int ordCount = FH.CountAllOrder(file3, maxId);
                System.out.println("--------------------------------------------------------"); 
                System.out.println("The best: " + name + ", with total earning of " + maxDouble + " (" + ordCount + " orders)");
                
            } else {
                System.out.println("No matching entries found for type: " + type);
            }

        } catch (IOException ex) {
            System.err.println("Read Error: " + ex.getMessage());
        }
    }
    
    public static String checkVoucher(String filename,String code){
        try{
            FileReader fileReader=new FileReader(filename);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            StringBuilder updatedContent=new StringBuilder();
            
            Boolean found=false;
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                String[] parts=line.split(",");
                
                if(parts[0].equals(code))
                {
                    found=true;
                    return parts[1];
                }
            }
            bufferedReader.close();
            
            if (!found) 
                {
                    Scanner sc=new Scanner(System.in);
                    System.out.println("\u001B[31mError: Voucher not found.\u001B[0m");
                    System.out.println("Wrong Voucher Code, Try Again (Yes/No) : ");
                    String continueInput=sc.next();
                    if(continueInput.equals("yes")){
                        Voucher.check_voucher(0);
                    }
                    else{
                    return null;}
                }
            
        }catch(IOException ex){
            System.out.println("Update Error: "+ex.getMessage());
        }
        return null;
    }
    
    
    public static String GetDineInTakeawayFromFile(String FileName, String id){
        try{
            FileReader fileReader = new FileReader(FileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                String myId = dataParts[2].trim();
                String orderStat = dataParts[3].trim();
                String orderType = dataParts[5].trim();
                if(myId.equals(id) && orderStat.equals("Accepted") && !orderType.equals("DELIVERY")){
                    System.out.println(line);
                }
            }
            bufferedReader.close();
        
        } catch (IOException ex) {
            System.out.println("Search Error");
        }
        
        return null;
    }
    
    public static void UpdateOrdTypeFromFile(String fileName, String Id) {
        ArrayList<String> updatedLines = new ArrayList<>();
        boolean itemUpdated = false; // Flag to track if the item was found and updated

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 9) {
                    String itemID = dataParts[0].trim();
                    if (itemID.equals(Id)) {
                        dataParts[3] = "Finished";
                        line = String.join(",", dataParts);
                        System.out.println("Item updated: " + line);
                        itemUpdated = true; // Set the flag to true
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
            return; // Exit the method if an error occurs
        }

        // Write the updated lines back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }
            if (itemUpdated) {
                System.out.println("File updated successfully.");
            } else {
                System.out.println("Wrong input: Item not found for the given ID and name.");
            }
        } catch (IOException ex) {
            System.out.println("Error writing updated file: " + ex.getMessage());
        }
    }
}
