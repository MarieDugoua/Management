import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.forAll

class BookPropertyTest : StringSpec({
    "All books in the returned list should be in the stored list" {
        forAll(Arb.list(BookGenerator.book())) { books ->
            // Suppose you have a function getAllBooks() that retrieves all books.
            val storedBooks = getAllBooks() // Replace with your actual implementation.

            books.all { it in storedBooks }
        }
    }
})

object BookGenerator {
    fun book() = Arb.bind(
        Arb.string(1..50),
        Arb.string(1..50)
    ) { title, author ->
        Book(title, author)
    }
}
