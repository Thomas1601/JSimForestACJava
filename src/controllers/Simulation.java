package controllers;

import models.Cellule;
import views.Fenetre;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static void simulerCoissanceDesArbre() {
        Cellule[][] nouveau = new Cellule[Configuration.LONGEURGRILLE][Configuration.LARGEURGRILLE];
        List<Cellule> voisins;
        Cellule cellule;
        int row, col;
        for ( row = 0; row < Configuration.LONGEURGRILLE; row++) {
            for (col = 0; col < Configuration.LARGEURGRILLE; col++) {

                //APPLICATION DU VOISINAGE DE MOORE LORS DE LA LECTURE CROISSANCE
                cellule = Configuration.CELLULES[row][col];
                voisins = voisinDeMoore(cellule);
                Cellule c = nouveauEtatCoissanceArbre(cellule, voisins);
                nouveau[row][col] = new Cellule(c.getxCell(), c.getyCell(), c.getEtatCell(), c.getPosEtat());
            }
        }
        // incrementer le pas
        Configuration.PASACTUELLE = Configuration.PASACTUELLE + 1;
        Fenetre.dessinerNouveauEtat(nouveau);
    }


    public static void simulerFeuDeForet() {
        Cellule[][] nouveau = new Cellule[Configuration.LONGEURGRILLE][Configuration.LARGEURGRILLE];
        List<Cellule> voisins;
        Cellule cellule;
        int row, col;
        for ( row = 0; row < Configuration.LONGEURGRILLE; row++) {
            for (col = 0; col < Configuration.LARGEURGRILLE; col++) {

                //APPLICATION DU VOISINAGE DE VON NEUMANN LORS DE L\'INCENDIE
                cellule = Configuration.CELLULES[row][col];
                voisins = voisinDeMoore(cellule);
                Cellule c = nouveauEtatFeuDeForet(cellule, voisins);
                nouveau[row][col] = new Cellule(c.getxCell(), c.getyCell(), c.getEtatCell(), c.getPosEtat());
            }
        }
        Configuration.PASACTUELLE = Configuration.PASACTUELLE + 1;
        Fenetre.dessinerNouveauEtat(nouveau);
    }

    public static void simulerInvasionDInsects() {
        Cellule[][] nouveau = new Cellule[Configuration.LONGEURGRILLE][Configuration.LARGEURGRILLE];
        List<Cellule> voisins;
        Cellule cellule;
        int row, col;
        for ( row = 0; row < Configuration.LONGEURGRILLE; row++) {
            for (col = 0; col < Configuration.LARGEURGRILLE; col++) {

                //APPLICATION DU VOISINAGE DE VON NEUMANN LORS DE L\'INFECTION
                cellule = Configuration.CELLULES[row][col];
                voisins = voisinDeVonNeumann(cellule);
                Cellule c = nouveauEtatInvesionInsect(cellule, voisins);
                nouveau[row][col] = new Cellule(c.getxCell(), c.getyCell(), c.getEtatCell(), c.getPosEtat());
            }
        }
        Configuration.PASACTUELLE = Configuration.PASACTUELLE + 1;
        Fenetre.dessinerNouveauEtat(nouveau);
    }

    public static List<Cellule> voisinDeMoore(Cellule cellule) {
        // Ensemble des celulle voisin
        List<Cellule> voisins = new ArrayList<>();
        // Cordonnée des la cellule
        int row = cellule.getxCell();
        int col = cellule.getyCell();

        //AJOUT DE LA CLASSE LIMITE DE LA GRILLE AU VOISINAGE MOORE
                ajouterVoisin(row + 1, col, voisins);
                ajouterVoisin(row - 1, col, voisins);
                ajouterVoisin(row + 1, col+1, voisins);
                ajouterVoisin(row - 1, col -1, voisins);
                ajouterVoisin(row + 1, col -1, voisins);
                ajouterVoisin(row - 1, col+1, voisins);
                ajouterVoisin(row, col -1, voisins);
                ajouterVoisin(row, col+1, voisins);

        return voisins;
    }
        //AJOUT DE CETTE CLASSE POUR FAIRE EN SORTE QUE QUAND LE VOISINAGE
        // NE DEPASSE PAS LES LIMITES DE LA GRILLE ET CONTINU
        private static void ajouterVoisin(int X, int Y, List<Cellule> voisins) {
            if ((X >= 0) && (X < Configuration.LARGEURGRILLE) && (Y>=0) && (Y < Configuration.LONGEURGRILLE)){
                voisins.add(Configuration.CELLULES[Y][X]);
                }
        }

    public static List<Cellule> voisinDeVonNeumann(Cellule cellule) {
        // Ensemble des celulle voisin
        List<Cellule> voisins = new ArrayList<>();
        // Cordonnée des la cellule
        int row = cellule.getxCell();
        int col = cellule.getyCell();

        //AJOUT DE LA CLASSE LIMITE DE LA GRILLE AU VOISINAGE VON NEUMANN
        ajouterVoisin(row, col+1, voisins);
            ajouterVoisin(row, col-1, voisins);
            ajouterVoisin(row + 1, col, voisins);
            ajouterVoisin(row - 1, col, voisins);

        return voisins;
    }

    private static Cellule nouveauEtatCoissanceArbre(Cellule cellule, List<Cellule> voisins) {
// TEST POUR SAVOIR SI LE VOISINAGE ME RENVOI LES BONNES COORDONNEES DE LA GRILLE
        /*
        System.out .println("*********************");
        System.out.println(cellule.getxCell() + " " + cellule.getyCell());
*/


        int nbArbuste = 0;
        int nbArbre = 0;

        for (Cellule c : voisins) {
            if (c.getEtatCell().equals(EtatEnum.ARBUSTE))
                nbArbuste++;
            if (c.getEtatCell().equals(EtatEnum.ARBRE))
                nbArbre++;

// TEST POUR SAVOIR SI LE VOISINAGE ME RENVOI LES BONNES COORDONNEES DE LA GRILLE POUR JEUNE POUSSE -> ARBUSTE
            /*
            System.out.println(c.getxCell() + " " + c.getyCell());
*/
        }

        // Naissances des arbres
        if (cellule.getEtatCell().equals(EtatEnum.VIDE)) {
            if ((nbArbre >= 2)) {
                cellule.setEtatCell(EtatEnum.JEUNEPOUSSE);
            }
            if ((nbArbuste >= 3)) {
                cellule.setEtatCell(EtatEnum.JEUNEPOUSSE);
            }
            if ((nbArbre == 1) && (nbArbuste == 2)) {
                cellule.setEtatCell(EtatEnum.JEUNEPOUSSE);
            }
            nbArbre = 0;
            nbArbuste = 0;
            return cellule;
        }

        // Croissance des jeunes
        if (cellule.getEtatCell().equals(EtatEnum.JEUNEPOUSSE)) {
            if ((nbArbre <= 3) && (nbArbuste <= 3)) {
                cellule.setEtatCell(EtatEnum.ARBUSTE);
            }
            nbArbre = 0;
            nbArbuste = 0;
            return cellule;
        }

    // Croissance des arbustes
        if (cellule.getEtatCell().equals(EtatEnum.ARBUSTE)) {
            if ((Configuration.PASACTUELLE > 0) && (Configuration.PASACTUELLE % 2 == 0)) {
                cellule.setEtatCell(EtatEnum.ARBRE);
            }
            nbArbre = 0;
            nbArbuste = 0;
            return cellule;
        }
        nbArbre = 0;
        nbArbuste = 0;
        return cellule;
    }

    private static Cellule nouveauEtatFeuDeForet(Cellule cellule, List<Cellule> voisins) {

        int nbFeu = 0;

        for (Cellule c : voisins) {
            if (c.getEtatCell().equals(EtatEnum.ENFEU))
                nbFeu++;
        }

        if (cellule.getEtatCell().equals(EtatEnum.ENFEU)) {
            cellule.setEtatCell(EtatEnum.ENCENDRE);
            return cellule;
        }

        if (cellule.getEtatCell().equals(EtatEnum.ENCENDRE)) {
            cellule.setEtatCell(EtatEnum.VIDE);
            return cellule;
        }

        if (nbFeu >= 1) {
            // 25 % de chance
            if (cellule.getEtatCell().equals(EtatEnum.JEUNEPOUSSE)) {
                double prob = 1 - Math.random();
                if (prob >= 0.75) {
                    cellule.setEtatCell(EtatEnum.ENFEU);
                }
                nbFeu = 0;
                return cellule;
            }

            // 50 % de chance
            if (cellule.getEtatCell().equals(EtatEnum.ARBUSTE)) {
                double prob = 1 - Math.random();
                if (prob >= 0.5) {
                    cellule.setEtatCell(EtatEnum.ENFEU);
                }
                nbFeu = 0;
                return cellule;
            }

            // 75 % de chance
            if (cellule.getEtatCell().equals(EtatEnum.ARBRE)) {
                double prob = 1 - Math.random();
                if (prob >= 0.25) {
                    cellule.setEtatCell(EtatEnum.ENFEU);
                }
                nbFeu = 0;
                return cellule;
            }
        }

        return cellule;
    }

    private static Cellule nouveauEtatInvesionInsect(Cellule cellule, List<Cellule> voisins) {

        int nbInfec = 0;

        for (Cellule c : voisins) {
            if (c.getEtatCell().equals(EtatEnum.INFECTE))
                nbInfec++;
        }

        if (cellule.getEtatCell().equals(EtatEnum.INFECTE)) {
            cellule.setEtatCell(EtatEnum.VIDE);
            return cellule;
        }

        if (nbInfec >= 1) {
            // 75 % de chance
            if (cellule.getEtatCell().equals(EtatEnum.JEUNEPOUSSE)) {
                double prob = 1 - Math.random();
                if (prob >= 0.25) {
                    cellule.setEtatCell(EtatEnum.INFECTE);
                }
                nbInfec = 0;
                return cellule;
            }

            // 50 % de chance
            if (cellule.getEtatCell().equals(EtatEnum.ARBUSTE)) {
                double prob = 1 - Math.random();
                if (prob >= 0.5) {
                    cellule.setEtatCell(EtatEnum.INFECTE);
                }
                nbInfec = 0;
                return cellule;
            }

            // 25 % de chance
            if (cellule.getEtatCell().equals(EtatEnum.ARBRE)) {
                double prob = 1 - Math.random();
                if (prob >= 0.75) {
                    cellule.setEtatCell(EtatEnum.INFECTE);
                }
                nbInfec = 0;
                return cellule;
            }
        }

        return cellule;
    }
}
