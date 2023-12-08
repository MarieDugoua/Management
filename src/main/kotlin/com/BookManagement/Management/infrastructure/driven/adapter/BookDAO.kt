package com.BookManagement.Management.infrastructure.driven.adapter

import com.BookManagement.Management.domain.model.Book
import com.BookManagement.Management.domain.port.BookPort
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class BookDAO(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate): BookPort {
    override fun getAllBooks(): List<Book> {
        return namedParameterJdbcTemplate
            .query("SELECT * FROM BOOK", MapSqlParameterSource()) { rs, _ ->
                Book(
                    name = rs.getString("title"),
                    author = rs.getString("author"),
                    reserved = rs.getBoolean("reserved")
                )
            }
    }

    override fun createBook(book: Book) {
        namedParameterJdbcTemplate
            .update("INSERT INTO BOOK (title, author, reserved) values (:title, :author, :reserved)", mapOf(
                "title" to book.name,
                "author" to book.author,
                "reserved" to book.reserved
            ))
    }

    override fun updateBookReservation(bookId: Int, reserved: Boolean) {
        val params = MapSqlParameterSource()
        params.addValue("id", bookId)
        params.addValue("reserved", reserved)
        namedParameterJdbcTemplate.update(
            "UPDATE book SET reserved = :reserved WHERE id = :bookId",
            params
        )
    }

   override fun getBookById(bookId: Int): Book {
        val params = MapSqlParameterSource().addValue("id", bookId)
        return namedParameterJdbcTemplate.queryForObject(
            "SELECT * FROM book WHERE id = :bookId",
            params
        ) { rs, _ ->
            Book(
                name = rs.getString("title"),
                author = rs.getString("author"),
                reserved = rs.getBoolean("reserved")
            )
        }!!
    }
}