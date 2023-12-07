package com.BookManagement.Management.service

import com.BookManagement.Management.model.Book

interface BookService {
    fun addBook(book: Book): Book
    fun getAllBooksSortedByTitle(): List<Book>
}
