package com.monprojet;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        /* On clear la console */
        System.out.print("\033[H\033[2J");   
        System.out.flush();

        System.out.println( "Hello World!" );
        Connexion link = new Connexion();
        GestionUtilisateur gu = new GestionUtilisateur(link);

        /* On demande à l'utilisateur ce qu'il veut faire */
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do { 
            System.out.println("Que voulez-vous faire ?");
            System.out.println("1 - Lister les utilisateurs");
            System.out.println("2 - Ajouter un utilisateur");
            System.out.println("3 - Supprimer un utilisateur par ID");
            System.out.println("4 - Modifier un utilisateur par ID");
            System.out.println("5 - Rechercher un utilisateur par Nom et Email");
            System.out.println("6 - Générer un fichier CSV des utilisateurs");
            System.out.println("0 - Quitter");
            choice = sc.nextInt();
            
            System.out.print("\033[H\033[2J");   
            System.out.flush(); 
            
            switch (choice) {
                case 1:
                    gu.listUtilisateurs();
                    System.out.println("---------------------");
                    break;

                case 2:
                    System.out.print("Nom de l'utilisateur: ");
                    sc.nextLine();
                    String nom = sc.nextLine();

                    System.out.print("Email de l'utilisateur: ");
                    String email = sc.nextLine();

                    Utilisateur utilisateur = new Utilisateur(nom, email);
                    gu.addUtilisateurs(utilisateur);
                    System.out.println("Utilisateur ajouté avec succès!");
                    System.out.println("---------------------");
                    break;
                
                case 3:
                    System.out.print("ID de l'utilisateur à supprimer: ");
                    int idSupp = sc.nextInt();
                    gu.supprimerUtilisateur(idSupp);
                    System.out.println("Utilisateur supprimé avec succès!");
                    System.out.println("---------------------");
                    break;
                
                case 4:
                    System.out.print("ID de l'utilisateur à modifier: ");
                    int idModif = sc.nextInt();
                    sc.nextLine(); 
                    
                    System.out.print("Nouveau nom: ");
                    String newNom = sc.nextLine();
                    
                    System.out.print("Nouvel email: ");
                    String newEmail = sc.nextLine();
                    
                    gu.modifierUtilisateur(idModif, newNom, newEmail);
                    System.out.println("Utilisateur modifié avec succès!");
                    System.out.println("---------------------");
                    break;
                
                case 5:
                    System.out.print("Nom de l'utilisateur: ");
                    sc.nextLine();
                    String rechercheNom = sc.nextLine();
                    
                    System.out.print("Email de l'utilisateur: ");
                    String rechercheEmail = sc.nextLine();
                    
                    gu.rechercherUtilisateur(rechercheNom, rechercheEmail);
                    System.out.println("---------------------");
                    break;
                
                case 6:
                gu.genererCSVUtilisateur("utilisateurs.csv");
                System.out.println("Fichier CSV généré avec succès!");
                System.out.println("---------------------");
                break;
                
                case 0:
                    System.out.println("Fermeture de l'application...");
                    break;
                
                default:
                    System.out.println("Pas d'action pour ce choix !");
                    break;
            }
        } while(choice != 0);

        link.close();
        sc.close();
    }
}
