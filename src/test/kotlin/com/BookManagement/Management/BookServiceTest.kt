import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import com.BookManagement.Management.model.Book
import com.BookManagement.Management.repository.BookRepository
import com.BookManagement.Management.service.BookService
import com.BookManagement.Management.service.BookServiceImpl

class BookServiceTest {

    private val bookRepository = mockk<BookRepository>()
    private val bookService = BookServiceImpl(bookRepository)

    @Test
    fun `addBook saves the book when title and author are provided`() {
        val book = Book(title = "Test Book", author = "Author")
        every { bookRepository.save(any()) } returns book

        val savedBook = bookService.addBook(book)

        verify(exactly = 1) { bookRepository.save(book) }
        assertEquals(book.title, savedBook.title)
        assertEquals(book.author, savedBook.author)
    }

    @Test
    fun `getAllBooksSortedByTitle returns books sorted by title`() {
        val book1 = Book(title = "Alpha", author = "Author A")
        val book2 = Book(title = "Beta", author = "Author B")
        every { bookRepository.findAllByOrderByTitleAsc() } returns listOf(book1, book2)

        val books = bookService.getAllBooksSortedByTitle()

        assertTrue(books[0].title < books[1].title)
    }
}
