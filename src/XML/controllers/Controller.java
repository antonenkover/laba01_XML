package XML.controllers;

import XML.models.Department;
import XML.models.Product;
import XML.models.Warehouse;
import XML.services.ParserService;
import XML.views.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class Controller extends View {

    private final ParserService parserService = new ParserService();
    Warehouse warehouse = new Warehouse(1, "Amazon");

    @FXML
    private void initialize() {
        setToggleGroups();
    }

    @FXML
    public void listAllDepartments(ActionEvent actionEvent) {
        listAllDepartments();
    }

    @FXML
    public void openXMLFile(ActionEvent actionEvent) {
        this.openXMLFile1();
    }


    @FXML
    public void saveToXML(ActionEvent actionEvent) {
        saveToXML1();
    }


    @FXML
    public void searchProductOrDepartmentByID(ActionEvent actionEvent) {
        searchProductOrDepartmentByID();
    }


    @FXML
    public void departmentCRUD(ActionEvent actionEvent) {
        departmentCRUD();
    }


    @FXML
    public void productCRUD(ActionEvent actionEvent) {
        productCRUD();
    }


    @FXML
    public void viewAllXML(ActionEvent actionEvent) {
        viewAllXML();
    }


    @FXML
    public void countAllProducts(ActionEvent actionEvent) {
        countAllProducts();
    }


    @FXML
    public void countAllDepartments(ActionEvent actionEvent) {
        countAllDepartments();
    }

    private void listAllDepartments() {
        setMainTextPaneText(warehouse.printDepartments(warehouse.getDepartments()));
    }

    private void openXMLFile1() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML files", "*.xml")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String xmlFile = selectedFile.getAbsolutePath();
            String isXMLValid = parserService.validateXML(xmlFile);
            if (isXMLValid == null) {
                warehouse = parserService.parseXML();
                setMainTextPaneText(warehouse.toString());
            } else {
                setErrorTextPaneText(isXMLValid);
            }
        }
    }

    private void saveToXML1() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            parserService.createXML(file.getAbsolutePath(), warehouse);
            setErrorTextPaneText("XML saved successfully to file\n " + file.getAbsolutePath());
        }
    }

    private void searchProductOrDepartmentByID() {
        if (getBarcodeOrIDInput().isEmpty()) {
            setErrorTextPaneText("Please enter barcode or ID.");
        } else {
            if (isSelectedSearchDepartmentByID()) {
                Optional<Department> searchedDepartment = warehouse.getDepartmentById(Integer.parseInt(getBarcodeOrIDInput()));
                if (isSelectedShowProductsByIdSearch()) {
                    setMainTextPaneText(searchedDepartment.isPresent() ? searchedDepartment.toString() : "Department not found.");
                } else {
                    setMainTextPaneText(searchedDepartment.isPresent() ? searchedDepartment.get().printDepartment(searchedDepartment.get()) : "Department not found.");
                }
            } else if (isSelectedSearchProductByID()) {
                Optional<Product> searchedProduct = warehouse.getProductsByBarcode(getBarcodeOrIDInput());
                setMainTextPaneText(searchedProduct.isPresent() ? searchedProduct.toString() : "Product not found.");
            } else setErrorTextPaneText("Please choose whether you want to search department or product.");
        }
    }

    private void departmentCRUD() {
        if (getDepartmentIDInput().isEmpty() || getDepartmentNameInput().isEmpty()) {
            setErrorTextPaneText("Please enter parameters of department");
        } else {
            if (isSelectedRbAddDepartment()) {
                Department department = new Department(Integer.parseInt(getDepartmentIDInput()), getDepartmentNameInput());
                warehouse.addDepartment(department);
                setMainTextPaneText(warehouse.toString());
            } else if (isSelectedRbUpdateDepartment()) {
                Optional<Department> department = warehouse.getDepartmentById(Integer.parseInt(getDepartmentIDInput()));
                department.ifPresent(value -> value.setName(getDepartmentNameInput()));
                setMainTextPaneText(warehouse.toString());
            } else if (isSelectedRbDeleteDepartment()) {
                Department department = new Department(Integer.parseInt(getDepartmentIDInput()), getDepartmentNameInput());
                warehouse.deleteDepartment(department);
                setMainTextPaneText(warehouse.toString());
            }
        }
    }

    private void productCRUD() {
        if (getProductSearchDepartmentId().isEmpty()) {
            setErrorTextPaneText("Please enter department id.");
        } else {
            if (getProductBarcodeInput().isEmpty() || getProductPriceInput().isEmpty() || getProductWeightInput().isEmpty() || getProductNameInput().isEmpty()) {
                setErrorTextPaneText("Please enter all details about product.");
            } else {
                if (isSelectedRbAddProduct()) {
                    Product newProduct = new Product(getProductBarcodeInput(), getProductNameInput(), Integer.parseInt(getProductWeightInput()), Integer.parseInt(getProductPriceInput()));
                    warehouse.addProduct(newProduct, Integer.parseInt(getProductSearchDepartmentId()));
                    setMainTextPaneText(warehouse.toString());
                } else if (isSelectedRbDeleteProduct()) {
                    Product newProduct = new Product(getProductBarcodeInput(), getProductNameInput(), Integer.parseInt(getProductWeightInput()), Integer.parseInt(getProductPriceInput()));
                    warehouse.deleteProduct(newProduct, Integer.parseInt(getProductSearchDepartmentId()));
                    setMainTextPaneText(warehouse.toString());
                } else if (isSelectedRbUpdateProduct()) {
                    Optional<Product> product = warehouse.getProductsByBarcode(getProductBarcodeInput());
                    product.ifPresent(value -> {
                        value.setName(getProductNameInput());
                        value.setPrice(Integer.parseInt(getProductPriceInput()));
                        value.setWeight(Integer.parseInt(getProductWeightInput()));
                        setMainTextPaneText(warehouse.toString());
                    });
                    setMainTextPaneText(warehouse.toString());
                }
            }
        }
    }

    private void viewAllXML() {
        setMainTextPaneText(warehouse.toString());
    }

    private void countAllProducts() {
        setMainTextPaneText("Number of products: " + warehouse.countProducts());
    }

    private void countAllDepartments() {
        setMainTextPaneText("Number of departments: " + warehouse.countDepartments());
    }

}
