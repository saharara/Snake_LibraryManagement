package com.example.librarymanagementsystem2;

import java.util.Date;

public class ListIssue {
    private int id;
    private String msv;
    private String name;
    private String isbn;
    private String title;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;

    public ListIssue(int id, String msv, String name, String isbn, String title, Date issueDate, Date dueDate, Date returnDate) {
        this.id = id;
        this.msv = msv;
        this.name = name;
        this.isbn = isbn;
        this.title = title;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
