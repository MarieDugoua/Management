package com.BookManagement.Management

import com.BookManagement.Management.model.Book
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BookTest {

    @Test
    fun `book creation with non-empty title and author`() {
        val book = Book(title = "1984", author = "George Orwell")
        assertThat(book.title).isNotEmpty()
        assertThat(book.author).isNotEmpty()
    }

    @Test
    fun `book creation fails with empty title`() {
        assertThrows<IllegalArgumentException> {
            Book(title = "", author = "George Orwell")
        }
    }

    @Test
    fun `book creation fails with empty author`() {
        assertThrows<IllegalArgumentException> {
            Book(title = "1984", author = "")
        }
    }
}
