# 🎨 Coloration de Graphes — JavaFX

Application pédagogique pour créer, visualiser et colorer des graphes via une interface JavaFX soignée (FXML + CSS), sans mélanger logique métier et UI.

## 🚀 Démarrage rapide
### Prérequis
- JDK 17
- Maven (gère JavaFX via le plugin)

### Lancer l'application
- IDE : exécuter `up.mi.projet.gui.Launcher`.
- Maven CLI :
  ```bash
  mvn javafx:run
  ```

### Exécuter les tests
```bash
mvn test
```

## 🧩 Fonctionnalités
- Création dynamique d’un graphe jusqu’à 50 sommets.
- Ajout d’arêtes via spinners de sélection.
- Visualisation sur canevas avec disposition circulaire + répulsion pour éviter les chevauchements.
- Coloration interactive avec rafraîchissement instantané.
- Effacement des couleurs sans toucher à la structure.
- Alertes claires (erreurs, avertissements, à propos).

## 🧮 Algorithmes disponibles
- **2-Coloration** (biparti).
- **Glouton** (ordre naturel).
- **Welsh–Powell** (ordre décroissant des degrés).
- **Wigderson** (graphes 3-coloriables, alerte si non coloriable).

## 🖥️ Interface & style
- Mise en page en FXML (`MainView.fxml`) et contrôleur dédié (`MainController`).
- Canvas pour le dessin des sommets/arêtes (couleurs mappées depuis l’énumération métier).
- Thème moderne appliqué via `style.css` (dégradé sombre, cartes latérales, boutons différenciés).

## 🏗️ Organisation du code
```
src/main/java/up/mi/projet
├── AlgorithmesUtilitaires.java
├── Couleur.java
├── Etiquetage.java
├── Graphe.java
├── NonBipartiException.java
├── Sommet.java
├── TailleInsuffisanteException.java
├── TestGraphe.java
└── gui
    ├── Launcher.java          (point d’entrée JVM)
    ├── Main.java              (initialisation JavaFX + CSS)
    └── MainController.java    (logique UI)

src/main/resources/up/mi/projet/gui
├── MainView.fxml
└── style.css

src/test/java/up/mi/projet
└── AlgorithmesUtilitairesTest.java
```

## 🧭 Guide d’utilisation rapide
1. Ajouter des sommets (`Ajouter un Sommet`).
2. Ajouter des arêtes en sélectionnant les indices dans les spinners, puis `Ajouter`.
3. Choisir un algorithme dans la liste et cliquer sur `Exécuter`.
4. Utiliser `Effacer les couleurs` pour tester un autre algorithme sans reconstruire le graphe.
5. Menu `Fichier > Nouveau Graphe` pour repartir de zéro.

## 🎯 Objectifs pédagogiques
- Manipuler des structures de graphes et leurs colorations.
- Comparer des algorithmes classiques.
- Illustrer une séparation nette UI / métier avec JavaFX + FXML.

## 👤 Auteur
Abderahmane Nazim HAMIA — L3 Informatique (Programmation Avancée & Applications).
