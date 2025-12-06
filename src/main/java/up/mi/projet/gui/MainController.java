package up.mi.projet.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import up.mi.projet.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur pour l'interface graphique principale de l'application de coloration de graphe.
 * Cette classe gère toutes les interactions utilisateur via JavaFX, incluant l'ajout de sommets et d'arêtes,
 * l'exécution d'algorithmes de coloration, et le rendu visuel du graphe sur un canevas.
 * 
 * Le contrôleur utilise un algorithme de disposition circulaire combiné avec des forces de répulsion
 * pour positionner automatiquement les sommets de manière esthétique et éviter les chevauchements.
 * 
 * Les algorithmes de coloration supportés incluent :
 * - 2-Coloration (pour les graphes bipartis)
 * - Glouton (ordre naturel)
 * - Welsh-Powell (ordre décroissant de degré)
 * - Wigderson (pour les graphes 3-coloriables)
 */
public class MainController {

    @FXML
    private Canvas canevas;
    @FXML
    private Spinner<Integer> deSpinner;
    @FXML
    private Spinner<Integer> aSpinner;
    @FXML
    private ComboBox<String> algorithmeComboBox;

    private Graphe graphe;
    private Etiquetage etiquetage;
    private final Map<Integer, Point> positionsSommets = new HashMap<>();
    private int compteurSommets = 0;

    /**
     * Initialise le contrôleur après le chargement du fichier FXML.
     * Cette méthode configure les éléments de l'interface graphique, notamment :
     * - Remplit la ComboBox avec les algorithmes de coloration disponibles
     * - Initialise un graphe vide avec une capacité maximale de 50 sommets
     * - Crée un étiquetage vide pour stocker les couleurs des sommets
     * - Désactive les spinners de sélection d'arêtes jusqu'à ce que des sommets soient ajoutés
     * 
     * IMPORTANT : Le nom 'initialize' est une convention JavaFX et ne doit pas être modifié.
     * Cette méthode est appelée automatiquement par le framework après l'injection des dépendances FXML.
     */
    @FXML
    public void initialize() {
        algorithmeComboBox.setItems(FXCollections.observableArrayList(
                "2-Coloration",
                "Glouton",
                "Welsh-Powell",
                "Wigderson"));
        graphe = new Graphe(50);
        etiquetage = new Etiquetage(50);
        deSpinner.setDisable(true);
        aSpinner.setDisable(true);
    }

    /**
     * Gère l'action du menu "Nouveau Graphe".
     * Réinitialise complètement l'application en créant un nouveau graphe vide et en effaçant
     * toutes les données existantes, y compris :
     * - Le graphe et son étiquetage
     * - Les positions des sommets sur le canevas
     * - Le compteur de sommets
     * - L'état des spinners de sélection d'arêtes
     * - Le contenu visuel du canevas
     */
    @FXML
    private void gererNouveauGraphe() {
        graphe = new Graphe(50);
        etiquetage = new Etiquetage(50);
        positionsSommets.clear();
        compteurSommets = 0;
        deSpinner.setDisable(true);
        aSpinner.setDisable(true);
        effacerCanevas();
    }

    /**
     * Gère l'action du bouton "Ajouter Sommet".
     * Ajoute un nouveau sommet au graphe et le positionne visuellement sur le canevas.
     * 
     * Le positionnement utilise une disposition circulaire initiale, où les sommets sont placés
     * sur un cercle centré dans le canevas. Ensuite, un algorithme de force dirigée est appliqué
     * pour espacer les sommets et éviter les chevauchements.
     * 
     * Cette méthode effectue également les actions suivantes :
     * - Vérifie que la limite de 50 sommets n'est pas dépassée
     * - Met à jour l'étiquetage pour inclure le nouveau sommet
     * - Active et configure les spinners de sélection d'arêtes
     * - Redessine le graphe complet
     * 
     * En cas d'erreur (par exemple, si la capacité maximale est atteinte), une alerte est affichée.
     */
    @FXML
    private void gererAjouterSommet() {
        if (graphe == null) {
            graphe = new Graphe(50);
            etiquetage = new Etiquetage(50);
        }
        if (compteurSommets >= 50) {
            afficherAlerte("Erreur", "Nombre maximum de sommets atteint.");
            return;
        }
        try {
            graphe.ajouterSommet(new Sommet(compteurSommets));
            double centreX = canevas.getWidth() / 2;
            double centreY = canevas.getHeight() / 2;
            double rayon = Math.min(centreX, centreY) * 0.7;
            double angle = (2 * Math.PI * compteurSommets) / Math.max(compteurSommets + 1, 8);
            double x = centreX + rayon * Math.cos(angle);
            double y = centreY + rayon * Math.sin(angle);
            positionsSommets.put(compteurSommets, new Point(x, y));
            compteurSommets++;

            Etiquetage nouvelEtiquetage = new Etiquetage(compteurSommets);
            if (etiquetage != null) {
                for (int i = 0; i < etiquetage.getTaille() && i < compteurSommets - 1; i++) {
                    nouvelEtiquetage.setCouleur(i, etiquetage.getCouleur(i));
                }
            }
            etiquetage = nouvelEtiquetage;

            mettreAJourSpinners();
            appliquerDispositionForceDirigee();
            dessinerGraphe();
        } catch (TailleInsuffisanteException e) {
            afficherAlerte("Erreur", e.getMessage());
        }
    }

    /**
     * Gère l'action du bouton "Ajouter Arête".
     * Crée une arête entre deux sommets sélectionnés via les spinners.
     * 
     * Cette méthode vérifie d'abord que le graphe a été initialisé et contient des sommets.
     * Si les sommets sélectionnés sont valides, une arête non orientée est ajoutée entre eux.
     * Le graphe est ensuite redessiné pour afficher la nouvelle arête.
     * 
     * En cas d'erreur (par exemple, si l'arête existe déjà ou si les indices sont invalides),
     * une alerte est affichée à l'utilisateur.
     */
    @FXML
    private void gererAjouterArete() {
        if (graphe == null) {
            afficherAlerte("Erreur", "Veuillez d'abord ajouter des sommets ou créer un nouveau graphe.");
            return;
        }
        int de = deSpinner.getValue();
        int a = aSpinner.getValue();
        try {
            graphe.ajouterArrete(de, a);
            dessinerGraphe();
        } catch (IllegalArgumentException e) {
            afficherAlerte("Erreur", e.getMessage());
        }
    }

    /**
     * Gère l'action du bouton "Exécuter".
     * Exécute l'algorithme de coloration sélectionné dans la ComboBox sur le graphe actuel.
     * 
     * Les algorithmes disponibles sont :
     * - 2-Coloration : Tente un 2-coloriage (graphe biparti). Échoue si le graphe n'est pas biparti.
     * - Glouton : Applique un algorithme glouton dans l'ordre naturel des sommets.
     * - Welsh-Powell : Applique un algorithme glouton en triant les sommets par degré décroissant.
     * - Wigderson : Algorithme spécialisé pour les graphes 3-coloriables.
     * 
     * Pour l'algorithme de Wigderson, une vérification supplémentaire est effectuée après l'exécution.
     * Si la coloration obtenue n'est pas valide (sommets adjacents de même couleur), cela indique
     * que le graphe n'est pas 3-coloriable, et un avertissement détaillé est affiché à l'utilisateur.
     * 
     * Cette méthode vérifie également que :
     * - Le graphe contient au moins un sommet
     * - Un algorithme a été sélectionné dans la ComboBox
     * 
     * En cas d'erreur lors de l'exécution de l'algorithme, un message d'erreur est affiché.
     */
    @FXML
    private void gererExecuterAlgorithme() {
        if (graphe == null || graphe.getCompteur() == 0) {
            afficherAlerte("Erreur", "Veuillez d'abord ajouter des sommets au graphe.");
            return;
        }
        String algorithmeSelectionne = algorithmeComboBox.getValue();
        if (algorithmeSelectionne == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un algorithme.");
            return;
        }
        try {
            switch (algorithmeSelectionne) {
                case "2-Coloration":
                    etiquetage = AlgorithmesUtilitaires.deuxCol(graphe);
                    break;
                case "Glouton":
                    int[] ordre = new int[graphe.getCompteur()];
                    for (int i = 0; i < graphe.getCompteur(); i++) {
                        ordre[i] = i;
                    }
                    etiquetage = AlgorithmesUtilitaires.glouton(graphe, ordre);
                    break;
                case "Welsh-Powell":
                    etiquetage = AlgorithmesUtilitaires.welshPowell(graphe);
                    break;
                case "Wigderson":
                    etiquetage = AlgorithmesUtilitaires.wigderson(graphe);
                    if (!AlgorithmesUtilitaires.estCol(graphe, etiquetage)) {
                        afficherAlerte("Avertissement",
                                "La coloration obtenue n'est pas propre.\n\n" +
                                        "Cela signifie que le graphe n'est pas 3-coloriable.\n" +
                                        "L'algorithme de Wigderson fonctionne correctement uniquement\n" +
                                        "sur les graphes 3-coloriables.\n\n" +
                                        "Conseil : Essayez l'algorithme '2-Coloration' pour vérifier\n" +
                                        "si votre graphe est biparti, ou utilisez 'Welsh-Powell'\n" +
                                        "pour une coloration générale.");
                    }
                    break;
            }
            dessinerGraphe();
        } catch (Exception e) {
            afficherAlerte("Erreur", "Échec de l'algorithme : " + e.getMessage());
        }
    }

    /**
     * Gère l'action du bouton "Effacer les couleurs".
     * Réinitialise toutes les couleurs des sommets à {@link Couleur#AUCUNE} sans modifier
     * la structure du graphe (sommets et arêtes restent inchangés).
     * 
     * Cette méthode crée un nouvel étiquetage vide de la même taille que le graphe actuel,
     * puis redessine le graphe avec les sommets non colorés.
     * 
     * Utile pour tester différents algorithmes de coloration sur le même graphe sans avoir
     * à recréer la structure complète.
     */
    @FXML
    private void gererEffacer() {
        if (graphe == null) {
            afficherAlerte("Erreur", "Le graphe n'a pas été initialisé.");
            return;
        }
        etiquetage = new Etiquetage(graphe.getCompteur());
        dessinerGraphe();
    }

    /**
     * Gère l'action du menu "Quitter".
     * Ferme l'application en terminant la JVM avec un code de sortie 0 (succès).
     */
    @FXML
    private void gererQuitter() {
        System.exit(0);
    }

    /**
     * Gère l'action du menu "À propos".
     * Affiche une boîte de dialogue d'information contenant le nom et la version de l'application.
     */
    @FXML
    private void gererAPropos() {
        afficherAlerte("À propos", "Application de Coloration de Graphe v1.0");
    }

    /**
     * Dessine le graphe complet sur le canevas.
     * Cette méthode effectue un rendu visuel en deux passes :
     * 
     * 1. Dessin des arêtes : Parcourt toutes les paires de sommets adjacents et trace des lignes noires
     *    entre leurs positions respectives.
     * 
     * 2. Dessin des sommets : Pour chaque sommet, dessine un cercle rempli avec la couleur correspondant
     *    à son étiquetage actuel, puis affiche son numéro au centre.
     * 
     * Le canevas est d'abord effacé complètement avant le dessin pour éviter les artefacts visuels.
     * Les sommets sont dessinés après les arêtes pour qu'ils apparaissent au-dessus.
     */
    private void dessinerGraphe() {
        GraphicsContext gc = canevas.getGraphicsContext2D();
        gc.clearRect(0, 0, canevas.getWidth(), canevas.getHeight());

        gc.setStroke(Color.BLACK);
        for (int i = 0; i < graphe.getCompteur(); i++) {
            for (int j = i + 1; j < graphe.getCompteur(); j++) {
                if (graphe.estAdjacent(i, j)) {
                    Point p1 = positionsSommets.get(i);
                    Point p2 = positionsSommets.get(j);
                    gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        for (int i = 0; i < graphe.getCompteur(); i++) {
            Point p = positionsSommets.get(i);
            Couleur c = etiquetage.getCouleur(i);
            gc.setFill(obtenirCouleur(c));
            gc.fillOval(p.x - 10, p.y - 10, 20, 20);
            gc.strokeText(String.valueOf(i), p.x - 5, p.y + 5);
        }
    }

    /**
     * Efface complètement le contenu du canevas.
     * Remplit tout le canevas avec la couleur de fond par défaut (transparent/blanc).
     */
    private void effacerCanevas() {
        GraphicsContext gc = canevas.getGraphicsContext2D();
        gc.clearRect(0, 0, canevas.getWidth(), canevas.getHeight());
    }

    /**
     * Met à jour les spinners de sélection d'arêtes.
     * Configure les plages de valeurs des spinners "de" et "à" en fonction du nombre actuel
     * de sommets dans le graphe. Les valeurs vont de 0 à (compteurSommets - 1).
     * 
     * Si au moins un sommet existe, les spinners sont activés. Sinon, ils restent désactivés.
     * Cette méthode est appelée après chaque ajout de sommet pour maintenir la cohérence de l'interface.
     */
    private void mettreAJourSpinners() {
        if (compteurSommets > 0) {
            deSpinner.setDisable(false);
            aSpinner.setDisable(false);
            deSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, compteurSommets - 1, 0));
            aSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, compteurSommets - 1, 0));
        }
    }

    /**
     * Convertit une couleur de l'énumération {@link Couleur} en une couleur JavaFX {@link Color}.
     * Cette méthode effectue la correspondance entre les couleurs métier de l'application
     * et les couleurs utilisées pour le rendu graphique.
     * 
     * Correspondances :
     * - {@link Couleur#ROUGE} vers {@link Color#RED}
     * - {@link Couleur#VERT} vers {@link Color#GREEN}
     * - {@link Couleur#BLEU} vers {@link Color#BLUE}
     * - {@link Couleur#JAUNE} vers {@link Color#YELLOW}
     * - {@link Couleur#ORANGE} vers {@link Color#ORANGE}
     * - {@link Couleur#VIOLET} vers {@link Color#VIOLET}
     * - {@link Couleur#AUCUNE} ou {@code null} vers {@link Color#LIGHTGRAY}
     *
     * @param c La couleur de l'énumération à convertir, peut être {@code null}.
     * @return La couleur JavaFX correspondante pour le rendu graphique.
     */
    private Color obtenirCouleur(Couleur c) {
        if (c == null) {
            return Color.LIGHTGRAY;
        }
        switch (c) {
            case ROUGE:
                return Color.RED;
            case VERT:
                return Color.GREEN;
            case BLEU:
                return Color.BLUE;
            case JAUNE:
                return Color.YELLOW;
            case ORANGE:
                return Color.ORANGE;
            case VIOLET:
                return Color.VIOLET;
            default:
                return Color.LIGHTGRAY;
        }
    }

    /**
     * Affiche une boîte de dialogue d'information modale.
     * Cette méthode utilitaire crée et affiche une alerte JavaFX de type INFORMATION
     * avec le titre et le message spécifiés. L'exécution est bloquée jusqu'à ce que
     * l'utilisateur ferme la boîte de dialogue.
     * 
     * Utilisée pour communiquer avec l'utilisateur dans diverses situations :
     * - Messages d'erreur (graphe vide, limite de sommets atteinte, etc.)
     * - Avertissements (algorithme de Wigderson sur graphe non 3-coloriable)
     * - Informations générales (à propos de l'application)
     *
     * @param titre   Le titre de la fenêtre de dialogue.
     * @param message Le contenu du message à afficher à l'utilisateur.
     */
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Applique un algorithme de force dirigée simplifié pour espacer les sommets et éviter les chevauchements.
     * Cet algorithme simule des forces de répulsion entre les sommets pour améliorer la lisibilité du graphe.
     * 
     * Principe de fonctionnement :
     * 1. Pour chaque paire de sommets, calcule la distance euclidienne entre eux
     * 2. Si la distance est inférieure au seuil MIN_DISTANCE, applique une force de répulsion
     * 3. La force est inversement proportionnelle au carré de la distance (loi de Coulomb)
     * 4. Les forces sont appliquées avec un facteur d'amortissement pour stabiliser le système
     * 5. Les sommets sont maintenus dans les limites du canevas avec une marge de sécurité
     * 
     * Paramètres de l'algorithme :
     * - REPULSION_STRENGTH = 2000.0 : Intensité de la force de répulsion
     * - MIN_DISTANCE = 60.0 : Distance minimale souhaitée entre deux sommets (en pixels)
     * - ITERATIONS = 50 : Nombre d'itérations pour converger vers une disposition stable
     * - damping = 0.5 : Facteur d'amortissement pour éviter les oscillations
     * 
     * Cette méthode est appelée après chaque ajout de sommet pour maintenir une disposition
     * esthétique et lisible du graphe.
     */
    private void appliquerDispositionForceDirigee() {
        if (compteurSommets < 2) return;

        final double REPULSION_STRENGTH = 2000.0;
        final double MIN_DISTANCE = 60.0;
        final int ITERATIONS = 50;

        for (int iter = 0; iter < ITERATIONS; iter++) {
            Map<Integer, Point> forces = new HashMap<>();

            for (int i = 0; i < compteurSommets; i++) {
                forces.put(i, new Point(0, 0));
            }

            for (int i = 0; i < compteurSommets; i++) {
                for (int j = i + 1; j < compteurSommets; j++) {
                    Point p1 = positionsSommets.get(i);
                    Point p2 = positionsSommets.get(j);

                    double dx = p2.x - p1.x;
                    double dy = p2.y - p1.y;
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    if (distance < MIN_DISTANCE && distance > 0) {
                        double force = REPULSION_STRENGTH / (distance * distance);
                        double fx = (dx / distance) * force;
                        double fy = (dy / distance) * force;

                        Point f1 = forces.get(i);
                        Point f2 = forces.get(j);
                        f1.x -= fx;
                        f1.y -= fy;
                        f2.x += fx;
                        f2.y += fy;
                    }
                }
            }

            double damping = 0.5;
            for (int i = 0; i < compteurSommets; i++) {
                Point p = positionsSommets.get(i);
                Point f = forces.get(i);

                p.x += f.x * damping;
                p.y += f.y * damping;

                double margin = 30;
                p.x = Math.max(margin, Math.min(canevas.getWidth() - margin, p.x));
                p.y = Math.max(margin, Math.min(canevas.getHeight() - margin, p.y));
            }
        }
    }

    /**
     * Classe interne représentant un point 2D dans le système de coordonnées du canevas.
     * Utilisée pour stocker les positions des sommets et les vecteurs de force dans
     * l'algorithme de disposition force dirigée.
     * 
     * Les coordonnées sont mutables pour permettre les mises à jour de position
     * lors de l'application des forces de répulsion.
     */
    private static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
