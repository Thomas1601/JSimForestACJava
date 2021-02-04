package views;

import controllers.Configuration;
import database.DatabaseJSimForest;

import javax.swing.*;
import java.awt.*;

public class Menu extends JMenuBar {
    /**
     * Menu de l'application
     * */
    private final int width = 0;
    private final int height = 28;

    // Menus
    private static JMenu fichier, configurer, simuler;
    // Fichier
    private static JMenuItem sauvegarder, charger, quitter;
    // simuler
    private static JMenuItem croissance, feux, insect;
    // Configutrer
    private static JMenuItem conf;

    public Menu() {
        super();

        // def menus
        fichier = new JMenu("Fichier");
        configurer = new JMenu("Configurer");
        simuler = new JMenu("Simuler");

        // def sous menus
        // Fichier
        sauvegarder = new JMenuItem("Sauvegarder");
        charger = new JMenuItem("Charger");
        quitter = new JMenuItem("Quitter");

        // Simuler
        croissance = new JMenuItem("Croissance des arbres");
        feux = new JMenuItem("Feu de forêt");
        insect = new JMenuItem("Invasion d'insectes");

        // Configurer
        conf = new JMenuItem("Configurer");

        // Ajouter les elements

        fichier.add(sauvegarder);
        fichier.add(charger);
        fichier.addSeparator();
        fichier.add(quitter);

        simuler.add(croissance);
        simuler.addSeparator();
        simuler.add(feux);
        simuler.addSeparator();
        simuler.add(insect);

        configurer.add(conf);

        // ajouter les effets
        conf.addActionListener(v -> {
            new Proprietes();
        });
        quitter.addActionListener(v -> {
            System.exit(0);
        });

        croissance.addActionListener(v -> {
            Configuration.SIMULATIONCHOISIT = Configuration.CROISSANCEDESARBRES;
        });

        feux.addActionListener(v -> {
            Configuration.SIMULATIONCHOISIT = Configuration.FEUXDEFORET;
        });

        insect.addActionListener(v -> {
            Configuration.SIMULATIONCHOISIT = Configuration.INVASIONDINSECTES;
        });

        sauvegarder.addActionListener(v -> {
            // Pour ne pas bloquer l'application
            new Thread(DatabaseJSimForest::sauvegarder).start();
        });

        charger.addActionListener(v -> {
            new Thread(DatabaseJSimForest::chargerlesInfos).start();
        });

        // ajouter les menus
        this.add(fichier);
        this.add(configurer);
        this.add(simuler);
        this.setPreferredSize(new Dimension(width, height));
    }

}
