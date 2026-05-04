package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
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

public class ModifierSalleController extends MenuController implements Initializable {

    @FXML
    private TextField tfNumero, tfDescription, tfNbPlaces;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Cinema> lvCinema;

    private int idSalle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Chargement de la liste des cinémas dans la ListView
        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();
        ObservableList<Cinema> list = FXCollections.observableArrayList(cinemas);
        lvCinema.setItems(list);
    }

    // Méthode appelée depuis ListeSalleController pour pré-remplir le formulaire
    public void setAttributes(Salle salle) {
        tfNumero.setText(String.valueOf(salle.getNumero()));
        tfDescription.setText(salle.getDescription());
        tfNbPlaces.setText(String.valueOf(salle.getNbPlaces()));
        // Sélection du cinéma correspondant dans la liste
        lvCinema.getSelectionModel().select(salle.getIdCinema() - 1);
        this.idSalle = salle.getIdSalle();
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String numeroStr = tfNumero.getText();
        String description = tfDescription.getText();
        String nbPlacesStr = tfNbPlaces.getText();
        Cinema cinemaSelectionne = lvCinema.getSelectionModel().getSelectedItem();

        // Vérification que tous les champs sont remplis
        if (numeroStr.isBlank() || nbPlacesStr.isBlank() || cinemaSelectionne == null) {
            return;
        }

        try {
            int numero = Integer.parseInt(numeroStr);
            int nbPlaces = Integer.parseInt(nbPlacesStr);

            Salle salle = new Salle(this.idSalle, numero, description, nbPlaces,
                    cinemaSelectionne.getIdCinema());

            SalleDAO salleDAO = new SalleDAO();
            boolean controle = salleDAO.update(salle);
            if (controle) {
                Stage stageP = (Stage) bRetour.getScene().getWindow();
                stageP.close();

                try {
                    // Retour à la liste des salles après modification
                    FXMLLoader fxmlLoader = new FXMLLoader(
                            getClass().getResource("/cinema/views/page_liste_salle.fxml"));
                    Parent root = fxmlLoader.load();

                    ListeSalleController listeSalleController = fxmlLoader.getController();
                    listeSalleController.setName(nameUti);

                    Stage stage = new Stage();
                    stage.setTitle("Liste des salles");
                    stage.setScene(new Scene(root));

                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();

        try {
            // Retour à la liste des salles
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_salle.fxml"));
            Parent root = fxmlLoader.load();

            ListeSalleController listeSalleController = fxmlLoader.getController();
            listeSalleController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Liste des salles");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}