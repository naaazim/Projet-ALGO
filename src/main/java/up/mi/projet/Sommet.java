package up.mi.projet;

public class Sommet {
    private int valeur;
    public Sommet(int valeur) {
        this.valeur = valeur;

    }


    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
    void afficher(){
        System.out.println("Sommet " + valeur);
    }
}
