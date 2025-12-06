package up.mi.projet;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un graphe non orienté, défini par un ensemble de sommets et une matrice d'adjacence.
 * Les sommets sont identifiés par des indices entiers de 0 à n-1, où n est le nombre de sommets.
 */
public class Graphe {

    private final Sommet[] v; // Ensemble des sommets (V)
    private final int[][] adjacence; // Matrice d'adjacence pour les arêtes (E)
    private int compteur; // Nombre actuel de sommets dans le graphe

    /**
     * Construit un graphe vide avec une capacité maximale spécifiée.
     *
     * @param taille La capacité maximale du graphe (nombre maximal de sommets).
     */
    public Graphe(int taille) {
        v = new Sommet[taille];
        adjacence = new int[taille][taille];
        compteur = 0;
    }

    /**
     * Ajoute un sommet au graphe.
     *
     * @param sommet Le sommet à ajouter.
     * @throws TailleInsuffisanteException Si le nombre maximal de sommets est déjà atteint.
     */
    public void ajouterSommet(Sommet sommet) throws TailleInsuffisanteException {
        if (compteur >= v.length) {
            throw new TailleInsuffisanteException("Impossible d'ajouter un sommet : taille maximale du graphe atteinte.");
        }
        v[compteur] = sommet;
        compteur++;
    }

    /**
     * Ajoute une arête entre deux sommets, spécifiés par leurs indices.
     * L'arête est ajoutée dans les deux sens car le graphe est non orienté.
     *
     * @param i L'indice du premier sommet.
     * @param j L'indice du second sommet.
     * @throws IllegalArgumentException Si les indices des sommets sont invalides ou identiques.
     */
    public void ajouterArrete(int i, int j) throws IllegalArgumentException {
        if (i < 0 || i >= compteur) {
            throw new IllegalArgumentException("Le sommet d’indice " + i + " n’existe pas dans le graphe.");
        }
        if (j < 0 || j >= compteur) {
            throw new IllegalArgumentException("Le sommet d’indice " + j + " n’existe pas dans le graphe.");
        }
        if (i == j) {
            throw new IllegalArgumentException("Impossible de créer une arête d’un sommet vers lui-même.");
        }
        adjacence[i][j] = 1;
        adjacence[j][i] = 1;
    }

    /**
     * Vérifie s'il existe une arête entre deux sommets.
     *
     * @param i L'indice du premier sommet.
     * @param j L'indice du second sommet.
     * @return {@code true} si les sommets sont adjacents, {@code false} sinon.
     * @throws IllegalArgumentException Si l'un des indices est invalide.
     */
    public boolean estAdjacent(int i, int j) {
        if (i < 0 || i >= compteur) throw new IllegalArgumentException("Indice i invalide.");
        if (j < 0 || j >= compteur) throw new IllegalArgumentException("Indice j invalide.");
        return adjacence[i][j] == 1;
    }

    /**
     * Récupère un sommet par son indice.
     *
     * @param i L'indice du sommet.
     * @return Le {@link Sommet} à l'indice spécifié.
     */
    public Sommet getSommet(int i) {
        return v[i];
    }

    /**
     * Renvoie le nombre actuel de sommets dans le graphe.
     *
     * @return Le nombre de sommets.
     */
    public int getCompteur() {
        return compteur;
    }

    /**
     * Renvoie la liste des sommets voisins d'un sommet donné.
     *
     * @param i L'indice du sommet dont on cherche les voisins.
     * @return Une {@link List} de {@link Sommet}s voisins.
     */
    public List<Sommet> getVoisins(int i) {
        List<Sommet> liste = new ArrayList<>();
        for (int j = 0; j < compteur; j++) {
            if (estAdjacent(i, j)) {
                liste.add(getSommet(j));
            }
        }
        return liste;
    }

    /**
     * Calcule le degré d'un sommet (le nombre de ses voisins).
     *
     * @param i L'indice du sommet.
     * @return Le degré du sommet.
     */
    public int getDegre(int i) {
        return getVoisins(i).size();
    }

    /**
     * Affiche la matrice d'adjacence du graphe sur la sortie standard.
     * Utile pour le débogage.
     */
    public void afficher() {
        for (int i = 0; i < compteur; i++) {
            for (int j = 0; j < compteur; j++) {
                System.out.print(adjacence[i][j] + " ");
            }
            System.out.println();
        }
    }
}