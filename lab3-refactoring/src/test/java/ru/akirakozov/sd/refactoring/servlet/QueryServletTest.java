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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

public class QueryServletTest extends AbstractProductServletTest {
    QueryServlet servlet;

    public QueryServletTest() throws SQLException {
    }

    @Before
    public void setup() {
        servlet = new QueryServlet(new DaoProduct("jdbc:sqlite:test.db"));
    }

    @Test
    public void testBadCommand() throws IOException {
        StringWriter testWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(testWriter));
        when(request.getParameter("command")).thenReturn("badCommand");
        servlet.doGet(request, response);
        Assert.assertEquals("Unknown command: badCommand\n", testWriter.toString());
    }

    @Test
    public void testEmpty() throws IOException, SQLException {
        test(Collections.emptyList());
    }

    @Test
    public void testOne() throws SQLException, IOException {
        test(List.of(new Product("product", 100)));
    }

    @Test
    public void testMany() throws SQLException, IOException {
        test(IntStream.range(0, 1000)
                .mapToObj(i -> new Product(String.format("product_%s", i), i))
                .collect(Collectors.toList()));
    }

    private void test(List<Product> products) throws SQLException, IOException {
        for (Product product: products) {
            putDatabase(product);
        }

        test(products.stream().max(Comparator.comparingLong(Product::getPrice)).orElse(null),
                products.stream().min(Comparator.comparingLong(Product::getPrice)).orElse(null),
                products.stream().map(Product::getPrice).reduce(0L, Long::sum),
                products.size()
        );
    }

    private void test(Product max, Product min, long sum, int count) throws IOException {
        test("max", "<h1>Product with max price: </h1>\n" + (max == null ? "" : max + "\n"));
        test("min", "<h1>Product with min price: </h1>\n" + (min == null ? "" : min + "\n"));
        test("sum", "Summary price: \n" + sum + "\n");
        test("count", "Number of products: \n" + count + "\n");
    }

    private void test(String command, String expected) throws IOException {
        StringWriter testWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(testWriter));

        when(request.getParameter("command")).thenReturn(command);
        servlet.doGet(request, response);

        Assert.assertEquals(
                "<html><body>\n" +
                        expected +
                        "</body></html>\n", testWriter.toString());
    }
}
