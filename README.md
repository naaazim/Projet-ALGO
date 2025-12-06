# 🎨 Projet de Coloration des Graphes

## 📚 Module
**Programmation Avancée & Applications (PAA)**

## 👤 Auteur
Abderahmane Nazim HAMIA  
(L3 Informatique)

---

## 🧠 Description du projet

Ce projet est une application **JavaFX** permettant de **créer, visualiser et colorer des graphes** à l'aide de plusieurs **algorithmes de coloration classiques**.

L'application fournit une **interface graphique interactive** où l'utilisateur peut :
- Ajouter des sommets
- Ajouter des arêtes
- Visualiser le graphe
- Appliquer différents algorithmes de coloration
- Observer le résultat graphiquement

Le projet met l'accent sur :
- la **programmation orientée objet**
- la **séparation logique UI / métier**
- l'utilisation de **JavaFX + FXML**
- l'implémentation et l'étude d'algorithmes de graphes

---

## 🧩 Fonctionnalités principales

- ✅ Création dynamique d'un graphe (jusqu'à 50 sommets)
- ✅ Ajout et suppression de sommets / arêtes
- ✅ Visualisation graphique sur un canevas
- ✅ Coloration des sommets avec mise à jour visuelle
- ✅ Effacement des couleurs sans modifier la structure
- ✅ Messages d'erreur et alertes utilisateur

---

## 🧮 Algorithmes de coloration implémentés

- **2-Coloration**  
  Pour les graphes bipartis

- **Algorithme Glouton**  
  Coloration selon l'ordre naturel des sommets

- **Welsh–Powell**  
  Coloration gloutonne avec tri décroissant des degrés

- **Wigderson**  
  Algorithme destiné aux graphes 3-coloriables  
  (avec détection si le graphe n'est pas 3-coloriable)

---

## 🏗️ Architecture du projet

Le projet suit une architecture **claire et modulaire**, inspirée du modèle MVC :

```
src/main/java
└── up.mi.projet
    ├── algo (algorithmes de coloration)
    ├── model (Graphe, Sommet, Etiquetage, Couleur, exceptions)
    └── gui
        ├── Launcher (point d'entrée JVM)
        ├── Main (classe JavaFX principale)
        └── MainController (contrôleur FXML)

src/main/resources
└── up.mi.projet.gui
    └── MainView.fxml
```

---

## 🖥️ Interface graphique

- Développée avec **JavaFX**
- Mise en page via **FXML**
- Logique traitée dans un **Controller dédié**
- Dessin du graphe via `Canvas` et `GraphicsContext`
- Positionnement automatique des sommets avec un algorithme de forces de répulsion

---

## 🚀 Lancement du projet

### Prérequis
- Java **17**
- Maven
- JavaFX (géré via Maven)

### Lancement avec IntelliJ IDEA
1. Ouvrir le projet comme **projet Maven**
2. Attendre la résolution des dépendances
3. Lancer la classe :
   ```java
   up.mi.projet.gui.Launcher
   ```
   ou via le bouton ▶️

### Lancement via Maven
```bash
mvn javafx:run
```

---

## 🛠️ Technologies utilisées

- Java 17
- JavaFX
- FXML
- Maven
- IntelliJ IDEA

---

## 🎯 Objectifs pédagogiques

- Manipuler des structures de graphes
- Implémenter et comparer des algorithmes de coloration
- Concevoir une application JavaFX structurée
- Appliquer les principes de la programmation orientée objet
- Séparer interface graphique et logique métier

---

## ✅ État du projet

- ✅ Application fonctionnelle
- ✅ Interface graphique stable
- ✅ Algorithmes implémentés
- ✅ Architecture propre et maintenable

---

## ℹ️ Remarque

Ce projet a été réalisé dans un cadre pédagogique, dans le but d'illustrer les concepts étudiés en Programmation Avancée & Applications.
