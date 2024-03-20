package Assignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Payment {
    private static double balance;
    private static String customerId;
    
    public Payment(String customerId, double balance){
        this.balance = balance;
        this.customerId = customerId;
    }
    
    public Payment(){
    }
    
    protected double getBalance() {
        return balance;
    }
    
    protected String getId(){
        return customerId;
    }    
    
    private static ArrayList<String> paymentData = FH.ReadFromFile("payment.txt");

    public void payForOrders(String orderId, String customerId, double amount) {
        if (balance >= amount) {
            // Update balance and save payment record
            double newBalance = balance - amount;
            newBalance = FH.roundToTwoDecimalPlaces(newBalance);
            String balanceString = "" + newBalance;
            Payment cus = Order.createPaymentObj(customerId);
            cus.updateBalance("payment.txt", customerId, balanceString);
            String transactionType = orderId + "PayOrder";
            // Record the payment
            Admin.generate_receipt(amount, customerId, transactionType);
            System.out.println("Payment successful. New balance: " + newBalance);
        } else {
            System.out.println("Insufficient funds. Payment failed.");
        }
    }

    public void refundOrders(String orderId, String customerId) {
        ArrayList<String> orderHistory = FH.ReadFromFile("orders.txt");

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
        
        if (orderToCancel == null){
            System.out.println("Order does not exist. Please try again.");
            return;
        }
        String[] orderElements = orderToCancel.split(",");
        Order order = FH.createOrderFromData(orderElements);
        // Update balance and save refund record
        double amount = order.getTotalAmount();
        double newBalance = balance + amount;
        newBalance = FH.roundToTwoDecimalPlaces(newBalance);
        String Stringbalance = "" + newBalance;
        Payment cus = Order.createPaymentObj(customerId);
        cus.updateBalance("payment.txt", customerId, Stringbalance);
        
        // Record the refund
        String transactionType = orderId + "RefundOrder";
        Admin.generate_receipt(amount, customerId, transactionType);
        System.out.println("Refund successful. New balance: " + newBalance);
    }
    
    public void refundDeliveryFee(String orderId, String customerId) {
        ArrayList<String> orderHistory = FH.ReadFromFile("orders.txt");

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
        
        if (orderToCancel == null){
            System.out.println("Order does not exist. Please try again.");
            return;
        }
        String[] orderElements = orderToCancel.split(",");
        Order order = FH.createOrderFromData(orderElements);
        // Update balance and save refund record
        double amount = 5.0; //flat delivery fee
        double newBalance = balance + amount;
        newBalance = FH.roundToTwoDecimalPlaces(newBalance);
        String Stringbalance = "" + newBalance;
        Payment cus = Order.createPaymentObj(customerId);
        cus.updateBalance("payment.txt", customerId, Stringbalance);
        System.out.println("Delivery Fee Refunded");
        System.out.println("");
    }

    public void updateBalance(String fileName, String customerId, String newBalance) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] dataParts = line.split(",");
                if (dataParts.length == 2) {
                    String filereceiptId = dataParts[0].trim();
                    if (customerId.equals(filereceiptId)) {
                        dataParts[1] = newBalance;
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
    
    protected boolean checkBalance(double Amount){
        if(balance > Amount){
            return true;
        }
        return false;
    }
}