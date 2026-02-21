package com.kce.main;

import java.util.Date;
import java.util.Scanner;
import com.kce.bean.Item;
import com.kce.service.InventoryService;

public class Main {

    private static InventoryService inventoryService = new InventoryService();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== Warehouse Damage & Inventory Menu =====");
            System.out.println("1. Add New Item");
            System.out.println("2. Log Damage");
            System.out.println("3. Remove Item");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    addItem();
                    break;

                case 2:
                    logDamage();
                    break;

                case 3:
                    removeItem();
                    break;

                case 4:
                    System.out.println("Exiting Application...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 4);
    }

    private static void addItem() {
        try {
            Item item = new Item();

            System.out.print("Enter Item Code: ");
            item.setItemCode(sc.nextLine());

            System.out.print("Enter Description: ");
            item.setItemDescription(sc.nextLine());

            System.out.print("Enter Unit of Measure: ");
            item.setUnitOfMeasure(sc.nextLine());

            System.out.print("Enter Unit Price: ");
            item.setUnitPrice(sc.nextDouble());

            System.out.print("Enter On-Hand Quantity: ");
            item.setOnHandQuantity(sc.nextDouble());

            item.setTotalDamagedQuantity(0.0);

            boolean result = inventoryService.addNewItem(item);
            System.out.println(result ? "Item Added Successfully!" : "Failed to Add Item");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void logDamage() {
        try {
            System.out.print("Enter Item Code: ");
            String code = sc.nextLine();

            System.out.print("Enter Reported By: ");
            String reportedBy = sc.nextLine();

            System.out.print("Enter Damage Type: ");
            String type = sc.nextLine();

            System.out.print("Enter Quantity: ");
            double qty = sc.nextDouble();
            sc.nextLine();

            Date today = new Date();

            boolean result = inventoryService.logDamage(code, today, reportedBy, type, qty);
            System.out.println(result ? "Damage Logged Successfully!" : "Failed to Log Damage");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void removeItem() {
        try {
            System.out.print("Enter Item Code to Remove: ");
            String code = sc.nextLine();

            boolean result = inventoryService.removeItem(code);
            System.out.println(result ? "Item Removed Successfully!" : "Failed to Remove Item");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}