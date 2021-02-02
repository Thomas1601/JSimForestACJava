package models;

import controllers.Configuration;
import controllers.EtatEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cellule extends JPanel {
    /**
     * Class Cellule
     * */

    // Position de la cellule
    private int xCell;
    private int yCell;

    // Etat de la cellule
    private EtatEnum etatCell;
    private int posEtat = 0;


    public Cellule() {
        // Couleur sellon l'etat
        etatCell = EtatEnum.VIDE;
        setBackground(Color.decode(etatCell.getCouleur()));
        posEtat = 0;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EtatEnum [] etatsPossibles = Configuration.lesEtatsSelonLaSimulation();
                if (posEtat == etatsPossibles.length - 1) {
                    etatCell = etatsPossibles[0];
                    posEtat = 0;
                } else {
                    posEtat++;
                    etatCell = etatsPossibles[posEtat];
                }
                setBackground(Color.decode(etatCell.getCouleur()));
            }
        });
    }

    public Cellule(int x, int y, EtatEnum etat, int posEta) {
        // Couleur sellon l'etat
        etatCell = etat;
        setBackground(Color.decode(etatCell.getCouleur()));
        posEtat = posEta;
        xCell = x;
        yCell = y;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EtatEnum [] etatsPossibles = Configuration.lesEtatsSelonLaSimulation();
                if (posEtat >= etatsPossibles.length - 1) {
                    etatCell = etatsPossibles[0];
                    posEtat = 0;
                } else {
                    posEtat++;
                    etatCell = etatsPossibles[posEtat];
                }
                setBackground(Color.decode(etatCell.getCouleur()));
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Configuration.DIMCELLULEWIDTH, Configuration.DIMCELLULEHEIGHT);
    }

    public int getxCell() {
        return xCell;
    }

    public void setxCell(int xCell) {
        this.xCell = xCell;
    }

    public int getyCell() {
        return yCell;
    }

    public void setyCell(int yCell) {
        this.yCell = yCell;
    }

    public EtatEnum getEtatCell() {
        return etatCell;
    }

    public void setEtatCell(EtatEnum etatCell) {
        this.etatCell = etatCell;
        posEtat++;
    }

    public void update() {
        this.setBackground(Color.decode(etatCell.getCouleur()));
    }

    public int getPosEtat() {
        return posEtat;
    }

    public void setPosEtat(int posEtat) {
        this.posEtat = posEtat;
    }

    // pour base de donnes
    public String toString() {
        return "('" + xCell +"', '" + yCell+ "', '" + etatCelluleToString() + "', '" + posEtat + "')";
    }

    // changer etat pour bas de donner on peut pas stocker une enumeration
    private String etatCelluleToString() {
        if (this.etatCell.equals(EtatEnum.JEUNEPOUSSE)) {
            return "jeunepousse";
        }
        if (this.etatCell.equals(EtatEnum.ARBUSTE)) {
            return "arbuste";
        }
        if (this.etatCell.equals(EtatEnum.ARBRE)) {
            return "arbre";
        }
        if (this.etatCell.equals(EtatEnum.ENFEU)) {
            return "enfeu";
        }
        if (this.etatCell.equals(EtatEnum.ENCENDRE)) {
            return "encendre";
        }
        if (this.etatCell.equals(EtatEnum.INFECTE)) {
            return "infecte";
        }
        return "vide";
    }

}
