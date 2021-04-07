0 - Création d'un dépot git pour ce projet qui était délivré en zip, on n'a donc pas d'historique mais ce projet sera une base. (Un peux triché car contient tous le projet P5)

1 - Faire une première execution pour avoir un projet fonctionnel, besoin de mettre a jour le graddle wrapper qui buguait completement. J'ai donc mis a jour aussi directement les différentent dépendances dont "com.android.tools.build:gradle:4.1.3" qui est le Android Graddle Plugin

2 - Appropriation rapide du code général et lecture des commentaires pour comprendre son fonctionnement

3 - Passage de Android Support a AndroiX qui est beaucoup plus clair a l'utilisation et plus moderne. 

Passage aux View Bindings pour acceder facilement aux differentes View type safe et null safe, cela permet aussi d'avoir beaucoup moins de code a écrire.

J'en ai profiter pour passer a java 8 aussi pour profiter des Lambda

4 - Ajout du minimify et obfuscuration dans les config de compilation

5 - Generation d'un keystore pour avoir nos clé de signature

6 - Création d'un modèle relationel pour notre futur base de données

7 - Ajout de ROOM pour gerer notre DB SQLite
La RoomDatabase va avoir differentes Entity (Task et Projet ici) et va utiliser des DAO pour communiquer avec la base de données sans passer par du code SQL a chaque requette mais par du bon vieux POO.

8 - On a des repository qui permette d'acceder a la database et aux DAO facilement sans s'occuper de ce qu'il ce passe derière, nos répositori dans cet example n'accede que a ROOM mais ils pourraient tres bien aussi trouver des source en ligne, dans des fichier de l'utilisateur etc...

9 - On a le Viewmodel qui récupère la data nécéssaire au fonctionnement de l'UI et récupère la data des DB via les LiveData qui permet ensuite a l'UI d'observer des changement dans la data en étant notifier, ce qui permet de mettre a jour l'UI directement. 

C'est tellement bien fait que même changer les valeur de la DB via le gestionnaire de DB sqlite d'android studio que sa change sur l'ui instant

## Test Unitaire

10 - Ensuite on a les Tests pour facilement verifier qu'on ne casse rien dans notre application. 

Les test unitaire on a gardé ceux originaux a quelques details prêt comme par example le constructor qui a changé et nous garantie que même avec nos changement dans le model Task et Project pour en faire des ENtity de room que sa fonctionne encore.

11 - Les teste d'integration on été beaucoup plus changé on test les differentes partie de l'application dans un premier temps en ajoutant, supprimant des task et en erifiant le nombre et si sa a été bien ajouter.

On a une deuxieme classe de test qui elle test d'ajouter des project ou task a la db et ensuite de les get pour voir si le get fonctionne (et donc a bien été ajouté a la liste)

12 - Enfin pour finir on a le Diagramme de Classe, commencé sur StarUML mais finis avec Visual Paradigm
et le use case diagram qui a été fait avec draw.io