package up.mi.projet;

import java.util.ArrayList;
import java.util.List;

// G = (V,E)
public class Graphe {
    private Sommet[] v;
    private int[][] adjacence;
    private int compteur;
    public Graphe(int taille) {
        v = new Sommet[taille];
        adjacence = new int[taille][taille];
        compteur = 0;
    }
    public void ajouterSommet(Sommet sommet) throws TailleInsuffisanteException {
        if(compteur == v.length) throw new TailleInsuffisanteException("La taille du graphe est insuffisante pour ajouter ce sommet");
        v[compteur] = sommet;
        compteur++;
    }
    public void ajouterArrete(int i, int j) throws IllegalArgumentException {
        // Vérification des bornes
        if (i < 0 || i >= compteur) {
            throw new IllegalArgumentException(
                    "Le sommet d’indice " + i + " n’existe pas dans le graphe."
            );
        }

        if (j < 0 || j >= compteur) {
            throw new IllegalArgumentException(
                    "Le sommet d’indice " + j + " n’existe pas dans le graphe."
            );
        }

        // Interdire les boucles
        if (i == j) {
            throw new IllegalArgumentException("Impossible de créer une arête d’un sommet vers lui-même.");
        }

        // Ajout de l’arête dans les deux sens (graphe non orienté)
        adjacence[i][j] = 1;
        adjacence[j][i] = 1;
    }
    public boolean estAdjacent(int i, int j) {
        if (i < 0 || i >= compteur) throw new IllegalArgumentException("indice i invalide.");
        if (j < 0 || j >= compteur) throw new IllegalArgumentException("indice j invalide.");
        return adjacence[i][j] == 1;
    }
    public Sommet getSommet(int i){
        return v[i];
    }
    public int getCompteur() {
        return compteur;
    }
    public List<Sommet> getVoisins(int i) {
        List<Sommet> liste = new ArrayList<>();
        for(int j = 0; j < compteur; j++){
            if(estAdjacent(i, j)){
                liste.add(getSommet(j));
            }
        }
        return liste;
    }
    public void afficher(){
        for (int i = 0; i < compteur; i++) {
            for (int j = 0; j < compteur; j++) {
                System.out.print(adjacence[i][j] + " ");
            }
            System.out.println();
        }
    }

}
