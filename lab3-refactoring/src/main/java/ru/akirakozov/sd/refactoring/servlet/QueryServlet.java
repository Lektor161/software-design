package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DaoProduct;
import ru.akirakozov.sd.refactoring.printer.ResponsePrinter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            case "max" -> ResponsePrinter.printMax(response, dao.getMax().orElse(null));
            case "min" -> ResponsePrinter.printMin(response, dao.getMin().orElse(null));
            case "sum" -> ResponsePrinter.printSum(response, dao.getSum());
            case "count" -> ResponsePrinter.printCount(response, dao.getCount());
            default -> ResponsePrinter.printUnknownCommand(response, command);
        }
    }

}
