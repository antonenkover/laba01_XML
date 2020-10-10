package main.java.XML.formviews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.XML.models.Department;
import main.java.XML.models.Product;
import main.java.XML.models.Warehouse;
import main.java.XML.services.ParserService;
import main.java.XML.views.View;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.Optional;

public class FormView extends View {

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
            try {
                parserService.validateXML(xmlFile);
                warehouse = parserService.parseXML();
                setMainTextPaneText(warehouse.toString());
            } catch (SAXException e) {
                e.printStackTrace();
            }

//            if (isXMLValid == null) {
//                warehouse = parserService.parseXML();
//                setMainTextPaneText(warehouse.toString());
//            } else {
//                showErrorMessage(isXMLValid);
//            }
        } else {
            showWarningMessage("You haven't chosen XML file.");
        }
    }

    private void saveToXML1() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            parserService.createXML(file.getAbsolutePath(), warehouse);
            showInformationDialog("XML saved successfully to file\n " + file.getAbsolutePath());
        } else {
            showWarningMessage("You haven't selected a file to save to. Saving not completed.");
        }
    }

    private void searchProductOrDepartmentByID() {
        if (getBarcodeOrIDInput().isEmpty()) {
            showWarningMessage("ID or barcode wasn't entered.");
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
            } else showWarningMessage("Please choose whether you want to search department or product.");
        }
    }

    private void departmentCRUD() {
        if (getDepartmentIDInput().isEmpty() || getDepartmentNameInput().isEmpty()) {
            showWarningMessage("Please enter parameters of department");
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
            showWarningMessage("Please enter department id.");
        } else {
            if (getProductBarcodeInput().isEmpty() || getProductPriceInput().isEmpty() || getProductWeightInput().isEmpty() || getProductNameInput().isEmpty()) {
                showWarningMessage("Please enter all details about product.");
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
