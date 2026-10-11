# TP1 - Modélisation XML et interrogation XPath (University Management)

**Matière :** Architecture SOA et services web - LSI3 - 2025/2026

## Description

Petit projet Java/Maven qui modélise une université en XML (étudiants,
professeurs, cours), valide le document avec un schéma XSD, puis l'interroge
avec XPath.

## Structure du projet

```
├── pom.xml
└── src/main
    ├── java/Main.java                 # validation XSD + requêtes XPath
    └── resources
        ├── university.xml             # données
        └── university.xsd             # schéma de validation
```

## Modélisation

- **student** : `id` (attribut), `name`, `level` (LSI1, LSI2 ou LSI3)
- **professor** : `id` (attribut), `name`
- **course** : `id` (attribut), `title`, `professorId` (référence l'`id` du
  professeur qui enseigne le cours)

Le XSD impose l'ordre des éléments, la présence de l'attribut `id`, et limite
`level` aux valeurs LSI1, LSI2 et LSI3.

## Requêtes XPath

| Objectif | Requête |
|---|---|
| Noms des étudiants | `/university/students/student/name` |
| Titres des cours | `/university/courses/course/title` |
| Étudiants de LSI3 | `/university/students/student[level='LSI3']/name` |

## Prérequis

- JDK 1.8 ou supérieur
- Maven

## Exécution

```
mvn compile exec:java
```

Ou depuis IntelliJ IDEA : ouvrir le projet, puis lancer la classe `Main`.

## Résultat attendu

- Message de validation : `Le fichier university.xml est VALIDE.`
- Liste des noms d'étudiants, des titres de cours et des étudiants de LSI3.

## Test de la validation

Remplacez `LSI3` par `LSI9` dans `university.xml` : le programme affiche alors
que le fichier est invalide et s'arrête.
