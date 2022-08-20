package com.muzi.example.service;

import com.muzi.example.entity.es.Book;

import java.util.List;

public interface BookService {
    void addBook(Book book);

    List<Book> searchBook(String keyword);

}
