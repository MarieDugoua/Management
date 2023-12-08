package com.BookManagement.Management.domain.usecase

import com.BookManagement.Management.domain.model.Book
import com.BookManagement.Management.domain.port.BookPort

class BookUseCase(
    private val bookPort: BookPort
) {
    fun getAllBooks(): List<Book> {
        return bookPort.getAllBooks().sortedBy {
            it.name.lowercase()
        }
    }

    fun addBook(book: Book) {
        bookPort.createBook(book)
    }

    fun reserveBook(bookId: Int) {
        val book = bookPort.getBookById(bookId)
        if (book.reserved) throw IllegalStateException("Book already reserved")
        bookPort.updateBookReservation(bookId, true)
    }
}
