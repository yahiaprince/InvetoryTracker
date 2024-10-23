package InventoryTracker.system;

public class InventoryItem {
    private final String name;
    private double buyPrice, sellPrice, finalPrice;
    private int quantity, sold, bought;
    private double revenue, cost;
    
    public InventoryItem(String name, double buyPrice, double sellPrice, int quantity) {
        this.name = name;
        this.buyPrice = buyPrice;
        finalPrice = this.sellPrice = sellPrice;
        this.quantity = quantity;
    }
    
    public boolean canBuy(double budget, int quantity) {
        return budget - (buyPrice * quantity) >= 0;
    }
    
    public double buy(int quantity) {
        double cost = quantity * buyPrice;
        
        this.quantity += quantity;
        this.cost += cost;
        bought += quantity;
        
        return cost;
    }
    
    public boolean canSell(int quantity) {
        return this.quantity - quantity >= 0;
    }
    
    public double sell(int quantity) {
        double revenue = quantity * finalPrice;
        
        this.quantity -= quantity;
        this.revenue += revenue;
        sold += quantity;
        
        return revenue;
    }
    
    public void setBuyPrice(double price) {
        buyPrice = price;
    }
    
    public void setSellPrice(double price) {
        finalPrice = sellPrice = price;
    }
    
    public void setDiscount(double percent) {
        finalPrice = sellPrice * (1 - (percent / 100));
    }
    
    public String getName() {
        return name;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public double getRevenue() {
        return revenue;
    }
        
    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("%s | Final Price: %.2f | Sell Price: %.2f | Buy Price: %.2f | "
                           + "Quantity: %d | Bought: %d | Sold: %d | Revenue: +%.2f | Cost: -%.2f",
                             name, finalPrice, sellPrice, buyPrice, quantity, bought, sold, revenue, cost);
    }
}
