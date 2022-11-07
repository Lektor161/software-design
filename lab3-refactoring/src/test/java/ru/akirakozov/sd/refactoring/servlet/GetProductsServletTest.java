package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.Model.Product;
import ru.akirakozov.sd.refactoring.database.DaoProduct;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class GetProductsServletTest extends AbstractProductServletTest {
    private GetProductsServlet servlet;

    @Before
    public void setup() {
        servlet = new GetProductsServlet(new DaoProduct("jdbc:sqlite:test.db"));
    }

    @Test
    public void testEmpty() throws IOException {
        test(Collections.emptyList());
    }

    @Test
    public void testOne() throws IOException {
        test(List.of(new Product("product", 10)));
    }

    @Test
    public void testMany() throws IOException {
        test(getProducts(1000));
    }

    private void test(List<Product> products) throws IOException {
        StringBuilder expected = new StringBuilder("<html><body>\n");
        for (Product product : products) {
            daoProduct.addProduct(product);
            expected.append(product).append("\n");
        }
        expected.append("</body></html>\n");

        servlet.doGet(request, response);
        Assert.assertEquals(expected.toString(), testWriter.toString());
    }
}
