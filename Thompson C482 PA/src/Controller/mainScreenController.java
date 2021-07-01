package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {
        
    public Label mainTitle;
    public HBox tableHbox;
    public VBox partsVbox;
    public HBox PartsTitleSearchHbox;
    public Label partsTitle;
    public TextField partsSearch;
    public TableView partsTable;
    public TableColumn partsIdCol;
    public TableColumn partsNameCol;
    public TableColumn partsInvCol;
    public TableColumn partsPriceCol;
    public HBox partsButtonHbox;
    public Button partsAddButton;
    public Button partsModifyButton;
    public Button partsDeleteButton;
    public VBox prodVbox;
    public HBox prodTitleSearchHbox;
    public Label prodTitle;
    public TextField prodSearch;
    public TableView prodTable;
    public TableColumn prodIdCol;
    public TableColumn prodNameCol;
    public TableColumn prodInvCol;
    public TableColumn prodPriceCol;
    public HBox prodButtonHbox;
    public Button prodAddButton;
    public Button prodModifyButton;
    public Button prodDeleteButton;
    public Button exitButton;
    public Button partSearchBtn;
    public Button prodSearchBtn;

    //Private data for passing Part/Product to Modify screens
    private static Part selectedPart = null;
    private static Product selectedProduct = null;

    public static Part getSelectedPart() {
        return selectedPart;
    }
    public static Product getSelectedProduct() {
        return selectedProduct;
    }


    /**
     * initialize will set up the Parts and Products tables on startup.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set up Parts table
        partsTable.setItems(Inventory.getAllParts());
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set up Products table
        prodTable.setItems(Inventory.getAllProducts());
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Parts search button action event. Pressing the button without entering any text will show all parts.
     * Pressing button after entering text will use Inventory.lookupPart() to search.
     * @param actionEvent
     */
    public void onPartSearch(ActionEvent actionEvent) {
        String userSearch = partsSearch.getText();
        partsTable.setItems(Inventory.getAllParts());

        if (partsSearch.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }
        else {
            try {
                int id = Integer.parseInt(userSearch);
                if (Inventory.lookupPart(id) == null) {
                    Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
                    noSelectionAlert.setTitle("No parts found");
                    noSelectionAlert.setContentText("No parts found");
                    Optional<ButtonType> result = noSelectionAlert.showAndWait();
                }
                else {
                    partsTable.getSelectionModel().select(Inventory.lookupPart(id));
                }
            }
            catch (NumberFormatException e) {
                if (Inventory.lookupPart(userSearch).isEmpty()) {
                    Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
                    noSelectionAlert.setTitle("No parts found");
                    noSelectionAlert.setContentText("No parts found");
                    Optional<ButtonType> result = noSelectionAlert.showAndWait();
                }
                else {
                    partsTable.setItems(Inventory.lookupPart(userSearch));
                }
            }
        }
    }

    /**
     * Add button takes user to Add Part screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onPartsAdd(ActionEvent actionEvent) throws IOException {

        Parent add_part_parent = FXMLLoader.load(getClass().getResource("/View_Controller/addPart.fxml"));
        Scene add_part_scene = new Scene(add_part_parent);
        Stage app_stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(add_part_scene);
        app_stage.show();
    }

    /**
     * Modify button takes user to Modify Part screen.
     * The selected part will be stored in the selectedPart static variable, so that the data can be moved to the new screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onPartsModify(ActionEvent actionEvent) throws IOException {

        selectedPart = (Part)partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("No part selected");
            noSelectionAlert.setContentText("No part selected.");
            Optional<ButtonType> result = noSelectionAlert.showAndWait();
            return;
        }

        Parent modify_part_parent = FXMLLoader.load(getClass().getResource("/View_Controller/modifyPart.fxml"));
        Scene modify_part_scene = new Scene(modify_part_parent);
        Stage app_stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(modify_part_scene);
        app_stage.show();
    }

    /**
     * Delete button will delete the selected part after user confirmation.
     * @param actionEvent
     * @throws IOException
     */
    public void onPartsDelete(ActionEvent actionEvent) throws IOException {

        selectedPart = (Part)partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected");
            alert.setContentText("No part selected.");
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (Inventory.deletePart(selectedPart)) {
                return;
            }
        }

        else {
            return;
        }
    }

    /**
     * Product search button action event. Pressing the button without entering any text will show all products.
     * Pressing button after entering text will use Inventory.lookupProduct() to search.
     * @param actionEvent
     */
    public void onProdSearch(ActionEvent actionEvent) {

        String userSearch = prodSearch.getText();
        prodTable.setItems(Inventory.getAllProducts());

        if (prodSearch.getText().isEmpty()) {
            prodTable.setItems(Inventory.getAllProducts());
        }
        else {
            try {
                int id = Integer.parseInt(userSearch);
                if (Inventory.lookupProduct(id) == null) {
                    Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
                    noSelectionAlert.setTitle("No products found");
                    noSelectionAlert.setContentText("No products found");
                    Optional<ButtonType> result = noSelectionAlert.showAndWait();
                }
                else {
                    prodTable.getSelectionModel().select(Inventory.lookupProduct(id));
                }
            } catch (NumberFormatException e) {
                if (Inventory.lookupProduct(userSearch).isEmpty()) {
                    Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
                    noSelectionAlert.setTitle("No products found");
                    noSelectionAlert.setContentText("No products found");
                    Optional<ButtonType> result = noSelectionAlert.showAndWait();
                }
                else {
                    prodTable.setItems(Inventory.lookupProduct(userSearch));
                }
            }
        }
    }

    /**
     * Add button takes user to Add Product screen
     * @param actionEvent
     * @throws IOException
     */
    public void onProdAdd(ActionEvent actionEvent) throws IOException {

        Parent add_product_parent = FXMLLoader.load(getClass().getResource("/View_Controller/addProduct.fxml"));
        Scene add_product_scene = new Scene(add_product_parent);
        Stage app_stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(add_product_scene);
        app_stage.show();
    }

    /**
     * Modify button takes user to Modify Product screen.
     * The selected product will be stored in the selectedProduct static variable, so that the data can be moved to the new screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onProdModify(ActionEvent actionEvent) throws IOException {

        selectedProduct = (Product)prodTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("No product selected");
            noSelectionAlert.setContentText("No product selected.");
            Optional<ButtonType> result = noSelectionAlert.showAndWait();
            return;
        }

        Parent modify_product_parent = FXMLLoader.load(getClass().getResource("/View_Controller/modifyProduct.fxml"));
        Scene modify_product_scene = new Scene(modify_product_parent);
        Stage app_stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(modify_product_scene);
        app_stage.show();
    }

    /**
     * Delete button will delete the selected part after user confirmation.
     * @param actionEvent
     * @throws IOException
     */
    public void onProdDelete(ActionEvent actionEvent) throws IOException {

        selectedProduct = (Product)prodTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("No product selected");
            noSelectionAlert.setContentText("No product selected.");
            Optional<ButtonType> result = noSelectionAlert.showAndWait();
            return;
        }

        Alert delConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        delConfirm.setTitle("Delete Product");
        delConfirm.setContentText("Are you sure you want to delete this product?");
        Optional<ButtonType> result = delConfirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (Inventory.deleteProduct(selectedProduct)) {
                Alert partsCheck = new Alert(Alert.AlertType.ERROR);
                partsCheck.setTitle("Product has associated parts");
                partsCheck.setContentText("Product still contains associated parts");
                Optional<ButtonType> partsCheckResult = partsCheck.showAndWait();
            }
            else {
                return;
            }
        }
        else {
            return;
        }
    }

    /**
     * Exit button will exit application after user confirms.
     * @param actionEvent
     */
    public void onExit(ActionEvent actionEvent) {

        Alert exitConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        exitConfirm.setTitle("Exit program");
        exitConfirm.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = exitConfirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
        else {
            return;
        }

    }

}
