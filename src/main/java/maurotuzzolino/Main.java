package maurotuzzolino;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Customer customer1 = new Customer(1L, "Mario Rossi", 1);
        Customer customer2 = new Customer(2L, "Luisa Verdi", 2);
        Customer customer3 = new Customer(3L, "Anna Bianchi", 1);

        Product product1 = new Product(1L, "Laptop", "Elettronica", 1200.0);
        Product product2 = new Product(2L, "Libro", "Cultura", 20.0);
        Product product3 = new Product(3L, "Smartphone", "Elettronica", 800.0);
        Product product4 = new Product(4L, "Zaino", "Accessori", 50.0);
        Product product5 = new Product(5L, "TV", "Elettronica", 1300.0);
        Product product6 = new Product(6L, "Auricolari", "Elettronica", 150.0);

        Order order1 = new Order(1L, "Spedito", LocalDate.now().minusDays(5), LocalDate.now(), List.of(product1, product2), customer1);
        Order order2 = new Order(2L, "In elaborazione", LocalDate.now().minusDays(2), LocalDate.now().plusDays(3), List.of(product3), customer2);
        Order order3 = new Order(3L, "Consegnato", LocalDate.now().minusDays(10), LocalDate.now().minusDays(2), List.of(product4), customer1);
        Order order4 = new Order(4L, "Spedito", LocalDate.now().minusDays(3), LocalDate.now().plusDays(1), List.of(product2, product4), customer3);
        Order order5 = new Order(5L, "In elaborazione", LocalDate.now(), LocalDate.now().plusDays(4), List.of(product1), customer2);

        List<Order> allOrders = List.of(order1, order2, order3, order4, order5);
        List<Product> products = List.of(product1, product2, product3, product4, product5, product6);


        System.out.println("========== Parte 1 ==========");
        Map<String, List<Order>> ordersByCustomer = allOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getName()));

        ordersByCustomer.forEach((customerName, orders) -> {
            System.out.println("Cliente: " + customerName);
            orders.forEach(order -> System.out.println("  " + order));
            System.out.println();
        });


        System.out.println("========== Parte 2 ==========");
        Map<Customer, Double> totalSalesByCustomer = allOrders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.summingDouble(order -> order.getProducts()
                                .stream()
                                .mapToDouble(Product::getPrice)
                                .sum())
                ));

        totalSalesByCustomer.forEach((customer, total) -> {
            System.out.println("Cliente: " + customer.getName() + " | Totale acquisti: €" + total);
        });


        System.out.println("\n========== Parte 3 ==========");
        List<Product> top3ExpensiveProducts = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(3)
                .collect(Collectors.toList());

        top3ExpensiveProducts.forEach(p ->
                System.out.println(p.getName() + " - €" + p.getPrice()));


        System.out.println("\n========== Parte 4 ==========");
        double averageOrderTotal = allOrders.stream()
                .mapToDouble(order -> order.getProducts()
                        .stream()
                        .mapToDouble(Product::getPrice)
                        .sum())
                .average()
                .orElse(0.0);

        System.out.println("Media valore ordine: €" + averageOrderTotal);


        System.out.println("\n========== Parte 5 ==========");
        Map<String, Double> totalPriceByCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.summingDouble(Product::getPrice)
                ));
        
        totalPriceByCategory.forEach((category, total) ->
                System.out.println("Categoria: " + category + " | Totale: €" + total));
    }
}
