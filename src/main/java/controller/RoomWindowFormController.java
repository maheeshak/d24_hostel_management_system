package controller;

import bo.BOFactory;
import bo.custom.RoomBO;
import com.jfoenix.controls.JFXButton;
import dto.RoomDTO;
import dto.StudentDTO;
import dto.tm.RoomTM;
import dto.tm.StudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.regex.RegExFactory;
import util.regex.RegExType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private TableView<RoomTM> tblRooms;

    @FXML
    private TableColumn<?, ?> colRoomTypeId;

    @FXML
    private TableColumn<?, ?> colRoomType;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TextField txtKeyMoney;

    @FXML
    private TextField txtQty;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField txtSearch;

    ObservableList<RoomTM> obList = FXCollections.observableArrayList();


    RoomBO<RoomDTO> roomBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ROOM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setRoomID();
        setRoomTypes();
        getAll();
        setCellValueFactory();

    }

    private void setRoomTypes() {
        String[] gender = {null, "Non-AC", "Non-AC / Food ", "AC", "AC / Food"};
        cmbType.getItems().addAll(gender);
    }

    private void setRoomID() {
        String roomID = roomBO.generateNewRoomID();

        if (roomID == null) {
            txtId.setText("RM000001");
        } else {
            String[] split = roomID.split("[R][M]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("RM%06d", lastDigits);
            txtId.setText(newID);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        String room_id = txtId.getText();
        String room_type = cmbType.getSelectionModel().getSelectedItem();
        String key_money = txtKeyMoney.getText();
        String qty = txtQty.getText();

       if( validity()) {
           if(checkRegex()) {

           RoomDTO roomDTO = new RoomDTO();
           roomDTO.setRoom_id(room_id);
           roomDTO.setType(room_type);
           roomDTO.setKey_money(Double.valueOf(key_money));
           roomDTO.setQty(Integer.valueOf(qty));


               boolean isAdded = roomBO.addRoom(roomDTO);

               if (isAdded) {

                   refreshTable();
                   new Alert(Alert.AlertType.CONFIRMATION, "Room  Added successful :) !!!").show();
               } else {
                   new Alert(Alert.AlertType.ERROR, "Room  Added Unsuccessful :( !!!").show();

               }
           }else {
               new Alert(Alert.AlertType.ERROR, "Invalid types !!!").show();
           }
       }else{
           new Alert(Alert.AlertType.ERROR, "Please fill all fields :( !!!").show();
       }


    }

    private boolean checkRegex() {
        return RegExFactory.getInstance().getPattern(RegExType.DOUBLE).matcher(txtKeyMoney.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(cmbType.getSelectionModel().getSelectedItem()).matches() && RegExFactory.getInstance().getPattern(RegExType.INTEGER).matcher(txtQty.getText()).matches();

    }

    private boolean validity() {
        return !txtId.getText().isEmpty() &&
                cmbType.getSelectionModel().getSelectedItem() != null &&
                !txtKeyMoney.getText().isEmpty() &&
                !txtQty.getText().isEmpty();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        RoomTM selectedItem = tblRooms.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                roomBO.deleteRoom(selectedItem.getRoom_id());
                new Alert(Alert.AlertType.INFORMATION, "Romm Deleted").show();
                refreshTable();
                clearAll();
                setRoomID();

            } else {
                new Alert(Alert.AlertType.ERROR, "Select Room first!").show();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        btnUpdate.setDisable(true);
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);


    }

    private void clearAll() {
        txtId.clear();
        cmbType.setValue(null);
        txtKeyMoney.clear();
        txtQty.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String room_id = txtId.getText();
        String room_type = cmbType.getSelectionModel().getSelectedItem();
        String key_money = txtKeyMoney.getText();
        String qty = txtQty.getText();

        if( validity()) {
            if(checkRegex()) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoom_id(room_id);
        roomDTO.setType(room_type);
        roomDTO.setKey_money(Double.valueOf(key_money));
        roomDTO.setQty(Integer.valueOf(qty));

        boolean isUpdated = roomBO.updateRoom(roomDTO);

        if (isUpdated) {

            refreshTable();
            btnAdd.setDisable(false);
            new Alert(Alert.AlertType.CONFIRMATION, "Room  Updated successful :) !!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Room  Updated Unsuccessful :( !!!").show();

        }
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid types !!!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please fill all fields :( !!!").show();
        }
    }

    @FXML
    void tblRoomsOnMouseClicked(MouseEvent event) {
        RoomTM selectedItem = tblRooms.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            txtId.setText(selectedItem.getRoom_id());
            cmbType.setValue(selectedItem.getType());
            txtQty.setText(String.valueOf(selectedItem.getQty()));
            txtKeyMoney.setText(String.valueOf(selectedItem.getKey_money()));
        } else {
            btnAdd.setDisable(false);
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnKeyReleased(KeyEvent event) {

    }

    private void getAll() {


        List<RoomDTO> allRooms = roomBO.getAllRooms();

        if (!(allRooms.isEmpty())) {

            for (RoomDTO roomDTO : allRooms) {

                obList.add(new RoomTM(roomDTO.getRoom_id(),
                        roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));

                tblRooms.setItems(obList);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Empty Rooms :( !!!").show();
        }

    }

    private void setCellValueFactory() {
        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));


    }

    private void refreshTable() {
        obList.clear();
        getAll();
        setRoomID();
    }

}
