package com.BookManagement.Management.domain.port

import com.BookManagement.Management.domain.model.Book

interface BookPort {
    fun getAllBooks(): List<Book>
    fun createBook(book: Book)
}