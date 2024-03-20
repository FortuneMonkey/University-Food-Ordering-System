package Assignment;

import java.time.LocalDate;
import java.util.ArrayList;

public class RunnerReview extends Task{
    private String details;
    
    // Constructor
    public RunnerReview(String taskId, String customerId, String vendorId, String location,
                        String status, String runnerId, LocalDate creationDate, String orderId, ArrayList<OrderItem> items) {
        super(taskId, customerId, vendorId, location, status, runnerId, creationDate, orderId, items);
    }
    
    public RunnerReview(String taskId, String orderId, String details){
        super(taskId, orderId);
        this.details = details;
    }
    
    //Empty constructor
    public RunnerReview(){
    }
    
    // Getter and Setter methods
    protected String getRunnerId() {
        return runnerId;
    }
    
    public String getDetails(){
        return details;
    }
   
    @Override
    public void validateOrder(String cusId) {
        System.out.println("--------Reviewing Runner--------");
        Order orderToReview = getOrderToReview(cusId);
        if(orderToReview == null){
            return;
        }
        if(!(orderToReview.getOrderType().equals("DELIVERY"))){
            System.out.println("Not a delivery order. Invalid Order ID.");
        } else {
            review(orderToReview);
        }
    }

    @Override
    public void review(Order order) {
        String runnerId = "";
        ArrayList<String> taskFile = FH.ReadFromFile("tasks.txt");
        for (String taskLine : taskFile) {
            String[] taskData = taskLine.split(",");
            ArrayList<OrderItem> orderItems = FH.createOrderItems(taskData[8], taskData[2]);
            // Create an instance of DeliveryTask
            RunnerReview tempObj = new RunnerReview(taskData[0], taskData[1], taskData[2], taskData[3],taskData[4], taskData[5], LocalDate.parse(taskData[6]), taskData[7], orderItems);

            if(tempObj.getOrderId().equals(orderId)){
                runnerId = tempObj.getRunnerId();  
                if(tempObj.getStatus().equals("REVIEWED")){
                    System.out.println("This order has been reviewed. Please choose another order");
                    return;
                }
            } 
        }
        
        ArrayList<String> runnerFile = FH.ReadFromFile("runners.txt");
        String runnerName = "";
        for (String runnerLine : runnerFile) {
            String[] runnerData = runnerLine.split(",");
            DeliveryRunner tempRunner = new DeliveryRunner(runnerData[0], runnerData[2], Integer.parseInt(runnerData[3]));
            String fileRunnerId = tempRunner.getDeliID();
            
            if(fileRunnerId.equals(runnerId)){
                runnerName = tempRunner.getDeliName();
            }
        }
        
        System.out.println("Rating for " + runnerName + ":");
        int rating = getValidRating();
        System.out.print("Enter message: ");
        String message = Sc.nextLine();
        saveReview(orderId, runnerId, rating, message);
    }

    public void saveReview(String orderID, String runnerId, int rating, String message) {
        String reviewData = String.format("%s,%s,%d,%s", orderID, runnerId, rating, message);
        FH.Write2File(reviewData, "runner_reviews.txt", new ArrayList<>());
        System.out.println("Runner review submitted successfully.");
    }

    @Override
    public void updateFile() {
        FH.UpdateTaskStatusFromFile("tasks.txt", taskId, "REVIEWED", vendorId);
    }
    
}
