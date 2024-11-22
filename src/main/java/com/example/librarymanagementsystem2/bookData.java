package com.example.librarymanagementsystem2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public static List<bookData> searchBooks(String query) {
        List<bookData> bookList = new ArrayList<>();
        try {
            JsonArray booksData = GoogleBooksAPI.getBookData(query);

            for (JsonElement element : booksData) {
                JsonObject volume = element.getAsJsonObject();
                JsonObject volumeInfo = volume.getAsJsonObject("volumeInfo");

                String isbn10 = GoogleBooksAPI.getISBN10(volumeInfo);
                String isbn13 = GoogleBooksAPI.getISBN13(volumeInfo);
                String isbn = !isbn13.isEmpty() ? isbn13 : isbn10;

                Date publishDate = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateStr = GoogleBooksAPI.getPublishedDate(volumeInfo);
                    publishDate = sdf.parse(dateStr);
                } catch (Exception e) {
                }

                bookData book = new bookData(
                        isbn,
                        GoogleBooksAPI.getTitle(volumeInfo),
                        GoogleBooksAPI.getAuthors(volumeInfo),
                        GoogleBooksAPI.getCategories(volumeInfo),
                        GoogleBooksAPI.getBookImage(volumeInfo),
                        publishDate,
                        0,
                        0,
                        0
                );

                bookList.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;
    }
}