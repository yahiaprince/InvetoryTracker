package InventoryTracker.system;

import java.util.Scanner;
import java.util.ArrayList;

public class InventoryTracker {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        ArrayList<InventoryItem> items = new ArrayList<>();
        
        System.out.print("Enter total budget: ");
        double budget = Double.parseDouble(scn.nextLine());
        
        GetProducts(scn, items, -1);
        
        System.out.println("The setup is complete. List of operations:");
        
        System.out.println("General");
        System.out.println("'new': Add new products");
        System.out.println("'sum': Get summary");
        System.out.println("'code': Get products code");
        System.out.println("'end': Exit the app");
        System.out.println();
        
        System.out.println("Product specific");
        System.out.println("'info': Get product information");
        System.out.println("'sell': Sell product");
        System.out.println("'disc': Set product discount");
        System.out.println("'prc': Set product price");
        System.out.println("'buy': Buy product");

        while (true) {
            System.out.println();
            System.out.print("Enter operation: ");
            String op = scn.nextLine();
            
            if (op.equalsIgnoreCase("new")) {
                budget = GetProducts(scn, items, budget);
                continue;
            }
            
            if (op.equalsIgnoreCase("sum")) {
                printSummary(items, budget);
                continue;
            }
            
            if (op.equalsIgnoreCase("code")) {
                for (int i = 0; i < items.size(); ++i) System.out.println(String.format("%s: [%d]", items.get(i).getName(), i + 1));
                continue;
            }
            
            if (op.equalsIgnoreCase("end")) { 
                printSummary(items, budget);                
                break;
            }

            if (op.equalsIgnoreCase("info")) {
                System.out.println(items.get(getCode(scn)));
                continue;
            }
            
            if (op.equalsIgnoreCase("sell")) {
                int code = getCode(scn);
                
                System.out.print("Enter number of items to sell: ");
                int quantity = Integer.parseInt(scn.nextLine()); 
                
                if (items.get(code).canSell(quantity)) budget += items.get(code).sell(quantity);
                else System.out.println("Number exceeds product quantity. Product quantity: " + items.get(code).getQuantity());
                
                continue;
            }
            
            if (op.equalsIgnoreCase("disc")) {
                int code = getCode(scn);
                
                System.out.print("Enter discount percent: ");
                double sale = Double.parseDouble(scn.nextLine());                 
                items.get(code).setDiscount(sale);
                continue;
            }
            
            if (op.equalsIgnoreCase("prc")) {
                int code = getCode(scn);
                
                System.out.print("Enter sell price: ");
                double sellPrice = Double.parseDouble(scn.nextLine()); 
                items.get(code).setSellPrice(sellPrice);
                
                System.out.print("Enter buy price: ");
                double buyPrice = Double.parseDouble(scn.nextLine()); 
                items.get(code).setBuyPrice(buyPrice);
                continue;
            }
            
            if (op.equalsIgnoreCase("buy")) {
                int code = getCode(scn);
                
                System.out.print("Enter number of items to buy: "); 
                int quantity = Integer.parseInt(scn.nextLine()); 
                
                if (items.get(code).canBuy(budget, quantity)) budget -= items.get(code).buy(quantity);
                else System.out.println("Total price exceeds budget. Budget: " + budget);
                
                continue;
            }
            
            System.out.println("Incorect operation");
        }                        
    }
    
    private static double GetProducts(Scanner scn, ArrayList<InventoryItem> items, double budget)
    {
        System.out.println("Enter products using format 'Name, Sell Price, Buy Price, Quantity'. Type 'end' to add prodcuts");
        
        while (true) {
            String in = scn.nextLine();
            if (in.isEmpty() || in.equalsIgnoreCase("end")) break;
            
            String[] data = in.split(",");
            
            String name = data[0].trim();
            double sellPrice = Double.parseDouble(data[1].trim());
            double buyPrice = Double.parseDouble(data[2].trim());
            int quantity = Integer.parseInt(data[3].trim());
            
            if (budget == -1) items.add(new InventoryItem(name, buyPrice, sellPrice, quantity));            
            else
            {
                InventoryItem item = new InventoryItem(name, buyPrice, sellPrice, 0);
                
                if (item.canBuy(budget, quantity)) {
                    budget -= item.buy(quantity);
                    items.add(item);
                } else {
                    System.out.println("Total price exceeds budget. Budget: " + budget);
                }
            }
        }
        
        return budget;
    }
    
    private static int getCode(Scanner scn)
    {
        System.out.print("Enter product code: ");
        return Integer.parseInt(scn.nextLine()) - 1; 
    }
    
    private static void printSummary(ArrayList<InventoryItem> items, double budget) {
        String out = "";                
        double revenue = 0, cost = 0;
                
        for (int i = 0; i < items.size(); ++i) {
            InventoryItem item = items.get(i);
            
            out += String.format("[%d] | %s\n", i + 1, item);
            revenue += item.getRevenue();
            cost += item.getCost();
        }
                
        System.out.println(String.format("Budget: %.2f", budget));
        System.out.println(String.format("Revenue: +%.2f", revenue));
        System.out.println(String.format("Cost: -%.2f", cost));
        System.out.print(out);
    }
}
