package com.BookManagement.Management

import com.BookManagement.Management.model.Book
import com.BookManagement.Management.repository.BookRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BookRepositoryTest(@Autowired val bookRepository: BookRepository) {

    @Test
    fun `test findAllByOrderByTitleAsc`() {
        // Setup - create and save some books
        val book1 = Book(title = "Book A", author = "Author 1")
        val book2 = Book(title = "Book B", author = "Author 2")
        bookRepository.saveAll(listOf(book1, book2))

        // Execution
        val books = bookRepository.findAllByOrderByTitleAsc()

        // Assertion
        assertThat(books).hasSize(2)
        assertThat(books[0]).isEqualTo(book1)
        assertThat(books[1]).isEqualTo(book2)
    }
}