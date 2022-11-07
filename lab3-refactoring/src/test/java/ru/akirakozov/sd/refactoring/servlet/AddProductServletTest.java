package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.Model.Product;
import ru.akirakozov.sd.refactoring.database.DaoProduct;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;


public class AddProductServletTest extends AbstractProductServletTest {
    private AddProductServlet servlet;

    @Before
    public void setup() {
        servlet = new AddProductServlet(new DaoProduct("jdbc:sqlite:test.db"));
    }

    @Test
    public void addOneProduct() throws IOException {
        Product product = new Product("product", 10);
        add(product);
        Assert.assertEquals(List.of(product), daoProduct.getProducts());
    }

    @Test
    public void addMany() throws IOException {
        List<Product> products = getProducts(1000);
        for (Product product: products) {
            add(product);
        }
        Assert.assertEquals(products, daoProduct.getProducts());
    }

    private void add(Product product) throws IOException {
        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(Long.toString(product.getPrice()));

        updateWriter();
        servlet.doGet(request, response);
        Assert.assertEquals("OK" + System.lineSeparator(), testWriter.toString());
    }

}
