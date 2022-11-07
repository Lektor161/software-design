package ru.akirakozov.sd.refactoring.printer;

import ru.akirakozov.sd.refactoring.Model.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResponsePrinter {
    public static void printOk(HttpServletResponse response) throws IOException {
        println(response, "OK");
    }

    public static void printProducts(HttpServletResponse response, List<Product> products) throws IOException {
        printHeader(response);
        for (Product product: products) {
            println(response, product.toString());
        }
        printFooter(response);
        setOk(response);
    }

    public static void printMax(HttpServletResponse response, Product product) throws IOException {
        printQuery(response, "<h1>Product with max price: </h1>", product);
    }

    public static void printMin(HttpServletResponse response, Product product) throws IOException {
        printQuery(response, "<h1>Product with min price: </h1>", product);
    }

    public static void printSum(HttpServletResponse response, int sum) throws IOException {
        printQuery(response, "Summary price: ", sum);
    }

    public static void printCount(HttpServletResponse response, int count) throws IOException {
        printQuery(response, "Number of products: ", count);
    }

    public static void printUnknownCommand(HttpServletResponse response, String command) throws IOException {
        println(response, "Unknown command: " + command);
        setOk(response);
    }

    private static void printQuery(HttpServletResponse response, String text, Object obj) throws IOException {
        printHeader(response);
        println(response, text);
        if (obj != null) {
            println(response, obj.toString());
        }
        printFooter(response);
        setOk(response);
    }


    private static void printHeader(HttpServletResponse response) throws IOException {
        println(response, "<html><body>");
    }

    private static void printFooter(HttpServletResponse response) throws IOException {
        println(response, "</body></html>");
    }

    private static void println(HttpServletResponse response, String text) throws IOException {
        response.getWriter().println(text);
    }

    private static void setOk(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
