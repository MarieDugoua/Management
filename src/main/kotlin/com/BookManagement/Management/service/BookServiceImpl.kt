package com.BookManagement.Management.service

import com.BookManagement.Management.model.Book
import com.BookManagement.Management.repository.BookRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(val bookRepository: BookRepository) : BookService {

    override fun addBook(book: Book): Book {
        // Implement logic to ensure title and author are not empty
        if (book.title.isBlank() || book.author.isBlank()) {
            throw IllegalArgumentException("Book title and author must not be empty")
        }
        return bookRepository.save(book)
    }

    override fun getAllBooksSortedByTitle(): List<Book> {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "Title"))
    }
}
