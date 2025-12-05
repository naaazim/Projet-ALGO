package up.mi.projet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        boolean[] interdit = new boolean[n];

        for (Sommet v : g.getVoisins(s)) {
            Couleur c = e.getCouleur(v.getValeur());
            int code = c.ordinal() - 1; // ROUGE = 0, VERT = 1, ...
            if (code >= 0) {
                interdit[code] = true;
            }
        }

        for (int couleur = 0; couleur < n; couleur++) {
            if (!interdit[couleur]) {
                return couleur;
            }
        }

        return n;
    }

    public static Etiquetage glouton(Graphe g, int[] num) {
        int n = g.getCompteur();
        Etiquetage e = new Etiquetage(n);

        // Parcours des sommets selon l’ordre num
        for(int k = 0; k < n; k++) {
            int s = num[k]; // numéro du sommet à colorier

            // Plus petite couleur disponible pour s
            int c = minCouleurPossible(g, e, s);

            // On attribue la couleur c
            e.setCouleur(s, Couleur.values()[c + 1]);
            // +1 car valeur 0 correspond à AUCUNE dans ton enum
        }
        return e;
    }
    public static int[] triDegre(Graphe g) {
        int n = g.getCompteur();

        // Tableau des indices de sommets : 0, 1, ..., n-1
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Tri par ordre décroissant du degré
        Arrays.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i, Integer j) {
                int degI = g.getDegre(i);
                int degJ = g.getDegre(j);
                // ordre décroissant : on met d'abord le plus grand
                return Integer.compare(degJ, degI);
            }
        });

        // Conversion en int[]
        int[] num = new int[n];
        for (int k = 0; k < n; k++) {
            num[k] = indices[k];
        }

        return num;
    }
    public static Etiquetage welshPowell(Graphe g) {
        return glouton(g, triDegre(g));
    }
    public static Graphe sousGraphe(Graphe g, int[] sg) throws TailleInsuffisanteException {
        int k = sg.length;               // taille du sous-graphe
        Graphe sous = new Graphe(k);     // nouveau graphe de k sommets

        // Ajout des sommets 0..k-1
        for (int i = 0; i < k; i++) {
            sous.ajouterSommet(new Sommet(i));
        }

        // Création des arêtes du sous-graphe
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                int u = sg[i];
                int v = sg[j];

                if (g.estAdjacent(u, v)) {
                    sous.ajouterArrete(i, j);
                }
            }
        }

        return sous;
    }
    public static int[] voisinsNonColories(Graphe g, Etiquetage e, int s) {
        List<Integer> liste = new ArrayList<>();

        for (Sommet v : g.getVoisins(s)) {
            if (e.getCouleur(v.getValeur()) == Couleur.AUCUNE) {
                liste.add(v.getValeur());
            }
        }

        // conversion List<Integer> -> int[]
        int[] res = new int[liste.size()];
        for (int i = 0; i < liste.size(); i++) {
            res[i] = liste.get(i);
        }
        return res;
    }
    public static int degreNonColories(Graphe g, Etiquetage e, int s) {
        int count = 0;

        for (Sommet v : g.getVoisins(s)) {
            if (e.getCouleur(v.getValeur()) == Couleur.AUCUNE) {
                count++;
            }
        }

        return count;
    }


}
