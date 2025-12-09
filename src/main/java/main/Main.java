package main;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args) {
        DataRetriever retriever = new DataRetriever();

        System.out.println("=== Test getAllCategories ===");
        retriever.getAllCategories().forEach(System.out::println);

        System.out.println("\n=== Test getProductList (page=1, size=3) ===");
        retriever.getProductList(1, 3).forEach(System.out::println);

        System.out.println("\n=== Test getProductList (page=2, size=2) ===");
        retriever.getProductList(2, 2).forEach(System.out::println);

        System.out.println("\n=== Tests getProductsByCriteria ===");

        // Test 1: Recherche par nom de produit
        System.out.println("1. Recherche 'Dell':");
        retriever.getProductsByCriteria("Dell", null, null, null)
                .forEach(System.out::println);

        // Test 2: Recherche par catégorie
        System.out.println("\n2. Recherche catégorie 'info':");
        retriever.getProductsByCriteria(null, "info", null, null)
                .forEach(System.out::println);

        // Test 3: Recherche par date
        System.out.println("\n3. Produits entre 2024-02-01 et 2024-03-01:");
        Instant minDate = LocalDateTime.of(2024, 2, 1, 0, 0).toInstant(ZoneOffset.UTC);
        Instant maxDate = LocalDateTime.of(2024, 3, 1, 23, 59, 59).toInstant(ZoneOffset.UTC);
        retriever.getProductsByCriteria(null, null, minDate, maxDate)
                .forEach(System.out::println);

        System.out.println("\n=== Tests avec pagination ===");
        System.out.println("1. Page 1, taille 10 (tous):");
        retriever.getProductsByCriteria(null, null, null, null, 1, 10)
                .forEach(System.out::println);

        System.out.println("\n2. Recherche 'Dell', page 1, taille 5:");
        retriever.getProductsByCriteria("Dell", null, null, null, 1, 5)
                .forEach(System.out::println);

        System.out.println("\n3. Catégorie 'informatique', page 1, taille 10:");
        retriever.getProductsByCriteria(null, "informatique", null, null, 1, 10)
                .forEach(System.out::println);
    }
}
