package up.mi.projet;

public class AlgorithmesUtilitaires {

    // Fournie dans ton code
    public static boolean estCol(Graphe g, Etiquetage e) {
        if(e.getTaille() < g.getCompteur()) return false;

        for(int i = 0; i < g.getCompteur(); i++){
            for(int j = 0; j < g.getCompteur(); j++){
                if(g.estAdjacent(i, j)){
                    if(e.getCouleur(i) == e.getCouleur(j)){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    // ********** QUESTION 6 ***********
    /**
     * Renvoie un 2-coloriage du graphe si possible.
     * Si le graphe n'est pas 2-coloriable, le comportement est laissé libre.
     */
    public static Etiquetage deuxCol(Graphe g) throws NonBipartiException {
        int n = g.getCompteur();
        Etiquetage e = new Etiquetage(n);

        for(int i = 0; i < n; i++) {
            if(e.getCouleur(i) == Couleur.AUCUNE) {
                boolean ok = dfsColor(g, e, i, Couleur.ROUGE);
                if(!ok) {
                    throw new NonBipartiException("Le graphe n'est pas biparti : conflit détecté.");
                }
            }
        }

        return e;
    }

    /**
     * DFS récursif pour colorier le graphe en 2 couleurs.
     * Renvoie false si un conflit est rencontré.
     */
    private static boolean dfsColor(Graphe g, Etiquetage e, int u, Couleur couleur) {

        // Si déjà colorié : vérifier compatibilité
        if(e.getCouleur(u) != Couleur.AUCUNE) {
            return e.getCouleur(u) == couleur;
        }

        // Colorier le sommet
        e.setCouleur(u, couleur);

        // Couleur opposée
        Couleur autre = (couleur == Couleur.ROUGE) ? Couleur.VERT : Couleur.ROUGE;

        // Explorer les voisins
        for(Sommet v : g.getVoisins(u)) {
            int idx = v.getValeur();
            if(!dfsColor(g, e, idx, autre)) {
                return false; // conflit détecté
            }
        }

        return true;
    }

    public static int minCouleurPossible(Graphe g, Etiquetage e, int s) {
        int n = g.getCompteur();

        // Tableau pour marquer les couleurs déjà interdites
        boolean[] interdit = new boolean[n];

        // Parcours des voisins : coût O(n) avec une matrice d'adjacence
        for (Sommet v : g.getVoisins(s)) {
            Couleur c = e.getCouleur(v.getValeur());
            if (c != Couleur.AUCUNE) {
                interdit[c.ordinal()] = true;
            }
        }

        // Recherche de la plus petite couleur non interdite
        for (int couleur = 0; couleur < n; couleur++) {
            if (!interdit[couleur]) {
                return couleur;
            }
        }

        // Ne devrait jamais arriver avec n couleurs
        return n;
    }

}
