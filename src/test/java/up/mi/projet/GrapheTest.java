package up.mi.projet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Graphe}.
 * Vérifie le bon fonctionnement de la création de graphes, l'ajout de sommets
 * et d'arêtes,
 * ainsi que les opérations de vérification d'adjacence et de récupération des
 * voisins.
 */
@DisplayName("Tests de la classe Graphe")
class GrapheTest {

    private Graphe graphe;

    /**
     * Initialise un nouveau graphe avant chaque test.
     */
    @BeforeEach
    void setUp() {
        graphe = new Graphe(10);
    }

    @Test
    @DisplayName("Création d'un graphe vide")
    void testCreationGrapheVide() {
        assertEquals(0, graphe.getCompteur(), "Un nouveau graphe doit avoir 0 sommets");
    }

    @Test
    @DisplayName("Ajout d'un sommet")
    void testAjouterSommet() throws TailleInsuffisanteException {
        Sommet s1 = new Sommet(0);
        graphe.ajouterSommet(s1);

        assertEquals(1, graphe.getCompteur(), "Le graphe doit avoir 1 sommet après ajout");
        assertEquals(s1, graphe.getSommet(0), "Le sommet ajouté doit être récupérable");
    }

    @Test
    @DisplayName("Ajout de plusieurs sommets")
    void testAjouterPlusieursSommets() throws TailleInsuffisanteException {
        for (int i = 0; i < 5; i++) {
            graphe.ajouterSommet(new Sommet(i));
        }

        assertEquals(5, graphe.getCompteur(), "Le graphe doit avoir 5 sommets");
    }

    @Test
    @DisplayName("Exception lors du dépassement de la capacité")
    void testDepassementCapacite() throws TailleInsuffisanteException {
        Graphe petitGraphe = new Graphe(2);
        petitGraphe.ajouterSommet(new Sommet(0));
        petitGraphe.ajouterSommet(new Sommet(1));

        assertThrows(TailleInsuffisanteException.class, () -> {
            petitGraphe.ajouterSommet(new Sommet(2));
        }, "Doit lever une exception si la capacité est dépassée");
    }

    @Test
    @DisplayName("Ajout d'une arête valide")
    void testAjouterArreteValide() throws TailleInsuffisanteException {
        graphe.ajouterSommet(new Sommet(0));
        graphe.ajouterSommet(new Sommet(1));

        graphe.ajouterArrete(0, 1);

        assertTrue(graphe.estAdjacent(0, 1), "Les sommets 0 et 1 doivent être adjacents");
        assertTrue(graphe.estAdjacent(1, 0), "L'adjacence doit être symétrique");
    }

    @Test
    @DisplayName("Exception pour arête avec indice invalide")
    void testAjouterArreteIndiceInvalide() throws TailleInsuffisanteException {
        graphe.ajouterSommet(new Sommet(0));

        assertThrows(IllegalArgumentException.class, () -> {
            graphe.ajouterArrete(0, 5);
        }, "Doit lever une exception si l'indice est invalide");
    }

    @Test
    @DisplayName("Exception pour boucle (arête vers soi-même)")
    void testAjouterArreteBoucle() throws TailleInsuffisanteException {
        graphe.ajouterSommet(new Sommet(0));

        assertThrows(IllegalArgumentException.class, () -> {
            graphe.ajouterArrete(0, 0);
        }, "Doit lever une exception pour une boucle");
    }

    @Test
    @DisplayName("Vérification d'adjacence pour sommets non adjacents")
    void testEstAdjacentFaux() throws TailleInsuffisanteException {
        graphe.ajouterSommet(new Sommet(0));
        graphe.ajouterSommet(new Sommet(1));

        assertFalse(graphe.estAdjacent(0, 1), "Les sommets ne doivent pas être adjacents initialement");
    }

    @Test
    @DisplayName("Récupération des voisins d'un sommet")
    void testGetVoisins() throws TailleInsuffisanteException {
        // Créer un graphe en étoile : sommet 0 connecté à 1, 2, 3
        for (int i = 0; i < 4; i++) {
            graphe.ajouterSommet(new Sommet(i));
        }
        graphe.ajouterArrete(0, 1);
        graphe.ajouterArrete(0, 2);
        graphe.ajouterArrete(0, 3);

        assertEquals(3, graphe.getVoisins(0).size(), "Le sommet 0 doit avoir 3 voisins");
        assertEquals(1, graphe.getVoisins(1).size(), "Le sommet 1 doit avoir 1 voisin");
    }

    @Test
    @DisplayName("Calcul du degré d'un sommet")
    void testGetDegre() throws TailleInsuffisanteException {
        for (int i = 0; i < 4; i++) {
            graphe.ajouterSommet(new Sommet(i));
        }
        graphe.ajouterArrete(0, 1);
        graphe.ajouterArrete(0, 2);
        graphe.ajouterArrete(0, 3);

        assertEquals(3, graphe.getDegre(0), "Le degré du sommet 0 doit être 3");
        assertEquals(1, graphe.getDegre(1), "Le degré du sommet 1 doit être 1");
        assertEquals(1, graphe.getDegre(3), "Le degré du sommet 3 doit être 1");
    }

    @Test
    @DisplayName("Graphe complet K3 (triangle)")
    void testGrapheCompletK3() throws TailleInsuffisanteException {
        for (int i = 0; i < 3; i++) {
            graphe.ajouterSommet(new Sommet(i));
        }
        graphe.ajouterArrete(0, 1);
        graphe.ajouterArrete(1, 2);
        graphe.ajouterArrete(2, 0);

        assertTrue(graphe.estAdjacent(0, 1), "0-1 adjacents");
        assertTrue(graphe.estAdjacent(1, 2), "1-2 adjacents");
        assertTrue(graphe.estAdjacent(2, 0), "2-0 adjacents");
        assertEquals(2, graphe.getDegre(0), "Tous les sommets doivent avoir degré 2");
        assertEquals(2, graphe.getDegre(1), "Tous les sommets doivent avoir degré 2");
        assertEquals(2, graphe.getDegre(2), "Tous les sommets doivent avoir degré 2");
    }
}
