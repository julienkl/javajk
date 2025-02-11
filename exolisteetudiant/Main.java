package exolisteetudiant;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Etudiant> listeEtudiants = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\nMenu:");
            System.out.println("1. Afficher la liste des étudiants");
            System.out.println("2. Ajouter un étudiant");
            System.out.println("3. Supprimer un étudiant par son nom");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne
            
            switch (choix) {
                case 1:
                    afficherEtudiants();
                    break;
                case 2:
                    ajouterEtudiant();
                    break;
                case 3:
                    supprimerEtudiant();
                    break;
                case 4:
                    continuer = false;
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
        scanner.close();
    }

    private static void afficherEtudiants() {
        if (listeEtudiants.isEmpty()) {
            System.out.println("Aucun étudiant dans la liste.");
        } else {
            for (Etudiant etudiant : listeEtudiants) {
                System.out.println(etudiant);
            }
        }
    }

    private static void ajouterEtudiant() {
        System.out.print("Entrez le nom de l'étudiant: ");
        String nom = scanner.nextLine();
        System.out.print("Entrez le prénom de l'étudiant: ");
        String prenom = scanner.nextLine();
        System.out.print("Entrez la classe de l'étudiant: ");
        String classe = scanner.nextLine();
        listeEtudiants.add(new Etudiant(nom, prenom, classe));
        System.out.println("Étudiant ajouté avec succès.");
    }

    private static void supprimerEtudiant() {
        System.out.print("Entrez le nom de l'étudiant à supprimer: ");
        String nom = scanner.nextLine();
        listeEtudiants.removeIf(etudiant -> etudiant.getNom().equalsIgnoreCase(nom));
        System.out.println("Si l'étudiant existait, il a été supprimé.");
    }
}

