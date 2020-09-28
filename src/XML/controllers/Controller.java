package XML.controllers;

import XML.classes.Department;
import XML.classes.Product;
import XML.classes.Warehouse;
import XML.services.ParserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class Controller {
    public Button saveToXML;
    public Button parseXML;
    public Button confirmDepartmentCRUD;
    public TextField departmentNameInput;
    public RadioButton RbAddDepartment;
    public RadioButton RbDeleteDepartment;
    public Button openXML;
    public TextField productNameInput;
    public TextField productWeightInput;
    public TextField productPriceInput;
    public TextField productBarcodeInput;
    public Button confirmProductCRUD;
    public TextField departmentIDInput;
    public RadioButton RbUpdateDepartment;
    public RadioButton RbAddProduct;
    public RadioButton RbDeleteProduct;
    public RadioButton RbUpdateProduct;
    public Button listAllDepartments;
    public Button searchProductOrDepByID;
    public TextField barcodeOrIDInput;
    public RadioButton searchDepartmentByID;
    public RadioButton searchProductByID;
    public TextArea mainTextPane;
    public TextArea errorTextPane;
    public TextField productSearchDepartmentId;
    public Button viewAllXML;
    public CheckBox showProductsByIdSearch;
    public Button countAllDepartments;
    public Button countAllProducts;
    ParserService parserService = new ParserService();
    Warehouse warehouse = new Warehouse(1, "Amazon");
    String xmlFile;

    @FXML
    private void initialize() {
        ToggleGroup group1 = new ToggleGroup();
        searchProductByID.setToggleGroup(group1);
        searchDepartmentByID.setToggleGroup(group1);

        ToggleGroup group2 = new ToggleGroup();
        RbAddDepartment.setToggleGroup(group2);
        RbDeleteDepartment.setToggleGroup(group2);
        RbUpdateDepartment.setToggleGroup(group2);

        ToggleGroup group3 = new ToggleGroup();
        RbAddProduct.setToggleGroup(group3);
        RbDeleteProduct.setToggleGroup(group3);
        RbUpdateProduct.setToggleGroup(group3);
    }

    public void listAllDepartments(ActionEvent actionEvent) {
        listAllDepartments.setOnAction(value -> mainTextPane.setText(warehouse.printDepartments(warehouse.getDepartments())));
    }

    public void parseXML(ActionEvent actionEvent) {
        parseXML.setOnAction(event -> mainTextPane.setText(parserService.parseXML(xmlFile).toString()));
    }

    public void openXMLFile(ActionEvent actionEvent) {
        openXML.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML files", "*.xml")
            );
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                xmlFile = selectedFile.getAbsolutePath();
                warehouse = parserService.parseXML(selectedFile.getAbsolutePath());
            }
            mainTextPane.setText(warehouse.toString());
        });
    }

    public void saveToXML(ActionEvent actionEvent) {
        saveToXML.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                parserService.createXML(file.getAbsolutePath(), warehouse);
                errorTextPane.setText("XML saved successfully to file\n " + file.getAbsolutePath());
            }
        });
    }

    public void searchProductOrDepartmentByID(ActionEvent actionEvent) {
        searchProductOrDepByID.setOnAction(event -> {
            if (barcodeOrIDInput.getText().isEmpty()) {
                errorTextPane.setText("Please enter barcode or ID.");
            } else {
                if (searchDepartmentByID.isSelected()) {
                    Optional<Department> searchedDepartment = warehouse.getDepartmentById(Integer.parseInt(barcodeOrIDInput.getText()));
                    if (showProductsByIdSearch.isSelected()) {
                        mainTextPane.setText(searchedDepartment.isPresent() ? searchedDepartment.toString() : "Department not found.");
                    } else {
                        mainTextPane.setText(searchedDepartment.isPresent() ? searchedDepartment.get().printDepartment(searchedDepartment.get()) : "Department not found.");
                    }
                } else if (searchProductByID.isSelected()) {
                    Optional<Product> searchedProduct = warehouse.getProductsByBarcode(barcodeOrIDInput.getText());
                    mainTextPane.setText(searchedProduct.isPresent() ? searchedProduct.toString() : "Product not found.");
                } else errorTextPane.setText("Please choose whether you want to search department or product.");
            }
        });
    }

    public void departmentCRUD(ActionEvent actionEvent) {
        confirmDepartmentCRUD.setOnAction(event -> {
            if (departmentIDInput.getText().isEmpty() || departmentNameInput.getText().isEmpty()) {
                errorTextPane.setText("Please enter parameters of department");
            } else {
                if (RbAddDepartment.isSelected()) {
                    Department department = new Department(Integer.parseInt(departmentIDInput.getText()), departmentNameInput.getText());
                    warehouse.addDepartment(department);
                    mainTextPane.setText(warehouse.toString());
                } else if (RbUpdateDepartment.isSelected()) {
                    Optional<Department> department = warehouse.getDepartmentById(Integer.parseInt(departmentIDInput.getText()));
                    department.ifPresent(value -> {
                        value.setName(departmentNameInput.getText());
                    });
                    mainTextPane.setText(warehouse.toString());
                } else if (RbDeleteDepartment.isSelected()) {
                    Department department = new Department(Integer.parseInt(departmentIDInput.getText()), departmentNameInput.getText());
                    warehouse.deleteDepartment(department);
                    mainTextPane.setText(warehouse.toString());
                }
            }
        });
    }

    public void productCRUD(ActionEvent actionEvent) {
        confirmProductCRUD.setOnAction(event -> {
            if (productSearchDepartmentId.getText().isEmpty()) {
                errorTextPane.setText("Please enter department id.");
            } else {
                if (productBarcodeInput.getText().isEmpty() || productPriceInput.getText().isEmpty() || productWeightInput.getText().isEmpty() || productNameInput.getText().isEmpty()) {
                    errorTextPane.setText("Please enter all details about product.");
                } else {
                    if (RbAddProduct.isSelected()) {
                        Product newProduct = new Product(productBarcodeInput.getText(), productNameInput.getText(), Integer.parseInt(productWeightInput.getText()), Integer.parseInt(productPriceInput.getText()));
                        warehouse.addProduct(newProduct, Integer.parseInt(productSearchDepartmentId.getText()));
                        mainTextPane.setText(warehouse.toString());
                    } else if (RbDeleteProduct.isSelected()) {
                        Product newProduct = new Product(productBarcodeInput.getText(), productNameInput.getText(), Integer.parseInt(productWeightInput.getText()), Integer.parseInt(productPriceInput.getText()));
                        warehouse.deleteProduct(newProduct, Integer.parseInt(productSearchDepartmentId.getText()));
                        mainTextPane.setText(warehouse.toString());
                    } else if (RbUpdateProduct.isSelected()) {
                        Optional<Product> product = warehouse.getProductsByBarcode(productBarcodeInput.getText());
                        product.ifPresent(value -> {
                            value.setName(productNameInput.getText());
                            value.setPrice(Integer.parseInt(productPriceInput.getText()));
                            value.setWeight(Integer.parseInt(productWeightInput.getText()));
                            mainTextPane.setText(warehouse.toString());
                        });
                        mainTextPane.setText(warehouse.toString());
                    }
                }
            }
        });
    }

    public void viewAllXML(ActionEvent actionEvent) {
        viewAllXML.setOnAction(event -> mainTextPane.setText(warehouse.toString()));
    }

    public void countAllProducts(ActionEvent actionEvent) {
        countAllProducts.setOnAction(event -> {
            mainTextPane.setText("Number of products: " + warehouse.countProducts());
        });
    }

    public void countAllDepartments(ActionEvent actionEvent) {
        countAllDepartments.setOnAction(event -> mainTextPane.setText("Number of departments: " + warehouse.countDepartments()));
    }

    public void setErrorTextPaneTextUwU(String error) {
        errorTextPane.setText(error);
    }
}
