import java.util.*;

class Stock {
    String name;
    double price;

    Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Transaction {
    String type;
    String stockName;
    int quantity;

    Transaction(String type, String stockName, int quantity) {
        this.type = type;
        this.stockName = stockName;
        this.quantity = quantity;
    }

    void display() {
        System.out.println(type + " " + quantity + " shares of " + stockName);
    }
}

class User {
    double balance;
    HashMap<String, Integer> portfolio;
    ArrayList<Transaction> transactions;

    User(double balance) {
        this.balance = balance;
        portfolio = new HashMap<>();
        transactions = new ArrayList<>();
    }

    void buyStock(Stock stock, int quantity) {
        double cost = stock.price * quantity;

        if (cost <= balance) {
            balance -= cost;

            portfolio.put(stock.name,
                    portfolio.getOrDefault(stock.name, 0) + quantity);

            transactions.add(
                    new Transaction("BOUGHT", stock.name, quantity));

            System.out.println("Bought " + quantity +
                    " shares of " + stock.name);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.name, 0);

        if (owned >= quantity) {
            balance += stock.price * quantity;

            portfolio.put(stock.name, owned - quantity);

            transactions.add(
                    new Transaction("SOLD", stock.name, quantity));

            System.out.println("Sold " + quantity +
                    " shares of " + stock.name);
        } else {
            System.out.println("Not enough shares!");
        }
    }

    void displayPortfolio(Stock apple, Stock google) {
        System.out.println("\nPortfolio:");

        double portfolioValue = 0;

        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned.");
        }

        for (String stock : portfolio.keySet()) {
            int shares = portfolio.get(stock);

            System.out.println(stock + " : " + shares + " shares");

            if (stock.equals("Apple")) {
                portfolioValue += shares * apple.price;
            } else if (stock.equals("Google")) {
                portfolioValue += shares * google.price;
            }
        }

        double totalWealth = balance + portfolioValue;

        System.out.println("Balance: $" + balance);
        System.out.println("Portfolio Value: $" + portfolioValue);
        System.out.println("Total Wealth: $" + totalWealth);
    }

    void showTransactions() {
        System.out.println("\nTransaction History:");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : transactions) {
                t.display();
            }
        }
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Stock apple = new Stock("Apple", 150);
        Stock google = new Stock("Google", 2800);

        User user = new User(10000);

        while (true) {

            System.out.println("\n--- Stock Trading Platform ---");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\nMarket Data");
                    System.out.println("Apple : $" + apple.price);
                    System.out.println("Google : $" + google.price);
                    break;

                case 2:
                    System.out.println("1. Apple");
                    System.out.println("2. Google");
                    System.out.print("Choose stock: ");
                    int buyChoice = sc.nextInt();

                    System.out.print("Quantity: ");
                    int qty = sc.nextInt();

                    if (buyChoice == 1) {
                        user.buyStock(apple, qty);
                    } else if (buyChoice == 2) {
                        user.buyStock(google, qty);
                    } else {
                        System.out.println("Invalid choice!");
                    }
                    break;

                case 3:
                    System.out.println("1. Apple");
                    System.out.println("2. Google");
                    System.out.print("Choose stock: ");
                    int sellChoice = sc.nextInt();

                    System.out.print("Quantity: ");
                    qty = sc.nextInt();

                    if (sellChoice == 1) {
                        user.sellStock(apple, qty);
                    } else if (sellChoice == 2) {
                        user.sellStock(google, qty);
                    } else {
                        System.out.println("Invalid choice!");
                    }
                    break;

                case 4:
                    user.displayPortfolio(apple, google);
                    break;

                case 5:
                    user.showTransactions();
                    break;

                case 6:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}