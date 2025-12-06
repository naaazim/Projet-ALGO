package up.mi.projet;

/**
 * Représente un sommet dans un graphe.
 * Chaque sommet est identifié par une valeur entière, qui correspond généralement
 * à son indice dans les structures de données du graphe.
 */
public class Sommet {
    private int valeur;

    /**
     * Construit un sommet avec une valeur spécifique.
     *
     * @param valeur La valeur (ou l'identifiant) du sommet.
     */
    public Sommet(int valeur) {
        this.valeur = valeur;
    }

    /**
     * Récupère la valeur du sommet.
     *
     * @return La valeur du sommet.
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Met à jour la valeur du sommet.
     *
     * @param valeur La nouvelle valeur du sommet.
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /**
     * Affiche la valeur du sommet sur la sortie standard.
     */
    void afficher() {
        System.out.println("Sommet " + valeur);
    }
}