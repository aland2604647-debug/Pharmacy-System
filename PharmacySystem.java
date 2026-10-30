package net.oopproject;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PharmacySystem {

    // Inventory Parallel Lists
    static ArrayList<String> ids = new ArrayList<>();
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<Double> prices = new ArrayList<>();
    static ArrayList<Integer> stocks = new ArrayList<>();
    static ArrayList<String> details = new ArrayList<>();

    // Transaction History Parallel Lists
    static ArrayList<String> historyItems = new ArrayList<>();
    static ArrayList<String> historyTimestamps = new ArrayList<>();
    static ArrayList<Integer> historyQty = new ArrayList<>();
    static ArrayList<Double> historyTotals = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        addInitialData("M101", "Amoxicillin 500mg", 12.50, 45, "Prescription - Antibiotic");
        addInitialData("M102", "Paracetamol 500mg", 3.20, 120, "OTC - Pain relief");
        addInitialData("M103", "Ibuprofen 400mg", 4.50, 85, "OTC - Anti-inflammatory");

        boolean running = true;

        while (running) {
            System.out.println("\n=================================");
            System.out.println("   PHARMACY MANAGEMENT SYSTEM   ");
            System.out.println("=================================");
            System.out.println("1. View All Medicines");
            System.out.println("2. Add New Medicine");
            System.out.println("3. Sell Medicine");
            System.out.println("4. View Sales History Log");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                showAllMedicines();
            } else if (choice == 2) {
                addNewMedicine();
            } else if (choice == 3) {
                sellMedicine();
            } else if (choice == 4) {
                showSalesHistory();
            } else if (choice == 5) {
                running = false;
                System.out.println("Exiting system. Goodbye!");
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addInitialData(String id, String name, double price, int stock, String detail) {
        ids.add(id);
        names.add(name);
        prices.add(price);
        stocks.add(stock);
        details.add(detail);
    }

    static void showAllMedicines() {
        if (ids.isEmpty()) {
            System.out.println("No medicines in inventory.");
            return;
        }

        System.out.println("\n--- CURRENT INVENTORY LIST ---");
        for (int i = 0; i < ids.size(); i++) {
            System.out.printf("[%d] ID: %-5s | Name: %-25s | Price: $%-6.2f | Stock: %-3d | Info: %s\n",
                    (i + 1), ids.get(i), names.get(i), prices.get(i), stocks.get(i), details.get(i));
        }
    }

    static void addNewMedicine() {
        System.out.print("Enter Medicine ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Stock Quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Details/Instructions: ");
        String detail = scanner.nextLine();

        ids.add(id);
        names.add(name);
        prices.add(price);
        stocks.add(stock);
        details.add(detail);

        System.out.println("Medicine successfully added!");
    }

    static void sellMedicine() {
        System.out.print("Enter Medicine ID to sell: ");
        String targetId = scanner.nextLine();

        int index = -1;
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).equalsIgnoreCase(targetId)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Error: Medicine ID not found!");
            return;
        }

        System.out.print("Enter Quantity to sell: ");
        int qty = scanner.nextInt();

        int currentStock = stocks.get(index);
        if (qty > currentStock) {
            System.out.println("Error: Insufficient stock available!");
        } else {
            int newStock = currentStock - qty;
            stocks.set(index, newStock);

            double totalPrice = qty * prices.get(index);
            
            // Get current Date & Time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            // Record to Sales History
            historyItems.add(names.get(index));
            historyQty.add(qty);
            historyTotals.add(totalPrice);
            historyTimestamps.add(timestamp);

            // Print Full Detailed Receipt
            System.out.println("\n=================================");
            System.out.println("       OFFICIAL RECEIPT          ");
            System.out.println("=================================");
            System.out.println("Date & Time:    " + timestamp);
            System.out.println("Item Name:      " + names.get(index));
            System.out.println("Unit Price:     $" + prices.get(index));
            System.out.println("Quantity Sold:  " + qty);
            System.out.println("---------------------------------");
            System.out.printf("TOTAL AMOUNT:   $%.2f\n", totalPrice);
            System.out.println("Remaining Stock: " + newStock);
            System.out.println("=================================");
        }
    }

    static void showSalesHistory() {
        if (historyItems.isEmpty()) {
            System.out.println("\nNo sales transactions recorded yet.");
            return;
        }

        System.out.println("\n==========================================================================");
        System.out.println("                        TRANSACTION SALES HISTORY LOG                     ");
        System.out.println("==========================================================================");
        double grandTotal = 0;
        for (int i = 0; i < historyItems.size(); i++) {
            System.out.printf("[%d] Date: %s | Item: %-20s | Qty: %-3d | Total: $%.2f\n",
                    (i + 1), historyTimestamps.get(i), historyItems.get(i), historyQty.get(i), historyTotals.get(i));
            grandTotal += historyTotals.get(i);
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("TOTAL REVENUE GENERATED: $%.2f\n", grandTotal);
        System.out.println("==========================================================================");
    }
}