package up.mi.projet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Cette classe fournit une collection d'algorithmes utilitaires pour la coloration de graphes.
 * Elle inclut des méthodes pour vérifier la validité d'un coloriage, des algorithmes de 2-coloriage,
 * des algorithmes gloutons comme Welsh-Powell, et l'algorithme de Wigderson.
 */
public class AlgorithmesUtilitaires {

    /**
     * Vérifie si un étiquetage donné est un coloriage valide pour un graphe.
     * Un coloriage est valide si aucun des deux sommets adjacents n'a la même couleur.
     *
     * @param g Le graphe à vérifier.
     * @param e L'étiquetage (coloriage) à tester.
     * @return {@code true} si l'étiquetage est un coloriage valide, {@code false} sinon.
     */
    public static boolean estCol(Graphe g, Etiquetage e) {
        if (e.getTaille() < g.getCompteur()) {
            return false;
        }

        for (int i = 0; i < g.getCompteur(); i++) {
            for (int j = 0; j < g.getCompteur(); j++) {
                if (g.estAdjacent(i, j)) {
                    if (e.getCouleur(i) == e.getCouleur(j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Tente de réaliser un 2-coloriage d'un graphe.
     * Un graphe est 2-coloriable si et seulement s'il est biparti.
     * L'algorithme utilise un parcours en profondeur (DFS) pour assigner les couleurs.
     *
     * @param g Le graphe à colorier.
     * @return Un {@link Etiquetage} représentant le 2-coloriage.
     * @throws NonBipartiException Si le graphe n'est pas biparti et ne peut donc pas être 2-colorié.
     */
    public static Etiquetage deuxCol(Graphe g) throws NonBipartiException {
        int n = g.getCompteur();
        Etiquetage e = new Etiquetage(n);

        for (int i = 0; i < n; i++) {
            if (e.getCouleur(i) == Couleur.AUCUNE) {
                boolean ok = dfsColor(g, e, i, Couleur.ROUGE);
                if (!ok) {
                    throw new NonBipartiException("Le graphe n'est pas biparti : conflit de couleur détecté.");
                }
            }
        }

        return e;
    }

    /**
     * Fonction auxiliaire récursive (DFS) pour le 2-coloriage.
     * Elle parcourt le graphe et assigne une couleur à chaque sommet, en s'assurant
     * que les sommets adjacents ont des couleurs différentes.
     *
     * @param g       Le graphe en cours de coloriage.
     * @param e       L'étiquetage à mettre à jour.
     * @param u       L'indice du sommet actuel à traiter.
     * @param couleur La couleur à assigner au sommet {@code u}.
     * @return {@code true} si le coloriage partiel est cohérent, {@code false} si un conflit est détecté.
     */
    private static boolean dfsColor(Graphe g, Etiquetage e, int u, Couleur couleur) {
        if (e.getCouleur(u) != Couleur.AUCUNE) {
            return e.getCouleur(u) == couleur;
        }

        e.setCouleur(u, couleur);
        Couleur autre = (couleur == Couleur.ROUGE) ? Couleur.VERT : Couleur.ROUGE;

        for (Sommet v : g.getVoisins(u)) {
            if (!dfsColor(g, e, v.getValeur(), autre)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Détermine la plus petite couleur (représentée par un entier) qui peut être
     * assignée à un sommet donné sans conflit avec ses voisins déjà coloriés.
     *
     * @param g Le graphe.
     * @param e L'étiquetage actuel.
     * @param s L'indice du sommet à colorier.
     * @return L'entier représentant la plus petite couleur disponible.
     */
    public static int minCouleurPossible(Graphe g, Etiquetage e, int s) {
        int n = g.getCompteur();
        boolean[] interdit = new boolean[n];

        for (Sommet v : g.getVoisins(s)) {
            Couleur c = e.getCouleur(v.getValeur());
            int code = c.ordinal() - 1;
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

    /**
     * Applique un algorithme de coloriage glouton sur un graphe.
     * L'ordre dans lequel les sommets sont coloriés est déterminé par le tableau {@code num}.
     *
     * @param g   Le graphe à colorier.
     * @param num Un tableau d'entiers spécifiant l'ordre de traitement des sommets.
     * @return Un {@link Etiquetage} résultant du coloriage glouton.
     */
    public static Etiquetage glouton(Graphe g, int[] num) {
        int n = g.getCompteur();
        Etiquetage e = new Etiquetage(n);

        for (int s : num) {
            int c = minCouleurPossible(g, e, s);
            e.setCouleur(s, Couleur.values()[c + 1]);
        }
        return e;
    }

    /**
     * Trie les sommets d'un graphe par ordre de degré décroissant.
     *
     * @param g Le graphe dont les sommets doivent être triés.
     * @return Un tableau d'indices de sommets triés par degré décroissant.
     */
    public static int[] triDegre(Graphe g) {
        int n = g.getCompteur();
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, Comparator.comparingInt(g::getDegre).reversed());

        int[] num = new int[n];
        for (int k = 0; k < n; k++) {
            num[k] = indices[k];
        }

        return num;
    }

    /**
     * Implémente l'algorithme de Welsh-Powell pour la coloration de graphe.
     * C'est un algorithme glouton qui colorie les sommets dans l'ordre de leur degré décroissant.
     *
     * @param g Le graphe à colorier.
     * @return Un {@link Etiquetage} produit par l'algorithme de Welsh-Powell.
     */
    public static Etiquetage welshPowell(Graphe g) {
        return glouton(g, triDegre(g));
    }

    /**
     * Crée un sous-graphe induit par un ensemble de sommets.
     *
     * @param g  Le graphe original.
     * @param sg Un tableau d'indices de sommets qui formeront le sous-graphe.
     * @return Un nouveau {@link Graphe} qui est le sous-graphe induit.
     * @throws TailleInsuffisanteException Si la taille du tableau est insuffisante.
     */
    public static Graphe sousGraphe(Graphe g, int[] sg) throws TailleInsuffisanteException {
        int k = sg.length;
        Graphe sous = new Graphe(k);

        for (int i = 0; i < k; i++) {
            sous.ajouterSommet(new Sommet(i));
        }

        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                if (g.estAdjacent(sg[i], sg[j])) {
                    sous.ajouterArrete(i, j);
                }
            }
        }

        return sous;
    }

    /**
     * Récupère la liste des voisins non coloriés d'un sommet.
     *
     * @param g Le graphe.
     * @param e L'étiquetage actuel.
     * @param s L'indice du sommet.
     * @return Un tableau d'indices des voisins non coloriés.
     */
    public static int[] voisinsNonColories(Graphe g, Etiquetage e, int s) {
        List<Integer> liste = new ArrayList<>();
        for (Sommet v : g.getVoisins(s)) {
            if (e.getCouleur(v.getValeur()) == Couleur.AUCUNE) {
                liste.add(v.getValeur());
            }
        }
        return liste.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Calcule le degré résiduel d'un sommet, c'est-à-dire le nombre de ses voisins non coloriés.
     *
     * @param g Le graphe.
     * @param e L'étiquetage actuel.
     * @param s L'indice du sommet.
     * @return Le nombre de voisins non coloriés.
     */
    public static int degreNonColories(Graphe g, Etiquetage e, int s) {
        int count = 0;
        for (Sommet v : g.getVoisins(s)) {
            if (e.getCouleur(v.getValeur()) == Couleur.AUCUNE) {
                count++;
            }
        }
        return count;
    }

    /**
     * Renvoie la liste des sommets qui n'ont pas encore été coloriés.
     *
     * @param e L'étiquetage.
     * @return Un tableau d'indices des sommets non coloriés.
     */
    public static int[] nonColories(Etiquetage e) {
        List<Integer> liste = new ArrayList<>();
        for (int i = 0; i < e.getTaille(); i++) {
            if (e.getCouleur(i) == Couleur.AUCUNE) {
                liste.add(i);
            }
        }
        return liste.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Implémente l'algorithme de Wigderson pour la coloration de graphe.
     * L'algorithme fonctionne en deux phases :
     * 1. Phase de Wigderson : Tant qu'il existe des sommets non coloriés avec un degré résiduel
     *    supérieur ou égal à sqrt(n) (où n est le nombre de sommets), l'algorithme sélectionne
     *    un tel sommet, 2-colorie le sous-graphe de ses voisins non coloriés avec deux nouvelles
     *    couleurs, et répète.
     * 2. Phase gloutonne : Les sommets restants sont coloriés en utilisant un algorithme glouton simple.
     *
     * @param g Le graphe à colorier.
     * @return Un {@link Etiquetage} produit par l'algorithme de Wigderson.
     * @throws Exception Si une erreur se produit, notamment lors du 2-coloriage.
     */
    public static Etiquetage wigderson(Graphe g) throws Exception {
        int n = g.getCompteur();
        Etiquetage e = new Etiquetage(n);
        int seuil = (int) Math.ceil(Math.sqrt(n));
        int couleurCourante = 1;

        while (true) {
            int meilleurSommet = -1;
            int meilleurDegre = -1;

            // Chercher un sommet non colorié de degré résiduel >= seuil
            for (int s : nonColories(e)) {
                int deg = degreNonColories(g, e, s);
                if (deg >= seuil && deg > meilleurDegre) {
                    meilleurDegre = deg;
                    meilleurSommet = s;
                }
            }

            if (meilleurSommet == -1) {
                break; // Fin de la phase Wigderson
            }

            int[] voisins = voisinsNonColories(g, e, meilleurSommet);
            if (voisins.length == 0) continue;

            Graphe sg = sousGraphe(g, voisins);
            Etiquetage eLocal = deuxCol(sg);

            // Intégrer le 2-coloriage avec deux nouvelles couleurs
            for (int i = 0; i < voisins.length; i++) {
                Couleur c = eLocal.getCouleur(i);
                if (c == Couleur.ROUGE) {
                    e.setCouleur(voisins[i], Couleur.values()[couleurCourante]);
                } else if (c == Couleur.VERT) {
                    e.setCouleur(voisins[i], Couleur.values()[couleurCourante + 1]);
                }
            }
            couleurCourante += 2;
        }

        // Phase gloutonne pour les sommets restants
        Etiquetage fin = glouton(g, nonColories(e));
        for (int i = 0; i < n; i++) {
            if (e.getCouleur(i) == Couleur.AUCUNE) {
                e.setCouleur(i, fin.getCouleur(i));
            }
        }

        return e;
    }
}
