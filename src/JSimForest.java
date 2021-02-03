import views.Fenetre;

import javax.swing.*;

public class JSimForest {
    /**
     * Class principale de JSimForest
     * */


    // Fenetre
    public static Fenetre fenetre;

    public static void main(String[] args) {
        fenetre = new Fenetre();
        fenetre.run();

        //FERMETURE DE LA SIMULATION EN DOUCEUR ET NON BRUTALE
        fenetre.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //POSITION DE LA FENETRE CENTRER PAR RAPPORT A CELLE DU BUREAU
        fenetre.setLocationRelativeTo(null);

        //DIMENSION DE LA FENETRE NON REDIMENSIONNABLE (FIXE)
        fenetre.setResizable(false);
    }

}
