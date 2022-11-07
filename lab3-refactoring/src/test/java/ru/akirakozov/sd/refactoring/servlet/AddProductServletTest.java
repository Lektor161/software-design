package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.Model.Product;
import ru.akirakozov.sd.refactoring.database.DaoProduct;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;


public class AddProductServletTest extends AbstractProductServletTest {
    private AddProductServlet servlet;

    public AddProductServletTest() throws SQLException {
    }

    @Before
    public void setup() {
        servlet = new AddProductServlet(new DaoProduct("jdbc:sqlite:test.db"));
    }

    @Test
    public void addOneProduct() throws IOException, SQLException {
        add(new Product("product", 10));
        checkDatabaseContain(List.of(new Product("product", 10)));
    }

    @Test
    public void addMany() throws IOException, SQLException {
        List<Product> products = IntStream.range(0, 1000)
                .mapToObj(i -> new Product(String.format("product_%s", i), i))
                .collect(Collectors.toList());

        for (Product product: products) {
            add(product);
        }
        checkDatabaseContain(products);
    }

    private void add(Product product) throws IOException {
        StringWriter testWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(testWriter));
        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(Long.toString(product.getPrice()));

        servlet.doGet(request, response);
        Assert.assertEquals("OK" + System.lineSeparator(), testWriter.toString());
    }

}
