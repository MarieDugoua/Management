package com.BookManagement.Management.controller

import com.BookManagement.Management.model.BookRequest
import com.BookManagement.Management.model.BookResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `test adding a book`() {
        val bookRequest = BookRequest(title = "Test Book", author = "Author")
        val responseEntity = restTemplate.postForEntity("/books", bookRequest, BookResponse::class.java)

        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(responseEntity.body?.title).isEqualTo(bookRequest.title)
        assertThat(responseEntity.body?.author).isEqualTo(bookRequest.author)
    }

    @Test
    fun `test getting all books`() {
        // Assume there are some books in the database
        val responseEntity = restTemplate.exchange("/books", HttpMethod.GET, null, object : ParameterizedTypeReference<List<BookResponse>>() {})

        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).isNotEmpty()
    }
}