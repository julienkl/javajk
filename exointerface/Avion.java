package exointerface;

public class Avion implements Transport {
    @Override
    public void deplacer() {
        System.out.println("L'avion vole dans le ciel.");
    }
}