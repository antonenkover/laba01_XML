package main.java.XML.models;

import java.util.Objects;

public class Product {
    private String barcode;
    private String name;
    private int weight;
    private int price;

    public Product(String barcode, String title, int weight, int price) {
        this.barcode = barcode;
        this.name = title;
        this.weight = weight;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "\nProduct{\n" +
                "barcode='" + barcode + '\'' +
                ",\n title='" + name + '\'' +
                ",\n weight=" + weight +
                ",\n price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return weight == product.weight &&
                price == product.price &&
                barcode.equals(product.barcode) &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name, weight, price);
    }
}
