import java.util.*;

class InventoryManager {

    private Map<String, Product> productMap;
    private Scanner input;

    public InventoryManager() {
        productMap = new HashMap<>();
        input = new Scanner(System.in);
    }

    public void addProduct() {
        System.out.println("Adding a new product...");

        System.out.print("Enter Product Name: ");
        String name = input.nextLine();

        System.out.print("Enter Product ID: ");
        String id = input.nextLine();

        if (productMap.containsKey(id)) {
            System.out.println("⚠️ Product ID already exists. Try again.");
            return;
        }

        System.out.print("Initial Stock Count: ");
        int stock = input.nextInt();

        System.out.print("Restock threshold: ");
        int threshold = input.nextInt();

        System.out.print("Unit Price: $");
        double price = input.nextDouble();
        input.nextLine(); 

        Product p = new Product(name, id, stock, threshold, price);
        productMap.put(id, p);

        System.out.println("Product added\n");
    }

    public void recordSale() {
        System.out.print("Enter Product ID to record sale: ");
        String id = input.nextLine();

        Product p = productMap.get(id);
        if (p == null) {
            System.out.println("Product not found. Make sure ID is correct.");
            return;
        }

        System.out.print("Units sold: ");
        int amount = input.nextInt();
        input.nextLine();

        p.recordSale(amount);

        if (p.needsRestock()) {
            System.out.println("----NEEDS RESTOCKING----");
            System.out.println("Name: " + p.getName());
            System.out.println("Stock left: " + p.getCurrentStock());
            System.out.println("Minimum required: " + p.getMinStock());
            System.out.println("Suggested Reorder: " + p.suggestedReorderAmount());
        }
    }

    public void viewInventory() {
        System.out.println("\n=== Inventory Overview ===");

        if (productMap.isEmpty()) {
            System.out.println("Inventory is currently empty.");
            return;
        }

        for (Product p : productMap.values()) {
            System.out.println("Name: " + p.getName());
            System.out.println("ID: " + p.getId());
            System.out.println("In Stock: " + p.getCurrentStock());
            System.out.println("Threshold: " + p.getMinStock());
            System.out.println("Price: $" + String.format("%.2f", p.getPrice()));
            System.out.println("Sales History: " + p.getHistory());
            System.out.println("Status: " + (p.needsRestock() ? "LOW STOCK" : "OK"));
            System.out.println("---------------------------");
        }
    }

    public void checkLowStock() {
        System.out.println("\n=== LOW STOCK REPORT ===");

        boolean anyLow = false;

        for (Product p : productMap.values()) {
            if (p.needsRestock()) {
                anyLow = true;
                System.out.println("Product: " + p.getName() + " (ID: " + p.getId() + ")");
                System.out.println("Stock: " + p.getCurrentStock());
                System.out.println("Threshold: " + p.getMinStock());
                System.out.println("Suggested reorder: " + p.suggestedReorderAmount());
                System.out.println("Approx. cost: $" + String.format("%.2f", p.getPrice() * p.suggestedReorderAmount()));
                System.out.println();
            }
        }

        if (!anyLow) {
            System.out.println("👍 All products are above threshold.");
        }
    }

    public void updateThreshold() {
        System.out.print("Product ID: ");
        String id = input.nextLine();

        Product p = productMap.get(id);
        if (p == null) {
            System.out.println("No such product.");
            return;
        }

        System.out.println("Current threshold: " + p.getMinStock());
        System.out.print("Enter new threshold: ");
        int newMin = input.nextInt();
        input.nextLine();

        p.setMinStock(newMin);
        System.out.println("Threshold updated.");
    }

    public void restockProduct() {
        System.out.print("Enter Product ID: ");
        String id = input.nextLine();

        Product p = productMap.get(id);
        if (p == null) {
            System.out.println("Could not find product.");
            return;
        }

        System.out.print("Quantity to add: ");
        int amount = input.nextInt();
        input.nextLine();

        int newTotal = p.getCurrentStock() + amount;
        p.setCurrentStock(newTotal);

        System.out.println("Restocked. New stock level: " + newTotal);
    }

    private void showMenu() {
        System.out.println("\n===== Inventory Menu =====");
        System.out.println("1. Add Product");
        System.out.println("2. Record Sale");
        System.out.println("3. Check Low Stock");
        System.out.println("4. View Inventory");
        System.out.println("5. Update Restock Threshold");
        System.out.println("6. Restock Product");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    public void run() {
        System.out.println("Welcome to Inventory Central!");

        while (true) {
            showMenu();

            int option = -1;
            try {
                option = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                input.nextLine(); 
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (option) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    recordSale();
                    break;
                case 3:
                    checkLowStock();
                    break;
                case 4:
                    viewInventory();
                    break;
                case 5:
                    updateThreshold();
                    break;
                case 6:
                    restockProduct();
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid selection, try again.");
            }
        }
    }
}
