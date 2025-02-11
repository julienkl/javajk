package exointerface;

// Implémentation de l'interface dans la classe Voiture
public class Voiture implements Transport {
    @Override
    public void deplacer() {
        System.out.println("La voiture roule sur la route.");
    }
}