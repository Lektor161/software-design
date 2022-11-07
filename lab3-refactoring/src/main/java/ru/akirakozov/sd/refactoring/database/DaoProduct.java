package ru.akirakozov.sd.refactoring.database;

import ru.akirakozov.sd.refactoring.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoProduct extends AbstractDao {
    public DaoProduct(String url) {
        super(url);
        update("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public void addProduct(Product product) {
        update("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
    }

    public List<Product> getProducts() {
        return execute("SELECT * FROM PRODUCT", rs -> {
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                String  name = rs.getString("name");
                int price  = rs.getInt("price");
                products.add(new Product(name, price));
            }
            return products;
        });
    }

    public Optional<Product> getMax() {
        return execute("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", rs -> {
            Optional<Product> product = Optional.empty();
            while (rs.next()) {
                String  name = rs.getString("name");
                int price  = rs.getInt("price");
                product = Optional.of(new Product(name, price));
            }
            return product;
        });
    }

    public Optional<Product> getMin() {
        return execute("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", rs -> {
            Optional<Product> product = Optional.empty();
            while (rs.next()) {
                String  name = rs.getString("name");
                int price  = rs.getInt("price");
                product = Optional.of(new Product(name, price));
            }
            return product;
        });
    }

    public int getSum() {
        return execute("SELECT SUM(price) FROM PRODUCT",
                rs -> rs.next() ? rs.getInt(1) : 0);
    }

    public int getCount() {
        return execute("SELECT COUNT(*) FROM PRODUCT",
                rs -> rs.next() ? rs.getInt(1) : 0);
    }
}
