package dining;

public class Main {
    public static void main(String[] args) {
        DiningExperienceManager manager = new DiningExperienceManager();

        manager.addToMenu("Encebollado", 10.0);
        manager.addToMenu("Seco de chivo", 15.0);
        manager.addToMenu("Sopa de camarón", 20.0);

        manager.takeOrder();
        int totalCost = manager.processOrder();

        if (totalCost != -1) {
            System.out.println("Costo total del pedido: $" + totalCost);
        } else {
            System.out.println("Pedido no válido.");
        }
    }
}
