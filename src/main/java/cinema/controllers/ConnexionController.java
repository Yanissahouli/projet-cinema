package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Utilisateur;
import cinema.DAO.UtilisateurDAO;
import cinema.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnexionController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfMDP;
    @FXML
    private Button bConnexion;

    @FXML
    public void bConnexionClick(ActionEvent event) {
        String truc = tfLogin.getText();
        String chose = tfMDP.getText();

        UtilisateurDAO userDAO = new UtilisateurDAO();
        // TODO
        Utilisateur user = userDAO.authenticate(truc, chose);

        // message d'erreur lors de la connexion
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Identifiant ou mot de passe incorrect.");
            alert.showAndWait();
            return;
        }

        // Stockage de l'utilisateur connecté dans la Session pour les logs
        Session.setUtilisateur(user);

        showAccueil(user.getNom() + " " + user.getPrenom());
    }

    private void showAccueil(String name) {
        Stage stageP = (Stage) bConnexion.getScene().getWindow();
        // on ferme l'écran
        stageP.close();
        try {

            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(name);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Accueil Gestion de franchises");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/cinema/images/cinema_16x16.png"));
            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showError() {

        try {
            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/ErreurConnexion.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la pop-up
            ErrorController errorController = fxmlLoader.getController();

            // Passer la variable au contrôleur de la pop-up
            // errorController.setMajLabel(Integer.toString(compteur));

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Error Window");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}