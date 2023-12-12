# TP - Méthodologies de Tests

## Projet BookManagment de Marie Dugoua

#### Ajouter une colonne en BDD pour réserver le livre (liquibase)

````XML
  <changeSet id="add-reserved-column-to-book" author="Management">
      <addColumn tableName="book">
          <column name="reserved" type="BOOLEAN" defaultValueBoolean="false"/>
      </addColumn>
  </changeSet>
````

#### Ajouter:
- Un endpoint REST pour réserver un livre
````KT
@CrossOrigin
@PostMapping("/{bookId}/reserve")
@ResponseStatus(HttpStatus.OK)
fun reserveBook(@PathVariable bookId: Int) {
    bookUseCase.reservedBook(bookId)
}

````

- Une fonction dans le usecase pour réserver le livre (et vérifier sa disponibilité)
````KT
fun reserveBook(bookId: Int) {
    bookPort.updateBookReservation(bookId, true)
}
````

- Une fonction dans le port et l'adapter pour écrire cette information.
````KT
interface BookPort {
    // ...
    fun updateBookReservation(bookId: Int, reserved: Boolean)
}

class BookDAO(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : BookPort {
    // ...
    override fun updateBookReservation(bookId: Int, reserved: Boolean) {
        val params = MapSqlParameterSource()
        params.addValue("id", bookId)
        params.addValue("reserved", reserved)
        namedParameterJdbcTemplate.update(
            "UPDATE book SET reserved = :reserved WHERE id = :bookId",
            params
        )
    }
}
````

#### Modifier le endpoint REST de récupération de liste pour indiquer l'information sur la disponibilité du livre.
````KT
data class BookDTO(/* ... */ val reserved: Boolean)

data class BookDTO(/* .... */ val reserved: Boolean) {
    fun toDomain(): Book {
        return Book(
            // ...
            reserved = false
        )
    }
}

fun Book.toDto() = BookDTO(
    /* .... */
    reserved = false
)
````

#### Implémenter les règles suivantes :
- Un livre déjà réservé ne peux pas être réservé à nouveau
````KT
fun reserveBook(bookId: Int) {
    val book = bookPort.getBookById(bookId)
    if (book.reserved) throw IllegalStateException("Book already reserved")
    bookPort.updateBookReservation(bookId, true)
}
````

#### Développer des Tests Unitaires :

- Créez une classe de test unitaire spécifique pour la fonctionnalité de réservation de livre.
- Écrivez des tests pour vérifier différents scénarios, par exemple :
  - Réserver un livre qui n'est pas encore réservé.
    ````kt
    @Test
    fun `reserveBook should call updateBookReservation with true`() {
        val bookId = 1
        val book = Book("Les Misérables", "Victor Hugo", false)
        every { bookPort.getBookById(bookId) } returns book
        justRun { bookPort.updateBookReservation(bookId, true) }
    
        bookUseCase.reserveBook(bookId)
    
        verify(exactly = 1) { bookPort.updateBookReservation(bookId, true) }
    }
    ````
  - Réserver un livre qui est déjà réservé (pour tester la règle d'interdiction).
    ````kt
    @Test
    fun `reserveBook should throw IllegalStateException when book is already reserved`() {
        val bookId = 1
        val book = Book("Les Misérables", "Victor Hugo", true)
        every { bookPort.getBookById(bookId) } returns book

        assertThrows<IllegalStateException> { bookUseCase.reserveBook(bookId) }
    }
    ````

#### Développer des Tests d'Intégration :
- Créez des tests d'intégration spécifiques à la réservation de livre. Ces tests devraient simuler des requêtes REST pour réserver un livre, en utilisant par exemple RestTemplate ou un client HTTP.

#### Validez la Fonctionnalité :
- Testez manuellement la réservation de livre en utilisant l'interface utilisateur ou les requêtes REST pour vous assurer que tout fonctionne comme prévu.