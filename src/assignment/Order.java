package Assignment;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Order {
    private static String orderId;
    private static ArrayList<OrderItem> items;
    private static String status;
    private static double totalAmount;
    private static OrderType orderType;
    private static String VenID;
    private static String CusID;
    private static String location;
    private static String currentOrderId; 
    private static Scanner Sc = new Scanner(System.in);
    
    public Order(String VenID, String CusID, ArrayList<OrderItem> items, OrderType orderType, String location, double discount) {
        this.items = items;
        this.status = "Placed";
        this.totalAmount = calculateTotalAmount(discount);
        this.orderType = orderType;
        this.CusID = CusID;
        this.VenID = VenID;
        this.location = location;
    }
    
    public Order(String VenID, String CusID, ArrayList<OrderItem> items, OrderType orderType, String status, String location, double discount) {
        this.items = items;
        this.status = status;
        this.totalAmount = calculateTotalAmount(discount);
        this.orderType = orderType;
        this.CusID = CusID;
        this.VenID = VenID;
        this.location = location;
    }    

    public String getOrderId() {
        return orderId;
    }
    
    public String getCusId() {
        return CusID;
    }

    public ArrayList<OrderItem> getOrderItems(){
        return items;
    }
    
    public String getVendorId() {
        return VenID;
    }
    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderType() {
        return orderType.toString();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setOrderType(OrderType orderType){
        this.orderType = orderType;
    }
    
    public static void placeOrder() {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        String VenId = "";
        // Display menu
        ArrayList<String> menu = Customer.viewMenu();
        System.out.println("0. Finish Order");
        System.out.println("");
        
        // Allow customer to select items for the order
        while (true) {
            System.out.print("Enter item ID to add to the order: ");
            String itemId = "I" + Sc.nextLine();
            if (itemId.equals("I0")) {
                break;
            }
           
            // Find the selected item by ID
            String selectedItem = null;
            for (String item : menu) {
                String[] dataParts = item.split(",");
                VenId = dataParts[0];
                if (dataParts[1].equals(itemId)) {
                    selectedItem = item;
                    break;
                }
            }
            
            if (selectedItem != null) {
                String[] itemElements = selectedItem.split(",");
                // Prompt the user for quantity and extra notes
                System.out.print("Enter quantity for " + itemElements[2] + ": ");
                int quantity = Sc.nextInt();
                Sc.nextLine(); // Consume the newline character

                // Assume that the user knows that they can skip this part by hitting enter
                System.out.print("Enter extra notes for " + itemElements[2] + ": ");
                String extraNotes = Sc.nextLine();
            
                // Create the OrderItem
                OrderItem orderItem = new OrderItem(itemElements[1], itemElements[2], itemElements[3], Double.parseDouble(itemElements[4]), quantity, extraNotes);
                orderItems.add(orderItem);
                System.out.println(itemElements[2] + " added to the order.");
            } else {
                System.out.println("Invalid item ID. Please try again.");
            }
        }

        // Choose order type (dine-in, takeaway, or delivery)
        System.out.println("");
        System.out.println("Choose order type:");
        System.out.println("1. Dine-in");
        System.out.println("2. Takeaway");
        System.out.println("3. Delivery");
        System.out.print("Enter order type: ");
        int orderTypeChoice = Sc.nextInt();
        System.out.println("");

        switch (orderTypeChoice) {
            case 1:
                orderType = OrderType.DINE_IN;
                location = "DINEIN";
                break;
            case 2:
                orderType = OrderType.TAKEAWAY;
                location = "TAKEAWAY";
                break;
            case 3:
                orderType = OrderType.DELIVERY;
                System.out.print("Enter the address to deliver to: ");
                Sc.nextLine();
                location = Sc.nextLine();
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Takeaway.");
                orderType = OrderType.TAKEAWAY;
        }
       
        // Create the order if there are items in the order
            if (!orderItems.isEmpty()) {
                String disc = null;
                Order order = new Order(Vendor.VenID, Customer.cusId, orderItems, orderType, location, 0);
                System.out.println("Total Price : " + totalAmount);
                
                String continueInput;
                System.out.print("Do you have any voucher to use ? (Yes/No) : ");
                continueInput=Sc.next().toLowerCase();

                if(continueInput.equalsIgnoreCase("yes")){

                    disc = Voucher.check_voucher(totalAmount);
                    if(disc==null){

                        disc = 0 + "";
                    }
                } else { 
                    disc = 0 + "";
                }
                
            double discountValue = Double.parseDouble(disc);
            order = new Order(Vendor.VenID, Customer.cusId, orderItems, orderType, location, discountValue);
            
            System.out.println("Total Price : " + totalAmount);
            Payment tempPayment = createPaymentObj(Customer.cusId);
            if(tempPayment.checkBalance(totalAmount)){
                System.out.println("Order placed successfully!");
                saveOrder();
                tempPayment.payForOrders(currentOrderId, CusID, totalAmount);
                Payment vendor = createPaymentObj(order.getVendorId());
                double totalprice = FH.roundToTwoDecimalPlaces(totalAmount);
                if(order.getOrderType().equals("DELIVERY")){
                    vendor.updateBalance("payment.txt", vendor.getId(), ("" + (vendor.getBalance() + totalprice - 5.0)));
                } else {
                    vendor.updateBalance("payment.txt", vendor.getId(), ("" + (vendor.getBalance() + totalprice)));
                }
            } else {
                System.out.println("Insufficient balance. Please top up before ordering.");
                System.out.println("Current balance: " + tempPayment.getBalance());
                System.out.println("Total order amount: " + totalAmount);
                System.out.println("You are lacking RM" + (totalAmount - tempPayment.getBalance()));
            }
            
        } else {
            System.out.println("No items selected. Order not placed.");
        }
    }

    public static void cancelOrder(String cusId) {
        ArrayList<String> orderHistory = FH.ReadFromFile("orders.txt");
        System.out.print("Enter the Order ID to cancel: ");
        String orderId = Sc.nextLine();
        String VenId = null;
        
        // Check if the order with the given ID exists in the order history
        String orderToCancel = null;
        for (String order : orderHistory) {
            String[] dataParts = order.split(",");
            String fileOrderId = dataParts[0];
            if (fileOrderId.equals(orderId)) {
                orderToCancel = order;
                break;
            }
        }   
        
        if (orderToCancel != null) {
            // Assume that order elements [3] is status
            String[] orderElements = orderToCancel.split(",");
            if (orderElements[3].equals("Cancelled")) {
                System.out.println("This order has already been cancelled.");
                return;
            }
            if (orderElements[3].equals("Placed")) {    
                //create order object
                Order cancelledOrder = FH.createOrderFromData(orderElements);
                //update order status in txt file
                FH.UpdateOrdStatusFromFile("orders.txt", orderId, "Cancelled");
                //create payment object and refund customers
                Payment tempPayment = createPaymentObj(cancelledOrder.getCusId());
                tempPayment.refundOrders(orderElements[0], cusId);
                
                //Deduct money from vendors and runners
                Payment vendor = createPaymentObj(cancelledOrder.getVendorId());
                
                if(cancelledOrder.getOrderType().equals("DELIVERY")){
                    vendor.updateBalance("payment.txt", vendor.getId(), ("" + (vendor.getBalance() - totalAmount - 5.0)));
                } else {
                 vendor.updateBalance("payment.txt", vendor.getId(), ("" + (vendor.getBalance() - totalAmount)));
                }
                
                
                //confirm to customer
                System.out.println("Order " + orderId + " has been cancelled successfully.");
            } else {
                System.out.println("Failed to cancel. Make sure that your ordered has not been accepted/delivered/finished.");
            }
        } else {
            System.out.println("Order not found. Please enter a valid Order ID.");
        }
    }
    
      
    public static void reorder(String cusId) {
        Customer.viewOrderHistory(cusId);
        ArrayList<String> orderHistory = FH.ReadFromFile("orders.txt");
        String VenId = null;
        System.out.print("Enter the Order ID to reorder: ");
        String orderId = Sc.nextLine();
        for (String orderData : orderHistory) {
            String[] dataParts = orderData.split(",");
            String fileOrderId = dataParts[0];

            if (fileOrderId.equals(orderId)) {
                String customerId = dataParts[1];
                VenId = dataParts[2];
                String status = dataParts[3];
                String location = dataParts[8];
                double totalAmount = Double.parseDouble(dataParts[4]);
                OrderType orderType = OrderType.valueOf(dataParts[5]);
                String orderDate = dataParts[6];
                String itemDetails = dataParts[7];

                // Create OrderItem objects from itemDetails
                ArrayList<OrderItem> orderItems = new ArrayList<>();
                String[] itemsArray = itemDetails.split(";");
                for (String item : itemsArray) {
                    String[] itemParts = item.split(":");
                    String itemId = itemParts[0];
                    int quantity = Integer.parseInt(itemParts[1].substring(0, 1));
                    String extraNotes = itemParts[1].substring(2, itemParts[1].length() - 1);

                    // Use the newly added methods to get item details
                    String itemName = Item.getItemName(itemId, VenId);
                    String itemDesc = Item.getItemDesc(itemId, VenId);
                    double itemPrice = Double.parseDouble(Item.getItemPrice(itemId, VenId));

                    OrderItem orderItem = new OrderItem(itemId, itemName, itemDesc, itemPrice, quantity, extraNotes);
                    orderItems.add(orderItem);
                }

                // Display the order details
                System.out.println("Original Order Details:");
                displayOrderDetails(orderId, customerId, VenId, status, totalAmount, orderType, orderDate);

                boolean continueLoop = true;
                while(continueLoop){
                // Ask if the user wants to change anything
                    System.out.println("");
                    System.out.println("Would you like to change anything?");
                    System.out.println("1. Quantity");
                    System.out.println("2. Extra Notes");
                    System.out.println("3. Order Type");
                    System.out.println("0. Continue");

                    System.out.print("Enter your choice: ");
                    int choice = Sc.nextInt();
                    Sc.nextLine(); // Consume the newline character

                    switch (choice) {
                        case 1:
                            updateQuantity(orderItems, VenId);
                            break;
                        case 2:
                            updateExtraNotes(orderItems, VenId);
                            break;
                        case 3:
                            orderType = updateOrderType(orderType);
                            break;
                        case 0:
                            continueLoop = false;
                            break; 
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
                // Create Order object
                Order reorderedOrder = new Order(VenId, customerId, orderItems, orderType, location, 0);
                Payment tempPayment = createPaymentObj(reorderedOrder.getCusId());
                Payment vendor = createPaymentObj(reorderedOrder.getVendorId());
                
                if(tempPayment.checkBalance(totalAmount)){
                    saveOrder();
                    tempPayment.payForOrders(currentOrderId, CusID, totalAmount);
                    Order.saveOrder();
                    if(reorderedOrder.getOrderType().equals("DELIVERY")){
                        vendor.updateBalance("payment.txt", vendor.getId(), ("" + (vendor.getBalance() + totalAmount - 5.0)));
                    } else {
                     vendor.updateBalance("payment.txt", vendor.getId(), ("" + (vendor.getBalance() + totalAmount)));
                    }
                    System.out.println("Reorder placed successfully!");
                    } else {
                    System.out.println("Insufficient balance. Please top up before ordering.");
                    System.out.println("Current balance: " + tempPayment.getBalance());
                    System.out.println("Total order amount: " + totalAmount);
                    System.out.println("You are lacking RM" + (totalAmount - tempPayment.getBalance()));
                }
                return;

            }
        }

        System.out.println("Order not found. Please enter a valid Order ID.");
    }

    private static void displayOrderDetails(String orderId, String customerId, String vendorId, String status,
                                            double totalAmount, OrderType orderType, String orderDate) {
        // Display the order details, modify as needed
        System.out.println("------------------------------");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Vendor ID: " + vendorId);
        System.out.println("Status: " + status);
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Order Type: " + orderType);
        System.out.println("Order Date: " + orderDate);
        System.out.println("------------------------------");
    }

    private static void updateQuantity(List<OrderItem> orderItems, String vendorId) {
        System.out.println("Update Quantity:");
        for (OrderItem item : orderItems) {
            System.out.print("Enter new quantity for " + item.getItemName(vendorId) + ": ");
            int newQuantity = Sc.nextInt();
            Sc.nextLine(); // Consume the newline character

            // Validate input (assuming quantity cannot be negative)
            if (newQuantity >= 0) {
                item.setQuantity(newQuantity);
                System.out.println("Quantity updated for " + item.getItemName(vendorId));
            } else {
                System.out.println("Invalid quantity. Quantity cannot be negative.");
            }
        }
    }

    private static void updateExtraNotes(List<OrderItem> orderItems, String vendorId) {
        System.out.println("Update Extra Notes:");
        for (OrderItem item : orderItems) {
            System.out.print("Enter new extra notes for " + item.getItemName(vendorId) + ": ");
            String newExtraNotes = Sc.nextLine();

            // No specific validation for extra notes in this example
            item.setExtraNotes(newExtraNotes);
            System.out.println("Extra notes updated for " + item.getItemName(vendorId));
        }
    }
    
    private static OrderType updateOrderType(OrderType orderType) {
        System.out.println("Update Order Type:");
        System.out.print("Enter new order type ([TAKEAWAY|DINE_IN|DELIVERY): ");
        String newOrderTypeStr = Sc.nextLine();
        try {
            OrderType newOrderType = OrderType.valueOf(newOrderTypeStr.toUpperCase());
            // Validate input to be one of the enum values
            orderType = newOrderType;
            System.out.println("Order type updated to " + orderType);
            return newOrderType;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid order type. Please enter DELIVERY or TAKEAWAY or DINE_IN.");
        }
        return null;
    }
    
    private double calculateTotalAmount(double discount) {
        double totalAmount = 0.0;

        for (OrderItem item : items) {
            totalAmount += item.calculateTotalPrice();
        }

        // Add delivery fee for delivery orders
        if (orderType == OrderType.DELIVERY) {
            totalAmount += DeliveryFeeCalculator.calculateDeliveryFee();
        }

        // Round to two decimal places
        totalAmount = totalAmount - discount;
        totalAmount = FH.roundToTwoDecimalPlaces(totalAmount);

        return totalAmount;
    }

    private static class DeliveryFeeCalculator {
        public static double calculateDeliveryFee() {
            return 5.0; 
        }
    }
    
    protected static void saveOrder(){
        LocalDate currentDate = LocalDate.now();
        ArrayList<String> File = FH.ReadFromFile("orders.txt");
        currentOrderId = "O0";
        
        for(String line : File){
            if(line == null){
                break;
            }
            
            String[] dataParts = line.split(",");
            currentOrderId = dataParts[0];
        }
        int numericPart = Integer.parseInt(currentOrderId.substring(1)) + 1;
        currentOrderId = String.format("O%01d", numericPart);
        
        StringBuilder orderData = new StringBuilder();
        orderData.append(currentOrderId).append(",");
        orderData.append(CusID).append(",");
        orderData.append(VenID).append(",");
        orderData.append(status).append(",");
        orderData.append(totalAmount).append(",");
        orderData.append(orderType).append(",");
        orderData.append(currentDate).append(",");
        
        // Add item details
        for (OrderItem item : items) {
            orderData.append(item.getItemId()).append(":").append(item.getQuantity()).append("(").append(item.getExtraNotes()).append(")").append(";");
        }
        
        orderData.append(",").append(location);
        FH.Write2File(orderData, "orders.txt", FH.ordersList);
        
        ArrayList<String> RunnerFile = FH.ReadFromFile("tasks.txt");
        String currentTaskId = "T0";
        
        
        if (orderType.toString().equals("DELIVERY")){
            for(String line : RunnerFile){
                if(line == null){
                    break;
                }

                String[] dataParts = line.split(",");
                currentTaskId = dataParts[0];
            }

            int numeric = Integer.parseInt(currentTaskId.substring(1)) + 1;
            currentTaskId = String.format("T%01d", numeric);

            StringBuilder runnerTask = new StringBuilder();
            runnerTask.append(currentTaskId).append(",");
            runnerTask.append(CusID).append(",");
            runnerTask.append(VenID).append(",");
            runnerTask.append(location).append(",");
            runnerTask.append("Pending").append(",");
            runnerTask.append("D1").append(",");
            runnerTask.append(currentDate).append(",");
            runnerTask.append(currentOrderId).append(",");

            for (OrderItem item : items) {
                runnerTask.append(item.getItemId()).append(":").append(item.getQuantity()).append("(").append(item.getExtraNotes()).append(")").append(";");
            }

            FH.Write2File(runnerTask, "tasks.txt", FH.tasksList);
        }
    }
    
    public static Payment createPaymentObj(String cusID){
        ArrayList<String> paymentFile = FH.ReadFromFile("payment.txt");
        Payment tempPayment = null;
        for(String line : paymentFile){
            String[] parts = line.split(",");
            if (cusID.equals(parts[0])){
                tempPayment = new Payment(parts[0], Double.parseDouble(parts[1]));
                return tempPayment;
            }
        }
        return null;
    }
    
    public static void updateOrder(String orderId){
    // Read the information from the "updated.txt" file
    ArrayList<String> orderData = FH.ReadFromFile("orders.txt");

    for (String line : orderData) {
            String[] parts = line.split(",");
            ArrayList<OrderItem> items = FH.createOrderItems(parts[7], parts[2]);
            Order orderToUpdate = new Order(parts[2],parts[1],items,OrderType.valueOf(parts[5]),parts[0],parts[8], 0);
            
            if(parts[0].equals(orderId)){
                System.out.println("Sorry. We were unable to allocate you a driver for your order with ID " + orderId + ".");
                System.out.println("Please choose another order type as 'DELIVERY' is not available right now."); 
                System.out.print("Enter new order type (TAKEAWAY|DINE_IN): ");
                String newOrderTypeStr = Sc.nextLine().toUpperCase();
                try {
                    OrderType newOrderType = OrderType.valueOf(newOrderTypeStr);
                    // Validate input to be one of the enum values
                    orderType = newOrderType;
                    System.out.println("Order type updated to " + orderType);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid order type. Please enter DINE_IN or TAKEAWAY.");
                }
                Order updatedOrder = new Order(parts[2],parts[1],items,OrderType.valueOf(newOrderTypeStr),parts[0],parts[8], 0);
                FH.UpdateOrdTypeFromFile("orders.txt", orderId, newOrderTypeStr);
                Payment tempPayment = createPaymentObj(updatedOrder.getCusId());
                tempPayment.refundDeliveryFee(orderId, Customer.cusId);
                // Mark the receipt status as "Printed" at index 9 in the "updated.txt" file
                FH.UpdateReceiptStatus("updated.txt", orderId, "Printed", 9);
                System.out.println("Order " + orderId + " has been updated");
            }
        }
        System.out.println("");
    }
}