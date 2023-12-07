package com.BookManagement.Management.repository

import com.BookManagement.Management.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findAllByOrderByTitleAsc(): List<Book>
}