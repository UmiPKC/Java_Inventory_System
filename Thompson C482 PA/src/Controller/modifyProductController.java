package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyProductController implements Initializable {

    public HBox mainHbox;
    public VBox addVbox;
    public Label titleLabel;
    public VBox fieldsVbox;
    public HBox idHbox;
    public Label idLabel;
    public TextField idField;
    public HBox nameHbox;
    public Label nameLabel;
    public TextField nameField;
    public HBox invHbox;
    public Label invLabel;
    public TextField invField;
    public HBox priceHbox;
    public Label priceLabel;
    public TextField priceField;
    public HBox maxMinHbox;
    public Label maxLabel;
    public TextField maxField;
    public Label minLabel;
    public TextField minField;
    public VBox tableVbox;
    public TextField searchField;
    public TableView allTable;
    public TableColumn allIdCol;
    public TableColumn allNameCol;
    public TableColumn allInvCol;
    public TableColumn allPriceCol;
    public Button addButton;
    public TableView assocTable;
    public TableColumn assocIdCol;
    public TableColumn assocNameCol;
    public TableColumn assocInvCol;
    public TableColumn assocPriceCol;
    public Button removeAssocButton;
    public HBox saveCancelHbox;
    public Button saveButton;
    public Button cancelButton;
    public VBox exceptionVbox;
    public Label nameExcepLabel;
    public Label invExcepLabel;
    public Label priceExcepLabel;
    public Label maxExcepLabel;
    public Label minExcepLabel;
    public Button partSearchBtn;

    private Product selectedProduct = null;
    private ObservableList<Part> assocPartsList = FXCollections.observableArrayList();
    private ObservableList<Part> removePartsList = FXCollections.observableArrayList();

    /**
     * Initializes screen and sets up AllParts table and AssocParts table.
     * AssocTable is setup using the premade assocPartsList private ObservableList.
     * User's selected product is retrieved using mainScreenController's selectedProduct static variable.
     * <p><b>LOGICAL ERROR</b></p>
     * I came across a bug where every Product object shared the same associated parts list. As a result, using the Add and Remove parts buttons on the Modify Product
     * screen would affect every single Product's associated parts list. The problem was that I had accidentally declared the Product class' associatedParts observableList
     * as a private static object, rather than just as a private object. After fixing it, every Product now had its own associated parts list as intended.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //get selected product
        selectedProduct = mainScreenController.getSelectedProduct();

        //set fields
        idField.setText(String.format("%d",selectedProduct.getId()));
        nameField.setText(selectedProduct.getName());
        invField.setText(String.format("%d",selectedProduct.getStock()));
        priceField.setText(String.format("%.2f",selectedProduct.getPrice()));
        maxField.setText(String.format("%d",selectedProduct.getMax()));
        minField.setText(String.format("%d",selectedProduct.getMin()));

        //set assocPartsList
        assocPartsList.clear();
        assocPartsList.addAll(selectedProduct.getAllAssociatedParts());

        //setting Product.getAllAssociatedParts() and Product's associatedParts to static made it so every instance
        //of a Product shared the same associated parts list

        //Set up All Parts table (top table)
        allTable.setItems(Inventory.getAllParts());
        allIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set up Associated Parts table (bottom table)
        assocTable.setItems(assocPartsList);
        assocIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Parts search button action event. Pressing the button without entering any text will show all parts.
     * Pressing button after entering text will use Inventory.lookupPart() to search.
     * @param actionEvent
     */
    public void onPartSearch(ActionEvent actionEvent) {
        String userSearch = searchField.getText();
        allTable.setItems(Inventory.getAllParts());

        if (searchField.getText().isEmpty()) {
            allTable.setItems(Inventory.getAllParts());
        } else {
            try {
                int id = Integer.parseInt(userSearch);
                if (Inventory.lookupPart(id) == null) {
                    Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
                    noSelectionAlert.setTitle("No parts found");
                    noSelectionAlert.setContentText("No parts found");
                    Optional<ButtonType> result = noSelectionAlert.showAndWait();
                } else {
                    allTable.getSelectionModel().select(Inventory.lookupPart(id));
                }
            } catch (NumberFormatException e) {
                if (Inventory.lookupPart(userSearch).isEmpty()) {
                    Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
                    noSelectionAlert.setTitle("No parts found");
                    noSelectionAlert.setContentText("No parts found");
                    Optional<ButtonType> result = noSelectionAlert.showAndWait();
                } else {
                    allTable.setItems(Inventory.lookupPart(userSearch));
                }
            }
        }
    }

    /**
     * The part selected in the All Parts table (allParts observable list) is copied to the Assoc Parts table (assocPartsList).
     * @param actionEvent
     */
    public void onAdd(ActionEvent actionEvent) {
        //System.out.println("Add!");
        Part selectedPart = (Part)allTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            return;
        }

        assocPartsList.add(selectedPart);
    }

    /**
     * Removes the selected part from the Assoc Parts table (assocPartsList) after user confirmation.
     * @param actionEvent
     */
    public void onRemove(ActionEvent actionEvent) {
        //System.out.println("Remove!");
        Part selectedPart = (Part)assocTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Part");
        alert.setContentText("Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            assocPartsList.remove(selectedPart);
            removePartsList.add(selectedPart);
        }

        else {
            return;
        }
    }

    /**
     * Saves the product information to the main screen's Product table when user clicks Save.
     * A new Product object is made and replaces the original Product object.
     * Also brings user back to main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onSave(ActionEvent actionEvent) throws IOException {

        int id = selectedProduct.getId();
        int index = id - 1;
        String name = null;
        int inv = 0;
        double price = 0;
        int min = 0;
        int max = 0;

        name = nameField.getText();
        if (name.trim().isEmpty()) {
            name = null;
            nameExcepLabel.setText("Exception: Name field must be filled");
        }
        else {
            nameExcepLabel.setText("");
        }

        try {
            inv = Integer.parseInt(invField.getText());
            invExcepLabel.setText("");
        }
        catch (NumberFormatException e) {
            //System.out.println("Exception: Inv is not an integer");
            invExcepLabel.setText("Exception: Inv is not an integer");
        }

        try {
            price = Double.parseDouble(priceField.getText());
            priceExcepLabel.setText("");
        }
        catch (NumberFormatException e) {
            //System.out.println("Exception: Price is not a double");
            priceExcepLabel.setText("Exception: Price is not a double");
        }

        try {
            max = Integer.parseInt(maxField.getText());
            maxExcepLabel.setText("");
        }
        catch (NumberFormatException e) {
            //System.out.println("Exception: Max is not an integer");
            maxExcepLabel.setText("Exception: Max is not an integer");
        }

        try {
            min = Integer.parseInt(minField.getText());
            minExcepLabel.setText("");
        }
        catch (NumberFormatException e) {
            //System.out.println("Exception: Min is not an integer");
            minExcepLabel.setText("Exception: Min is not an integer");
        }

        if (min > max) {
            minExcepLabel.setText("Exception: Min value must be less than the Max value");
            min = 0;
        }

        if (!(inv >= min && inv <= max)) {
            invExcepLabel.setText("Exception: Inv value should be between the Min and Max values");
            inv = 0;
        }

        if (name == null || price == 0 || inv == 0 || min == 0 || max == 0) {
            return;
        }

        Product updateProduct = new Product(id, name, price, inv, min, max);
        updateProduct.getAllAssociatedParts().clear();
        updateProduct.getAllAssociatedParts().addAll(assocPartsList);

        for(Part p:removePartsList) {
            updateProduct.deleteAssociatedPart(p);
        }

        Inventory.updateProduct(index, updateProduct);
        
        Parent main_parent = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
        Scene main_scene = new Scene(main_parent);
        Stage app_stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(main_scene);
        app_stage.show();
    }

    /**
     * Doesn't save product changes, goes back to main screen after user confirmation.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to discard your work?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent main_parent = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
            Scene main_scene = new Scene(main_parent);
            Stage app_stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(main_scene);
            app_stage.show();
        }

        else {
            return;
        }
    }
}
