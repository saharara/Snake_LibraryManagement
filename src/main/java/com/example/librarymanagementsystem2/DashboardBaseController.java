package com.example.librarymanagementsystem2;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardBaseController extends BaseController {
    protected Connection connect;
    protected Connection connect1;
    protected PreparedStatement pst;
    protected PreparedStatement pst1;
    protected ResultSet rs;
    protected ResultSet rs1;

    protected final static String jan = "January";
    protected final static String feb = "February";
    protected final static String mar = "March";
    protected final static String apr = "April";
    protected final static String may = "May";
    protected final static String jun = "June";
    protected final static String jul = "July";
    protected final static String aug = "August";
    protected final static String sept = "September";
    protected final static String oct = "October";
    protected final static String nov = "November";
    protected final static String dec = "December";

    String[] months = {jan, feb, mar, apr, may, jun, jul, aug, sept, oct, nov, dec};


    public void switchForm(ActionEvent event) throws SQLException{}
    public void displayUsername() throws SQLException{}
    public void close(){}
    public void minimize(){}
    public void logout(){}
}
