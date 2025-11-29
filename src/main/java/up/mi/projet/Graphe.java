package up.mi.projet;

import java.util.List;

// G = (V,E)
public class Graphe {
    private String nom;
    private Sommet[] v;
    private int[][] adjacence;
    int compteur;
    public Graphe(String nom, int taille) {
        this.nom = nom;
        v = new Sommet[taille];
        adjacence = new int[taille][taille];
        compteur = 0;
    }
    public void ajouterSommet(Sommet sommet) throws TailleInsuffisanteException {
        if(compteur == v.length) throw new TailleInsuffisanteException("La taille du graphe est insuffisante pour ajouter ce sommet");
        v[compteur] = sommet;
        compteur++;
    }
    public void ajouterArrete(int indice1, int indice2) throws IllegalArgumentException {
        // Vérification des bornes
        if (indice1 < 0 || indice1 >= compteur) {
            throw new IllegalArgumentException(
                    "Le sommet d’indice " + indice1 + " n’existe pas dans le graphe."
            );
        }

        if (indice2 < 0 || indice2 >= compteur) {
            throw new IllegalArgumentException(
                    "Le sommet d’indice " + indice2 + " n’existe pas dans le graphe."
            );
        }

        // Interdire les boucles
        if (indice1 == indice2) {
            throw new IllegalArgumentException("Impossible de créer une arête d’un sommet vers lui-même.");
        }

        // Ajout de l’arête dans les deux sens (graphe non orienté)
        adjacence[indice1][indice2] = 1;
        adjacence[indice2][indice1] = 1;
    }

}
