package com.BookManagement.Management.infrastructure.driver.web

import com.BookManagement.Management.domain.usecase.BookUseCase
import com.BookManagement.Management.infrastructure.driver.web.dto.BookDTO
import com.BookManagement.Management.infrastructure.driver.web.dto.toDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(
    private val bookUseCase: BookUseCase
) {
    @CrossOrigin
    @GetMapping
    fun getAllBooks(): List<BookDTO> {
        return bookUseCase.getAllBooks()
            .map { it.toDto() }
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody bookDTO: BookDTO) {
        bookUseCase.addBook(bookDTO.toDomain())
    }

    @CrossOrigin
    @PostMapping("/{bookId}/reserve")
    @ResponseStatus(HttpStatus.OK)
    fun reserveBook(@PathVariable bookId: Int) {
        bookUseCase.reserveBook(bookId)
    }
}