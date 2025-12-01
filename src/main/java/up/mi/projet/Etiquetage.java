package up.mi.projet;

public class Etiquetage {

    private Couleur[] couleurs;   // couleurs[i] = couleur du sommet i

    public Etiquetage(int taille) {
        couleurs = new Couleur[taille];
        // Tous les sommets sont non coloriés au départ
        for (int i = 0; i < taille; i++) {
            couleurs[i] = Couleur.AUCUNE;
        }
    }

    public Couleur getCouleur(int i) {
        return couleurs[i];
    }

    public void setCouleur(int i, Couleur c) {
        couleurs[i] = c;
    }

    public int getTaille() {
        return couleurs.length;
    }

    public void afficher() {
        System.out.println("Étiquetage :");
        for (int i = 0; i < couleurs.length; i++) {
            System.out.println("Sommet " + i + " -> " + couleurs[i]);
        }
    }
}