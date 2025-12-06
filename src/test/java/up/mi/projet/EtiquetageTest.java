package up.mi.projet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Etiquetage}.
 * Vérifie la gestion des couleurs des sommets et les opérations de base sur
 * l'étiquetage.
 */
@DisplayName("Tests de la classe Etiquetage")
class EtiquetageTest {

    private Etiquetage etiquetage;

    /**
     * Initialise un nouvel étiquetage avant chaque test.
     */
    @BeforeEach
    void setUp() {
        etiquetage = new Etiquetage(5);
    }

    @Test
    @DisplayName("Création d'un étiquetage avec couleurs AUCUNE par défaut")
    void testCreationEtiquetage() {
        assertEquals(5, etiquetage.getTaille(), "La taille doit être 5");
        assertEquals(Couleur.AUCUNE, etiquetage.getCouleur(0), "Couleur initiale doit être AUCUNE");
        assertEquals(Couleur.AUCUNE, etiquetage.getCouleur(4), "Couleur initiale doit être AUCUNE");
    }

    @Test
    @DisplayName("Définition et récupération d'une couleur")
    void testSetEtGetCouleur() {
        etiquetage.setCouleur(0, Couleur.ROUGE);
        etiquetage.setCouleur(1, Couleur.VERT);

        assertEquals(Couleur.ROUGE, etiquetage.getCouleur(0), "La couleur du sommet 0 doit être ROUGE");
        assertEquals(Couleur.VERT, etiquetage.getCouleur(1), "La couleur du sommet 1 doit être VERT");
    }

    @Test
    @DisplayName("Modification d'une couleur existante")
    void testModificationCouleur() {
        etiquetage.setCouleur(2, Couleur.BLEU);
        assertEquals(Couleur.BLEU, etiquetage.getCouleur(2), "Couleur initiale doit être BLEU");

        etiquetage.setCouleur(2, Couleur.JAUNE);
        assertEquals(Couleur.JAUNE, etiquetage.getCouleur(2), "Couleur modifiée doit être JAUNE");
    }

    @Test
    @DisplayName("Toutes les couleurs disponibles")
    void testToutesLesCouleurs() {
        etiquetage.setCouleur(0, Couleur.ROUGE);
        etiquetage.setCouleur(1, Couleur.VERT);
        etiquetage.setCouleur(2, Couleur.BLEU);
        etiquetage.setCouleur(3, Couleur.JAUNE);
        etiquetage.setCouleur(4, Couleur.ORANGE);

        assertEquals(Couleur.ROUGE, etiquetage.getCouleur(0));
        assertEquals(Couleur.VERT, etiquetage.getCouleur(1));
        assertEquals(Couleur.BLEU, etiquetage.getCouleur(2));
        assertEquals(Couleur.JAUNE, etiquetage.getCouleur(3));
        assertEquals(Couleur.ORANGE, etiquetage.getCouleur(4));
    }

    @Test
    @DisplayName("Taille de l'étiquetage")
    void testGetTaille() {
        Etiquetage petit = new Etiquetage(3);
        Etiquetage grand = new Etiquetage(100);

        assertEquals(3, petit.getTaille(), "Taille doit être 3");
        assertEquals(100, grand.getTaille(), "Taille doit être 100");
    }

    @Test
    @DisplayName("Étiquetage de taille 1")
    void testEtiquetageUnSeulSommet() {
        Etiquetage un = new Etiquetage(1);
        assertEquals(1, un.getTaille());
        assertEquals(Couleur.AUCUNE, un.getCouleur(0));

        un.setCouleur(0, Couleur.VIOLET);
        assertEquals(Couleur.VIOLET, un.getCouleur(0));
    }

    @Test
    @DisplayName("Exception pour indice négatif")
    void testIndiceNegatif() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            etiquetage.getCouleur(-1);
        }, "Doit lever une exception pour indice négatif");
    }

    @Test
    @DisplayName("Exception pour indice trop grand")
    void testIndiceTropGrand() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            etiquetage.getCouleur(10);
        }, "Doit lever une exception pour indice hors limites");
    }
}
