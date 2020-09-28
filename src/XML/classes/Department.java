package XML.classes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class Department {
    private int id;
    private String name;
    private ArrayList<Product> products = new ArrayList<>();

    public Department(int id, String title) {
        this.id = id;
        this.name = title;
    }

    public void addProduct(Product product) {
        boolean exists = products.stream().anyMatch(product1 -> product1.getBarcode().equals(product.getBarcode()));
        if (!exists) {
            products.add(product);
        }
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public Predicate<Product> isProductBarcodeEqual(String barcode) {
        return p -> p.getBarcode().equals(barcode);
    }

    public Optional<Product> getProductByBarcode(String barcode) {
        return products.stream().filter(isProductBarcodeEqual(barcode)).findFirst();
    }

    public int countProductsForDepartment() {
        return products.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String printDepartment(Department department) {
        return "\nDepartment{\n" +
                "id=" + department.getId() +
                ",\n title='" + department.getName() + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "\nDepartment{\n" +
                "id=" + id +
                ",\n title='" + name + '\'' +
                ",\n products=" + products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}