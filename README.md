JIRAUD Loïc; 22108867; Info2
CHENAL Louka; 22114698 ; Info2

# Test

afin de tester le programme, il faut se placer à la racine du projet et lancer la commande suivante :

```bash
./gradlew test
```
Problème rencontré : 
    Les differents tests qui on besoin d'une initialisation d'un Snake ne fonctionne pas. La classe ne trouve pas comment initialiser le Snake. Nous avons preferé ne pas supprimer les tests et laissé tel quel.

# Exécution

afin d'exécuter le programme, il faut se placer à la racine du projet et lancer la commande suivante :

```bash
./gradlew run
```

Une fois le programme lancé.

On a deux choix, soit en ligne (implémentation Server-Client non parfait qui sera présenté en soutenance) soit en local (implémentation Local).

Pour le mode local :
    
    - On peut de jouer avec le mode 'Bouclier'
        -> Quand on lance en 'Bouclier', on possède un bouclier qui nous des collisions 1 fois. Une fois le bouclier utilisé, pendant 5 secondes, on est immortel puis on redevient vulnérable.
    - On peut choisir le skin de son personnage
    - On peut choisir la couleur de son Snake

Pour le mode en ligne :

    On doit choisir entre créer une partie ou rejoindre une partie.

    - Si on crée une partie : On doit taper un port. Une fois la partie créée, on joue dans une partie en attente qu'un joueur rejoigne la partie.

    - Si on rejoins une partie : On doit taper l'adresse IP de la partie et le port de la partie. Une fois la partie rejointe, on joue dans le meme serveur que les joueurs déjà présents.

# Gitlab

https://gaufre.informatique.univ-paris-diderot.fr/jiraud/projet-cpoo-jiraud-chenal

(Nous avons mis Maintener Aldric Degorre)



