package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Model.Product;
import ru.akirakozov.sd.refactoring.database.DaoProduct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {

    public GetProductsServlet(DaoProduct dao) {
        super(dao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("<html><body>");
        for (Product product: dao.getProducts()) {
            response.getWriter().println(product);
        }
        response.getWriter().println("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
