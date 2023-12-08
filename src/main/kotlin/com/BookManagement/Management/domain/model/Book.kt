package com.BookManagement.Management.domain.model

data class Book(val name: String, val author: String, val reserved: Boolean) {
    init {
        require(name.isNotBlank()) { "Book name cannot be blank" }
        require(author.isNotBlank()) { "Book author cannot be blank" }
    }
}