import java.util.*;

public class Product {

    private String name;
    private String id;
    private int currentStock;
    private int minRequiredStock;
    private List<Integer> history;
    private double price;

    public Product(String name, String id, int stock, int minStock, double price) {
        this.name = name;
        this.id = id;
        this.currentStock = stock;
        this.minRequiredStock = minStock;
        this.price = price;
        this.history = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int updatedStock) {
        this.currentStock = updatedStock;
    }

    public int getMinStock() {
        return minRequiredStock;
    }

    public void setMinStock(int newThreshold) {
        this.minRequiredStock = newThreshold;
    }

    public double getPrice() {
        return price;
    }

    public List<Integer> getHistory() {
        return history;
    }

    public void recordSale(int soldAmount) {
        
        if (soldAmount <= currentStock) {
            currentStock -= soldAmount;
            history.add(soldAmount);
            System.out.println("✅ Sale logged: " + soldAmount + " units of " + name);
        } else {

            System.out.println("❌ Not enough stock for sale of " + soldAmount);
        }
    }

    public boolean needsRestock() {
        return currentStock <= minRequiredStock;
    }

    public int suggestedReorderAmount() {

        if (history.size() == 0) {
            return minRequiredStock * 2;
        }

        double sum = 0;
        for (int sale : history) {
            sum += sale;
        }

        double average = sum / history.size();
        return (int)(average * 7) + minRequiredStock;
    }
}
