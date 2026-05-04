package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListeSalleController extends MenuController implements Initializable {

    @FXML
    private TableView<Salle> tvSalles;

    @FXML
    private TableColumn<Salle, String> tcNumero, tcDescription, tcNbPlaces, tcCinema;

    @FXML
    private TableColumn<Salle, Void> tcModifier, tcSupprimer;

    @FXML
    private Button bRetour;

    // Cinéma utilisé pour filtrer les salles (null = toutes les salles)
    private Cinema cinemaFiltre = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcNbPlaces.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));

        // Affichage du nom du cinéma au lieu de l'id
        CinemaDAO cinemaDAO = new CinemaDAO();
        Map<Integer, Cinema> cinemas = cinemaDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Cinema::getIdCinema, c -> c));

        tcCinema.setCellValueFactory(cellData -> {
            Cinema cinema = cinemas.get(cellData.getValue().getIdCinema());
            return new SimpleStringProperty(
                    cinema != null ? cinema.getDenomination() : "Aucun cinéma");
        });

        ObservableList<Salle> data = getSalleList();
        tvSalles.setItems(data);

        addButtonModifierToTable();
        addButtonSupprimerToTable();
    }

    // Permet de filtrer les salles par cinéma depuis ListeCinemaController
    public void setCinema(Cinema cinema) {
        this.cinemaFiltre = cinema;
        // Recharger la liste filtrée une fois le cinéma défini
        tvSalles.setItems(getSalleList());
    }

    private ObservableList<Salle> getSalleList() {
        SalleDAO salleDAO = new SalleDAO();
        List<Salle> salles;

        if (cinemaFiltre != null) {
            // Filtrer les salles pour ce cinéma uniquement
            salles = salleDAO.findByCinema(cinemaFiltre.getIdCinema());
        } else {
            salles = salleDAO.findAll();
        }

        ObservableList<Salle> list = FXCollections.observableArrayList();
        if (salles != null) {
            list.addAll(salles);
        }
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

            Stage stage = new Stage();
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addButtonModifierToTable() {
        tcModifier.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    Stage stageP = (Stage) bRetour.getScene().getWindow();
                    stageP.close();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("/cinema/views/page_modif_salle.fxml"));
                        Parent root = fxmlLoader.load();

                        ModifierSalleController modifierSalleController = fxmlLoader.getController();
                        modifierSalleController.setAttributes(salle);
                        modifierSalleController.setName(nameUti);

                        Stage stage = new Stage();
                        stage.setTitle("Modification salle");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void addButtonSupprimerToTable() {
        tcSupprimer.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation de suppression");
                    alert.setHeaderText("Supprimer la salle");
                    alert.setContentText("Voulez-vous vraiment supprimer la salle n°"
                            + salle.getNumero() + " ?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        tvSalles.getItems().remove(salle);
                        SalleDAO salleDAO = new SalleDAO();
                        salleDAO.delete(salle);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }
}