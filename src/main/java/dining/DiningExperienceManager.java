package dining;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class DiningExperienceManager {
    private Map<Integer, Double> menu;
    private Map<Integer, Integer> order;
    private double totalCost;
    private List<String> menuItems;

    public DiningExperienceManager() {
        menu = new HashMap<>();
        order = new HashMap<>();
        totalCost = 5.0;  // Costo base
        menuItems = new ArrayList<>();
    }

    public void displayMenu() {
        System.out.println("Menú:");
        int index = 1;
        for (String item : menuItems) {
            System.out.println(index + ". " + item + " - $" + menu.get(index));
            index++;
        }
    }

    public void addToMenu(String meal, double price) {
        menuItems.add(meal);
        menu.put(menuItems.size(), price);
    }

    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            System.out.print("Seleccione el número de un plato (o 'fin' para finalizar): ");
            String choice = scanner.nextLine();

            if (choice.equals("fin")) {
                break;
            }

            try {
                int mealNumber = Integer.parseInt(choice);

                if (mealNumber < 1 || mealNumber > menuItems.size()) {
                    System.out.println("Número de plato no válido. Por favor, seleccione otro.");
                    continue;
                }

                System.out.print("Cantidad: ");
                int quantity = scanner.nextInt();

                if (quantity <= 0 || quantity > 100) {
                    System.out.println("Cantidad no válida. Debe ser un número entre 1 y 100.");
                    continue;
                }

                if (order.containsKey(mealNumber)) {
                    order.put(mealNumber, order.get(mealNumber) + quantity);
                } else {
                    order.put(mealNumber, quantity);
                }

                scanner.nextLine(); 
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, seleccione un número de plato.");
                scanner.nextLine(); 
            }
        }
    }

    public void calculateTotalCost() {       
        totalCost = 5.0;  // Costo base
        for (Integer mealNumber : order.keySet()) {
            double price = menu.get(mealNumber);
            int quantity = order.get(mealNumber);
            totalCost += price * quantity;
        }

        if (order.size() > 5) {
            totalCost *= 0.9;  // Aplicar 10% de descuento por más de 5 platos
        }

        if (order.size() > 10) {
            totalCost *= 0.8;  // Aplicar 20% de descuento por más de 10 platos
        }

        if (totalCost > 100) {
            totalCost -= 25;  // Aplicar un descuento de $25 para un costo total superior a $100
        } else if (totalCost > 50) {
            totalCost -= 10;  // Aplicar un descuento de $10 para un costo total superior a $50
        }
    }

    public void displayOrder() {
        System.out.println("Resumen del pedido:");
        for (Integer mealNumber : order.keySet()) {
            String meal = menuItems.get(mealNumber - 1);
            int quantity = order.get(mealNumber);
            double price = menu.get(mealNumber);
            System.out.println(meal + " x" + quantity + " - $" + price * quantity);
        }
        System.out.println("Costo base: $5.00");

        double totalOrderCost = 5.0;  // Costo base
        for (Integer mealNumber : order.keySet()) {
            double price = menu.get(mealNumber);
            int quantity = order.get(mealNumber);
            totalOrderCost += price * quantity;
        }

        System.out.println("Costo total: $" + totalOrderCost);
    }

    public int processOrder() {
        if (order.isEmpty()) {
            System.out.println("No se ha realizado ningún pedido.");
            return -1;
        }

        calculateTotalCost();
        displayOrder();

        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Desea confirmar el pedido? (Sí/No): ");
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equalsIgnoreCase("si") || confirmation.equalsIgnoreCase("sí")) {
            return (int) totalCost;
        } else {
            System.out.println("Pedido cancelado.");
            return -1;
        }
    }
}

