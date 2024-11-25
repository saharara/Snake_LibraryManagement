package com.example.librarymanagementsystem2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.OptionalDataException;
import java.net.URL;
import java.security.cert.Extension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class dashboardController implements Initializable {

    @FXML
    private TextField availableBooks_ISBN;

    @FXML
    private Button availableBooks_addBtn;

    @FXML
    private TextField availableBooks_author;
    @FXML
    private DatePicker availableBooks_date;
    @FXML
    private TextField availableBooks_quantity;

    @FXML
    private Button availableBooks_clearBtn;

    @FXML
    private TableColumn<bookData, String> availableBooks_col_ISBN;

    @FXML
    private TableColumn<bookData, String> availableBooks_col_author;

    @FXML
    private TableColumn<bookData, String> availableBooks_col_genre;

    @FXML
    private TableColumn<bookData, String> availableBooks_col_title;
    @FXML
    private TableColumn<bookData, Integer> availableBooks_col_issued;

    @FXML
    private TableColumn<bookData, Date> availableBooks_col_pubdate;

    @FXML
    private TableColumn<bookData, Integer> availableBooks_col_quantity;

    @FXML
    private TableColumn<bookData, Integer> availableBooks_col_remain;
    private String msvIssue;
    private String isbnIssue;
    private Date issueDateIssue;


    @FXML
    private Button availableBooks_deleteBtn;

    @FXML
    private AnchorPane availableBooks_form;

    @FXML
    private TextField availableBooks_genre;

    @FXML
    private ImageView availableBooks_imageView;

    @FXML
    private Button availableBooks_importBtn;

    @FXML
    private TableView<bookData> availableBooks_tableView;

    @FXML
    private TextField availableBooks_search;

    @FXML
    private TextField availableBooks_title;

    @FXML
    private Button availableBooks_updateBtn;

    @FXML
    private Button bookManagement_btn;
    @FXML
    private Button userManagement_btn;

    @FXML
    private AnchorPane user_form;
    @FXML
    private Button issue_btn;

    @FXML
    private Button chat_bot;
    @FXML
    private Button listOfissue_btn;
    @FXML
    private ImageView user_imageView;
    @FXML
    private TextField user_msv;

    @FXML
    private TextField user_name;

    @FXML
    private TextField user_phoneNumber;
    @FXML
    private TextField user_email;
    @FXML
    private Button user_addBtn;

    @FXML
    private Button user_clearBtn;

    @FXML
    private Button user_deleteBtn;
    @FXML
    private Button user_updateBtn;
    @FXML
    private TableView<User> user_tableView;

    @FXML
    private TableColumn<User, String> user_col_email;

    @FXML
    private TableColumn<User, String> user_col_msv;

    @FXML
    private TableColumn<User, String> user_col_name;

    @FXML
    private TableColumn<User, String> user_col_phoneNumber;
    @FXML
    private TextField user_search;


    @FXML
    private Button close;

    @FXML
    private Label dashboard_AB;

    @FXML
    private Label dashboard_TIS;

    @FXML
    private Label dashboard_TU;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Label username;
    @FXML
    private AnchorPane issue_form;
    @FXML
    private TextField issue_isbn;
    @FXML
    private TextField issue_msv;
    @FXML
    private Label issue_title;
    @FXML
    private Label issue_author;
    @FXML
    private Label issue_genre;
    @FXML
    private Label issue_name;
    @FXML
    private Label issue_phoneNumber;
    @FXML
    private Label issue_email;
    @FXML
    private AnchorPane listIssue_form;
    @FXML
    private TextField listOfIssue_search;
    @FXML
    private TableView<ListIssue> listOfIssue_tableView;
    @FXML
    private TableColumn<ListIssue, Date> listIssue_col_dueDate;

    @FXML
    private TableColumn<ListIssue, String> listIssue_col_isbn;

    @FXML
    private TableColumn<ListIssue, Date> listIssue_col_issueDate;

    @FXML
    private TableColumn<ListIssue, String> listIssue_col_msv;

    @FXML
    private TableColumn<ListIssue, String> listIssue_col_name;

    @FXML
    private TableColumn<ListIssue, Date> listIssue_col_returnDate;

    @FXML
    private TableColumn<ListIssue, String> listIssue_col_title;
    private int idIssue;

    @FXML
    private AnchorPane snowContainer;

    private Connection connect;
    private Connection connect1;
    private PreparedStatement pst;
    private PreparedStatement pst1;
    private ResultSet rs;
    private ResultSet rs1;

    private Image image;

    public void availableBooksAdd() throws SQLException {
        if (availableBooks_ISBN.getText().isEmpty()
        || availableBooks_title.getText().isEmpty()
        || availableBooks_author.getText().isEmpty()
        || availableBooks_genre.getText().isEmpty()
        || availableBooks_quantity.getText().isEmpty()
        || availableBooks_date.getValue() == null
        || getData.path == "" || getData.path == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            connect = database.connectDB();
            connect.setAutoCommit(false);
            try {
                // kiểm tra xem isbn tồn tại chưa
                String sqlcheck = "SELECT isbn FROM book WHERE isbn = ?";
                pst = connect.prepareStatement(sqlcheck);
                pst.setString(1, availableBooks_ISBN.getText());
                rs = pst.executeQuery();

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Book ISBN: " + availableBooks_ISBN.getText() + " was already exist!");
                    alert.showAndWait();
                } else {

                    String sql = "INSERT INTO book VALUES (?,?,?,?,?,?,?,?,?)";
                    pst = connect.prepareStatement(sql);
                    pst.setString(1, availableBooks_ISBN.getText());
                    pst.setString(2, availableBooks_title.getText());
                    pst.setString(3, availableBooks_author.getText());
                    pst.setString(4, availableBooks_genre.getText());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");
                    pst.setString(5, uri);

                    pst.setString(6, String.valueOf(availableBooks_date.getValue()));
                    pst.setInt(7, Integer.valueOf(availableBooks_quantity.getText()));
                    pst.setInt(8, Integer.valueOf(availableBooks_quantity.getText()));
                    pst.setInt(9, 0);


                    pst.executeUpdate();
                    connect.commit(); // commit de update vao csdl


                    // thông báo thêm thành công

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    availableBooksShowListData(); // show ra bảng
                    availableBooksClear(); // clear fields
                }
            } catch (Exception e) {
                connect.rollback();
                e.printStackTrace();
            }
        }
    }

    public void setAvailableBooksUpdate() throws SQLException {
        if (availableBooks_ISBN.getText().isEmpty()
                || availableBooks_title.getText().isEmpty()
                || availableBooks_author.getText().isEmpty()
                || availableBooks_genre.getText().isEmpty()
                || availableBooks_quantity.getText().isEmpty()
                || availableBooks_date.getValue() == null
                || getData.path == "" || getData.path == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            // check trong csdl có isbn đấy không:
            connect = database.connectDB();
            connect1 = database.connectDB();
            try {

                String sql = "SELECT isbn FROM book WHERE isbn = ?";
                pst = connect.prepareStatement(sql);
                pst.setString(1, availableBooks_ISBN.getText());
                rs = pst.executeQuery();

                // nếu tìm thấy thì update
                if (rs.next()) {

                    // check xem quantity phải lớn hơn hoặc bằng issued mới đc update
                    int issued = 0;
                    String sql1 = "SELECT issued FROM book WHERE isbn = ?";
                    pst = connect.prepareStatement(sql1);
                    pst.setString(1, availableBooks_ISBN.getText());
                    rs = pst.executeQuery();
                    rs.next();
                    issued = rs.getInt("issued");
                    int quantity = Integer.parseInt(availableBooks_quantity.getText());

                    if (quantity >= issued) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to UPDATE Book with ISBN: "+ availableBooks_ISBN.getText() +"?");
                        Optional<ButtonType> option = alert.showAndWait();

                        if (option.get().equals(ButtonType.OK)) {
                            // update ở bảng book
                            String sqlUpdate = "UPDATE book SET title = ?, author = ?, genre = ?, image = ?" +
                                    ", pub_date = ?, quantity = ?, remain = ? " +
                                    "WHERE isbn = ?";
                            pst = connect.prepareStatement(sqlUpdate);
                            pst.setString(1, availableBooks_title.getText());
                            pst.setString(2, availableBooks_author.getText());
                            pst.setString(3, availableBooks_genre.getText());
                            pst.setString(8,availableBooks_ISBN.getText());

                            String uri = getData.path;
                            uri = uri.replace("\\", "\\\\");
                            pst.setString(4, uri);

                            pst.setString(5, String.valueOf(availableBooks_date.getValue()));
                            pst.setInt(6, Integer.parseInt(availableBooks_quantity.getText()));

                            // tinh so sach con lai
                            int rmain = 0;
                            String sqlremain = "SELECT issued FROM book WHERE isbn = ?";
                            pst1 = connect1.prepareStatement(sqlremain);
                            pst1.setString(1, availableBooks_ISBN.getText());
                            rs1 = pst1.executeQuery();
                            rs1.next();
                            rmain = rs1.getInt("issued"); // issued
                            int sumbook = Integer.parseInt(availableBooks_quantity.getText());
                            rmain = sumbook - rmain; // remain
                            pst.setInt(7, rmain);

                            pst.executeUpdate();

                            // update ở bảng issue:
                            String sqlUpdate1 = "UPDATE issue SET title = ? WHERE isbn = ?";
                            pst = connect.prepareStatement(sqlUpdate1);
                            pst.setString(1, availableBooks_title.getText());
                            pst.setString(2,availableBooks_ISBN.getText());
                            pst.executeUpdate();

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Updated!");
                            alert.showAndWait();

                            availableBooksShowListData();
                            availableBooksClear();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Quantity must be greater than Issued");
                        alert.showAndWait();
                    }



                // nếu không thì thông báo lỗi
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Not found Book ISBN");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void availableBooksDelete() throws SQLException {
        if (availableBooks_ISBN.getText().isEmpty()
                || availableBooks_title.getText().isEmpty()
                || availableBooks_author.getText().isEmpty()
                || availableBooks_genre.getText().isEmpty()
                || availableBooks_quantity.getText().isEmpty()
                || availableBooks_date.getValue() == null
                || getData.path == "" || getData.path == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            // check trong csdl có isbn đấy không:
            connect = database.connectDB();
            connect.setAutoCommit(false);
            try {

                String sql = "SELECT isbn FROM book WHERE isbn = ?";
                pst = connect.prepareStatement(sql);
                pst.setString(1, availableBooks_ISBN.getText());
                rs = pst.executeQuery();

                // nếu tìm thấy, và issued = 0 thì xóa
                // có người đang mượn thì không xóa được

                if (rs.next()) {
                    int issu = 0;
                    String sqlissue = "SELECT issued FROM book WHERE isbn = ?;";
                    pst1 = connect.prepareStatement(sqlissue);
                    pst1.setString(1, availableBooks_ISBN.getText());
                    rs1 = pst1.executeQuery();
                    rs1.next();
                    issu = rs1.getInt("issued");

                    if (issu == 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to DELETE Book with ISBN: "+ availableBooks_ISBN.getText() +"?");
                        Optional<ButtonType> option = alert.showAndWait();

                        if (option.get().equals(ButtonType.OK)) {
                            String sqlDelete = "DELETE FROM book WHERE isbn = ?;";
                            pst = connect.prepareStatement(sqlDelete);
                            pst.setString(1, availableBooks_ISBN.getText());
                            pst.executeUpdate();
                            connect.commit(); // commit de update vao csdl

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Delete!");
                            alert.showAndWait();

                            availableBooksShowListData();
                            availableBooksClear();
                        }
                    } else { // quyen sach dang duoc cho muon nen khong xoa duoc
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("This book is on loan!");
                        alert.showAndWait();
                    }
                    // nếu không thì thông báo lỗi
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Not found Book ISBN");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                connect.rollback();
                e.printStackTrace();
            }
        }
    }

    public void availableBooksClear() {
        availableBooks_ISBN.setText("");
        availableBooks_title.setText("");
        availableBooks_author.setText("");
        availableBooks_genre.setText("");
        availableBooks_date.setValue(null);
        availableBooks_quantity.setText("");

        getData.path = "";
        availableBooks_imageView.setImage(null);
    }

    public void availableBooksInsertImage() { // ham cho nut import

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Image", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow()); // đợi người dùng chọn file

        if (file != null) {
            getData.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 112, 137, false, true);
            availableBooks_imageView.setImage(image);
        }
    }

    public void availableBooksSearch() {
        String searchQuery = availableBooks_search.getText().toLowerCase();

        // Get books from API
        List<bookData> apiResults = bookData.searchBooks(searchQuery);

        // Combine API results with existing database books
        ObservableList<bookData> combinedBooks = FXCollections.observableArrayList();
        combinedBooks.addAll(availableBooksList); // Add database books
        combinedBooks.addAll(apiResults);         // Add API results

        // Create filtered list
        FilteredList<bookData> filter = new FilteredList<>(combinedBooks, e -> true);

        // Add listener for search text changes
        availableBooks_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateBookData -> {
                // If search text is empty, show all books
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                // Search in all fields
                return predicateBookData.getIsbn().toLowerCase().contains(searchKey) ||
                        predicateBookData.getTitle().toLowerCase().contains(searchKey) ||
                        predicateBookData.getAuthor().toLowerCase().contains(searchKey) ||
                        predicateBookData.getGenre().toLowerCase().contains(searchKey);
            });
        });

        // Create and bind sorted list
        SortedList<bookData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(availableBooks_tableView.comparatorProperty());
        availableBooks_tableView.setItems(sortList);
    }
    public ObservableList<bookData> availableBooksListData() throws SQLException {

        ObservableList<bookData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        connect.setAutoCommit(false);

        try {
            String sql = "SELECT * FROM book";
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();

            bookData bookD;

            while(rs.next()) {
                bookD = new bookData(rs.getString("isbn"), rs.getString("title")
                        , rs.getString("author"), rs.getString("genre")
                        , rs.getString("image"), rs.getDate("pub_date")
                        , rs.getInt("quantity"), rs.getInt("remain"), rs.getInt("issued"));
                listData.add(bookD);
            }
            connect.commit();
        } catch (Exception e){
            connect.rollback();
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<bookData> availableBooksList;
    public void availableBooksShowListData() throws SQLException {
        availableBooksList = availableBooksListData(); //list book trong csdl
        availableBooks_col_ISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        availableBooks_col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        availableBooks_col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        availableBooks_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availableBooks_col_pubdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        availableBooks_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        availableBooks_col_remain.setCellValueFactory(new PropertyValueFactory<>("remain"));
        availableBooks_col_issued.setCellValueFactory(new PropertyValueFactory<>("issued"));

        availableBooks_tableView.setItems(availableBooksList);
    }

    public void availableBooksSelect() {
        bookData bookD = availableBooks_tableView.getSelectionModel().getSelectedItem();
        int num = availableBooks_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        availableBooks_ISBN.setText(bookD.getIsbn());
        availableBooks_title.setText(bookD.getTitle());
        availableBooks_author.setText(bookD.getAuthor());
        availableBooks_genre.setText(bookD.getGenre());
        try {
            availableBooks_date.setValue(LocalDate.parse(String.valueOf(bookD.getDate())));
        } catch (Exception e) {
            System.out.println("loi get ngay");
        }
        availableBooks_quantity.setText(String.valueOf(bookD.getQuantity()));

        getData.path = bookD.getImage();

        String imageUrl = bookD.getImage();
        Image image = new Image(imageUrl, 112, 137, false, true);
        availableBooks_imageView.setImage(image);
    }

    public ObservableList<User> usersListData() throws SQLException {

        ObservableList<User> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        connect.setAutoCommit(false);

        try {
            String sql = "SELECT * FROM user";
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();

            User userD;

            while(rs.next()) {
                userD = new User(rs.getString("msv"), rs.getString("name")
                        , rs.getString("phonenumber"), rs.getString("email")
                        , rs.getString("image"));
                listData.add(userD);
            }
            connect.commit();
        } catch (Exception e){
            connect.rollback();
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<User> usersList;
    public void usersShowListData() throws SQLException {
        usersList = usersListData(); //list user trong csdl
        user_col_msv.setCellValueFactory(new PropertyValueFactory<>("msv"));
        user_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        user_col_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        user_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        user_tableView.setItems(usersList);
    }

    public void usersSelect() {
        User userD = user_tableView.getSelectionModel().getSelectedItem();
        int num = user_tableView.getSelectionModel().getSelectedIndex();

        if(num < 0) {
            return;
        }

        user_msv.setText(userD.getMsv());
        user_name.setText(userD.getName());
        user_phoneNumber.setText(userD.getPhoneNumber());
        user_email.setText(userD.getEmail());

        getData.path = userD.getImage();

        String uri = "file:" + userD.getImage();

        image = new Image(uri, 112, 137, false, true);
        user_imageView.setImage(image);
    }

    public void usersInsertImage() { // ham cho nut import

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File1");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Image1", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow()); // đợi người dùng chọn file

        if (file != null) {
            getData.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 112, 137, false, true);
            user_imageView.setImage(image);
        }
    }

    public void usersAdd() throws SQLException {
        if (user_msv.getText().isEmpty()
                || user_name.getText().isEmpty()
                || user_phoneNumber.getText().isEmpty()
                || user_email.getText().isEmpty()
                || getData.path == "" || getData.path == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            connect = database.connectDB();
            connect.setAutoCommit(false);
            try {
                // kiểm tra xem msv tồn tại chưa
                String sqlcheck = "SELECT msv FROM user WHERE msv = ?";
                pst = connect.prepareStatement(sqlcheck);
                pst.setString(1, user_msv.getText());
                rs = pst.executeQuery();

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student code: " + user_msv.getText() + " was already exist!");
                    alert.showAndWait();
                } else {

                    String sql = "INSERT INTO user VALUES (?,?,?,?,?)";
                    pst = connect.prepareStatement(sql);
                    pst.setString(1, user_msv.getText());
                    pst.setString(2, user_name.getText());
                    pst.setString(3, user_phoneNumber.getText());
                    pst.setString(4, user_email.getText());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");
                    pst.setString(5, uri);

                    pst.executeUpdate();
                    connect.commit(); // commit de update vao csdl


                    // thông báo thêm thành công

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    usersShowListData(); // show ra bảng
                    usersClear(); // clear fields
                }
            } catch (Exception e) {
                connect.rollback();
                e.printStackTrace();
            }
        }
    }

    public void usersUpdate() {
        if (user_msv.getText().isEmpty()
                || user_name.getText().isEmpty()
                || user_phoneNumber.getText().isEmpty()
                || user_email.getText().isEmpty()
                || getData.path == "" || getData.path == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            connect = database.connectDB();
            try {
                String sql = "SELECT msv FROM user WHERE msv = ?";
                pst = connect.prepareStatement(sql);
                pst.setString(1, user_msv.getText());
                rs = pst.executeQuery();

                // nếu tìm thấy thì update ở 2 table
                if (rs.next()) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to UPDATE student with student code: "+ user_msv.getText() +"?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        String sqlUpdate = "UPDATE user SET name = ?, phonenumber = ?, email = ?, image = ? " +
                                "WHERE msv = ?";
                        pst = connect.prepareStatement(sqlUpdate);
                        pst.setString(1, user_name.getText());
                        pst.setString(2, user_phoneNumber.getText());
                        pst.setString(3, user_email.getText());

                        String uri = getData.path;
                        uri = uri.replace("\\", "\\\\");
                        pst.setString(4, uri);

                        pst.setString(5, user_msv.getText());

                        pst.executeUpdate();

                        // update ở bảng issue
                        String sqlUpdate1 = "UPDATE issue SET name = ? " +
                                "WHERE msv = ?";
                        pst = connect.prepareStatement(sqlUpdate1);
                        pst.setString(1, user_name.getText());
                        pst.setString(2, user_msv.getText());

                        pst.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated!");
                        alert.showAndWait();

                        usersShowListData();
                        usersClear();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Not found Student code");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void usersDelete() {
        if (user_msv.getText().isEmpty()
                || user_name.getText().isEmpty()
                || user_phoneNumber.getText().isEmpty()
                || user_email.getText().isEmpty()
                || getData.path == "" || getData.path == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            //
            connect = database.connectDB();
            try {
                String sql = "SELECT msv FROM user WHERE msv = ?";
                pst = connect.prepareStatement(sql);
                pst.setString(1, user_msv.getText());
                rs = pst.executeQuery();

                // nếu tìm thấy msv trong bảng users
                if (rs.next()) {
                    // check trong bảng issue nếu không có mới xóa được
                    String sql1 = "SELECT msv FROM issue WHERE msv = ?";
                    pst = connect.prepareStatement(sql1);
                    pst.setString(1, user_msv.getText());
                    rs = pst.executeQuery();

                    // nếu có ở bảng issue thì không xóa được
                    if (rs.next()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("This student is on the all issue list.");
                        alert.showAndWait();
                    //  xóa
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to DELETE student with student code: "+ user_msv.getText() +"?");
                        Optional<ButtonType> option = alert.showAndWait();

                        if (option.get().equals(ButtonType.OK)) {
                            String sqlDelete = "DELETE FROM user WHERE msv = ?;";
                            pst = connect.prepareStatement(sqlDelete);
                            pst.setString(1, user_msv.getText());
                            pst.executeUpdate();

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Deleted!");
                            alert.showAndWait();

                            usersShowListData();
                            usersClear();
                        }
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Not found Student code");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void usersClear() {
        user_msv.setText("");
        user_name.setText("");
        user_phoneNumber.setText("");
        user_email.setText("");

        getData.path = "";
        user_imageView.setImage(null);
    }

    public void usersSearch() {
        FilteredList<User> filter = new FilteredList<>(usersList, e -> true);

        user_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateUser -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchkey = newValue.toLowerCase();
                if (predicateUser.getMsv().toLowerCase().contains(searchkey)) {
                    return true;
                } else if (predicateUser.getName().toLowerCase().contains(searchkey)) {
                    return true;
                } else if (predicateUser.getPhoneNumber().toLowerCase().contains(searchkey)) {
                    return true;
                } else if (predicateUser.getEmail().toLowerCase().contains(searchkey)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<User> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(user_tableView.comparatorProperty());
        user_tableView.setItems((sortList));
    }

    public void searchBookByIsbn() {
        connect = database.connectDB();
        try {
            String sql = "SELECT title, author, genre FROM book WHERE isbn = ?";
            pst = connect.prepareStatement(sql);
            pst.setString(1, issue_isbn.getText());
            rs = pst.executeQuery();

            // neu tim thay
            if (rs.next()) {
                issue_title.setText(rs.getString("title"));
                issue_author.setText(rs.getString("author"));
                issue_genre.setText(rs.getString("genre"));
            } else {
                issue_title.setText("Title");
                issue_author.setText("Author");
                issue_genre.setText("Genre");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchUserByMsv() {
        connect = database.connectDB();
        try {
            String sql = "SELECT name, phonenumber, email FROM user WHERE msv = ?";
            pst = connect.prepareStatement(sql);
            pst.setString(1, issue_msv.getText());
            rs = pst.executeQuery();

            // neu tim thay
            if (rs.next()) {
                issue_name.setText(rs.getString("name"));
                issue_phoneNumber.setText(rs.getString("phonenumber"));
                issue_email.setText(rs.getString("email"));
            } else {
                issue_title.setText("Name");
                issue_author.setText("Phone Number");
                issue_genre.setText("Email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrow() {
        connect = database.connectDB();
        try {

            // check xem an enter chua
            if (issue_title.getText().equals("Title") || issue_name.getText().equals("Name")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not found");
                alert.showAndWait();
                clearIssueInfo();
            } else {
                String sql = "SELECT msv FROM user WHERE msv = ?";
                pst = connect.prepareStatement(sql);
                pst.setString(1, issue_msv.getText());
                rs = pst.executeQuery();

                // check co student khong
                if (rs.next()) {
                    // check xem co sach khong, so remain > 0
                    String sql1 = "SELECT isbn FROM book WHERE isbn = ?";
                    pst = connect.prepareStatement(sql1);
                    pst.setString(1, issue_isbn.getText());
                    rs = pst.executeQuery();

                    // neu co sach
                    if (rs.next()) {
                        int rmain = 0;
                        String sql2 = "SELECT remain FROM book WHERE isbn = ?";
                        pst = connect.prepareStatement(sql2);
                        pst.setString(1, issue_isbn.getText());
                        rs = pst.executeQuery();
                        rs.next();
                        rmain = rs.getInt("remain");

                        // neu so sach con lai > 0
                        if (rmain > 0) {
                            // check bang issue xem co chua
                            String sql3 = "SELECT msv, isbn FROM issue WHERE msv = ? AND isbn = ?";
                            pst = connect.prepareStatement(sql3);
                            pst.setString(1, issue_msv.getText());
                            pst.setString(2, issue_isbn.getText());
                            rs = pst.executeQuery();

                            // neu co
                            if (rs.next()) {
                                String sql4 = "SELECT returnDate FROM issue WHERE msv = ? AND isbn = ?";
                                pst = connect.prepareStatement(sql4);
                                pst.setString(1, issue_msv.getText());
                                pst.setString(2, issue_isbn.getText());
                                rs = pst.executeQuery();

                                boolean f = true;
                                while (rs.next()) {
                                    Date retuDate = rs.getDate("returnDate");
                                    if (retuDate == null) {
                                        f = false;
                                        break;
                                    }
                                }
                                if (f == false) { // neu chua tra thi khong cho muon
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("This book has been borrowed");
                                    alert.showAndWait();
                                    clearIssueInfo();
                                } else {
                                    String sql5 = "INSERT INTO issue (msv, name, isbn, title, issueDate, dueDate)" +
                                            " VALUES(?, ?, ?, ?, ?, ?)";
                                    pst = connect.prepareStatement(sql5);
                                    pst.setString(1, issue_msv.getText());
                                    pst.setString(2,issue_name.getText());
                                    pst.setString(3, issue_isbn.getText());
                                    pst.setString(4, issue_title.getText());

                                    Date isdate = new Date();
                                    java.sql.Date sqlDate = new java.sql.Date(isdate.getTime());
                                    pst.setDate(5, sqlDate);

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(isdate);
                                    calendar.add(Calendar.DAY_OF_YEAR, 150);
                                    java.sql.Date dueDate = new java.sql.Date(calendar.getTimeInMillis());
                                    pst.setDate(6, dueDate);
                                    pst.executeUpdate();

                                    // thay doi trong bang book
                                    // issued + 1
                                    String sql6 = "UPDATE book SET issued = issued + 1 WHERE isbn = ?";
                                    pst = connect.prepareStatement(sql6);
                                    pst.setString(1,issue_isbn.getText());
                                    pst.executeUpdate();
                                    // remain - 1
                                    String sql7 = "UPDATE book SET remain = remain - 1 WHERE isbn = ?";
                                    pst = connect.prepareStatement(sql7);
                                    pst.setString(1,issue_isbn.getText());
                                    pst.executeUpdate();

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Successfully Issue!");
                                    alert.showAndWait();
                                    clearIssueInfo();
                                }

                            } else { // neu trong bang issue chua co
                                String sql4 = "INSERT INTO issue (msv, name, isbn, title, issueDate, dueDate)" +
                                        " VALUES(?, ?, ?, ?, ?, ?)";
                                pst = connect.prepareStatement(sql4);
                                pst.setString(1, issue_msv.getText());
                                pst.setString(2,issue_name.getText());
                                pst.setString(3, issue_isbn.getText());
                                pst.setString(4, issue_title.getText());

                                Date isdate = new Date();
                                java.sql.Date sqlDate = new java.sql.Date(isdate.getTime());
                                pst.setDate(5, sqlDate);

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(isdate);
                                calendar.add(Calendar.DAY_OF_YEAR, 150);
                                java.sql.Date dueDate = new java.sql.Date(calendar.getTimeInMillis());
                                pst.setDate(6, dueDate);
                                pst.executeUpdate();

                                // thay doi trong bang book
                                // issued + 1
                                String sql5 = "UPDATE book SET issued = issued + 1 WHERE isbn = ?";
                                pst = connect.prepareStatement(sql5);
                                pst.setString(1,issue_isbn.getText());
                                pst.executeUpdate();
                                // remain - 1
                                String sql6 = "UPDATE book SET remain = remain - 1 WHERE isbn = ?";
                                pst = connect.prepareStatement(sql6);
                                pst.setString(1,issue_isbn.getText());
                                pst.executeUpdate();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Successfully Issue!");
                                alert.showAndWait();
                                clearIssueInfo();
                            }
                        } else { // neu so con  lai < 0
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("This book is not available!");
                            alert.showAndWait();
                            clearIssueInfo();
                        }
                    } else { // neu khong co sach
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("This book is not available!");
                        alert.showAndWait();
                        clearIssueInfo();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Not found this student with student code: " + issue_msv.getText());
                    alert.showAndWait();
                    clearIssueInfo();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearIssueInfo() {
        issue_isbn.setText("");
        issue_msv.setText("");
        issue_title.setText("Title");
        issue_author.setText("Author");
        issue_genre.setText("Genre");
        issue_name.setText("Name");
        issue_phoneNumber.setText("Phone Number");
        issue_email.setText("Email");
    }

    public ObservableList<ListIssue> issuesListData() throws SQLException {

        ObservableList<ListIssue> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        connect.setAutoCommit(false);

        try {
            String sql = "SELECT * FROM issue";
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();

            ListIssue listIssueD;

            while(rs.next()) {
                listIssueD = new ListIssue(rs.getInt("id"), rs.getString("msv"), rs.getString("name")
                        , rs.getString("isbn"), rs.getString("title")
                        , rs.getDate("issueDate"), rs.getDate("dueDate")
                        , rs.getDate("returnDate"));
                listData.add(listIssueD);
            }
            connect.commit();
        } catch (Exception e){
            connect.rollback();
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<ListIssue> issuesList;

    public void issuesShowListData() throws SQLException {
        issuesList = issuesListData(); //list trong csdl
        listIssue_col_msv.setCellValueFactory(new PropertyValueFactory<>("msv"));
        listIssue_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        listIssue_col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        listIssue_col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        listIssue_col_issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        listIssue_col_dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        listIssue_col_returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        listOfIssue_tableView.setItems(issuesList);
    }

    public void listIssueSearch() {

        FilteredList<ListIssue> filter = new FilteredList<>(issuesList, e -> true);

        listOfIssue_search.textProperty().addListener((observable, oldValue, newValue) -> {

            filter.setPredicate(predicateIssueData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateIssueData.getMsv().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateIssueData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateIssueData.getTitle().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateIssueData.getIsbn().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<ListIssue> sortList = new SortedList(filter);
        sortList.comparatorProperty().bind(listOfIssue_tableView.comparatorProperty());
        listOfIssue_tableView.setItems(sortList);
    }

    public void listIssueSelect() {
        ListIssue issueD = listOfIssue_tableView.getSelectionModel().getSelectedItem();
        int num = listOfIssue_tableView.getSelectionModel().getSelectedIndex();

        if(num < 0) {
            return;
        }

        idIssue = issueD.getId();
        msvIssue = issueD.getMsv();
        isbnIssue = issueD.getIsbn();
        issueDateIssue = issueD.getIssueDate();
    }
    public void listIssueDelete() {
        if (msvIssue == null || isbnIssue == null || issueDateIssue == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the item to delete");
            alert.showAndWait();
        } else {
            // kiem tra xem return chua
            connect = database.connectDB();
            try {
                String sql = "SELECT returnDate FROM issue WHERE id = ?";
                pst = connect.prepareStatement(sql);
                pst.setString(1, idIssue + "");
//                pst.setString(2, isbnIssue);
//
//                java.sql.Date sqlIssueDate = new java.sql.Date(issueDateIssue.getTime());
//                pst.setDate(3, sqlIssueDate);

                rs = pst.executeQuery();

                rs.next();
                Date retuDate = rs.getDate("returnDate");
                if (retuDate == null) { // chua return thi khong xoa
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("This book has been borrowed");
                    alert.showAndWait();
                } else { // xoa
                    String sql1 = "DELETE FROM issue WHERE id = ?";
                    pst = connect.prepareStatement(sql1);
                    pst.setString(1, idIssue + "");
//                    pst.setString(2, isbnIssue);
//
//                    java.sql.Date sqlIssueDate1 = new java.sql.Date(issueDateIssue.getTime());
//                    pst.setDate(3, sqlIssueDate1);
                    pst.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Delete!");
                    alert.showAndWait();
                    issuesShowListData();

                    //delete xong cho cac bien la null
                    msvIssue = null; isbnIssue = null; issueDateIssue =null;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void listIssueReturn() {
        if (msvIssue == null || isbnIssue == null || issueDateIssue == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the item to return");
            alert.showAndWait();
        } else {
            // kiem tra xem return chua
            connect = database.connectDB();
            try {
                String sql = "SELECT returnDate FROM issue WHERE id = ?";
                pst = connect.prepareStatement(sql);
                pst.setString(1, idIssue + "");
//                pst.setString(2, isbnIssue);
//
//                java.sql.Date sqlIssueDate = new java.sql.Date(issueDateIssue.getTime());
//                pst.setDate(3, sqlIssueDate);

                rs = pst.executeQuery();

                rs.next();
                Date retuDate = rs.getDate("returnDate");

                // neu chua tra thi tra
                if (retuDate == null) {
                    String sql1 = "UPDATE issue SET returnDate = ? WHERE id = ?";
                    pst = connect.prepareStatement(sql1);

                    Date homnay = new Date();
                    java.sql.Date homnay1 = new java.sql.Date(homnay.getTime());

                    pst.setDate(1, homnay1);
                    pst.setString(2, idIssue + "");
//                    pst.setString(3,isbnIssue);
//
//                    java.sql.Date sqlIssueDate1 = new java.sql.Date(issueDateIssue.getTime());
//                    pst.setDate(4, sqlIssueDate1);
                    pst.executeUpdate();

                    // issued - 1
                    String sql2 = "UPDATE book SET issued = issued - 1 WHERE isbn = ?";
                    pst = connect.prepareStatement(sql2);
                    pst.setString(1,isbnIssue);
                    pst.executeUpdate();
                    // remain - 1
                    String sql3 = "UPDATE book SET remain = remain + 1 WHERE isbn = ?";
                    pst = connect.prepareStatement(sql3);
                    pst.setString(1,isbnIssue);
                    pst.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Return!");
                    alert.showAndWait();
                    issuesShowListData();

                    //delete xong cho cac bien la null
                    msvIssue = null; isbnIssue = null; issueDateIssue =null;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("It has been retured");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void displayUsername() {
        String user = getData.username;
        user = user.substring(0,1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    public void switchForm(ActionEvent event) throws SQLException {

        if (event.getSource() == dashboard_btn) {
            availableBooks_form.setVisible(false);
            user_form.setVisible(false);
            issue_form.setVisible(false);
            listIssue_form.setVisible(false);
            dashboard_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #4f63b7, #709df3)");
            bookManagement_btn.setStyle("-fx-background-color: transparent");
            userManagement_btn.setStyle("-fx-background-color: transparent");
            issue_btn.setStyle("-fx-background-color: transparent");
            listOfissue_btn.setStyle("-fx-background-color: transparent");
        } else if (event.getSource() == bookManagement_btn) {
            dashboard_form.setVisible(false);
            user_form.setVisible(false);
            issue_form.setVisible(false);
            listIssue_form.setVisible(false);
            availableBooks_form.setVisible(true);

            bookManagement_btn.setStyle("-fx-background-color: linear-gradient(to top right, #4f63b7, #709df3)");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            userManagement_btn.setStyle("-fx-background-color: transparent");
            issue_btn.setStyle("-fx-background-color: transparent");
            listOfissue_btn.setStyle("-fx-background-color: transparent");

            availableBooksShowListData();
            availableBooksSearch();
            availableBooksClear();
        } else if (event.getSource() == userManagement_btn) {
            dashboard_form.setVisible(false);
            availableBooks_form.setVisible(false);
            issue_form.setVisible(false);
            listIssue_form.setVisible(false);
            user_form.setVisible(true);

            userManagement_btn.setStyle("-fx-background-color: linear-gradient(to top right, #4f63b7, #709df3)");
            bookManagement_btn.setStyle("-fx-background-color: transparent");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            issue_btn.setStyle("-fx-background-color: transparent");
            listOfissue_btn.setStyle("-fx-background-color: transparent");

            usersShowListData();
            usersSearch();
            usersClear();
        } else if (event.getSource() == issue_btn) {
            dashboard_form.setVisible(false);
            availableBooks_form.setVisible(false);
            user_form.setVisible(false);
            listIssue_form.setVisible(false);
            issue_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            listOfissue_btn.setStyle("-fx-background-color: transparent");
            bookManagement_btn.setStyle("-fx-background-color: transparent");
            userManagement_btn.setStyle("-fx-background-color: transparent");
            issue_btn.setStyle("-fx-background-color: linear-gradient(to top right, #4f63b7, #709df3)");
        } else if (event.getSource() == listOfissue_btn) {
            dashboard_form.setVisible(false);
            availableBooks_form.setVisible(false);
            user_form.setVisible(false);
            issue_form.setVisible(false);
            listIssue_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            bookManagement_btn.setStyle("-fx-background-color: transparent");
            userManagement_btn.setStyle("-fx-background-color: transparent");
            issue_btn.setStyle("-fx-background-color: transparent");
            listOfissue_btn.setStyle("-fx-background-color: linear-gradient(to top right, #4f63b7, #709df3)");

            issuesShowListData();
            listIssueSearch();
        }
    }

    public void logout(){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want o logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                // ẩn dashboard
                logout.getScene().getWindow().hide();
                // mở loginform
                Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        try {
            availableBooksShowListData();
            usersShowListData();
            issuesShowListData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void openSnakeBotWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatBot.fxml"));
            StackPane snakeBotRoot = loader.load();
            Stage newStage = new Stage();
            newStage.setTitle("SnakeBot");
            Scene newScene = new Scene(snakeBotRoot);
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        SnowEffect snowEffect = new SnowEffect(snowContainer, 10);
        snowEffect.startSnowfall();
    }
}
