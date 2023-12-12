package com.BookManagement.Management.driver.web

import com.BookManagement.Management.domain.model.Book
import com.BookManagement.Management.domain.usecase.BookUseCase
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@WebMvcTest
class BookControllerIT {

    @MockkBean
    private lateinit var bookUseCase: BookUseCase
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `rest route get books`() {
        // GIVEN
        every { bookUseCase.getAllBooks() } returns listOf(Book("A", "B", false))

        // WHEN
        mockMvc.get("/books")
            //THEN
            .andExpect {
                status { isOk() }
                content { content { APPLICATION_JSON } }
                content { json(
                    // language=json
                    """
                        [
                          {
                            "name": "A",
                            "author": "B",
                            "reserved": false
                          }
                        ]
                    """.trimIndent()
                ) }
            }
    }

    @Test
    fun `rest route post book`() {
        justRun { bookUseCase.addBook(any()) }

        mockMvc.post("/books") {
            // language=json
            content = """
                {
                  "name": "Les misérables",
                  "author": "Victor Hugo",
                  "reserved": false
                }
            """.trimIndent()
            contentType = APPLICATION_JSON
            accept = APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
        }

        val expected = Book(
            name = "Les misérables",
            author = "Victor Hugo",
            reserved = false
        )

        verify(exactly = 1) { bookUseCase.addBook(expected) }
        verify(exactly = 1) { bookUseCase.addBook(expected) }
    }

    @Test
    fun `rest route post book should return 400 when body is not good`() {
        justRun { bookUseCase.addBook(any()) }

        mockMvc.post("/books") {
            // language=json
            content = """
                {
                  "title": "Les misérables",
                  "author": "Victor Hugo",
                  "reserved": false
                }
            """.trimIndent()
            contentType = APPLICATION_JSON
            accept = APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }

        verify(exactly = 0) { bookUseCase.addBook(any()) }
    }

    @Test
    fun `reserveBook endpoint return 200 when book is reserved`() {
        val bookId = 1
        every { bookUseCase.reserveBook(bookId) } just Runs

        mockMvc.post("/books/$bookId/reserve")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `reserveBook endpoint should return 400 when book is already reserved`() {
        val bookId = 1
        every { bookUseCase.reserveBook(bookId) } throws IllegalStateException("Reservation was unsuccessful")

        mockMvc.post("/books/$bookId/reserve")
            .andExpect {
                status { isBadRequest() }
            }
    }
}