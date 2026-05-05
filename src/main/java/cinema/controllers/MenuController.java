package cinema.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    protected MenuItem bListeFranchise, bAjouterFranchise, bListeCinema, bAjouterCinema, bQuitter, bAccueil,
            bListeSalle, bAjouterSalle;

    protected String nameUti;

    /**
     * Ajoute les icônes CineForAll sur un stage (plusieurs tailles pour Windows)
     */
    private void setIcons(Stage stage) {
        stage.getIcons().addAll(
                new Image("/cinema/images/cinema_16x16.png"),
                new Image("/cinema/images/cinema_32x32.png"),
                new Image("/cinema/images/cinema_48x48.png")
        );
    }

    @FXML
    public void bQuitterClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void bAccueilClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            // CORRECTION : appel de setBienvenue() pour afficher le nom/prénom
            accueilController.setBienvenue();

            Stage stage = new Stage();
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            setIcons(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bListFranchiseClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_franchise.fxml"));
            Parent root = fxmlLoader.load();

            ListeFranchiseController listeFranchiseController = fxmlLoader.getController();
            listeFranchiseController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Liste franchises");
            stage.setScene(new Scene(root));
            setIcons(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bAjouterFranchiseClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_ajout_franchise.fxml"));
            Parent root = fxmlLoader.load();

            AjouterFranchiseController ajouterFranchiseController = fxmlLoader.getController();
            ajouterFranchiseController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Ajouter une franchise");
            stage.setScene(new Scene(root));
            setIcons(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bListeCinemaClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
            Parent root = fxmlLoader.load();

            ListeCinemaController listeCinemaController = fxmlLoader.getController();
            listeCinemaController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Liste cinémas");
            stage.setScene(new Scene(root));
            setIcons(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bAjouterCinemaClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_ajout_cinema.fxml"));
            Parent root = fxmlLoader.load();

            // CORRECTION : passage du nameUti au controller
            AjouterCinemaController ajouterCinemaController = fxmlLoader.getController();
            ajouterCinemaController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Ajouter un cinéma");
            stage.setScene(new Scene(root));
            setIcons(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bListeSalleClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_salle.fxml"));
            Parent root = fxmlLoader.load();

            // CORRECTION : passage du nameUti au controller
            ListeSalleController listeSalleController = fxmlLoader.getController();
            listeSalleController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Liste des salles");
            stage.setScene(new Scene(root));
            setIcons(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bAjouterSalleClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_ajout_salle.fxml"));
            Parent root = fxmlLoader.load();

            AjouterSalleController ajouterSalleController = fxmlLoader.getController();
            ajouterSalleController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Ajouter une salle");
            stage.setScene(new Scene(root));
            setIcons(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setName(String nameUti) {
        this.nameUti = nameUti;
    }
}