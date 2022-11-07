package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Model.Product;
import ru.akirakozov.sd.refactoring.database.DaoProduct;
import ru.akirakozov.sd.refactoring.printer.ResponsePrinter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractProductServlet {

    public AddProductServlet(DaoProduct dao) {
        super(dao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        dao.addProduct(new Product(name, price));
        ResponsePrinter.printOk(response);
    }
}
