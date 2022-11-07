package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.mock;

public abstract class AbstractProductServletTest {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");

    protected AbstractProductServletTest() throws SQLException {
    }

    @Before
    public void init() throws SQLException {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)");
            statement.executeUpdate("DELETE FROM PRODUCT WHERE 1 = 1");
        }
    }

    protected void putDatabase(Product product) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            String sql = "INSERT INTO PRODUCT (NAME, PRICE) " +
                    String.format("VALUES (\"%s\", %s)", product.getName(), product.getPrice());
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    protected void checkDatabaseContain(List<Product> products) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
            for(Product product : products) {
                Assert.assertTrue(rs.next());
                Assert.assertEquals(product.getName(), rs.getString("name"));
                Assert.assertEquals(product.getPrice(), rs.getInt("price"));
            }
            Assert.assertFalse(rs.next());
            stmt.close();
        }
    }

    public static class Product {
        private final String name;
        private final int price;

        public Product(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return String.format("%s\t%s</br>\n", name, price);
        }
    }
}
