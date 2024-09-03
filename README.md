# mokabank
Projet Architectures Logicielles Java : application bancaire en ligne

Mocha Bank est un établissement bancaire fictif, créé spécialement pour l’unité d’enseignement GLG204 - Architectures logicielles Java (2) du CNAM.

Ce projet ne reflète pas forcément tout à fait les pratiques du secteur bancaire, bien que, je me sois rapprochée de la règlementation DSP2, directive sur les services de paiement. En effet, il se limite à mes connaissances actuelles, car, je n'exerce pas dans ce secteur. Néanmoins, je serais ravie d'en apprendre plus dans ce domaine et serais tout à fait capable de m'adapter aux besoins métier.

Ainsi, certaines opérations bancaires sont présentement traitées de manière simplifiée et devraient certainement être corrigées afin de satisfaire, notamment, aux règles de sécurité. D'ailleurs, c'est également pour des questions de sécurité que le code de l'application complète n'est pas à disposition. 

Enfin, ce projet m'a permis à la fois, d'implémenter des fonctionnalités comprenant diverses contraintes et difficultés techniques, mais aussi, d'avoir un recul critique constructif sur les améliorations à apporter. 

Utilisation :
1. Créer les bases de données avec phpmyadmin:
  - mochabankdb
  - otherbankdb
 
User : root

password :

2. Lancer les applications mochabank et otherbank : gradle bootrun

3. Adresses :
- http://localhost:8181/otherbank/
- http://localhost:8080/mochabank/

Le fichier "Jeu de données" permet de tester l'application. Il contient les identifiants des deux banques, les informations nécessaires pour se connecter en tant qu'employé bancaire ou en tant que client, ainsi que les informations de comptes. 
