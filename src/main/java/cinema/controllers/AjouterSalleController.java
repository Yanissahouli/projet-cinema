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

public class AjouterSalleController extends MenuController implements Initializable {

    @FXML
    private TextField tfNumero, tfDescription, tfNbPlaces;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Cinema> lvCinema;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Chargement de la liste des cinémas dans la ListView
        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();
        ObservableList<Cinema> list = FXCollections.observableArrayList(cinemas);
        lvCinema.setItems(list);
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_salle.fxml"));
            Parent root = fxmlLoader.load();

            ListeSalleController listeSalleController = fxmlLoader.getController();
            listeSalleController.setName(nameUti);

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Liste des salles");
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
        // Récupération des valeurs saisies
        String numeroStr = tfNumero.getText();
        String description = tfDescription.getText();
        String nbPlacesStr = tfNbPlaces.getText();

        // Récupération du cinéma sélectionné
        Cinema cinemaSelectionne = lvCinema.getSelectionModel().getSelectedItem();

        // Vérification que tous les champs sont remplis
        if (numeroStr.isBlank() || nbPlacesStr.isBlank() || cinemaSelectionne == null) {
            return;
        }

        try {
            int numero = Integer.parseInt(numeroStr);
            int nbPlaces = Integer.parseInt(nbPlacesStr);

            Salle salle = new Salle(0, numero, description, nbPlaces, cinemaSelectionne.getIdCinema());

            SalleDAO salleDAO = new SalleDAO();
            boolean controle = salleDAO.create(salle);
            if (controle) {
                tfNumero.clear();
                tfDescription.clear();
                tfNbPlaces.clear();
                lvCinema.getSelectionModel().clearSelection();
            }
        } catch (NumberFormatException e) {
            // Si le numéro ou nb places n'est pas un nombre entier
            e.printStackTrace();
        }
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfNumero != null) tfNumero.clear();
        if (tfDescription != null) tfDescription.clear();
        if (tfNbPlaces != null) tfNbPlaces.clear();
        lvCinema.getSelectionModel().clearSelection();
    }
}