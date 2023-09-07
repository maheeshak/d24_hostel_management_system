package controller;

import bo.BOFactory;
import bo.custom.UserBO;
import com.jfoenix.controls.JFXButton;
import dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginWindowFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtShowPassword;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private RadioButton btnRadio;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private JFXButton btnRegister;

    private boolean passClicked;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void btnRadioOnAction(ActionEvent event) {


        if (!passClicked) {
            passClicked = true;
            String passwordText = txtPassword.getText();
            txtShowPassword.setText(passwordText);
            txtPassword.setVisible(false);
            txtShowPassword.setVisible(true);
        } else {
            passClicked = false;
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);
        }

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/register_window_form.fxml"));
            root.getChildren().clear();
            root.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnLogin.fire();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();

    }

    public void btnLoginOnAction(ActionEvent actionEvent) {

        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (validity()) {
            UserDTO userDTO = new UserDTO(userName, password, null);

            boolean isValid = userBO.validUser(userDTO);

            if (isValid) {

                try {
                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_window_form.fxml"));
                    Scene scene = new Scene(anchorPane);

                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {

                new Alert(Alert.AlertType.ERROR, "Invalid user name or password :( !!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill all field :) !!!").show();
        }
    }

    private boolean validity() {

        return !txtUserName.getText().isEmpty() &&
                !txtPassword.getText().isEmpty();
    }
}
