package Assignment;

import java.util.Scanner;
import java.util.ArrayList;

public class Item {
    public static Vendor VenID;
    public static String ItemID;
    private String ItemName;
    private String ItemDesc;
    private double ItemPrice;
    public static String currentItemId;
    
    public Item(Vendor VenID, String ItemID, String ItemName, String ItemDesc, double ItemPrice){
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.ItemDesc = ItemDesc;
        this.ItemPrice = ItemPrice;
    }
    
    public Item(String line) {
        String[] dataParts = line.split(",");
        this.ItemID = dataParts[1];
        this.ItemName = dataParts[2];
        this.ItemDesc = dataParts[3];
        this.ItemPrice = Double.parseDouble(dataParts[4]);
    }
    
    public String getItemId() {
        return ItemID;
    }
    
    public Vendor getVendorId() {
        return VenID;
    }
    
    public String getName() {
        return ItemName;
    }

    public String getDescription() {
        return ItemDesc;
    }

    public double getPrice() {
        return ItemPrice;
    }
    
    
    public void setItemName(String ItemName){
        this.ItemName = ItemName;
    }
    
    public void setItemDesc(String ItemDesc){
        this.ItemDesc = ItemDesc;
    }
    
    public void setItemPrice(double ItemPrice){
        this.ItemPrice = ItemPrice;
    }
    
 
    
    public static void createItem(){
        Scanner Sc = new Scanner(System.in);
        boolean continueit = true;
        
        
        while (continueit == true) {
            System.out.println("----------------------------");
            System.out.println(Vendor.VenID);
            System.out.println("Creating new menu...");
            System.out.println("----------------------------");
            
            FH.GetIdFromFile("menus.txt", Vendor.VenID);
            int numericPart = Integer.parseInt(currentItemId.substring(1)) + 1;
            currentItemId = String.format("I%01d", numericPart);
            
            System.out.print("Enter item name: ");
            String newItemName = Sc.nextLine();
            
            System.out.print("Enter item desc: ");
            String newItemDesc = Sc.nextLine();
            
            System.out.print("Enter item price: ");
            double newItemPrice = Sc.nextDouble();
            Sc.nextLine();
            System.out.println("----------------------------");
            
            String Line = Vendor.VenID + "," + currentItemId + "," +newItemName + "," + newItemDesc + "," + newItemPrice;
            FH.Write2File(Line, "menus.txt", FH.menuList);
            System.out.println("Successfully added");

            System.out.print("Do you wish to continue (1 = yes, 0 = no)? ");
            int cont = Sc.nextInt();
            Sc.nextLine();
            if (cont == 1){
                continueit = true;
            } else if (cont == 0) {
                return;
            } else {
                System.out.println("Invalid choice. Exiting.");
                continueit = false;
            }
        }
    }

    
    public static void readItem(){
        Scanner Sc = new Scanner(System.in);
        boolean continueit = true;
        
        
        while (continueit == true) {

            System.out.println("----------------------------");
            System.out.println("Searching for item...");
            System.out.println("----------------------------");
            
            System.out.print("Enter item ID to search: ");
            String input = Sc.nextLine();
            FH.SearchFromFile("menus.txt", input);
            
            System.out.print("Do you wish to continue (1 = yes, 0 = no)? ");
            int cont = Sc.nextInt();
            Sc.nextLine();
            if (cont == 1){
                continueit = true;
            } else if (cont == 0) {
                return;
            } else {
                System.out.println("Invalid choice. Exiting.");
                continueit = false;
            }   
        }
    }
    
    public static void updateItem(){
        Scanner Sc = new Scanner(System.in);
        boolean continueit = true;
        
        
        while (continueit == true) {

            System.out.println("----------------------------");
            System.out.println("Updating an item...");
            System.out.println("----------------------------");
            
            System.out.print("Enter which item name to update: ");
            String newItemName = Sc.nextLine();
            
            System.out.print("Enter the new item description: ");
            String newItemDesc = Sc.nextLine();
            
            
            System.out.print("Enter the new price: ");
            double newItemPrice = Sc.nextDouble();
            
            
            FH.UpdateItemFromFile("menus.txt", Vendor.VenID, newItemName, newItemDesc, newItemPrice);
            
            
            System.out.print("Do you wish to continue (1 = yes, 0 = no)? ");
            int cont = Sc.nextInt();
            Sc.nextLine();
            if (cont == 1){
                continueit = true;
            } else if (cont == 0) {
                return;
            } else {
                System.out.println("Invalid choice. Exiting.");
                continueit = false;
            }
            
        }
    }
    
    public static void deleteItem(){
        Scanner Sc = new Scanner(System.in);
        boolean continueit = true;
        
        
        while (continueit == true) {

            System.out.println("----------------------------");
            System.out.println("Deleting an item...");
            System.out.println("----------------------------");
            
//            FH.ShowMenu("menus.txt", Vendor.VenID);
            System.out.print("Enter item id to delete: ");
            String deleteItemId = Sc.nextLine();
            
            System.out.print("Confirm deletion by entering item name: ");
            String deleteItemName = Sc.nextLine();
            
            FH.DeleteItemFromFile("menus.txt", deleteItemId, deleteItemName);
            
            
            System.out.print("Do you wish to continue (1 = yes, 0 = no)? ");
            int cont = Sc.nextInt();
            Sc.nextLine();
            if (cont == 1){
                continueit = true;
            } else if (cont == 0) {
                return;
            } else {
                System.out.println("Invalid choice. Exiting.");
                continueit = false;
            }
            
        }
    }
    
    public static String getItemName(String itemId, String vendorId) {
        ArrayList<String> menuData = FH.ReadFromFile("menus.txt");

        for (String line : menuData) {
            String[] dataParts = line.split(",");
            String fileItemId = dataParts[1];
            String fileVendorId = dataParts[0];

            if (fileItemId.equals(itemId) && fileVendorId.equals(vendorId)) {
                return dataParts[2]; // Assuming index 2 is the item name
            }
        }

        return "Item not found";
    }

    public static String getItemDesc(String itemId, String vendorId) {
        ArrayList<String> menuData = FH.ReadFromFile("menus.txt");

        for (String line : menuData) {
            String[] dataParts = line.split(",");
            String fileItemId = dataParts[1];
            String fileVendorId = dataParts[0];

            if (fileItemId.equals(itemId) && fileVendorId.equals(vendorId)) {
                return dataParts[3]; // Assuming index 3 is the item description
            }
        }

        return "Item not found";
    }

    public static String getItemPrice(String itemId, String vendorId) {
        ArrayList<String> menuData = FH.ReadFromFile("menus.txt");

        for (String line : menuData) {
            String[] dataParts = line.split(",");
            String fileItemId = dataParts[1];
            String fileVendorId = dataParts[0];

            if (fileItemId.equals(itemId) && fileVendorId.equals(vendorId)) {
                return dataParts[4]; // Assuming index 4 is the item price
            }
        }

        return "Item not found";
    }
    
    /*
    public static void confirmation(){
    System.out.print("Do you wish to continue (1 = yes, 0 = no)? ");
            int cont = Sc.nextInt();
            Sc.nextLine();
            if (cont == 1){
                continueit = true;
            } else if (cont == 0) {
                continueit = false;
            } else {
                System.out.println("Invalid choice. Exiting.");
                continueit = false;
            }
    
    }        
    */
    
    /*
    public void updateItem(int ItemID, String newName, String newDesc, double newPrice){
    for(Item item : items){
        if(item.getItemID() == ItemID){
               item.setItemName(newName);
               item.setItemDesc(newDesc);
               item.setItemPrice(newPrice);
               System.out.println("Item updated : " + item);
               return;
            }
        }
        System.out.println("Item not found with ID: " + ItemID);
    }
    
    */
    
}