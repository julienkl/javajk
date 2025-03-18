package com.example;

public class Utilisateur {

    private int id;
    private String nom;
    private String email;

    // Constructeur
    public Utilisateur(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    // Getter et setter pour l'ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et setter pour le nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter et setter pour l'email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Méthode pour vérifier si le nom est valide
    public boolean isValidNom() {
        // Exemple simple : le nom ne doit pas être vide
        return nom != null && !nom.trim().isEmpty();
    }

    // Méthode toString() pour afficher les informations de l'utilisateur (facultatif)
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
