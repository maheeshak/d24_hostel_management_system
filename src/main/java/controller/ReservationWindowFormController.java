package controller;

import bo.BOFactory;
import bo.custom.ReservationBO;
import bo.custom.RoomBO;
import com.jfoenix.controls.JFXButton;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import dto.tm.ReservationTM;
import dto.tm.RoomTM;
import dto.tm.StudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ReservationWindowFormController implements Initializable {

    @FXML
    public AnchorPane root;
    @FXML
    private TableView<ReservationTM> tblReservations;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colRoomType;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<RoomTM> tblRoom;

    @FXML
    private TableColumn<?, ?> colRoomID;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private JFXButton btnViewUnpaidStudent;

    @FXML
    private JFXButton btnAddReservation;

    @FXML
    private JFXButton btnMarkAsPaid;

    @FXML
    private JFXButton btnDelete;

    RoomBO<RoomDTO> roomBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ROOM);


    ObservableList<RoomTM> obListRoom = FXCollections.observableArrayList();
    ObservableList<ReservationTM> obList = FXCollections.observableArrayList();

    ReservationBO<ReservationDTO> reservationBO = (ReservationBO<ReservationDTO>) BOFactory.getInstance().getBO(BOFactory.BOTypes.RESERVATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnMarkAsPaid.setDisable(true);
        btnDelete.setDisable(true);
        getReservationAll();
        setReservationCellValueFactory();

        getAllRooms();
        setRoomCellValueFactory();


    }

    private void setRoomCellValueFactory() {

        colRoomID.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

    }

    private void getAllRooms() {
        List<RoomDTO> allRooms = roomBO.getAllRooms();

        if (!(allRooms.isEmpty())) {

            for (RoomDTO roomDTO : allRooms) {

                obListRoom.add(new RoomTM(roomDTO.getRoom_id(),
                        roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));

                tblRoom.setItems(obListRoom);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Empty Rooms :( !!!").show();
        }

    }

    private void getReservationAll() {

        List<ReservationDTO> allReservations = reservationBO.getAllReservations();

        if (!(allReservations.isEmpty())) {

            for (ReservationDTO reservationDTO : allReservations) {

                obList.add(new ReservationTM(
                        reservationDTO.getRes_id(),
                        reservationDTO.getStudentDTO().getStudent_id(),
                        String.valueOf(reservationDTO.getDate()),
                        reservationDTO.getRoomDTO().getType(),
                        reservationDTO.getStatus()
                ));

                tblReservations.setItems(obList);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Empty Reservations:( !!!").show();
        }


    }

    private void setReservationCellValueFactory() {

        colReservationId.setCellValueFactory(new PropertyValueFactory<>("res_id"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("room_type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    @FXML
    void btnAddReservationOnAction(ActionEvent event) {

        try {
            Stage stage = new Stage();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/add_reservation_window_form.fxml"));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent e) {
                    refreshTable();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {

        obList.clear();
        getReservationAll();

        obListRoom.clear();
        getAllRooms();


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            ReservationTM reservationTm = tblReservations.getSelectionModel().getSelectedItem();
            if (reservationTm != null) {
                btnDelete.setDisable(false);
                reservationBO.deleteReservation(reservationTm.getRes_id());
                new Alert(Alert.AlertType.CONFIRMATION, "Reservation Deleted : " + reservationTm.getRes_id()).show();
                refreshTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Item First").show();
            }
            btnDelete.setDisable(true);
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }

    }

    @FXML
    void btnMarkAsPaidOnAction(ActionEvent event) {
        try {
            ReservationTM selectedItem = tblReservations.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                ReservationDTO dto = reservationBO.searchReservation(selectedItem.getRes_id());
                dto.setStatus("paid");
                reservationBO.updateReservation(dto);
                new Alert(Alert.AlertType.INFORMATION, "Payment updated").show();
                btnMarkAsPaid.setDisable(true);
                refreshTable();
            }
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
    }


    @FXML
    void btnViewUnpaidStudentOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/unpaid_window_form.fxml"));
            root.getChildren().clear();
            root.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblReservationsOnMouseClicked(MouseEvent event) {

        btnDelete.setDisable(false);
        btnMarkAsPaid.setDisable(false);

    }


}
