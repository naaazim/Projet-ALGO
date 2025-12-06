package up.mi.projet.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale de l'application JavaFX de coloration de graphe.
 * Cette classe etend Application et configure l'interface graphique
 * en chargeant le fichier FXML et en initialisant la fenetre principale.
 */
public class Main extends Application {

    /**
     * Methode de demarrage de l'application JavaFX.
     * Cette methode est appelee automatiquement par le framework JavaFX apres l'initialisation.
     * Elle charge l'interface depuis le fichier FXML, configure la fenetre principale
     * et l'affiche a l'utilisateur.
     *
     * @param primaryStage La fenetre principale (Stage) de l'application.
     * @throws IOException Si le fichier FXML ne peut pas etre charge.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/up/mi/projet/gui/MainView.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Coloration de Graphe");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
