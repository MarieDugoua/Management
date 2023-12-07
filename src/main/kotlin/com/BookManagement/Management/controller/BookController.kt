package com.BookManagement.Management.controller

import com.BookManagement.Management.model.Book
import com.BookManagement.Management.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @PostMapping("/create")
    fun createBook(@RequestBody book: Book): ResponseEntity<Book> {
        if (book.title.isBlank() || book.author.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
        val createdBook = bookService.addBook(book)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook)
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody book: Book): Book {
        return bookService.addBook(book)
    }

    @GetMapping
    fun listBooks(): ResponseEntity<List<Book>> {
        val books = bookService.getAllBooksSortedByTitle()
        return ResponseEntity.ok(books)
    }

}
