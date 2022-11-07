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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;


public class GetProductsServletTest extends AbstractProductServletTest {
    public GetProductsServletTest() throws SQLException {
    }

    private GetProductsServlet servlet;

    @Before
    public void setup() {
        servlet = new GetProductsServlet(new DaoProduct("jdbc:sqlite:test.db"));
    }

    @Test
    public void testEmpty() throws IOException, SQLException {
        test(Collections.emptyList());
    }

    @Test
    public void testOne() throws IOException, SQLException {
        test(List.of(new Product("product", 10)));
    }

    @Test
    public void testMany() throws IOException, SQLException {
        test(IntStream.range(0, 1000)
                .mapToObj(i -> new Product(String.format("product_%s", i), i))
                .collect(Collectors.toList()));
    }

    private void test(List<Product> products) throws IOException, SQLException {
        StringWriter testWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(testWriter));

        StringBuilder expected = new StringBuilder("<html><body>\n");
        for (Product product : products) {
            putDatabase(product);
            expected.append(product).append("\n");
        }
        expected.append("</body></html>\n");

        servlet.doGet(request, response);
        Assert.assertEquals(expected.toString(), testWriter.toString());
    }
}
