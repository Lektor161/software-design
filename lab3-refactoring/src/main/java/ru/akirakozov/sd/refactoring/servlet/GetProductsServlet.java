package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DaoProduct;
import ru.akirakozov.sd.refactoring.printer.ResponsePrinter;

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
        ResponsePrinter.printProducts(response, dao.getProducts());
    }
}
