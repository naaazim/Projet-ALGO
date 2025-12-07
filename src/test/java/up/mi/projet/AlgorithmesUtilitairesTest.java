package up.mi.projet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link AlgorithmesUtilitaires}.
 * Vérifie le bon fonctionnement des algorithmes de coloration de graphes.
 */
@DisplayName("Tests des algorithmes de coloration")
class AlgorithmesUtilitairesTest {

    @Test
    @DisplayName("estCol : coloration valide sur un graphe simple")
    void testEstColValide() throws TailleInsuffisanteException {
        Graphe g = new Graphe(3);
        g.ajouterSommet(new Sommet(0));
        g.ajouterSommet(new Sommet(1));
        g.ajouterSommet(new Sommet(2));
        g.ajouterArrete(0, 1);

        Etiquetage e = new Etiquetage(3);
        e.setCouleur(0, Couleur.ROUGE);
        e.setCouleur(1, Couleur.VERT);
        e.setCouleur(2, Couleur.ROUGE);

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "La coloration doit être valide");
    }

    @Test
    @DisplayName("estCol : coloration invalide (sommets adjacents de même couleur)")
    void testEstColInvalide() throws TailleInsuffisanteException {
        Graphe g = new Graphe(3);
        g.ajouterSommet(new Sommet(0));
        g.ajouterSommet(new Sommet(1));
        g.ajouterSommet(new Sommet(2));
        g.ajouterArrete(0, 1);

        Etiquetage e = new Etiquetage(3);
        e.setCouleur(0, Couleur.ROUGE);
        e.setCouleur(1, Couleur.ROUGE); // Même couleur que 0 !
        e.setCouleur(2, Couleur.VERT);

        assertFalse(AlgorithmesUtilitaires.estCol(g, e), "La coloration doit être invalide");
    }

    @Test
    @DisplayName("deuxCol : graphe biparti simple (chaîne)")
    void testDeuxColGrapheBiparti() throws Exception {
        Graphe g = new Graphe(4);
        for (int i = 0; i < 4; i++) {
            g.ajouterSommet(new Sommet(i));
        }
        g.ajouterArrete(0, 1);
        g.ajouterArrete(1, 2);
        g.ajouterArrete(2, 3);

        Etiquetage e = AlgorithmesUtilitaires.deuxCol(g);

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "Le 2-coloriage doit être valide");
        assertNotEquals(e.getCouleur(0), e.getCouleur(1), "Sommets adjacents doivent avoir des couleurs différentes");
        assertNotEquals(e.getCouleur(1), e.getCouleur(2));
        assertNotEquals(e.getCouleur(2), e.getCouleur(3));
    }

    @Test
    @DisplayName("deuxCol : graphe non biparti (triangle)")
    void testDeuxColGrapheNonBiparti() throws TailleInsuffisanteException {
        Graphe g = new Graphe(3);
        for (int i = 0; i < 3; i++) {
            g.ajouterSommet(new Sommet(i));
        }
        g.ajouterArrete(0, 1);
        g.ajouterArrete(1, 2);
        g.ajouterArrete(2, 0);

        assertThrows(NonBipartiException.class, () -> {
            AlgorithmesUtilitaires.deuxCol(g);
        }, "Doit lever NonBipartiException pour un triangle");
    }

    @Test
    @DisplayName("glouton : graphe simple")
    void testGloutonGrapheSimple() throws Exception {
        Graphe g = new Graphe(4);
        for (int i = 0; i < 4; i++) {
            g.ajouterSommet(new Sommet(i));
        }
        g.ajouterArrete(0, 1);
        g.ajouterArrete(1, 2);
        g.ajouterArrete(2, 3);

        int[] ordre = { 0, 1, 2, 3 };
        Etiquetage e = AlgorithmesUtilitaires.glouton(g, ordre);

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "La coloration gloutonne doit être valide");
    }

    @Test
    @DisplayName("glouton : graphe complet K3")
    void testGloutonGrapheComplet() throws Exception {
        Graphe g = new Graphe(3);
        for (int i = 0; i < 3; i++) {
            g.ajouterSommet(new Sommet(i));
        }
        g.ajouterArrete(0, 1);
        g.ajouterArrete(1, 2);
        g.ajouterArrete(2, 0);

        int[] ordre = { 0, 1, 2 };
        Etiquetage e = AlgorithmesUtilitaires.glouton(g, ordre);

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "La coloration doit être valide");
        // K3 nécessite 3 couleurs
        assertNotEquals(e.getCouleur(0), e.getCouleur(1));
        assertNotEquals(e.getCouleur(1), e.getCouleur(2));
        assertNotEquals(e.getCouleur(2), e.getCouleur(0));
    }

    @Test
    @DisplayName("welshPowell : graphe simple")
    void testWelshPowellGrapheSimple() throws Exception {
        Graphe g = new Graphe(5);
        for (int i = 0; i < 5; i++) {
            g.ajouterSommet(new Sommet(i));
        }
        // Créer une étoile : sommet 0 connecté à tous les autres
        for (int i = 1; i < 5; i++) {
            g.ajouterArrete(0, i);
        }

        Etiquetage e = AlgorithmesUtilitaires.welshPowell(g);

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "La coloration Welsh-Powell doit être valide");
    }

    @Test
    @DisplayName("welshPowell : graphe biparti")
    void testWelshPowellGrapheBiparti() throws Exception {
        Graphe g = new Graphe(4);
        for (int i = 0; i < 4; i++) {
            g.ajouterSommet(new Sommet(i));
        }
        g.ajouterArrete(0, 2);
        g.ajouterArrete(0, 3);
        g.ajouterArrete(1, 2);
        g.ajouterArrete(1, 3);

        Etiquetage e = AlgorithmesUtilitaires.welshPowell(g);

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "La coloration doit être valide");
    }

    @Test
    @DisplayName("wigderson : graphe biparti (peut fonctionner)")
    void testWigdersonGrapheBiparti() throws Exception {
        Graphe g = new Graphe(4);
        for (int i = 0; i < 4; i++) {
            g.ajouterSommet(new Sommet(i));
        }
        g.ajouterArrete(0, 1);
        g.ajouterArrete(1, 2);
        g.ajouterArrete(2, 3);

        // Wigderson peut produire une coloration valide ou invalide selon le graphe
        // On vérifie juste qu'il ne lève pas d'exception
        assertDoesNotThrow(() -> {
            AlgorithmesUtilitaires.wigderson(g);
        }, "Wigderson ne doit pas lever d'exception");
    }

    @Test
    @DisplayName("wigderson : graphe 3-coloriable")
    void testWigdersonGraphe3Coloriable() throws Exception {
        // Créer un graphe qui nécessite 3 couleurs mais est 3-coloriable
        Graphe g = new Graphe(5);
        for (int i = 0; i < 5; i++) {
            g.ajouterSommet(new Sommet(i));
        }
        // Cycle de longueur 5 (impair, donc non biparti mais 3-coloriable)
        g.ajouterArrete(0, 1);
        g.ajouterArrete(1, 2);
        g.ajouterArrete(2, 3);
        g.ajouterArrete(3, 4);
        g.ajouterArrete(4, 0);

        Etiquetage e = AlgorithmesUtilitaires.wigderson(g);

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "Wigderson doit produire une coloration valide");
    }

    @Test
    @DisplayName("Graphe vide")
    void testGrapheVide() throws Exception {
        Graphe g = new Graphe(10);
        Etiquetage e = new Etiquetage(0);

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "Un graphe vide doit avoir une coloration valide");
    }

    @Test
    @DisplayName("Graphe avec un seul sommet")
    void testGrapheUnSeulSommet() throws Exception {
        Graphe g = new Graphe(1);
        g.ajouterSommet(new Sommet(0));

        Etiquetage e = AlgorithmesUtilitaires.glouton(g, new int[] { 0 });

        assertTrue(AlgorithmesUtilitaires.estCol(g, e), "Un graphe à un sommet doit être coloriable");
        assertNotEquals(Couleur.AUCUNE, e.getCouleur(0), "Le sommet doit avoir une couleur");
    }
}
