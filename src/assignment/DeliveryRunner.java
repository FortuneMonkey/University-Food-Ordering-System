package Assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DeliveryRunner extends User{
    protected static String DeliID;
    protected static String DeliName;
    private int DeliContact;
    
    
    public DeliveryRunner(String DeliId, String DeliName, int DeliContact){
        super(DeliId, DeliName, DeliContact);
        this.DeliID = DeliId;
        this.DeliName = DeliName;
        this.DeliContact = DeliContact;
    }
    
    public String getDeliID() {
        return DeliID;
    }
    
    public String getDeliName() {
        return DeliName;
    }
    
    public static void viewTask(){
        FH.ShowPendingTask("tasks.txt", DeliID);
    }
    
    private static void decisionTasks() {
        ArrayList<String> taskList = new ArrayList<>();
        Scanner Sc = new Scanner(System.in);
 
        
        taskList = FH.ReadFromFile("tasks.txt");
        for (String task : taskList) {
        String[] dataParts = task.split(",");
            if (dataParts.length == 9) { 
                String newTaskId = dataParts[0].trim();
                String taskStatus = dataParts[4].trim();
                if(taskStatus.equals("Pending") && dataParts[5].equals(DeliID)){
                    System.out.println("Assigned task: " + task);
                    System.out.print("Accept or Decline Order(A/D): ");
                    String choice = Sc.nextLine().toUpperCase();
                
                    if (choice.equals("A")) {
                        String newTaskStatus = "ON PROGRESS";
                        FH.UpdateTaskStatusFromFile("tasks.txt", newTaskId, newTaskStatus, DeliID); 
                        FH.UpdateOrdStatusFromFile("orders.txt", dataParts[7], "Delivering");
                        System.out.println("");
                        break;
                    } 
                    if (choice.equals("D")){
                        ArrayList<String> runnerData = FH.ReadFromFile("runners.txt");
                        String length = "" + (runnerData.size());
                        for(String line : runnerData){
                            String[] data = line.split(",");
                            if(DeliID.substring(1).equals(length)){
                                FH.UpdateOrdStatusFromFile("orders.txt", dataParts[7], "Driver not found");
                                FH.UpdateTaskStatusFromFile("tasks.txt", newTaskId, "NO AVAILABLE DRIVER", DeliID); 
                                System.out.println("");
                                break;
                            } else {
                                int newRunnerId = Integer.parseInt(DeliID.substring(1))+ 1;
                                String runnerId = "D" + newRunnerId;
                                FH.UpdateRunner("tasks.txt", dataParts[7], runnerId);
                                System.out.println("");
                            } 
                        }
                    }
                }
            }
        }
    }
    
    private static void finishTask() {
        Scanner Sc = new Scanner(System.in);
        ArrayList<String> taskData = FH.ReadFromFile("tasks.txt");
        ArrayList<RunnerReview> tasksList = new ArrayList();
        for (String line : taskData){
            String parts[] = line.split(",");
            if(parts[5].equals(DeliID) && parts[4].equals("ON PROGRESS")){
                RunnerReview tempTask = new RunnerReview(parts[0], parts[7], line);
                tasksList.add(tempTask);
            }
        }
        
        if(tasksList.isEmpty()){
            System.out.println("You have not accepted any task.");
            return;
        }
        
        for(RunnerReview task : tasksList){
            System.out.println(task.getDetails());
            System.out.print("Enter the task ID you want to complete: ");
            String taskId = Sc.nextLine();
            if(task.getTaskId().equals(taskId)){
                FH.UpdateTaskStatusFromFile("tasks.txt", taskId, "FINISHED", DeliID); 
                FH.UpdateOrdStatusFromFile("orders.txt", task.getOrderId(), "Finished");
                Payment runner = Order.createPaymentObj(DeliID);
                runner.updateBalance("payment.txt", runner.getId(), ("" + (runner.getBalance() + 5.0)));
            }
            return;
        }
    }
    
    public static void taskHistory(){
        Scanner Sc = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
        int choice = -1;

        while (choice != 0){
            System.out.println("----------------------------");        
            System.out.println("View task history for... (today's date " + currentDate + ")");
            System.out.println("1. Daily");
            System.out.println("2. Weekly");
            System.out.println("3. Monthly");
            System.out.println("4. Yearly");
            System.out.println("0. Return to delivery runner menu");
            System.out.println("----------------------------");
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
        
        System.out.println("Displaying daily task history for " + combine);
        FH.ShowOrderByDayRunner("tasks.txt", combine, DeliID);
    }
    
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
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "01", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "02", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "03", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "04", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "05", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "06", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "07", DeliID);
                break;
            case 2:
                System.out.println("Displaying second week order history for month " + Month);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "08", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "09", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "10", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "11", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "12", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "13", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "14", DeliID);
                break;
            case 3:
                System.out.println("Displaying third week order history for month " + Month);   
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "15", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "16", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "17", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "18", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "19", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "20", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "21", DeliID);
                break;
            case 4:
                System.out.println("Displaying fourth week order history for month " + Month);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "22", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "23", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "24", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "25", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "26", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "27", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "28", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "29", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "30", DeliID);
                FH.ShowOrderByDayRunner("tasks.txt", YearMonth + "31", DeliID);
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
        FH.ShowOrderByMonthRunner("tasks.txt", combine, DeliID);
    }

    public static void viewYearlyHistory() {
        Scanner Sc = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
        
        String Date = currentDate.toString();
        
        System.out.print("Enter the month of the year you want to view (yyyy): ");
        String Year = Sc.nextLine();
        
        System.out.println("Displaying daily order history for " + Year + " year.");
        FH.ShowOrderByYearRunner("tasks.txt", Year, DeliID);
    }
    
    public static void deliRevDashboard(){
        LocalDate currentDate = LocalDate.now();
        String Date = currentDate.toString();
        
        System.out.println("----------------------------");    
        System.out.println("Showing revenue dashboard for " + DeliName);
        
        int Count = FH.CountDeliRun("tasks.txt", DeliID);
        int Payment = Count * 5;
        System.out.println("----------------------------");    
        System.out.println("You have made a total " + Count + " orders, your total revenue today is (" + currentDate + ") = " + Payment); 
        System.out.println("----------------------------");    
    }
    
    
        public static void delirunReview(){
        System.out.println("----------------------------");    
        System.out.println("Showing " + DeliName + " customers review!");    
        System.out.println("----------------------------"); 
        FH.getDeliRunReview("runner_reviews.txt", DeliID);
    }    
    
    public static void showBest(){
        FH.findMaxValues("payment.txt", "D", "runners.txt");
    }
        
    public static void welcome(){
        System.out.println("----------------------------");
        System.out.println("Welcome " + DeliName + "!");
        ArrayList<Integer> Count = FH.CountTask("tasks.txt", DeliID);
        
        System.out.println("You have " + Count.get(0) + " new task.");
        System.out.println("You have " + Count.get(1) + " active task.");
    }

    @Override
    public void performAction(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;
        
        while (continueLoop == true) {
            System.out.println("");
            welcome();
            System.out.println("---------------------------");
            System.out.println("Select an action:");
            System.out.println("1. View task");
            System.out.println("2. Accept/Decline task");
            System.out.println("3. Check task history");
            System.out.println("4. Read customer review");
            System.out.println("5. Revenue Dashboard");
            System.out.println("6. Finish Task");
            System.out.println("7. Show best delivery runner");
            System.out.println("0. Exit");
            System.out.println("---------------------------");
            System.out.print("Select an action: ");


            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    viewTask();
                    break;
                case 2:
                    decisionTasks();
                    break;
                case 3:
                    taskHistory();
                    break;
                case 4:
                    delirunReview();
                    break;
                case 5:
                    deliRevDashboard();
                    break;
                case 6:
                    finishTask();
                    break;
                case 7:
                    showBest();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
