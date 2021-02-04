package controllers;

import models.Cellule;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    /**
     * Configuration generale de l'application
     * */
    // Valeurs par defauts
    public static final int DEFAULTVALEWIDTH = 50;
    public static final int DEFAULTVALEHEIGHT = 50;

    public static final int CROISSANCEDESARBRES = 1;
    public static final int FEUXDEFORET = 2;
    public static final int INVASIONDINSECTES = 3;

    public static final int DIMCELLULEWIDTH = 20;
    public static final int DIMCELLULEHEIGHT = 20;

    // Longeur et Largeur globale
    public static int LONGEURGRILLE = DEFAULTVALEWIDTH;
    public static int LARGEURGRILLE = DEFAULTVALEHEIGHT;

    // Nombre de pas
    public static int NOMBREDEPAS = 20;

    // Pas Actuel
    public static int PASACTUELLE = 0;

    // Vitesse
    public static int VITESSESIMULATION = 1;

    // Toutes les cellule de la grille
    public static Cellule[][] CELLULES;

    // Etat de simulation choisie
    public static int SIMULATIONCHOISIT = CROISSANCEDESARBRES;

    public static final EtatEnum[] ETATSCROISSANCE = {EtatEnum.VIDE, EtatEnum.JEUNEPOUSSE, EtatEnum.ARBUSTE, EtatEnum.ARBRE};
    public static final EtatEnum[] ETATSFEUFFORET = {EtatEnum.VIDE, EtatEnum.ENFEU, EtatEnum.ENCENDRE};
    public static final EtatEnum[] ETATSINVASIONINSECTS = {EtatEnum.VIDE, EtatEnum.INFECTE};

    public static EtatEnum[] lesEtatsSelonLaSimulation() {
        if (SIMULATIONCHOISIT == CROISSANCEDESARBRES)
            return ETATSCROISSANCE;
        if (SIMULATIONCHOISIT == FEUXDEFORET)
            return ETATSFEUFFORET;
        if (SIMULATIONCHOISIT == INVASIONDINSECTES)
            return ETATSINVASIONINSECTS;
        return ETATSCROISSANCE;
    }
}
