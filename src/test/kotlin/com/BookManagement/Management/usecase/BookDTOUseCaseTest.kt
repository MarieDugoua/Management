package com.BookManagement.Management.usecase

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEqualTo
import com.BookManagement.Management.domain.model.Book
import com.BookManagement.Management.domain.port.BookPort
import com.BookManagement.Management.domain.usecase.BookUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import net.jqwik.api.Arbitrary
import net.jqwik.api.Combinators.combine
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import net.jqwik.api.lifecycle.BeforeProperty
import net.jqwik.kotlin.api.any
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
@ExtendWith(MockKExtension::class)
class BookDTOUseCaseTest {

    @InjectMockKs
    private lateinit var bookUseCase: BookUseCase
    @MockK
    private lateinit var bookPort: BookPort
    private val bookList = mutableListOf<Book>()

    @Test
    fun `get all books should returns all books sorted by name`() {
        // Arrange
        every { bookPort.getAllBooks() } returns listOf(
            Book("Les Misérables", "Victor Hugo", false),
            Book("Hamlet", "William Shakespeare", false)
        )

        // Act
        val res = bookUseCase.getAllBooks()

        // Assert
        assertThat(res).containsExactly(
            Book("Hamlet", "William Shakespeare", false),
            Book("Les Misérables", "Victor Hugo", false)
        )
    }

    @Test
    fun `add book`() {
        justRun { bookPort.createBook(any()) }

        val book = Book("Les Misérables", "Victor Hugo", false)

        bookUseCase.addBook(book)

        verify(exactly = 1) { bookPort.createBook(book) }
    }

    @Test
    fun `reserveBook a new book should be false`() {
        justRun { bookPort.createBook(any())}

        val book = Book("De Verre et de cendre", "Reina dolce", false)

        bookUseCase.addBook(book)

        // Assert
        val lastBook = bookList.last()
        assertThat(lastBook.reserved).isEqualTo(false)
    }

    @Test
    fun `reserveBook should call updateBookReservation with true`() {
        val bookId = 1
        val book = Book("Les Misérables", "Victor Hugo", false)
        every { bookPort.getBookById(bookId) } returns book
        justRun { bookPort.updateBookReservation(bookId, true) }

        bookUseCase.reserveBook(bookId)

        verify(exactly = 1) { bookPort.updateBookReservation(bookId, true) }
    }

    @Test
    fun `reserveBook should throw IllegalStateException when book is already reserved`() {
        val bookId = 1
        val book = Book("Les Misérables", "Victor Hugo", true)
        every { bookPort.getBookById(bookId) } returns book

        assertThrows<IllegalStateException> { bookUseCase.reserveBook(bookId) }
    }

    @BeforeProperty
    fun initMocks() {
        MockKAnnotations.init(this)
    }

    @Property
    fun `get all all book should have all books stored in db`(
        @ForAll("bookGenerator") books: List<Book>
    ) {
        every { bookPort.getAllBooks() } returns books

        val res = bookUseCase.getAllBooks()

        assertThat(res).containsExactlyInAnyOrder(*books.toTypedArray())
    }

    @Provide
    fun bookGenerator(): Arbitrary<List<Book>> {
        return combine(
            String.any().ofMinLength(1).ofMaxLength(20).alpha(),
            String.any().ofMinLength(1).ofMaxLength(20).alpha(), Boolean.any()).`as` { title: String, author: String, reserved: Boolean ->
            Book(title, author, reserved)
        }.list().uniqueElements().ofSize(10)
    }
}