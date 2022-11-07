package ru.akirakozov.sd.refactoring.Model;

public class Product {
    private final String name;
    private final long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s</br>", name, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product product = (Product) obj;
            return this.name.equals(product.name) &&
                    this.price == product.price;
        }
        return false;
    }
}
