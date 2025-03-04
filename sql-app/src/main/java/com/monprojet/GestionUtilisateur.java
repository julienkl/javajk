package com.monprojet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GestionUtilisateur {
    ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
    Connexion link = null;

    public GestionUtilisateur(Connexion plink) {
        this.link = plink;
    }

    public ArrayList<Utilisateur> listUtilisateurs() {
        ArrayList<Utilisateur> listeUtilisateurs = new ArrayList<>();
        try {
            Statement stmt = this.link.connexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nom, email FROM utilisateurs");
            System.out.println("Listes des utilisateurs:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");

                Utilisateur utilisateur = new Utilisateur(id, nom, email);
                listeUtilisateurs.add(utilisateur);
                System.out.println("ID : " + id + ", Nom : " + nom + ", Email : " + email);
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
        return listeUtilisateurs;
    }

    public void addUtilisateurs(Utilisateur utilisateur) {
        try {
            if (utilisateur.isValidNom()) {
                String sqlInsert = "INSERT INTO utilisateurs (nom, email) VALUES (?, ?)";
                PreparedStatement pstmtInsert = this.link.connexion.prepareStatement(sqlInsert);
                pstmtInsert.setString(1, utilisateur.getNom());
                pstmtInsert.setString(2, utilisateur.getEmail());
                pstmtInsert.executeUpdate();

                this.utilisateurs.add(utilisateur);
                
                System.out.println("Insertion réussie.");
            } else {
                System.out.println("Nom non valide !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }

    public void supprimerUtilisateur(int id) {
        try {
            String sqlDelete = "DELETE FROM utilisateurs WHERE id = ?";
            PreparedStatement pstmtDelete = this.link.connexion.prepareStatement(sqlDelete);
            pstmtDelete.setInt(1, id);
            pstmtDelete.executeUpdate();
            
            System.out.println("Utilisateur supprimé avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public void modifierUtilisateur(int id, String newNom, String newEmail) {
        try {
            String sqlUpdate = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";
            PreparedStatement pstmtUpdate = this.link.connexion.prepareStatement(sqlUpdate);
            pstmtUpdate.setString(1, newNom);
            pstmtUpdate.setString(2, newEmail);
            pstmtUpdate.setInt(3, id);
            pstmtUpdate.executeUpdate();
            
            System.out.println("Utilisateur modifié avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    public void rechercherUtilisateur(String nom, String email) {
        try {
            String sqlSearch = "SELECT id, nom, email FROM utilisateurs WHERE nom = ? AND email = ?";
            PreparedStatement pstmtSearch = this.link.connexion.prepareStatement(sqlSearch);
            pstmtSearch.setString(1, nom);
            pstmtSearch.setString(2, email);
            ResultSet rs = pstmtSearch.executeQuery();
            
            System.out.println("Résultats de la recherche :");
            while (rs.next()) {
                System.out.println("ID : " + rs.getInt("id") + ", Nom : " + rs.getString("nom") + ", Email : " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
    }
}
