import java.util.Scanner;

public class Ask {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);// création de l'objet 
        
        System.out.println("Enter your name:");
        String nom  = sc.nextLine(); // Lecture de la ligne saisie

        
        System.out.println("Entrez votre age : ");
        int age = sc.nextInt(); // Lecture d'un entier

        System.out.println("Bonjour " + nom + " ! Vous avez " + age + " ans");

        if (age > 18) {
            System.out.println("Vous êtes majeur");
        }else{
            System.out.println("Vous êtes mineur");
        }

        sc.close();// Fermeture du sc 


    }
}