package up.mi.projet;

import java.util.List;

// G = (V,E)
public class Graphe {
    private String nom;
    private Sommet[] v;
    private int[][] adjacence;
    public Graphe(String nom, int taille) {
        this.nom = nom;
        v = new Sommet[taille];
        adjacence = new int[taille][taille];
    }
    public void ajouterSommet(Sommet sommet) {
        v[v.length - 1] = sommet;
    }
}
