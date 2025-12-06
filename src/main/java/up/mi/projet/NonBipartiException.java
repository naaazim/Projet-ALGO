package up.mi.projet;

/**
 * Exception levée lorsqu'un algorithme détecte qu'un graphe n'est pas biparti,
 * rendant impossible un 2-coloriage.
 */
public class NonBipartiException extends Exception {

    /**
     * Construit une nouvelle exception avec un message de détail.
     *
     * @param message Le message de détail, qui est sauvegardé pour être récupéré
     *                plus tard par la méthode {@link #getMessage()}.
     */
    public NonBipartiException(String message) {
        super(message);
    }
}
