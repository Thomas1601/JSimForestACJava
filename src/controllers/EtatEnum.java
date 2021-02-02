package controllers;

public enum EtatEnum {
    /**
     * Etat d'une cellule
     * */

    VIDE("#FFFFFF"),
    JEUNEPOUSSE("#9EFF9D"),
    ARBUSTE("#2EC036"),
    ARBRE("#19551A"),
    ENFEU("#FF2621"),
    ENCENDRE("#9B9B9B"),
    INFECTE("#9331FF");

    private String couleur;

    EtatEnum(String couleur) {
        this.couleur = couleur;
    }

    public String getCouleur() {
        return this.couleur;
    }
}
