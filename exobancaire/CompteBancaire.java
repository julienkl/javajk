package exobancaire;

public class CompteBancaire {
   
    private String titulaire;
    private double solde;


    public CompteBancaire(String titulaire, double montant){
         this.titulaire = titulaire;
         this.solde = montant;
    }


    public void deposer(double montant){
      if (montant > 0){
         this.solde += montant;
         System.out.println(this.titulaire + ":" + montant+"a ete depose sur le compte .");
      }
      
    }

    public void retirer(double montant){
       if((this.solde - montant) > 0){
         this.solde -= montant;
         System.out.println(this.titulaire + ":" + montant +"a ete retire de le compte .");
       } else {
        System.out.println(this.titulaire + ": Vous n'avez que" + this.solde +"sur votre compte .");
       }
    }
}


