package com.example.librarymanagementsystem2;

import java.util.Date;

public class bookData {

    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String image;
    private Date date;
    private int quantity;
    private int remain;
    private int issued;

    public bookData(String isbn, String title, String author, String genre, String image,
                    Date date, int quantity, int remain, int issued) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.image = image;
        this.date = date;
        this.quantity = quantity;
        this.remain = remain;
        this.issued = issued;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getIssued() {
        return issued;
    }

    public void setIssued(int issued) {
        this.issued = issued;
    }
}
