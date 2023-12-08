package com.BookManagement.Management.infrastructure.driver.web.dto

import com.BookManagement.Management.domain.model.Book

data class BookDTO(val name: String, val author: String, val reserved: Boolean) {
    fun toDomain(): Book {
        return Book(
            name = this.name,
            author = this.author,
            reserved = false
        )
    }
}

fun Book.toDto() = BookDTO(
    name = this.name,
    author = this.author,
    reserved = false
)