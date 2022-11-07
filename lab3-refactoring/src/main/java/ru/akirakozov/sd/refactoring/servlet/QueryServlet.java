package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Model.Product;
import ru.akirakozov.sd.refactoring.database.DaoProduct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractProductServlet {
    public QueryServlet(DaoProduct dao) {
        super(dao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");
                Optional<Product> product = dao.getMax();
                if (product.isPresent()) {
                    response.getWriter().println(product.get());
                }
                response.getWriter().println("</body></html>");
            }
            case "min" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                Optional<Product> product = dao.getMin();
                if (product.isPresent()) {
                    response.getWriter().println(product.get());
                }
                response.getWriter().println("</body></html>");
            }
            case "sum" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");
                response.getWriter().println(dao.getSum());
                response.getWriter().println("</body></html>");
            }
            case "count" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");
                response.getWriter().println(dao.getCount());
                response.getWriter().println("</body></html>");
            }
            default -> response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
