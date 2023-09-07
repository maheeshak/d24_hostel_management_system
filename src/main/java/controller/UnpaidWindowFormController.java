package controller;

import bo.BOFactory;
import bo.custom.StudentBO;
import dto.StudentDTO;
import dto.tm.StudentTM;
import dto.tm.UnpaidStudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UnpaidWindowFormController implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    private TableView<UnpaidStudentTM> tblStudents;

    @FXML
    private TableColumn<?, ?> colStdId;

    @FXML
    private TableColumn<?, ?> colStdName;

    @FXML
    private TableColumn<?, ?> colStdAddress;

    @FXML
    private TableColumn<?, ?> colMobile;

    StudentBO<StudentDTO> studentBO = (StudentBO<StudentDTO>) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    ObservableList<UnpaidStudentTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getAll();
        setCellValueFactory();
    }

    private void getAll() {
        List<StudentDTO> allUnpaidStudents = studentBO.getUnpaidStudents();

        if (!(allUnpaidStudents.isEmpty())) {

            for (StudentDTO studentDTO : allUnpaidStudents) {
                obList.add(new UnpaidStudentTM(studentDTO.getStudent_id(),
                        studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact()));

                tblStudents.setItems(obList);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Empty Students :( !!!").show();
        }
    }

    private void setCellValueFactory() {
        colStdId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colStdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStdAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/reservation_window_form.fxml"));
            root.getChildren().clear();
            root.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblStudentsOnMouseClicked(MouseEvent event) {

    }


}
