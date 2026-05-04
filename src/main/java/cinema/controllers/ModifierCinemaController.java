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

public class ModifierCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Franchise> lvFranchise;

    private int idCinema;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Chargement de la liste des franchises dans la ListView
        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();
        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);
        lvFranchise.setItems(list);
    }

    // Méthode appelée depuis ListeCinemaController pour pré-remplir le formulaire
    public void setAttributes(Cinema cinema) {
        tfDenomination.setText(cinema.getDenomination());
        tfAdresse.setText(cinema.getAdresse());
        tfVille.setText(cinema.getVille());
        // Sélection de la franchise correspondante dans la liste
        lvFranchise.getSelectionModel().select(cinema.getIdFranchise() - 1);
        this.idCinema = cinema.getIdCinema();
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String denomination = tfDenomination.getText();
        String adresse = tfAdresse.getText();
        String ville = tfVille.getText();
        Franchise franchiseSelectionnee = lvFranchise.getSelectionModel().getSelectedItem();

        // Vérification que tous les champs sont remplis
        if (denomination != null && adresse != null && ville != null
                && franchiseSelectionnee != null
                && !denomination.trim().isEmpty()
                && !adresse.trim().isEmpty()
                && !ville.trim().isEmpty()) {

            Cinema cinema = new Cinema(this.idCinema, denomination, adresse, ville,
                    franchiseSelectionnee.getIdFranchise());

            CinemaDAO cinemaDAO = new CinemaDAO();
            boolean controle = cinemaDAO.update(cinema);
            if (controle) {
                Stage stageP = (Stage) bRetour.getScene().getWindow();
                stageP.close();

                try {
                    // Charger la liste des cinémas après modification
                    FXMLLoader fxmlLoader = new FXMLLoader(
                            getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
                    Parent root = fxmlLoader.load();

                    ListeCinemaController listeCinemaController = fxmlLoader.getController();
                    listeCinemaController.setName(nameUti);

                    Stage stage = new Stage();
                    stage.setTitle("Liste cinémas");
                    stage.setScene(new Scene(root));

                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();

        try {
            // Retour à la liste des cinémas
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
            Parent root = fxmlLoader.load();

            ListeCinemaController listeCinemaController = fxmlLoader.getController();
            listeCinemaController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Liste cinémas");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}