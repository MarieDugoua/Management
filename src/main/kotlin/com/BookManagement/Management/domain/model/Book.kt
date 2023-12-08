package com.BookManagement.Management.domain.model

data class Book(val name: String, val author: String) {
    init {
        require(name.isNotBlank()) { "com.BookManagement.Management.domain.model.Book name cannot be blank" }
        require(author.isNotBlank()) { "com.BookManagement.Management.domain.model.Book author cannot be blank" }
    }
}