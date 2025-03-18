package com.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class App extends Application {

    private GestionUtilisateur gestionUtilisateur;
    private TableView<Utilisateur> tableView;
    private ObservableList<Utilisateur> utilisateurList;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Connexion connexion = new Connexion();
        gestionUtilisateur = new GestionUtilisateur(connexion);

        // Chargement du layout FXML
        VBox root = FXMLLoader.load(getClass().getResource("/layout/App.fxml"));
        
        // Récupération des boutons via lookup()
       // Button listUsersButton = (Button) root.lookup("#listUsersButton");
        Button addUserButton = (Button) root.lookup("#addUserButton");
        Button deleteUserButton = (Button) root.lookup("#deleteUserButton");
        Button editUserButton = (Button) root.lookup("#editUserButton");
        Button searchUserButton = (Button) root.lookup("#searchUserButton");
        Button resetListButton = (Button) root.lookup("#resetListButton");
        Button generateCsvButton = (Button) root.lookup("#generateCsvButton");

        // TableView et initialisation
        tableView = new TableView<>();
        utilisateurList = FXCollections.observableArrayList(gestionUtilisateur.listUtilisateurs());
        tableView.setItems(utilisateurList);

        // Création des colonnes de la table
        TableColumn<Utilisateur, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Utilisateur, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Utilisateur, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Ajouter les colonnes à la table
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(emailColumn);

        // Ajout des événements aux boutons
        //listUsersButton.setOnAction(e -> showUsers());
        addUserButton.setOnAction(e -> addUser());
        deleteUserButton.setOnAction(e -> deleteUser());
        editUserButton.setOnAction(e -> editUser());
        searchUserButton.setOnAction(e -> searchUser());
        resetListButton.setOnAction(e -> resetUserList());
        generateCsvButton.setOnAction(e -> generateCsv());



        // Ajouter la TableView à la scène
        VBox layout = new VBox(10);
        layout.getChildren().add(tableView);
        layout.getChildren().addAll( /*listUsersButton*/addUserButton, deleteUserButton, editUserButton, searchUserButton, resetListButton, generateCsvButton );

        Scene scene = new Scene(layout, 500, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setTitle("Gestion des Utilisateurs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   /*  // Afficher la liste des utilisateurs sous forme de TableView
    private void showUsers() {
        // Mettre à jour la liste des utilisateurs dans la TableView
        utilisateurList.setAll(gestionUtilisateur.listUtilisateurs());
    } */

    // Ajouter un utilisateur
    private void addUser() {
        Optional<String> nom = showInputDialog("Nom", "Entrez le nom de l'utilisateur:");
        Optional<String> email = showInputDialog("Email", "Entrez l'email de l'utilisateur:");

        if (nom.isPresent() && email.isPresent()) {
            Utilisateur newUser = new Utilisateur(0, nom.get(), email.get());
            gestionUtilisateur.addUtilisateurs(newUser);
            utilisateurList.add(newUser);  // Ajouter l'utilisateur à la liste affichée
            showWindow("Ajout", "Utilisateur ajouté avec succès!");
        }
    }

    // Supprimer un utilisateur
    private void deleteUser() {
        Optional<String> id = showInputDialog("Suppression", "Entrez l'ID de l'utilisateur à supprimer:");
    
        id.ifPresent(value -> {
            try {
                int userId = Integer.parseInt(value);
                boolean userExists = gestionUtilisateur.listUtilisateurs().stream()
                    .anyMatch(user -> user.getId() == userId);
    
                if (userExists) {
                    gestionUtilisateur.supprimerUtilisateur(userId);
                    showWindow("Suppression", "Utilisateur supprimé avec succès!");
    
                    // Utilise tableView ici pour mettre à jour la table après suppression
                    tableView.setItems(FXCollections.observableArrayList(gestionUtilisateur.listUtilisateurs()));
                } else {
                    showWindow("Erreur", "L'ID " + userId + " n'existe pas !");
                }
            } catch (NumberFormatException e) {
                showWindow("Erreur", "Veuillez entrer un ID valide !");
            }
        });
    }
    
    

    // Modifier un utilisateur
    private void editUser() {
        Utilisateur selectedUser = tableView.getSelectionModel().getSelectedItem();
    
        if (selectedUser != null) {
            Optional<String> newNom = showInputDialog("Nouveau Nom", "Entrez le nouveau nom:");
            Optional<String> newEmail = showInputDialog("Nouvel Email", "Entrez le nouvel email:");
    
            if (newNom.isPresent() && newEmail.isPresent()) {
                // Met à jour l'objet utilisateur
                selectedUser.setNom(newNom.get());
                selectedUser.setEmail(newEmail.get());
    
                // Mise à jour de la base de données
                gestionUtilisateur.modifierUtilisateur(selectedUser.getId(), newNom.get(), newEmail.get());
    
                // Met à jour l'affichage de la TableView
                tableView.refresh();
                showWindow("Modification", "Utilisateur modifié avec succès!");
            }
        } else {
            showWindow("Erreur", "Veuillez sélectionner un utilisateur à modifier.");
        }
    }
    

    private void searchUser() {
        Optional<String> searchType = showInputDialog("Recherche", "Voulez-vous rechercher par (nom/email) ?");
    
        if (searchType.isPresent()) {
            String type = searchType.get().trim().toLowerCase();
    
            if (type.equals("nom")) {
                Optional<String> nom = showInputDialog("Recherche par Nom", "Entrez le nom de l'utilisateur:");
                if (nom.isPresent()) {
                    ObservableList<Utilisateur> filteredUsers = FXCollections.observableArrayList();
                    for (Utilisateur user : utilisateurList) {
                        if (user.getNom().toLowerCase().contains(nom.get().toLowerCase())) {
                            filteredUsers.add(user);
                        }
                    }
                    tableView.setItems(filteredUsers);
                }
            } else if (type.equals("email")) {
                Optional<String> email = showInputDialog("Recherche par Email", "Entrez l'email de l'utilisateur:");
                if (email.isPresent()) {
                    ObservableList<Utilisateur> filteredUsers = FXCollections.observableArrayList();
                    for (Utilisateur user : utilisateurList) {
                        if (user.getEmail().toLowerCase().contains(email.get().toLowerCase())) {
                            filteredUsers.add(user);
                        }
                    }
                    tableView.setItems(filteredUsers);
                }
            } else {
                showWindow("Erreur", "Veuillez entrer 'nom' ou 'email' !");
            }
        }
    }
    
    
    private void resetUserList() {
        utilisateurList.setAll(gestionUtilisateur.listUtilisateurs());
        tableView.setItems(utilisateurList);
    }
    
    
    private void generateCsv() {
        String filePath = "utilisateurs.csv"; // Tu peux aussi utiliser un FileChooser
        gestionUtilisateur.genererCSVUtilisateur(filePath);
        
        showWindow("Export CSV", "Le fichier CSV a été généré avec succès :\n" + filePath);
    
        // Recharger la liste après l'export (facultatif)
        utilisateurList.setAll(gestionUtilisateur.listUtilisateurs());
    }
    
    


    // Méthode pour afficher une nouvelle fenêtre avec un message
    private void showWindow(String title, String message) {
        Stage newStage = new Stage();
        VBox root = new VBox(10);
        root.getChildren().add(new Label(message));
        Scene scene = new Scene(root, 300, 200);
        newStage.setTitle(title);
        newStage.setScene(scene);
        newStage.show();
    }

    // Méthode pour afficher une boîte de dialogue pour saisir du texte
    private Optional<String> showInputDialog(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        return dialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
