package main;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final DBConnection dbConnection;

    public DataRetriever() {
        this.dbConnection = new DBConnection();
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT id, name FROM Product_category";

        try (Connection conn = dbConnection.getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        String query = "SELECT p.id, p.name, p.price, p.creation_datetime " +
                "FROM Product p " +
                "ORDER BY p.id " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, size);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));

                    Timestamp timestamp = rs.getTimestamp("creation_datetime");
                    product.setCreationDatetime(timestamp.toInstant());

                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName,
                                               Instant creationMin, Instant creationMax) {
        List<Product> products = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        queryBuilder.append("SELECT DISTINCT p.id, p.name, p.price, p.creation_datetime ");
        queryBuilder.append("FROM Product p ");
        queryBuilder.append("LEFT JOIN Product_category pc ON p.id = pc.product_id ");
        queryBuilder.append("WHERE 1=1 ");

        if (productName != null && !productName.trim().isEmpty()) {
            queryBuilder.append("AND p.name ILIKE ? ");
            parameters.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            queryBuilder.append("AND pc.name ILIKE ? ");
            parameters.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            queryBuilder.append("AND p.creation_datetime >= ? ");
            parameters.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            queryBuilder.append("AND p.creation_datetime <= ? ");
            parameters.add(Timestamp.from(creationMax));
        }

        queryBuilder.append("ORDER BY p.id");

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));

                    Timestamp timestamp = rs.getTimestamp("creation_datetime");
                    product.setCreationDatetime(timestamp.toInstant());

                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName,
                                               Instant creationMin, Instant creationMax,
                                               int page, int size) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        StringBuilder queryBuilder = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        queryBuilder.append("SELECT DISTINCT p.id, p.name, p.price, p.creation_datetime ");
        queryBuilder.append("FROM Product p ");
        queryBuilder.append("LEFT JOIN Product_category pc ON p.id = pc.product_id ");
        queryBuilder.append("WHERE 1=1 ");

        if (productName != null && !productName.trim().isEmpty()) {
            queryBuilder.append("AND p.name ILIKE ? ");
            parameters.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            queryBuilder.append("AND pc.name ILIKE ? ");
            parameters.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            queryBuilder.append("AND p.creation_datetime >= ? ");
            parameters.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            queryBuilder.append("AND p.creation_datetime <= ? ");
            parameters.add(Timestamp.from(creationMax));
        }

        queryBuilder.append("ORDER BY p.id ");
        queryBuilder.append("LIMIT ? OFFSET ?");

        parameters.add(size);
        parameters.add(offset);

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));

                    Timestamp timestamp = rs.getTimestamp("creation_datetime");
                    product.setCreationDatetime(timestamp.toInstant());

                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
