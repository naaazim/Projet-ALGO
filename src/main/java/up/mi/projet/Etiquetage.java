package up.mi.projet;

/**
 * Représente un étiquetage (ou coloriage) d'un graphe.
 * Cette classe associe une couleur à chaque sommet d'un graphe.
 * Les sommets sont identifiés par des indices entiers.
 */
public class Etiquetage {

    /**
     * Tableau stockant la couleur de chaque sommet. L'indice du tableau
     * correspond à l'identifiant du sommet.
     */
    private final Couleur[] couleurs;

    /**
     * Construit un nouvel étiquetage pour un graphe d'une taille donnée.
     * Initialement, tous les sommets sont initialisés avec la couleur
     * {@link Couleur#AUCUNE}.
     *
     * @param taille Le nombre de sommets dans le graphe, et donc la taille de
     *               l'étiquetage.
     */
    public Etiquetage(int taille) {
        couleurs = new Couleur[taille];
        for (int i = 0; i < taille; i++) {
            couleurs[i] = Couleur.AUCUNE;
        }
    }

    /**
     * Récupère la couleur d'un sommet spécifique.
     *
     * @param i L'indice du sommet.
     * @return La {@link Couleur} du sommet.
     */
    public Couleur getCouleur(int i) {
        return couleurs[i];
    }

    /**
     * Assigne une couleur à un sommet spécifique.
     *
     * @param i L'indice du sommet.
     * @param c La nouvelle {@link Couleur} à assigner.
     */
    public void setCouleur(int i, Couleur c) {
        couleurs[i] = c;
    }

    /**
     * Renvoie le nombre total de sommets gérés par cet étiquetage.
     *
     * @return La taille de l'étiquetage.
     */
    public int getTaille() {
        return couleurs.length;
    }

    /**
     * Affiche l'étiquetage complet sur la sortie standard, en listant
     * la couleur de chaque sommet.
     */
    public void afficher() {
        System.out.println("Étiquetage :");
        for (int i = 0; i < couleurs.length; i++) {
            System.out.println("Sommet " + i + " -> " + couleurs[i]);
        }
    }
}