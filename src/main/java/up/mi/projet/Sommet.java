package up.mi.projet;

public class Sommet {
    private int valeur;
    private Couleur couleur;
    public Sommet(int valeur) {
        this.valeur = valeur;
        this.couleur = Couleur.AUCUNE;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public int getValeur() {
        return valeur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}
