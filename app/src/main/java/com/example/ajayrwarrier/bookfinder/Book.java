package com.example.ajayrwarrier.bookfinder;

/**
 * Created by Ajay R Warrier on 03-11-2016.
 */
public class Book {
    String bookName;
    String authorName;

    Book(String bookName, String authorName) {
        this.bookName = bookName;
        this.authorName = authorName;

    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }


}
