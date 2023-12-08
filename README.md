# TP - Méthodologies de Tests

## Projet BookManagment d eMarie Dugoua

#### Ajouter une colonne en BDD pour réserver le livre (liquibase)

````
XML
  <changeSet id="add-reserved-column-to-book" author="Management">
      <addColumn tableName="book">
          <column name="reserved" type="BOOLEAN" defaultValueBoolean="false"/>
      </addColumn>
  </changeSet>
````

#### Ajouter:
- Un endpoint REST pour réserverun livre
````
KT


````

- Une fonction dans le usecase pour réserver le livre (et vérifier sa disponibilité)
````
KT


````

- Une fonction dans le port et l'adapter pour écrire cette information.
````
KT


````
