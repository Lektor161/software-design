package ru.akirakozov.sd.refactoring.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.Model.Product;
import ru.akirakozov.sd.refactoring.servlet.AbstractProductServletTest;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DaoProductTest {
    private DaoProduct daoProduct;

    @Before
    public void init() {
        daoProduct = new DaoProduct("jdbc:sqlite:test.db");
        daoProduct.update("DELETE FROM PRODUCT WHERE 1 = 1");
    }

    @Test
    public void testEmpty() {
        Assert.assertTrue(daoProduct.getProducts().isEmpty());
        Assert.assertTrue(daoProduct.getMax().isEmpty());
        Assert.assertTrue(daoProduct.getMin().isEmpty());
        Assert.assertEquals(0, daoProduct.getSum());
        Assert.assertEquals(0, daoProduct.getCount());
    }

    @Test
    public void testOne() {
        Product product = new Product("product", 1);
        daoProduct.addProduct(product);
        Assert.assertEquals(List.of(product), daoProduct.getProducts());
        Assert.assertEquals(Optional.of(product), daoProduct.getMax());
        Assert.assertEquals(Optional.of(product), daoProduct.getMin());
        Assert.assertEquals(product.getPrice(), daoProduct.getSum());
        Assert.assertEquals(1, daoProduct.getCount());
    }

    @Test
    public void testMany() {
        List<Product> products = AbstractProductServletTest.getProducts(1000);
        products.forEach(product -> daoProduct.addProduct(product));
        Assert.assertEquals(products, daoProduct.getProducts());
        Assert.assertEquals(products.stream().max(Comparator.comparingLong(Product::getPrice)), daoProduct.getMax());
        Assert.assertEquals(products.stream().min(Comparator.comparingLong(Product::getPrice)), daoProduct.getMin());
        Assert.assertEquals(products.stream().map(Product::getPrice).reduce(0L, Long::sum).intValue(), daoProduct.getSum());
        Assert.assertEquals(products.size(), daoProduct.getCount());
    }
}
