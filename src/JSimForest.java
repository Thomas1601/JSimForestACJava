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
        fenetre.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
    }

}
