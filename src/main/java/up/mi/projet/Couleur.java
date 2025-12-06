package up.mi.projet;

/**
 * Énumération représentant un ensemble de couleurs de base pouvant être utilisées
 * pour la coloration des sommets d'un graphe.
 *
 * La couleur {@code AUCUNE} est spéciale et indique qu'un sommet n'a pas encore
 * reçu de couleur.
 */
public enum Couleur {
    /**
     * Représente l'absence de couleur. C'est la couleur par défaut d'un sommet
     * avant tout algorithme de coloration.
     */
    AUCUNE,

    /**
     * La couleur Rouge.
     */
    ROUGE,

    /**
     * La couleur Verte.
     */
    VERT,

    /**
     * La couleur Bleue.
     */
    BLEU,

    /**
     * La couleur Jaune.
     */
    JAUNE,

    /**
     * La couleur Orange.
     */
    ORANGE,

    /**
     * La couleur Violette.
     */
    VIOLET
}
