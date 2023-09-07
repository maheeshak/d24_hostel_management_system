package controller;

import bo.BOFactory;
import bo.custom.UserBO;
import com.jfoenix.controls.JFXButton;
import dto.UserDTO;
import dto.tm.UserTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserWindowFormController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<UserTM> tblUsers;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colPasswordHInt;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPassword;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField txtPasswordHint;
    ObservableList<UserTM> obList = FXCollections.observableArrayList();
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colPasswordHInt.setCellValueFactory(new PropertyValueFactory<>("passwordHint"));

    }

    private void getAll() {
        try {
            List<UserDTO> allUsers = userBO.getAllUsers();
            List<UserTM> users = new ArrayList<>();


            for (UserDTO userDTO : allUsers) {

                UserTM userTM = new UserTM(userDTO.getId(), userDTO.getPassword(), userDTO.getPasswordHint());
                users.add(userTM);

            }

            obList.addAll(users);
            tblUsers.setItems(obList);
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/register_window_form.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
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

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            UserTM selectedItem = tblUsers.getSelectionModel().getSelectedItem();
            userBO.deleteUser(selectedItem.getId());
            new Alert(Alert.AlertType.WARNING, "User Deleted").showAndWait();
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
        tblUsers.getItems().clear();
        btnAdd.setDisable(false);
        refreshTable();
        clear();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            String user_id = txtId.getText();
            String password = txtPassword.getText();
            String passwordHint = txtPasswordHint.getText();
            UserDTO userDTO = new UserDTO(user_id, password, passwordHint);

            userBO.updateUser(userDTO);
            new Alert(Alert.AlertType.CONFIRMATION, "User Updated").showAndWait();
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
        tblUsers.getItems().clear();
        btnAdd.setDisable(false);
        refreshTable();
        clear();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

    }

    private void refreshTable() {
        obList.clear();
        getAll();
    }

    @FXML
    void tblUsersOnMouseClicked(MouseEvent event) {
        UserTM selectedItem = tblUsers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

            txtId.setText(selectedItem.getId());
            txtPassword.setText(selectedItem.getPassword());
            txtPasswordHint.setText(selectedItem.getPasswordHint());
        }

    }

    private void clear() {
        txtId.clear();
        txtPassword.clear();
        txtPasswordHint.clear();
    }

}
