// IBookManager.aidl
package com.aries.demo.service;

// Declare any non-default types here with import statements
import com.aries.demo.service.Book;

interface IBookManager {


    List<Book> getBookList();

    void addBook(in Book book);


}
