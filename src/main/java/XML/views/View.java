package main.java.XML.views;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class View {
    @FXML
    public Button saveToXML;
    @FXML
    public Button confirmDepartmentCRUD;
    @FXML
    public TextField departmentNameInput;
    @FXML
    public RadioButton RbAddDepartment;
    @FXML
    public RadioButton RbDeleteDepartment;
    @FXML
    public Button openXML;
    @FXML
    public TextField productNameInput;
    @FXML
    public TextField productWeightInput;
    @FXML
    public TextField productPriceInput;
    @FXML
    public TextField productBarcodeInput;
    @FXML
    public Button confirmProductCRUD;
    @FXML
    public TextField departmentIDInput;
    @FXML
    public RadioButton RbUpdateDepartment;
    @FXML
    public RadioButton RbAddProduct;
    @FXML
    public RadioButton RbDeleteProduct;
    @FXML
    public RadioButton RbUpdateProduct;
    @FXML
    public Button listAllDepartments;
    @FXML
    public Button searchProductOrDepByID;
    @FXML
    public TextField barcodeOrIDInput;
    @FXML
    public RadioButton searchDepartmentByID;
    @FXML
    public RadioButton searchProductByID;
    @FXML
    public TextArea mainTextPane;
    @FXML
    public TextField productSearchDepartmentId;
    @FXML
    public Button viewAllXML;
    @FXML
    public CheckBox showProductsByIdSearch;
    @FXML
    public Button countAllDepartments;
    @FXML
    public Button countAllProducts;

    public void setToggleGroups() {
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

    public String getDepartmentNameInput() {
        return departmentNameInput.getText();
    }

    public String getProductNameInput() {
        return productNameInput.getText();
    }

    public String getProductWeightInput() {
        return productWeightInput.getText();
    }

    public String getProductPriceInput() {
        return productPriceInput.getText();
    }

    public String getProductBarcodeInput() {
        return productBarcodeInput.getText();
    }

    public String getDepartmentIDInput() {
        return departmentIDInput.getText();
    }

    public String getBarcodeOrIDInput() {
        return barcodeOrIDInput.getText();
    }

    public String getProductSearchDepartmentId() {
        return productSearchDepartmentId.getText();
    }

    public void setMainTextPaneText(String text) {
        mainTextPane.setText(text);
    }

    public boolean isSelectedRbAddDepartment() {
        return RbAddDepartment.isSelected();
    }

    public boolean isSelectedRbDeleteDepartment() {
        return RbDeleteDepartment.isSelected();
    }

    public boolean isSelectedRbUpdateDepartment() {
        return RbUpdateDepartment.isSelected();
    }

    public boolean isSelectedRbAddProduct() {
        return RbAddProduct.isSelected();
    }

    public boolean isSelectedRbDeleteProduct() {
        return RbDeleteProduct.isSelected();
    }

    public boolean isSelectedRbUpdateProduct() {
        return RbUpdateProduct.isSelected();
    }

    public boolean isSelectedSearchDepartmentByID() {
        return searchDepartmentByID.isSelected();
    }

    public boolean isSelectedSearchProductByID() {
        return searchProductByID.isSelected();
    }

    public boolean isSelectedShowProductsByIdSearch() {
        return showProductsByIdSearch.isSelected();
    }

    public void showErrorMessage(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

    public void showWarningMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void showInformationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }


    public void printStackTrace(String ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Chosen XML file is not valid.");
        alert.setContentText(ex);

        alert.showAndWait();
    }
}
