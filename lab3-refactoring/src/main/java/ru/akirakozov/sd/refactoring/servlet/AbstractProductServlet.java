package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DaoProduct;

import javax.servlet.http.HttpServlet;

public abstract class AbstractProductServlet extends HttpServlet {
    protected final DaoProduct dao;

    public AbstractProductServlet(DaoProduct dao) {
        this.dao = dao;
    }
}
