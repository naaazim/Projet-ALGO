package up.mi.projet.gui;

import javafx.application.Application;

/**
 * Classe de lancement (Launcher) pour l'application JavaFX de coloration de
 * graphe.
 * Cette classe sert de point d'entrée principal et délègue le démarrage à la
 * classe {@link Main}.
 * 
 * L'utilisation d'un Launcher séparé permet de contourner certaines limitations
 * de JavaFX
 * lors de l'exécution depuis un JAR ou avec certaines configurations de
 * classpath.
 */
public class Launcher {

    /**
     * Point d'entrée principal de l'application.
     * Lance l'application JavaFX en déléguant à la classe {@link Main}.
     *
     * @param args Les arguments de la ligne de commande passés à l'application.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
