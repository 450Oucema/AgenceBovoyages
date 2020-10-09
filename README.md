# Agence Bovoyages
Agence Bovoyages est un projet destiné à la formation au back-end Java J2EE sous
la forme d'un site marchand de voyages. Dans ce cas de figure, il faut pouvoir gérer des destinations, des séjours qui
en dépendent ainsi que les commandes par des utilisateurs.

## Installation

```shell script
docker-compose up
```
&
```shell script
mvn install
```

Il faut également utiliser les scripts .sql situés dans le dossier /docs dans cet ordre :

    - create-script.sql
    - insert-script.sql

Le projet utilise une base de données PostGreSQL, qui nécessite les drivers JDBC 42.0 PostGRE (inclu dans les dépendances)
Il est compatible MySQL il suffit de changer les paramètres de base de données dans :

    - src\main\resources\META-INF\persistence.xml
    - src\main\webapp\META-INF\context.xml

en les adaptants à une base de données MySQL et de posséder les bons drivers

## Notes

Pour l'utilisation de l'ORM Hibernate, les DAO sont sous forme de Repositories, les annotations dans les classes métiers identifient
ce qui est persisté en base de données.

