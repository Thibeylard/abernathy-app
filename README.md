# Abernathy Application
[![shields](https://img.shields.io/badge/version-0.0.1-blue)](https://shields.io/)
[![shields](https://img.shields.io/badge/made_in-java-orange)](https://shields.io/)
[![shields](https://img.shields.io/badge/for-openClassrooms_project-blueviolet)](https://shields.io/)
[![shields](https://img.shields.io/badge/using-spring-deepgreen)](https://shields.io/)

## Description
AbernathyApp a été faite dans le cadre du parcours de formation OpenClassrooms "Développeur d'application Java". Le contexte proposé était celui d'une entreprise AGILE qui développait des solutions informatiques à des organismes de santé. L'objectif était de construire une application avec une architecture en micro-services en partant de zéro. Ce dépôt contient la solution que j'ai proposée.

## Architecture
L'application se décompose en deux parties : D'un côté le client, de l'autre, l'API Gateway, les Edge micro-service et les micro-services du domaine.
Voici un rapide résumé de chacun des modules composants.

- **client** : une simple application Spring MVC, implémentant Feign et Ribbon pour communiquer avec l'API Gateway
- Modules de l'application :
	- **app** : implémente un proxy Zuul via Spring Cloud Netflix, pour rediriger les requêtes à l'aide d'Eureka et de Ribbon vers les microservices adaptés. Contient un filtre personnalisé pour transformer le corps d'une requête x-form-url-encoded vers json, et un autre pour modifier la réponse hal+json des micro-services Spring Data REST
	- **configserver** : Edge micro-service Spring Config qui prend en charge la configuration externe vers le dépôt abernathy-config-repository.
	- **eureka** : Implémentation basique d'Eureka via Spring Cloud Netflix, pour mettre à découvert les micro-services.
	- **patient** : Application Spring Data Rest JPA, avec MySQL. Aucune configuration particulière en dehors de l'exposition d'une projection de l'unique entité Patient. 
	- **patHistory** : Application Spring Data Rest MongoDB. Ajout d'un controller personnalisé non lié à Spring Data Rest, afin d'exposer un endpoint utilisé par **assess**.
	- **assess**: Application Spring Web implémentant Feign et Ribbon pour communiquer avec **patient** et **patHistory**. 

## Docker
Chacun de ces modules a été "dockerisé", ainsi que les bases de données qui sont utilisés. L'application est donc complètement portable. Afin que chaque service puisse démarrer sans encombre, Spring Retry a été implémenté dans chaque micro-service, permettant d'attendre d'une part **configserver**, mais également pour **patient** et **patHistory**, les bases de données.
   
## Tests
Les rapports de tests et la couverture de test peut-être trouvé dans le dossier /reports. Compte tenu du cadre du projet, et des obstacles liés à Docker et Gradle, les tests d'intégration n'ont pas été implémentés.

##Technologies
- Java
- Spring
	- Boot
	- Data
		- Rest
		- MongoDB
		- JPA
	- Cloud
		- Netflix
		- Config
	- Retry
	- Web
	- Hateoas
- Thymeleaf
- Docker
	- Compose

 
 ## Authors
**Thibaut Beylard** \
_thibaut.beylard@lilo.org_