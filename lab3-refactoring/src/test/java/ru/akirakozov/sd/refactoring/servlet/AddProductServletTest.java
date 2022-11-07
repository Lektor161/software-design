package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.when;


public class AddProductServletTest extends AbstractProductServletTest {
    private AddProductServlet servlet;

    public AddProductServletTest() throws SQLException {
    }


    @Before
    public void setup() {
        servlet = new AddProductServlet();
    }

    @Test
    public void addOneProduct() throws IOException, SQLException {
        StringWriter testWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(testWriter));
        when(request.getParameter("name")).thenReturn("product_1");
        when(request.getParameter("price")).thenReturn("10");

        servlet.doGet(request, response);
        Assert.assertEquals("OK" + System.lineSeparator(), testWriter.toString());

        checkDatabaseContain(List.of(new Product("product_1", 10)));
    }
}
