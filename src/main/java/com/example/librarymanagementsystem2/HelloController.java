package com.example.librarymanagementsystem2;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloController {
    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_conirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private AnchorPane si_loginForm1;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private TextField su_username;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection connect;
    private PreparedStatement pst;
    private ResultSet rs;

    private Alert alert;

    private double x = 0;
    private double y = 0;

    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void LoginBtn() throws SQLException {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } else {
            connect = database.connectDB();
            connect.setAutoCommit(false);
            try {
                // truy van xem rs co ton tai khong
                String selctData = "SELECT username, password FROM admin WHERE username = ? AND password = ?";
                pst = connect.prepareStatement(selctData);
                pst.setString(1,si_username.getText());
                pst.setString(2,si_password.getText());
                rs = pst.executeQuery();
                if(rs.next()) {
//                    alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Information Massage");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Successfully Login!");
//                    alert.showAndWait();
                    // set username
                    getData.username = si_username.getText();
                    // ẩn đi loginForm
                    si_loginBtn.getScene().getWindow().hide();
                    // mở ra của sổ DASHBOARD
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT); // stage trong suốt

                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }

                connect.commit();
            } catch (Exception e) {
                connect.rollback();
                e.printStackTrace();
            }
        }
    }

    public void regBtn() throws SQLException { // không dùng tham số event thì không cần ActionEvent
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
        || su_question.getSelectionModel().getSelectedItem() == null
        || su_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO admin (username, password, question, answer, date)" +
                    " VALUES(?,?,?,?,?)";
            connect = database.connectDB();
            connect.setAutoCommit(false);
            try {
                // KIEM TRA XEM USERNAME TON TAI CHUA
                String checkUsername = "SELECT username FROM admin WHERE username = " +
                        "'" + su_username.getText() + "'";
                pst = connect.prepareStatement(checkUsername);
                rs = pst.executeQuery();
                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    pst = connect.prepareStatement(regData);
                    pst.setString(1, su_username.getText());
                    pst.setString(2, su_password.getText());
                    pst.setString(3, su_question.getSelectionModel().getSelectedItem().toString());
                    pst.setString(4, su_answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    pst.setDate(5, sqlDate);
                    pst.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered");
                    alert.showAndWait();

                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(0.5));

                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadyHave.setVisible(false);
                        side_CreateBtn.setVisible(true);
                    });
                    slider.play();
                }

                // đúng hết thì commit
                connect.commit();
            } catch (Exception e) {
                connect.rollback();
                e.printStackTrace();
            }
        }
    }

    private String[] questionList = {"What is your favorite Color?", "What is your favorite food?", "What is your birth date?"};
    public void regLquestionList() {
        List<String> listQ = new ArrayList<String>();
        for (String data: questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }

    public void switchForgotPass() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);
        forgotPassQuestionList();
    }

    public void proceedBtn() throws SQLException {

        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
        || fp_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            connect = database.connectDB();
            connect.setAutoCommit(false);
            try {
                String selectData = "SELECT username, question, answer FROM admin WHERE username = ? AND question = ? AND answer = ?";
                pst = connect.prepareStatement(selectData);
                pst.setString(1, fp_username.getText());
                pst.setString(2, fp_question.getSelectionModel().getSelectedItem().toString());
                pst.setString(3, fp_answer.getText());

                rs = pst.executeQuery();

                if(rs.next()) {
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText("null");
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }

                connect.commit();
            } catch (Exception e) {
                connect.rollback();
                e.printStackTrace();
            }
        }

    }

    public void changePassBtn() throws SQLException {

        if (np_newPassword.getText().isEmpty() || np_conirmPassword.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("null");
            alert.setContentText("Incorrect Information");
            alert.showAndWait();
        } else if (!np_newPassword.getText().equals(np_conirmPassword.getText())){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("null");
            alert.setContentText("Not match");
            alert.showAndWait();
        } else {
            connect = database.connectDB();
            connect.setAutoCommit(false);
            try {
//                String updatePass = "UPDATE admin SET password = '" + np_newPassword.getText()
//                        + "' WHERE username = '" + fp_username.getText() + "'";
                String updatePass = "UPDATE admin SET password = ? WHERE username = ?";
                pst = connect.prepareStatement(updatePass);
                pst.setString(1,np_newPassword.getText());
                pst.setString(2,fp_username.getText());
                pst.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText("null");
                alert.setContentText("Successfully changed Password!");
                alert.showAndWait();

                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

                // thay đổi mật khẩu xong thì làm trống
                np_newPassword.setText("");
                np_conirmPassword.setText("");
                fp_username.setText("");
                fp_question.getSelectionModel().clearSelection();
                fp_answer.setText("");

                connect.commit();
            } catch(Exception e) {
                connect.rollback();
                e.printStackTrace();
            }
        }

    }

    public void forgotPassQuestionList() {
        List<String> listQ = new ArrayList<>();

        for (String data: questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
    }

    public void backToLoginForm() {
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);
    }

    public void backToQuestionForm() {
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(550);
            slider.setDuration(Duration.seconds(0.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

                regLquestionList();
            });
            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });
            slider.play();
        }
    }
}