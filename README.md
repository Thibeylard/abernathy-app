[![shields](https://img.shields.io/badge/project%20status-validated-deepgreen)](https://shields.io/)
[![shields](https://img.shields.io/badge/made%20with-java-orange)](https://shields.io/)
[![shields](https://img.shields.io/badge/powered%20by-spring-green)](https://shields.io/)
____________________

> Ce README est basé sur les conclusions évoquées dans la présentation réalisée à la fin du projet.

# Développez une solution en microservices pour votre client

## Dans le cadre de la formation OpenClassrooms "Développeur d'application Java"

### Objectif du projet
À partir de zéro, développer une application avec une architecture micro-services en trois sprints selon les principes AGILE.

### Progression
Beaucoup de nouvelles technologies ont été abordées dans ce projet, notamment les extensions de Spring suivants : Cloud Netflix, Cloud Config, Data MongoDB, Retry, HateOAS. Il m'a permis de mieux découvrir, comprendre et appliquer la méthode AGILE en suivant et documentant les sprints, en mettant à jour le Kanban dédié au projet. Enfin bien sûr, l'architecture micro-service et ses bonnes pratiques.

### Réalisation
Dans un délai d'un mois, j'ai développé une application fonctionnelle, non exempte de défauts, mais riches de plusieurs initiatives qui n'étaient pas requises pour la validation du projet.
* Utilisation des Edge microservices Eureka, Zuul, Spring Cloud Config, Zipkin, Feign, Ribbon
* Création d'un module client back-end for front-end séparé de l'application
* Utilisation de Spring Retry pour gérer le démarrage automatisé avec Docker Compose
	* Sur tous les modules liés à Spring Cloud Config
	* Sur le micro-service Patient pour attendre MySQL
* Filtres personnalisés sur Zuul pour traiter les requêtes et réponses depuis et vers Spring Data Rest
* Configuration de Docker compose avec tous les containers nécessaire au projet, dès lors portable


### Axes d'améliorations
De nombreux TODOs ont été laissé au fur et à mesure du développement afin de garder une trace de toutes les améliorations à faire sur le code.
On peut citer parmi les plus importantes :
* Un peaufinage de la gestion des exceptions
* Une amélioration du filtre de reformatage des réponses Hal+Json
* Plus de tests d'intégration par exemple à l'aide de la librairie TestContainers

### Rapports de tests
Pour consulter les rapports de tests, merci de vous rendre au commit précédent : Ils ont été retiré de la branche principale pour ne pas fausser les statistiques de langages.
