package controller;

import bo.BOFactory;
import bo.custom.ReservationBO;
import bo.custom.RoomBO;
import bo.custom.StudentBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddReservationWindowFormController implements Initializable {
    @FXML
    private AnchorPane floatingPane;

    @FXML
    private ComboBox<String> cmbRoomId;

    @FXML
    private ComboBox<String> cmbStdId;

    @FXML
    private Label txtRoomAvailableQty;

    @FXML
    private DatePicker txtDate;

    @FXML
    private JFXButton btnPayNow;

    @FXML
    private JFXButton btnBook;

    @FXML
    private Label lblRoomPrice;

    @FXML
    private Label lblReservationId;

    ReservationBO<ReservationDTO> reservationBO = (ReservationBO<ReservationDTO>) BOFactory.getInstance().getBO(BOFactory.BOTypes.RESERVATION);
    StudentBO<StudentDTO> studentBO = (StudentBO<StudentDTO>) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    RoomBO<RoomDTO> roomBO = (RoomBO<RoomDTO>) BOFactory.getInstance().getBO(BOFactory.BOTypes.ROOM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setReservationId();
        setStudentId();
        setRoomID();
    }

    private void setRoomID() {
        try {
            List<RoomDTO> roomDtoList = roomBO.getAllRooms();
            ObservableList<String> roomIdObservableList = FXCollections.observableArrayList();
            roomDtoList.forEach(roomDto -> roomIdObservableList.add(roomDto.getRoom_id()));
            cmbRoomId.setItems(roomIdObservableList);
        } catch (RuntimeException ignored) {
        }
    }


    private void setStudentId() {
        List<StudentDTO> allStudents = studentBO.getAllStudents();

        try {
            ObservableList<String> studentIdObservableList = FXCollections.observableArrayList();
            allStudents.forEach(studentDto -> studentIdObservableList.add(studentDto.getStudent_id()));
            cmbStdId.setItems(studentIdObservableList);
        } catch (RuntimeException ignored) {
        }


    }

    private void setReservationId() {
        String reservationID = reservationBO.generateNewReservationID();

        if (reservationID == null) {
            lblReservationId.setText("RI000001");
        } else {
            String[] split = reservationID.split("[R][I]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("RI%06d", lastDigits);
            lblReservationId.setText(newID);
        }


    }

    @FXML
    void btnBookOnAction(ActionEvent event) {
        saveReservation("un-paid");

    }


    @FXML
    void btnPayNowOnAction(ActionEvent event) {
        saveReservation("paid");
    }

    private void saveReservation(String status) {

        if (validateData()) {

            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setRes_id(lblReservationId.getText());
            reservationDTO.setDate(Date.valueOf(txtDate.getValue()));
            reservationDTO.setStatus(status);


            RoomDTO roomDTO = roomBO.searchRoom(cmbRoomId.getValue());
            roomDTO.setQty(roomDTO.getQty()-1);
            reservationDTO.setRoomDTO(roomDTO);

            StudentDTO studentDTO = studentBO.searchStudent(cmbStdId.getValue());

            reservationDTO.setStudentDTO(studentDTO);


            boolean isAdded = reservationBO.addReservation(reservationDTO);
            Stage stage = (Stage) floatingPane.getScene().getWindow();
            stage.setAlwaysOnTop(false);
            new Alert(Alert.AlertType.INFORMATION, "Added!").showAndWait();
            stage.setAlwaysOnTop(true);


        }
    }


    @FXML
    void cmbRoomIdOnAction(ActionEvent event) {
        try {
            String selectedItem = cmbRoomId.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                RoomDTO room = roomBO.searchRoom(selectedItem);
                lblRoomPrice.setText(String.valueOf(room.getKey_money()));
                txtRoomAvailableQty.setText(String.valueOf(room.getQty()));

                if (room.getQty() != 0) {
                    btnPayNow.setDisable(false);
                    btnBook.setDisable(false);
                } else {
                    btnPayNow.setDisable(true);
                    btnBook.setDisable(true);
                    throw new RuntimeException("Room not available at the moment!");
                }
            } else {
                lblRoomPrice.setText("0");
                txtRoomAvailableQty.setText("0");
            }
        } catch (RuntimeException exception) {
            Stage stage = (Stage) floatingPane.getScene().getWindow();
            stage.setAlwaysOnTop(false);
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
            stage.setAlwaysOnTop(false);
        }

    }

    @FXML
    void cmbStudentIdOnAction(ActionEvent event) {
        System.out.println(cmbStdId.getValue());
    }

    private boolean validateData() throws RuntimeException {
        if (cmbStdId.getSelectionModel().getSelectedItem() != null) {
            if (cmbRoomId.getSelectionModel().getSelectedItem() != null) {
                if (txtDate.getValue() != null) {
                    if (!txtDate.getValue().isBefore(LocalDate.now())) {
                        System.out.println("Validation Done");
                        return true;
                    }
                    throw new RuntimeException("Date must be after today");
                }
                throw new RuntimeException("Select Date");
            }
            throw new RuntimeException("Select Room");
        }
        throw new RuntimeException("Select Student");
    }
}
