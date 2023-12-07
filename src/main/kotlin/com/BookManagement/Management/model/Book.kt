package com.BookManagement.Management.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id

@Entity
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    title: String,
    author: String
) {
    val title: String = title
        get() {
            require(field.isNotBlank()) { "Title must not be empty" }
            return field
        }

    val author: String = author
        get() {
            require(field.isNotBlank()) { "Author must not be empty" }
            return field
        }
}


/*

TP : Gestion de livre : Partie métier (4/6)

Définir la CI/CD pour GitHub Actions : https://github.com/marketplace/actions/gradle-build-action

Définir les étapes suivantes :
Build de l'application
Lancement des tests
Publier les résultats des tests dans le pipeline (https://github.com/EnricoMi/publish-unit-test-result-action)


TP : Gestion de livre : Partie métier (5/6)

Mise en place des tests de couvertures : https://docs.gradle.org/current/userguide/jacoco_plugin.html

Ajouter l'étape dans la CI/CD et publier le rapport dans le pipeline : https://github.com/PavanMudigonda/jacoco-reporter


TP : Gestion de livre : Partie métier (6/6)

Mise en place des tests de mutation : https://gradle-pitest-plugin.solidsoft.info

Ajout de l'étape dans la CI/CD
*/