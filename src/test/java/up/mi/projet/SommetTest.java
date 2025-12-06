package up.mi.projet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Sommet}.
 * Vérifie la création, la modification et l'affichage des sommets.
 */
@DisplayName("Tests de la classe Sommet")
class SommetTest {

    @Test
    @DisplayName("Création d'un sommet avec une valeur")
    void testCreationSommet() {
        Sommet s = new Sommet(5);
        assertEquals(5, s.getValeur(), "La valeur du sommet doit être 5");
    }

    @Test
    @DisplayName("Création de sommets avec différentes valeurs")
    void testCreationPlusieursSommets() {
        Sommet s1 = new Sommet(0);
        Sommet s2 = new Sommet(10);
        Sommet s3 = new Sommet(-5);

        assertEquals(0, s1.getValeur());
        assertEquals(10, s2.getValeur());
        assertEquals(-5, s3.getValeur(), "Les valeurs négatives doivent être acceptées");
    }

    @Test
    @DisplayName("Modification de la valeur d'un sommet")
    void testSetValeur() {
        Sommet s = new Sommet(3);
        assertEquals(3, s.getValeur(), "Valeur initiale doit être 3");

        s.setValeur(7);
        assertEquals(7, s.getValeur(), "Valeur modifiée doit être 7");
    }

    @Test
    @DisplayName("Modification multiple de la valeur")
    void testModificationsMultiples() {
        Sommet s = new Sommet(0);

        s.setValeur(1);
        assertEquals(1, s.getValeur());

        s.setValeur(2);
        assertEquals(2, s.getValeur());

        s.setValeur(100);
        assertEquals(100, s.getValeur());
    }

    @Test
    @DisplayName("Méthode afficher ne lève pas d'exception")
    void testAfficher() {
        Sommet s = new Sommet(42);
        assertDoesNotThrow(() -> s.afficher(), "La méthode afficher ne doit pas lever d'exception");
    }
}
