# Qualité logiciel 2 ![Build](https://travis-ci.org/steven9neuf/QL2.svg?branch=master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/7d7efccc7ca149758c3315a745449bbc)](https://www.codacy.com/app/steven9neuf/QL2?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=steven9neuf/QL2&amp;utm_campaign=Badge_Grade)

## Application des bonnes pratiques de qualité de code

## CHARPENTIER Emile | RAHIER Lucas | WONG Steven

# RUN

- Cloner le répertoire sur votre machine 'git clone https://github.com/steven9neuf/QL2'
- Placez-vous à la racine du projet 'cd QL2'
- Lancer l'exécutable 'java -Djava.library.path=./lib/natives -Dlog4j.configurationFile=src/log4j.properties -jar Storm.jar'



# I. Présentation de l’équipe

Pour ce projet de Qualité Logicielle 2, nous avons décidé de former une équipe de trois personnes : Émile Charpentier, Lucas Rahier et Steven Wong. Nous sommes tous les trois issus du programme Prep’Ac de l’ECE Paris. Nous avons choisi pour notre dernière année, différentes options d’approfondissement. Émile a souhaité approfondir ses connaissances en sécurité, Lucas a préféré découvrir le monde du design tandis que Steven s’est lancé dans l’apprentissage des nouvelles techno WEB.
Nous nous sommes souvent retrouvés ensemble pour la réalisation de divers projets. Nous avons travaillé sur des projets en Java, en C# ou encore en C. Nous avons décidé de nous remettre tous les trois car nous connaissons les différentes forces de chacun et savons surtout qu’aucun de nous ne rechigne à la tâche. C’est effectivement agréable de voir que dans un groupe, chaque personne se sent investi et produit le meilleur travail possible.


# II. Contexte

Nous sommes donc réunis une nouvelles fois pour traiter des design patterns. Pour cette dernière année à l’ECE Paris, nous avons la chance d’avoir des cours de qualité logicielle. Là où la première partie des cours se centrait sur les tests unitaires, vraiment importants mais sommes toute un peu rébarbatifs, la deuxième partie, elle, se concentre sur les bonnes pratiques et usages en termes de qualité de code. Cela nous forge une certaine connaissance et nous donnes des réflexes pour que dans nos projets actuels ou futurs, nous mettions en places des dynamiques d’écriture intelligentes, bien construites et faciles à comprendre et reprendre.
Pour ce projet, nous avons décidé de reprendre un de nos ancien projets fait en Prep’Ac. Cela consistait en la réalisation d’un space scrolling shooter en langage C. Comme nous ne nous sentions pas à l’aise de mettre en place des design patterns en C, nous avons pris la décision de recoder l’entièreté du jeu en Java. Nous espérons qu’au-delà de l’exercice d’implémentation des différents design patterns, vous apprécierez tester notre jeu.


# III. Présentation du code

Issus tous les trois de la filière Prep’Ac de l’ECE, nous avons réalisé nos début dans le monde de la programmation dans la même classe. Par la même occasion, nous avons réalisé notre premier projet informatique en langage C. C’est pourquoi, nous avons décidé de souffler la fine couche de poussière accumulée sur celui-ci afin de lui faire peau neuve dans un tout nouveau langage, le Java. 


Nous sommes reparti d’un “Hello world” pour arriver à ce résultat. Notre jeu dispose d’une difficulté qui suit le niveau du joueur. Celui-ci pourra naviguer avec les flèches directionnelles, tirer avec la touche espace ou encore effectuer un bond avec la touche B. Sa capacité de bond dispose d’un délai de récupération qui diminue en fonction de son niveau. Afin de monter en niveau, le joueur pourra récupérer des munitions, des vies ou encore tuer les ennemies qui s'opposeraient à lui. 
	Pour cela, nous avons fait appel à la librairie graphique Slick2D. Cette librairie nous permet d’embrasser une architecture de code simple malgré les difficultés que peuvent apporter l’aspect graphique. Effectivement, la librairie s’occupe de tous les détails techniques, et nous offre en surface trois méthode dans lesquelles nous pourrons faire appel à notre code. La fonction init, qui permet d’initialiser tout ce qui est nécessaire pour démarrer le jeu, la fonction update, qui est appelé selon un intervalle de temps que nous définissons dans le main et qui s’occupera de la logique du jeu, puis la fonction render qui s’occupera d’afficher à l’écran les éléments graphiques.


# IV. Les pratiques mises en places

## A. Outil de versionning

### 1. Description

Nous avons mis en place pour ce projet un outil de versionning avec git. C’est un logiciel de gestion de version décentralisé, permettant l’échange et la sauvegarde de fichiers.

Cet outil récupère les fichiers du dossier source pour les mettre dans le cloud. Ensuite, chaque utilisateur ayant l’accès au repository git peut récupérer tout le contenu de ce dossier pour créer un clone et ensuite effectuer des modifications dessus avec des commits.

Lorsqu’un utilisateur effectue des modifications sur le projet, il publie un commit, qui sera ensuite appliqué et stocké dans le repository. Ainsi, tous les utilisateurs pourront avoir la même version pour travailler. Et lorsque plusieurs commits modifient le même fichier, git avertit les utilisateurs et leur propose l'agrégation des modifications.


### 2. Mise en place

Pour mettre en place cet outil de versionning, nous avons créé un projet sur github, une plateforme libre sur internet utilisant le logiciel git. 

[Github](https://github.com/steven9neuf/QL2)

Une fois le repository créé, il suffit de le partager avec les membres de l’équipe pour que ceux-ci aient accès au code et puissent travailler dessus en parallèle.



### 3. Avantages et inconvénients

L’utilisation de git apporte de nombreux avantages pour la gestion du projet:
- Il permet le partage à distance facilité du projet entre les différents membres.
- Il sauvegarde chaque modification et crée un arbre affichant les différents commits.
- Il synchronise les dossiers de chacun pour que les modifications apportés par d’autres soient appliquées sur chaque dossier localement.


Mais il apporte aussi quelques ralentissements:
- La mise en place de git peut être ardue pour les premiers projets.
- Les merges de branches peuvent nécessiter l’intervention des membres pour corriger les erreurs.

Dans sa globalité, git est un outil quasiment indispensable dans tout projet informatique réunissant une équipe, et même pour les projets personnels grâce au versionning des fichiers.


## B. Mise en place de logs

### 1. Description

Un système de logging permet d’enregistrer des messages de débogage, d’informations, d’erreur ou d’avertissement. Il permet de sauvegarder dans un fichier le fonctionnement de l’application. Ainsi, si un problème survient lors de l’exécution de l’application, les logs permettent d’indiquer l’emplacement exacte où le programme cesse de fonctionner.


### 2. Mise en place

Dans le cadre de notre projet Java, nous avons choisi de d’utiliser la librairie Log4J pour mettre en place notre système de log. Le fichier de configuration log4j.properties permet de configurer les logs puis nous en indiquons le chemin à travers les arguments.


### 3. Avantages et inconvénients

L’avantage de ce système de log permet de placer des messages de log n’importe où dans le code. De plus, il permet également de sauvegarder les logs dans un fichier, ou bien de les afficher directement dans la console. Cependant, la mise en place de cette librairie demande un peu de temps pour apprendre à configurer correctement l’outil.


## C. Qualité de code

### 1. Description

Les outils de qualité de code permettent de vérifier les bonnes pratiques à mettre oeuvre dans la rédaction de code. Ces outils nous permettent de relever les mauvais aspects de notre code. De plus, certains outils nous permettent de corriger automatiquement ces mauvaises pratiques.


### 2. Mise en place

Pour cela, nous avons tout d’abord utiliser le plugin JDeodorant du logiciel Eclipse afin de corriger les “Bad smells”. Cet outil permet de détecter les méthodes trop longues, les possibilités de factoriser les classes etc. Ensuite, avec notre répertoire github, nous avons relier l’outil en ligne “Codacy”. Cet outil nous permet une revue complète de notre code à chacun de nos commit. Il nous fournit des informations précises sur les mauvaises pratiques de notre code et il nous attribue une note de qualité de code.

[Codacy](https://www.codacy.com/app/steven9neuf/QL2/dashboard) 


### 3. Avantages et inconvénients

L’avantage de ces outils est qu’ils nous permettent de produire des codes de qualité, uniformiser avec les normes des développeurs. Cependant, il ne faut pas se fier totalement aux outils, surtout ceux qui corrigent automatiquement, car en fonction du contexte, il peut avoir une mauvaise interprétation de notre code.


## D. Mise en place du test automatique de build

### 1. Description

Les outils de test automatique de build nous permettent de vérifier que chacun de nos commit produit un code capable d’être builder. Cela nous permet d’avoir une manière sûre de vérifier une fois de plus que le code ne comporte pas d’erreur.


### 2. Mise en place

Pour notre projet, nous avons utilisé l’outil en ligne Travis CI. C’est un outil qui effectue les tests en fonction du fichier de configuration .travis.yml que nous ajoutons à la racine de notre répertoire github. L’outil effectuera les tests automatiquement et permet de nous prévenir si le dernier commit est concluant ou non. 

[Travis  CI](https://travis-ci.org/steven9neuf/QL2/jobs/313235346)


### 3. Avantages et inconvénients

L’avantage de cet outil est qu’il nécessite seulement une configuration au tout début. Ensuite, l’outil réalisera les tests en autonomie, et pourra même nous envoyer des mails afin de nous prévenir de l’état de notre dernier commit. L’inconvénient, comme tout autre outil, est qu’il nécessite un minimum de connaissance et de temps pour configurer l’outil la première fois.


## E. Utilisation de Design patterns

### 1. Composite

Le design pattern Composite utilise le principe de l’héritage. Il permet à des classes qui peuvent ne rien avoir en commun d’autre que une ou deux méthodes. Ainsi, on crée un classe Component qui va définir une seule méthode partagée par plusieurs classe fille qui en hériteront. Ces autres classes sont appelées des Composites.
Concernant notre projet, nous avons codé un peu tête baissée. De ce fait nous sommes arrivés à un code quasi fini lorsqu’on a pris connaissance de ce design pattern. Nous avons dans un premier temps regardé nos différentes classes pour voir si certaines partageaient le même comportement. Suite à des problème d’export de notre solution, nous n’avons pas pu pousser notre recherche plus loin, souhaitant privilégier l’élaboration de ce rapport.


### 2. Decorator

Le principe du Decorator pattern développe encore le principe de l’héritage. Il permet d’utiliser cette technique de façon un peu plus souple. Cela permet d’avoir des classes qui vont hériter d’autres classes qui seront cataloguée comme des options de décoration. par exemple une classe abstraite dont va hériter d’autre classes. Ensuite on crée d’autre classe abstraites qui seront des options pour aller plus loin dans la définition de l’objet rechercher. Prenons comme exemple pour notre projet, un vaisseau spatial. Puis, on pourrait définir un vaisseau qui aurait l’option coeur nucléaire, ou panneau solaire. Ainsi, on a la classe abstraite principale vaisseau spatial, puis on aurait les trois autres classes. Une classe “VaisseauSpatialActif” qui hérite de VaisseauSpatial, puis on aurait VaisseauSpatialNucleaire et VaisseauSpatialSolaire qui héritent de VaisseauSpatialActif. Ainsi ce seraient des objets Vaisseau mais avec leur propres particularités.
Malheureusement, là encore nous n’avons pas eu le temps de mettre cela en place, mais c’était une des choses que nous voulions implémenter. En effet, dans notre jeu codé en C, nous avions déjà la possibilité de changer de classe de vaisseau et nous aurions aimé retrouver cette fonctionnalité.


### 3. Command

Le pattern Command est une méthode permettant d’avoir une classe qui a pour rôle de commander, diriger la direction que prend le code en fonction de divers paramètres. Nous n’avons malheureusement pas eu le temps nécessaire pour mettre cela en place, mais nous envisageons d’utiliser ce design pattern afin de s’occuper du système de “highscore”. Effectivement, nous enregistrons le meilleur score dans un simple fichier texte, et la communication entre le logiciel et le système de fichier peut être différent selon le système d’exploitation où l’on exécute le code. C’est pourquoi, en fonction du système d’exploitation, la classe commander pourra rediriger le code vers la bonne manière d’accéder au système de fichier afin de récupérer et sauvegarder les scores.
	

### 4. State

Le design pattern State permet de réaliser des fonctions dépendamment de l’état d’un objet. Cela permet d’obtenir un code plus structuré et une meilleur agencement entre les différentes classes. Pour cela, notre code prend en partie en charge ce pattern à l’échelle de la gestion du niveau du joueur. Effectivement, lorsqu’un joueur monte d’un niveau, il débloque des fonctionnalités et des capacités qu’il ne possédait pas au niveau précédent. Ces fonctionnalités sont imbriquées au sein de la classe fille du niveau correspondant. Ainsi, à chaque montée de niveau, il suffit de modifier l’état du joueur en lui attribuant l’objet state approprier à son niveau. Cela évite de devoir vérifier à chaque fonction logique le niveau du joueur dans des conditions switch qui peuvent devenir très long.


# 5. Conclusion

Nous avons également essayé de mettre en place le système de test unitaire de Java, Junit. Cependant, la configuration de notre projet, avec l’utilisation d’une librairie graphique Slick2D rend l’opération quelque peu compliqué à tester. Ensuite, l’organisation que nous offre cette librairie nous permet de séparer la vue du modèle et du controller. Cela pourrait s’apparenter à une organisation en MVC, malgré le fait que cela ne soit pas réalisé de manière aussi distinct que si nous avions séparé en plusieurs packages.
	A travers ce travail de qualité de code, nous avons pu apprendre qu’il existait de nombreuses manière d’améliorer la qualité du code que l’on produit. Il ne s’agit pas uniquement de test unitaire pour vérifier que notre code répond bien au cahier des charges, mais il est également question de bonne pratiques dans rédaction de code afin de permettre une bonne lisibilité, maintenance et réutilisabilité de notre code par d’autre développeurs.



