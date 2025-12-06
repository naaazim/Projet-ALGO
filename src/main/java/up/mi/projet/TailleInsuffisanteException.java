package up.mi.projet;

/**
 * Exception levée pour indiquer qu'une opération ne peut pas être terminée
 * car une structure de données sous-jacente (comme un tableau pour stocker les sommets
 * d'un graphe) n'a pas une capacité suffisante.
 */
public class TailleInsuffisanteException extends Exception {

    /**
     * Construit une nouvelle exception avec un message de détail.
     *
     * @param message Le message de détail, qui est sauvegardé pour être récupéré
     *                plus tard par la méthode {@link #getMessage()}.
     */
    public TailleInsuffisanteException(String message) {
        super(message);
    }
}