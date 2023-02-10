# mokabank
Projet Architecture Logicielle Java : application bancaire en ligne

Mocha Bank est un établissement bancaire fictif, créé spécialement pour l’unité d’enseignement GLG204 - Architectures logicielles Java (2) du CNAM.
Ce projet ne reflète pas forcément les pratiques du secteur bancaire, en effet, il se limite à mes connaissances actuelles. En effet, je n'exerce pas dans ce secteur.
Ainsi, certaines opérations bancaires sont présentement traitées de manière simplifiée et devraient certainement être corrigées afin de satisfaire aux règles de sécurité.
Enfin, ce projet m'a permis d'implémenter des fonctionnalités comprenant diverses contraintes et difficultés techniques. 

Utilisation :
1. Créer les bases de données avec phpmyadmin:
  - mochabankdb
  - otherbankdb
User : root
password :

2. Lancer les applications mochabank et otherbank : gradle bootrun

3. Adresses :
http://localhost:8181/otherbank/
http://localhost:8080/mochabank/

Le fichier "Jeu de données" permet de tester l'application. Il contient les identifiants des deux banques, les informations nécessaires pour se connecter en tant qu'employé bancaire ou en tant que client, ainsi que les informations de comptes. 
