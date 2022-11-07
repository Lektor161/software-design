package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import ru.akirakozov.sd.refactoring.Model.Product;
import ru.akirakozov.sd.refactoring.database.TestDaoProduct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractProductServletTest {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected TestDaoProduct daoProduct;

    protected StringWriter testWriter;

    @Before
    public void init() throws IOException {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        daoProduct = new TestDaoProduct("jdbc:sqlite:test.db");
        daoProduct.removeAll();
        updateWriter();

    }

    public static List<Product> getProducts(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new Product(String.format("product_%s", i), i))
                .collect(Collectors.toList());
    }

    protected void updateWriter() throws IOException {
        testWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(testWriter));
    }
}
