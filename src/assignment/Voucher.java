package Assignment;

import static Assignment.Admin.sc;
import static Assignment.Admin.user_menu;
import Assignment.FH;
import java.util.Scanner;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;
import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Voucher {
    static Scanner sc =new Scanner(System.in);
    
    public static void voucher_menu(){
        
        System.out.println("----------------VOUCHER MENU----------------");
        System.out.println("1. Create Voucher");
        System.out.println("2. Delete Voucher");
        System.out.println("3. Back To Administrator Menu");
        System.out.println("--------------------------------------------");
        System.out.print("Enter Your Choice : ");
        int choice=sc.nextInt();
        
        switch(choice){
            case 1:
                create_voucher();
                break;
            case 2:
                delete_voucher();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid Choice!");
                break;
        }
    }
    
    public static void create_voucher(){
        String voucherCode;
        Double discAmount;
        String continueInput;
        
        do{
            System.out.println("----------------------CREATE VOUCHER----------------------");
            System.out.print("Enter Voucher Code    : ");
            voucherCode=sc.next().toUpperCase();
            System.out.print("Enter Discount Amount : ");
            discAmount=sc.nextDouble();
            
            String line=voucherCode+','+discAmount;
            FH.writeToFile(voucherCode, line, "voucher.txt", FH.voucherList);
            
            System.out.println("----------------------------------------------------------");
            System.out.print("Do you want to continue ? (Yes/No) : ");
            continueInput=sc.next().toLowerCase();
            
            if(!continueInput.equalsIgnoreCase("yes")){
                voucher_menu();
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
    }
    
    public static void delete_voucher(){
        String voucherCode;
        String ask;
        String continueInput;
        
        do{
            System.out.println("----------------------DELETE VOUCHER----------------------");
            System.out.print("Enter The Voucher Code To Delete        : ");
            voucherCode=sc.next().toUpperCase();
            System.out.print("Are You Sure To Delete This ? (yes/no)  : ");
            ask=sc.next();
            
            if(ask.equalsIgnoreCase("yes"))
            {
                FH.deleteRecord(voucherCode, "voucher.txt", FH.voucherList);
            }
            
            System.out.println("----------------------------------------------------------");
            System.out.print("Do you want to continue? (yes/no) \t: ");
            continueInput =sc.next().toLowerCase();
            
            if (!continueInput.equalsIgnoreCase("yes"))
            {
                voucher_menu();
            }
            
        }while("yes".equalsIgnoreCase(continueInput));
    }
    
    public static String check_voucher(double amount){
        String voucherCode;
        double xx=amount;
        System.out.println("\u001B[32mMessage : Voucher Can Be Only Used If Total Price Is Greater Than Voucher Amount .\u001B[0m");
        System.out.print("Enter Voucher Code : ");
        voucherCode=sc.next().toUpperCase();
        
        String voucherAmount = FH.checkVoucher("voucher.txt", voucherCode);
        if (voucherAmount != null){
            if(Double.valueOf(voucherAmount) > amount){
                //voucherAmount = amount + "";
                //System.out.println("Your order is free.");
                voucherAmount=0.0+"";
                System.out.println("\u001B[32mMessage : Cannot Apply Voucher (Voucher Amount More Than Total Price) .\u001B[0m");
            }
        }
        return voucherAmount;
    }     
}

