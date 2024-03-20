package Assignment;

import java.util.Scanner;
import java.util.ArrayList;

public class Admin extends User {
    static Scanner sc =new Scanner(System.in);
    
    public static String AdminId;
    public static String username="admin";
    public static String pass="Admin2023";
    public static int AdminContact;
    
    public Admin(String adminId, String adminName, int adminContact) 
    {
        super(adminId, adminName, adminContact);
        this.AdminId = adminId;
        this.username = adminName;
        this.AdminContact = adminContact;
    }
    
    @Override
    public void performAction()
    {
        boolean continueLoop = true;
        
        while (continueLoop == true) {
            System.out.println("-------------------Administrator Menu-------------------");
            System.out.println("1. User Registration");
            System.out.println("2. Top-up Customer Credit");
            System.out.println("3. Generate Top-Up Receipt");
            System.out.println("4. Create Voucher");
            System.out.println("5. Log Out");
            System.out.println("--------------------------------------------------------");
        
            System.out.print("Enter Your Choice : ");
            int choice=sc.nextInt();
        
            switch (choice)
            {
                case 1:
                   user_registration();
                    break;
                case 2:
                    topupCustomer();
                    break;
                case 3:
                    read_receipt();
                    break;
                case 4:
                    Voucher.voucher_menu();
                    break;
                case 5:
                    end_code();
                    continueLoop = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }
    }
    
    public static void user_registration()
    {
        System.out.println("--------------------------------------------------------");
        System.out.println("User Registration");
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Vendor");
        System.out.println("2. Customer");
        System.out.println("3. Runner");
        System.out.println("4. Back To Administrator Menu");
        System.out.println("--------------------------------------------------------");
        
        System.out.print("Enter Your Choice : ");
        int choice=sc.nextInt();
        
        if(choice>3){return;}
        else
        {
            user_menu(choice);
        }
    }
    
    public static void user_menu(int a)
    {
        String name="";
        if(a==1)
        {
            name="Vendor";
        }
        else if(a==2)
        {
            name="Customer";
        }
        else if(a==3)
        {
            name="Runner";
        }
        
        
        System.out.println("--------------------------------------------------------");
        System.out.println(name+" Menu");
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Create "+name);
        System.out.println("2. Read "+name);
        System.out.println("3. Update "+name);
        System.out.println("4. Delete "+name);
        System.out.println("5. Back To User Registration");
        System.out.println("---------------------------------------------------------");
        
        System.out.print("Enter Your Choice : ");
        int choice=sc.nextInt();
        
        if(a==1)
        {
            if(choice==1){create_vendor();}
            else if(choice==2){read_vendor();}
            else if(choice==3){update_vendor();}
            else if(choice==4){delete_vendor();}
            else if(choice==5){user_registration();}
        }
        else if(a==2)
        {
            if(choice==1){create_customer();}
            else if(choice==2){read_customer();}
            else if(choice==3){update_customer();}
            else if(choice==4){delete_customer();}
            else if(choice==5){user_registration();}
        }
        else if(a==3)
        {
            if(choice==1){create_runner();}
            else if(choice==2){read_runner();}
            else if(choice==3){update_runner();}
            else if(choice==4){delete_runner();}
            else if(choice==5){user_registration();}
        }
        
    }
    
    private static String vendorId;
    private static String vendorPassword;
    private static String vendorName;
    private static String vendorContact;
    private static Double vendorCredit=0.0;
    
    public static void create_vendor()
    {
        String continueInput;
        do{
            System.out.println("----------------------CREATE VENDOR----------------------");
            
            vendorId = getValidId("V","Vendor");

            System.out.print("Enter Vendor Password \t: ");
            vendorPassword=sc.next();
            System.out.print("Enter Vendor Name \t: ");
            vendorName=sc.nextLine();//use double because error occured when run the file (still do not know the error)
            vendorName=sc.nextLine();
            System.out.print("Enter Vendor Contact \t: ");
            vendorContact=sc.next();
            
            String line=vendorId+','+vendorPassword+','+vendorName+','+vendorContact;
            String line2=vendorId+','+vendorCredit;
            FH.writeToFile(vendorId,line,"vendors.txt",FH.vendorsList);
            FH.writePayment(vendorId,line2, "payment.txt", FH.paymentList);
            
            System.out.println("---------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) \t: ");
            continueInput =sc.next().toLowerCase();
            
            if (!continueInput.equalsIgnoreCase("yes"))
            {
                user_menu(1);
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
        
        
    }
    
    public static void read_vendor(){
        
        System.out.println("----------------------------VENDOR LIST----------------------------");
        System.out.printf("%-10s %-15s %-25s %-1s","ID","Password","Name","Contact Number");
        System.out.println("\n-------------------------------------------------------------------");
        
        ArrayList<String>vendorData=FH.ReadFromFile("vendors.txt");
        for(String line:vendorData){
            String[]part=line.split(",");
            System.out.printf("%-10s %-15s %-25s %-1s \n",part[0],part[1],part[2],part[3]);
        }
        
        System.out.println("\n-------------------------------------------------------------------");
        System.out.print("Back To Vendor Menu ? (yes/no) : ");
        String userInput=sc.next();
        
        if(userInput.equalsIgnoreCase("yes")){
            user_menu(1);
        }
        else{
            end_code();
        }
    }
    
    public static void update_vendor()
    {
        String vendorId;
        String vendorNewPassword;
        String vendorNewName;
        String vendorNewContact;
        String continueInput;
        
        do{
            System.out.println("----------------------UPDATE VENDOR----------------------");
            System.out.print("Enter The Vendor ID To Update     : ");
            vendorId=sc.next().toUpperCase();

            System.out.print("Enter The New Password For Vendor : ");
            vendorNewPassword=sc.next();
            System.out.print("Enter The New Name For Vendor     : ");
            vendorNewName=sc.nextLine();
            vendorNewName=sc.nextLine();
            System.out.print("Enter The New Contact For Vendor  : ");
            vendorNewContact=sc.next();

            FH.updateVendor("vendors.txt", vendorId, vendorNewPassword, vendorNewName, vendorNewContact);
            System.out.println("---------------------------------------------------------");
             
            System.out.print("Do you want to continue? (yes/no) : ");
            continueInput =sc.next().toLowerCase();

            if (!continueInput.equalsIgnoreCase("yes"))
            {
                user_menu(1);
            }
        } while("yes".equalsIgnoreCase(continueInput));
        
    }
    
    public static void delete_vendor()
    {
        String vendorId;
        String ask;
        String continueInput;
        do{
            System.out.println("----------------------DELETE VENDOR----------------------");
            System.out.print("Enter The Vendor ID To Delete          : ");
            vendorId=sc.next().toUpperCase();

            System.out.print("Are You Sure To Delete This ? (yes/no) : ");
            ask=sc.next();
            if(ask.equalsIgnoreCase("yes"))
            {
                FH.deleteRecord(vendorId, "vendors.txt", FH.vendorsList);
                FH.deleteRecord(vendorId, "payment.txt", FH.paymentList);
            }
            
            System.out.println("---------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) \t: ");
            continueInput =sc.next().toLowerCase();
            
            if(!continueInput.equalsIgnoreCase("yes"))
            {
                user_menu(1);
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
    }
    
    private static String customerId;
    private static String customerPassword;
    private static String customerName;
    private static String customerEmail;
    private static String customerAddress;
    private static double creditBalance;
    private static String customerPhoneNumber;
    
    public static void create_customer()
    {
        String continueInput;
        do{
            System.out.println("----------------------CREATE CUSTOMER----------------------");
            
            customerId=getValidId("C","Customer");
            
            System.out.print("Enter Customer Password       : ");
            customerPassword=sc.next();
            System.out.print("Enter Customer Name           : ");
            customerName=sc.nextLine();
            customerName=sc.nextLine();
            System.out.print("Enter Customer E-Mail         : ");
            customerEmail=sc.next();
            System.out.print("Enter Customer Address        : ");
            customerAddress=sc.nextLine();
            customerAddress=sc.nextLine(); 
            System.out.print("Enter Customer Contact Number : ");
            customerPhoneNumber=sc.next();

            creditBalance=0;

            String line=customerId+','+customerPassword+','+customerName+','+customerEmail+','+customerAddress+','+customerPhoneNumber;
            String line2=customerId+','+creditBalance;
            FH.writeToFile(customerId,line,"customers.txt",FH.customersList);
            FH.writePayment(customerId,line2, "payment.txt", FH.paymentList);
            
            System.out.println("---------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) \t: ");
            continueInput =sc.next().toLowerCase();
                       if (!continueInput.equalsIgnoreCase("yes"))
            {
                user_menu(2);
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
    }
    
    public static void read_customer(){
        System.out.println("------------------------------------------------------CUSTOMER LIST------------------------------------------------------");
        System.out.printf("%-10s %-15s %-25s %-25s %-25s %-1s", "ID","Password","Name","E-Mail","Address","Contact Number");
        System.out.println("\n-------------------------------------------------------------------------------------------------------------------------");
        
        ArrayList<String>customerData =FH.ReadFromFile("customers.txt");
        for(String line:customerData){
            String [] part=line.split(",");
            System.out.printf("%-10s %-15s %-25s %-25s %-25s %-1s\n", part[0],part[1],part[2],part[3],part[4],part[5]);
        }
        System.out.println("\n-------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Back To Customer Menu ? (yes/no) : ");
        String userInput=sc.next();
        
        if(userInput.equalsIgnoreCase("yes")){
            user_menu(2);
        }
        else{
            end_code();
        }
    }
    
    public static void update_customer()
    {
        String customerId;
        String customerNewPasswordd;
        String customerNewName;
        String customerNewEmail;
        String customerNewAddress;
        String continueInput;
        String customerNewContactNumber;
        
        do{
            System.out.println("----------------------UPDATE CUSTOMER----------------------");
            System.out.print("Enter The Customer ID                       : ");
            customerId=sc.next().toUpperCase();
            System.out.print("Enter The New Password For Customer         : ");
            customerNewPasswordd=sc.next();
            System.out.print("Enter The New Name For Customer             : ");
            customerNewName=sc.nextLine();
            customerNewName=sc.nextLine();
            System.out.print("Enter The New E-mail For Customer           : ");
            customerNewEmail=sc.next();
            System.out.print("Enter The New Address For Customer          : ");
            customerNewAddress=sc.nextLine();
            customerNewAddress=sc.nextLine();
            System.out.print("Enter The New Contact Number For Customer   : ");
            customerNewContactNumber=sc.next();

            FH.updateCustomer("customers.txt", customerId, customerNewPasswordd, customerNewName, customerNewEmail, customerNewAddress,customerNewContactNumber);
            
            System.out.println("---------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no)   : ");
            continueInput =sc.next().toLowerCase();
            
            if (!continueInput.equalsIgnoreCase("yes"))
            {
                user_menu(2);
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
        
            
    }
    public static void delete_customer()
    {
        String customerId;
        String ask;
        String continueInput;
        do{
            System.out.println("----------------------DELETE CUSTOMER----------------------");
            System.out.print("Enter The Customer ID To Delete        : ");
            customerId=sc.next().toUpperCase();

            System.out.print("Are You Sure To Delete This ? (yes/no) : ");
            ask=sc.next();
            if(ask.equalsIgnoreCase("yes"))
            {
                FH.deleteRecord(customerId, "customers.txt", FH.customersList);
                FH.deleteRecord(customerId, "payment.txt", FH.paymentList);
            }
            
            System.out.println("-----------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) \t: ");
            continueInput =sc.next().toLowerCase();
            
            if (!continueInput.equalsIgnoreCase("yes"))
            {
                user_menu(2);
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
    }
    
    private static String runnerId;
    private static String runnerPassword;
    private static String runnerName;
    private static String runnerContact;
    private static Double runnerCredit=0.0;
    
    public static void create_runner(){
        String continueInput;
        do{
            System.out.println("----------------------CREATE RUNNER----------------------");
            
            runnerId=getValidId("D","Delivery Runner");

            System.out.print("Enter Runner Password       : ");
            runnerPassword=sc.next();
            System.out.print("Enter Runner Name           : ");
            runnerName=sc.nextLine();
            runnerName=sc.nextLine();
            System.out.print("Enter Runner Contact Number : ");
            runnerContact=sc.next();

            String line=runnerId+","+runnerPassword+","+runnerName+","+runnerContact;
            String line2=runnerId+","+runnerCredit;
            FH.writeToFile(runnerId,line, "runners.txt",FH.runnerList);
            FH.writePayment(runnerId,line2, "payment.txt", FH.paymentList);
            
            System.out.println("---------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) \t: ");
            continueInput =sc.next().toLowerCase();
            
            if (!continueInput.equalsIgnoreCase("yes")){
                user_menu(3);
            }
            
        } while("yes".equalsIgnoreCase(continueInput));
    }   

    public static void read_runner()
    {
        System.out.println("-----------------------RUNNER LIST-----------------------");
        System.out.printf("%-10s %-15s %-15s %-1s","ID","Password","Name","Contact Number");
        System.out.println("\n---------------------------------------------------------");
        
        ArrayList<String>runnerData=FH.ReadFromFile("runners.txt");
        for(String line:runnerData){
            String [] part=line.split(",");
            System.out.printf("%-10s %-15s %-15s %-1s \n",part[0],part[1],part[2],part[3]);
        }
        System.out.println("---------------------------------------------------------");
        System.out.print("Back To Runner Menu ? (yes/no) : ");
        String userInput=sc.next();
        
        if(userInput.equalsIgnoreCase("yes")){
            user_menu(3);
        }
        else{
            end_code();
        }
    }
    
    public static void update_runner()
    {
        String runnerId;
        String runnerNewPassword;
        String runnerNewName;
        String runnerNewContact;
        String continueInput;
        
        do{
            
            System.out.println("----------------------UPDATE RUNNER----------------------");

            System.out.print("Enter The Vendor ID               : ");
            runnerId=sc.next().toUpperCase();
            System.out.print("Enter The New Password For Runner : ");
            runnerNewPassword=sc.next();
            System.out.print("Enter The New Name For Runner     : ");
            runnerNewName=sc.nextLine();
            runnerNewName=sc.nextLine();
            System.out.print("Enter The New Contact For Runner  : ");
            runnerNewContact=sc.next();

            FH.updateRunner("runners.txt", runnerId, runnerNewPassword, runnerNewName, runnerNewContact);

            System.out.println("---------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) : ");
            continueInput =sc.next().toLowerCase();
            
            if (!continueInput.equalsIgnoreCase("yes"))
                {
                    user_menu(3);
                }
            
        }while("yes".equalsIgnoreCase(continueInput));
        
        
    }
    public static void delete_runner()
    {
        String runnerId;
        String ask;
        String continueInput;
        do{
            System.out.println("----------------------DELETE RUNNER----------------------");
            System.out.print("Enter The Runner ID To Delete          : ");
            runnerId=sc.next().toUpperCase();

            System.out.print("Are You Sure To Delete This ? (yes/no) : ");
            ask=sc.next();
            if(ask.equalsIgnoreCase("yes"))
            {
                FH.deleteRecord(runnerId, "runners.txt", FH.runnerList);
                FH.deleteRecord(runnerId, "payment.txt", FH.paymentList);
            }
            
            System.out.println("---------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) \t: ");
            continueInput =sc.next().toLowerCase();
            
            if(!continueInput.equalsIgnoreCase("yes"))
            {
                user_menu(3);
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
        
    }
    
    public static void topupCustomer(){
        
        String customerId = null;
        Double amount = 0.0;
        String continueInput;
        String password;
        
        do{
            System.out.println("----------------------TOPUP CUSTOMER----------------------");
            System.out.println("\u001B[31mWARNING : THIS IS AUTHORIZED USER ONLY !!!\u001B[0m");
            System.out.print("ADMIN Password : ");
            password=sc.nextLine();
            password=sc.nextLine();
            
            if(password.equals(pass)){
                System.out.println("\u001B[32mACCESS GRANTED .\u001B[0m");
                System.out.print("Enter The Customer ID   : ");
                customerId=sc.next().toUpperCase();
                System.out.print("Enter The Top-Up Amount : ");
                amount=sc.nextDouble();
                                                                                                                                                        
                FH.topup("payment.txt", customerId, amount);
                generate_receipt(amount, customerId, "TopUp");
            }
            
            System.out.println("----------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) \t: ");
            continueInput =sc.next().toLowerCase();
           
            
            if (!continueInput.equalsIgnoreCase("yes")){
                return;
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
    }
    
    public static void end_code(){
        System.out.println("\u001B[32mTHANK YOU FOR USING THIS MENU .\u001B[0m");
    }
    
    
    
    public static void generate_receipt(double amount, String cusId, String transaction_type)
    {
        ArrayList<String> File = FH.ReadFromFile("receipt.txt");
        String currentReceiptId = "RC0";
        
        for(String line : File){
            if(File.isEmpty()){
                break;
            }
            
            String[] dataParts = line.split(",");
            currentReceiptId = dataParts[0];
        }
        int numericPart = Integer.parseInt(currentReceiptId.substring(2)) + 1;
        currentReceiptId = String.format("RC%01d", numericPart);
        
        
        double balance = 0.0;
        ArrayList<String> cusData = FH.ReadFromFile("payment.txt");
        for(String line : cusData){
            String[] cusParts = line.split(",");
            String fileCusId = cusParts[0];
            if(cusId.equals(fileCusId)){
                balance = Double.parseDouble(cusParts[1]);
            }
        }
        
        StringBuilder receiptData = new StringBuilder();
        receiptData.append(currentReceiptId).append(",");
        receiptData.append(cusId).append(",");
        receiptData.append(balance).append(",");
        receiptData.append(amount).append(",");
        receiptData.append("Generated").append(",");
        receiptData.append(transaction_type);
        
        FH.Write2File(receiptData, "receipt.txt", FH.receiptList);
    }
    
    private static String getValidId(String prefix, String role) {
        String id;

        do {
            System.out.print("Enter " + role + " ID (" + prefix + " followed by one or more digits): ");
            id = sc.next().toUpperCase();
            if (!isValidId(id, prefix)) {
                System.out.println("\u001B[31mWarning: " + prefix + " followed by one or more digits\u001B[0m");
            }

        } while (!isValidId(id, prefix));

        return id;
    }

    private static boolean isValidId(String id, String prefix) {
        // Check if the ID starts with the specified prefix and is followed by one or more digits
        return id.matches(prefix + "\\d+");
    }
    
    public static void read_receipt(){
        System.out.println("-----------------------------------------Transaction History-----------------------------------------");
        System.out.printf("%-15s %-15s %-25s %-25s %-10s","Receipt ID","Customer ID","Current Balance","Previous Balance","Status");
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        
        ArrayList <String> receiptData=FH.ReadFromFile("receipt.txt");
        for(String line:receiptData){
            String [] parts=line.split(",");
            if(parts[5].equalsIgnoreCase("topup")){
                System.out.printf("%-15s %-15s %-25s %-25s %-10s\n",parts[0],parts[1],parts[2],parts[3],parts[5]);
            }
            
        }
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.print("Back To Administrator Menu (Press Any Key And Enter) : ");
        String userInput=sc.next();
        
        if(!userInput.equalsIgnoreCase("")){
            return;
        }
        
    }
    
}