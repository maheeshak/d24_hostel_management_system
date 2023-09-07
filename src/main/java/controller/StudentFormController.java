package controller;

import bo.BOFactory;
import bo.custom.StudentBO;
import com.jfoenix.controls.JFXButton;
import dto.StudentDTO;
import dto.tm.StudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<StudentTM> tblStudents;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker cmbDob;

    @FXML
    private RadioButton rBtnMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rBtnFemale;

    private RegExFactory regExFactory;

    ObservableList<StudentTM> obList = FXCollections.observableArrayList();

    StudentBO<StudentDTO> studentBO = (StudentBO<StudentDTO>) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setStudentID();
        getAll();
        setCellValueFactory();
        regExFactory = RegExFactory.getInstance();
    }

    private void getAll() {


        List<StudentDTO> allStudents = studentBO.getAllStudents();

        if (!(allStudents.isEmpty())) {

            for (StudentDTO studentDTO : allStudents) {

                obList.add(new StudentTM(studentDTO.getStudent_id(),
                        studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(),
                        studentDTO.getGender(), studentDTO.getDob()));

                tblStudents.setItems(obList);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Empty Students :( !!!").show();
        }

    }

    private void setCellValueFactory() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

    }

    private void setStudentID() {

        String studentID = studentBO.generateNewStudentID();

        if (studentID == null) {
            txtId.setText("ST000001");
        } else {
            String[] split = studentID.split("[S][T]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("ST%06d", lastDigits);
            txtId.setText(newID);
        }

    }


    @FXML
    void btnAddOnAction(ActionEvent event) {

        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String gender = (rBtnMale.isSelected()) ? "Male" : "Female";
        Date dob = Date.valueOf(cmbDob.getValue());

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudent_id(id);
        studentDTO.setName(name);
        studentDTO.setAddress(address);
        studentDTO.setContact(contact);
        studentDTO.setGender(gender);
        studentDTO.setDob(dob);


        if(validity()) {

            if (checkRegex()) {


                boolean isAdded = studentBO.addStudent(studentDTO);

                if (isAdded) {
                    refreshTable();
                    clearAll();
                    new Alert(Alert.AlertType.CONFIRMATION, "Student Added Successful :)!!!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student not Added :( !!!").show();

                }
            } else {

                new Alert(Alert.AlertType.ERROR, "Invalid types !!!").show();
            }
        }else {


            new Alert(Alert.AlertType.ERROR, "Please fill all fields :( !!!").show();
        }

    }

    private boolean checkRegex() {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtName.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.CITY).matcher(txtAddress.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.MOBILE).matcher(txtContact.getText()).matches() && cmbDob.getValue() != null;
    }

    private boolean validity() {

        return !txtId.getText().isEmpty() &&
                !txtName.getText().isEmpty() &&
                !txtAddress.getText().isEmpty() &&
                !txtContact.getText().isEmpty() &&
                (rBtnMale.isSelected() || rBtnFemale.isSelected()) &&
                cmbDob.getValue() != null;


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        StudentTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                studentBO.deleteStudent(selectedItem.getStudent_id());
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted").show();
                refreshTable();
                clearAll();

            } else {
                new Alert(Alert.AlertType.ERROR, "Select Student first!").show();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        btnUpdate.setDisable(true);
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
    }

    private void refreshTable() {
        obList.clear();
        getAll();
        setStudentID();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String gender = (rBtnMale.isSelected()) ? "Male" : "Female";
        Date dob = Date.valueOf(cmbDob.getValue());

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudent_id(id);
        studentDTO.setName(name);
        studentDTO.setAddress(address);
        studentDTO.setContact(contact);
        studentDTO.setGender(gender);
        studentDTO.setDob(dob);

        if(validity()) {

            if(checkRegex()) {

                boolean isAdded = studentBO.updateStudent(studentDTO);

                if (isAdded) {

                    btnAdd.setDisable(false);
                    new Alert(Alert.AlertType.CONFIRMATION, "Student Update Successful :)!!!").show();
                    clearAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student not Updated :( !!!").show();

                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid types !!!").show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields :( !!!").show();
        }


    }

    @FXML
    void tblStudentOnMouseClicked(MouseEvent event) {

        StudentTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);
                txtId.setText(selectedItem.getStudent_id());
                txtName.setText(selectedItem.getName());
                txtAddress.setText(selectedItem.getAddress());
                if (selectedItem.getGender().equals("Male")) {
                    rBtnMale.setSelected(true);
                } else {
                    rBtnFemale.setSelected(true);
                }
                txtContact.setText(selectedItem.getContact());
                cmbDob.setValue(selectedItem.getDob().toLocalDate());
            } else {
                btnUpdate.setDisable(true);
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
    }

    private void clearAll() {
        txtContact.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.clear();
    }
}
