CS(11)

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/t19xNtmg)



**Rapport de Réalisation du TP Observer:

Implémentation des Classes Demandées:

J'ai développé la classe Horloge qui affiche l'heure actuelle sur la console à chaque seconde écoulée, en suivant fidèlement le pattern Observer. J'ai également créé la classe CompteARebours qui décompte le temps à partir d'une valeur initiale et se désinscrit automatiquement du service lorsqu'elle atteint zéro.

Tests de Fonctionnement:

J'ai vérifié le bon fonctionnement des deux classes en instanciant une horloge normale et un compte à rebours de cinq secondes. Les deux composants ont parfaitement réagi aux notifications du service de temps, affichant les informations attendues dans la console.

Observation Sélective des Propriétés:

Pour répondre à l'exigence d'observer uniquement certaines propriétés, j'ai utilisé le mécanisme de filtrage dans la méthode propertyChange, permettant à chaque observateur de ne réagir qu'aux changements spécifiques qui l'intéressent, comme les modifications des secondes uniquement.

Tests avec Multiples Instances:

J'ai créé dix instances de CompteARebours avec des valeurs initiales aléatoires comprises entre cinq et quinze secondes. Durant l'exécution, j'ai constaté le problème de concurrence attendu lorsque plusieurs observateurs tentent de se désinscrire simultanément.

Résolution des Problèmes de Concurrence:

J'ai entièrement repensé la gestion des observateurs en intégrant la classe PropertyChangeSupport, qui offre une gestion thread-safe des listeners. Cette refonte a complètement éliminé les exceptions de modification concurrente et garanti un comportement stable même avec de nombreux observateurs.

Interface Graphique Bonus:

En plus des exigences de base, j'ai développé deux interfaces graphiques fonctionnelles : une horloge numérique moderne et une horloge analogique réaliste avec aiguilles animées. Ces deux interfaces utilisent le même système d'observation et se mettent à jour en temps réel.