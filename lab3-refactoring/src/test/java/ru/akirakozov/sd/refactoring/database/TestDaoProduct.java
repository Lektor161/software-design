package ru.akirakozov.sd.refactoring.database;

public class TestDaoProduct extends DaoProduct {
    public TestDaoProduct(String url) {
        super(url);
    }

    public void removeAll() {
        update("DELETE FROM PRODUCT WHERE 1 = 1");
    }
}
