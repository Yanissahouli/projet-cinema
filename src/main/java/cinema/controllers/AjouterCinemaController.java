package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjouterCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Franchise> lvFranchise;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Chargement de la liste des franchises dans la ListView
        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();
        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
        lvFranchise.setItems(list);
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        // On ferme l'écran actuel
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();

        // Récupération de la franchise sélectionnée dans la ListView
        Franchise franchiseSelectionnee = lvFranchise.getSelectionModel().getSelectedItem();

        // Si aucune franchise sélectionnée on ne fait rien
        if (franchiseSelectionnee == null) {
            return;
        }

        Cinema cinema = new Cinema(0, denomination, adresse, ville, franchiseSelectionnee.getIdFranchise());

        CinemaDAO cinemaDAO = new CinemaDAO();
        boolean controle = cinemaDAO.create(cinema);
        if (controle) {
            tfDenomination.clear();
            tfAdresse.clear();
            tfVille.clear();
            lvFranchise.getSelectionModel().clearSelection();
        }
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfDenomination != null) tfDenomination.clear();
        if (tfAdresse != null) tfAdresse.clear();
        if (tfVille != null) tfVille.clear();
        lvFranchise.getSelectionModel().clearSelection();
    }
}