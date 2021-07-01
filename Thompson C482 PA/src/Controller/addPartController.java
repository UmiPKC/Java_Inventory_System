package Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class addPartController implements Initializable {

    public VBox mainVbox;
    public HBox radioHbox;
    public Label titleLabel;
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
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
    public HBox machineCompanyHbox;
    public Label machineCompanyLabel;
    public TextField machineCompanyField;
    public HBox buttonsHbox;
    public Button saveButton;
    public Button cancelButton;
    public VBox exceptionVbox;
    public Label nameExcepLabel;
    public Label invExcepLabel;
    public Label priceExcepLabel;
    public Label maxExcepLabel;
    public Label minExcepLabel;
    public Label machineCompanyExcepLabel;

    private String inOut = "In-House";

    /**
     * For the In-House radio button; Changes final text field and sets a private variable inOut.
     * @param actionEvent
     */
    public void onInHouse(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Machine ID");
        inOut = "In-House";
    }

    /**
     * For Outsourced radio button; Changes final text field and sets a private variable inOut.
     * @param actionEvent
     */
    public void onOutsourced(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Company Name");
        inOut = "Outsourced";
    }

    /**
     * Save button; Creates an InHousePart or OutsourcedPart depending on the radio button states.
     * Try-catch blocks will stop user from saving a Part if there are errors.
     * @param actionEvent
     * @throws IOException
     */
    public void onSave(ActionEvent actionEvent) throws IOException {

        //Setting part parameters based on user's input in text fields
        int id = 0;
        String name = null;
        int inv = 0;
        double price = 0;
        int max = 0;
        int min = 0;

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
            //return;
        }

        if (min > max) {
            minExcepLabel.setText("Exception: Min value must be less than the Max value");
            min = 0;
        }

        if (!(inv >= min && inv <= max)) {
            invExcepLabel.setText("Exception: Inv value should be between the Min and Max values");
            inv = 0;
        }

        //Determining whether to create an in-house object or outsourced object
        //challenge: couldnt get part-modify to tell if part is inhouse/outhouse when declaring newPart specifically as a
        //InHousePart or OutsourcePart, but it works when declaring newPart as a Part
        //NOT THE REAL ISSUE ^; real issue was that the inOut String variable would never be correctly set if the user
        //never explicitly pushed the In-House radio button, despite it being toggled by default/

        if (inOut == "In-House") {
            int machineId = 0;
            try {
                machineId = Integer.parseInt(machineCompanyField.getText());
                machineCompanyExcepLabel.setText("");
            }
            catch (NumberFormatException e) {
                machineCompanyExcepLabel.setText("Exception: Machine ID must be an integer");
            }

            if (name == null || price == 0 || inv == 0 || min == 0 || max == 0 || machineId == 0) {
                return;
            }

            InhousePart newPart = new InhousePart(id, name, price, inv, min, max, machineId);
            Inventory.addPart(newPart);
            int newId = Inventory.getAllParts().indexOf(newPart); //for auto-incrementing ID
            newPart.setId(newId + 1);
        }

        else if (inOut == "Outsourced") {
            String companyName = machineCompanyField.getText();
            if (companyName.trim().isEmpty()) {
                companyName = null;
                machineCompanyExcepLabel.setText("Exception: Company Name field cannot be blank");
            }
            else {
                machineCompanyExcepLabel.setText("");
            }

            if (name == null || price == 0 || inv == 0 || min == 0 || max == 0 || companyName == null) {
                return;
            }

            OutsourcedPart newPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
            Inventory.addPart(newPart);
            int newId = Inventory.getAllParts().indexOf(newPart); //for auto-incrementing ID
            newPart.setId(newId + 1);
        }

        Parent main_parent = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
        Scene main_scene = new Scene(main_parent);
        Stage app_stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(main_scene);
        app_stage.show();

    }

    /**
     * Brings user back to mainScreen without saving work.
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

    /**
     * Initialize screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
