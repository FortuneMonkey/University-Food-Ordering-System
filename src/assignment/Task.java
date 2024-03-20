package Assignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Reviewable {
    void validateOrder(String cusId);
    void review(Order order);
    void updateFile();
}

public abstract class Task implements Reviewable{
    protected String taskId;
    protected String customerId;
    protected String vendorId;
    protected String location;
    protected String status;
    protected String runnerId;
    protected LocalDate creationDate;
    protected String orderId;
    protected List<OrderItem> items;

    // Constructor
    public Task(String taskId, String customerId, String vendorId, String location,
                String status, String runnerId, LocalDate creationDate, String orderId, List<OrderItem> items) {
        this.taskId = taskId;
        this.customerId = customerId;
        this.vendorId = vendorId;
        this.location = location;
        this.status = status;
        this.runnerId = runnerId;
        this.creationDate = creationDate;
        this.orderId = orderId;
        this.items = items;
    }
    
    public Task(String taskId, String orderId){
        this.taskId = taskId;
        this.orderId = orderId;
    }
    
    //Empty constructor
    public Task(){}
    
    public static Scanner Sc = new Scanner(System.in);
    
    public String getTaskId(){
        return taskId;
    }
    
    public String getOrderId(){
        return orderId;
    }
    
    public String getStatus(){
        return status;
    }
    public Order getOrderToReview(String cusId){
        boolean loop = true;
        Order orderToReview = null;
        
        while(loop){
            System.out.println("Make sure your order is finished.");
            System.out.print("Enter Order ID (0 to exit): ");
            String enteredOrderId = Sc.nextLine();
            orderId = enteredOrderId;
            
            if(enteredOrderId.equals("0")){
                return orderToReview;
            }
            orderToReview = findOrderById(enteredOrderId, cusId);
            if (orderToReview == null) {
                System.out.println("Invalid order ID. Review cannot be submitted.");
                System.out.println("Please input a vaild order ID.");
                System.out.println("");
            } else {
                loop = false;
            }
        }
        return orderToReview;
    }
    
    // Helper method to find an order by ID
    public static Order findOrderById(String orderId, String cusId) {
        ArrayList<String> orderLines = FH.ReadFromFile("orders.txt");

        for (String orderLine : orderLines) {
            String[] orderData = orderLine.split(",");
            if (orderData[0].equals(orderId) && orderData[1].equals(cusId)) {
                return FH.createOrderFromData(orderData);
            }
        }
        System.out.println("Order with ID " + orderId + " not found.");
        return null;
    }
    
        public static int getValidRating() {
        int rating;

        while (true) {
            System.out.print("Rating (1-5): ");
            try {
                rating = Integer.parseInt(Sc.nextLine());
                if (rating >= 1 && rating <= 5) {
                    break; // Break the loop if the input is valid
                } else {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
            }
        }

        return rating;
    }
}

