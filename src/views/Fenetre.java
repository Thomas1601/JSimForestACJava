package views;

import controllers.Configuration;
import controllers.Simulation;
import models.Cellule;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Fenetre extends JFrame implements Runnable {
    /**
     * Fenetre
     * */

    // Propritete
    public static final int HEIGHT = 900;
    public static final int WIDTH = 1200;

    // attributes
    public static JTabbedPane jTabbedPane;
    public static JPanel zoneBoutons;
    public static Menu menu;

    public static JPanel cellulePan;
    public static GridBagConstraints gbc;

    private Thread thread;

    // Boutons
    private static JButton demarrer, toutlespas, pause;

    public Fenetre() {
        super("                                                                                                    Simulateur d'environnement Naturelle JSimForest soumis aux incendies et invasions insectes");
        this.setLocationRelativeTo(null);



        // JTabbedPane
        jTabbedPane = new JTabbedPane();

        // Buttons
        zoneBoutons = new JPanel();

        demarrer = new JButton("Pas par pas");
        toutlespas = new JButton("Tout les pas");
        pause = new JButton("Pause");

        zoneBoutons.add(demarrer);
        zoneBoutons.add(toutlespas);
        zoneBoutons.add(pause);

        // Action
        demarrer.addActionListener(v -> {
            // Croissance des arbres
            if (Configuration.SIMULATIONCHOISIT == Configuration.CROISSANCEDESARBRES) {
                Simulation.simulerCoissanceDesArbre();
            }

            // feu de fôret
            if (Configuration.SIMULATIONCHOISIT == Configuration.FEUXDEFORET) {
                Simulation.simulerFeuDeForet();
            }

            // Invasion d'insecte
            if (Configuration.SIMULATIONCHOISIT == Configuration.INVASIONDINSECTES) {
                Simulation.simulerInvasionDInsects();
            }
        });

        // Tous les pas
        toutlespas.addActionListener(v -> {
            // Croissance des arbres
            if (Configuration.SIMULATIONCHOISIT == Configuration.CROISSANCEDESARBRES) {
                thread = new Thread(() -> {
                    try {
                        int pas;
                        for (pas = 0; pas < Configuration.NOMBREDEPAS; pas++) {
                            Simulation.simulerCoissanceDesArbre();
                            Thread.sleep(Configuration.VITESSESIMULATION * 1000);
                        }
                    } catch (InterruptedException e) {

                    }
                });
                thread.start();
            }

            // feu de fôret
            if (Configuration.SIMULATIONCHOISIT == Configuration.FEUXDEFORET) {
                thread = new Thread(() -> {
                    try {
                        int pas;
                        for (pas = 0; pas < Configuration.NOMBREDEPAS; pas++) {
                            Simulation.simulerFeuDeForet();
                            Thread.sleep(Configuration.VITESSESIMULATION * 1000);
                        }
                    } catch (InterruptedException e) {

                    }
                });
                thread.start();
            }

            // Invasion d'insecte
            if (Configuration.SIMULATIONCHOISIT == Configuration.INVASIONDINSECTES) {
                thread = new Thread(() -> {
                    try {
                        int pas;
                        for (pas = 0; pas < Configuration.NOMBREDEPAS; pas++) {
                            Simulation.simulerInvasionDInsects();
                            Thread.sleep(Configuration.VITESSESIMULATION * 1000);
                        }
                    } catch (InterruptedException ignored) { }
                });
                thread.start();
            }
        });

        // Pause
        pause.addActionListener(v -> {
            try {
                thread.interrupt();
            } catch (Exception ignored) {}
        });

        // Menu
        menu = new Menu();

        setCellules();

        // Add elements
        jTabbedPane.add("Simulation", cellulePan);
    }

    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) { }
        // definir la fenetre
        this.setJMenuBar(menu);
        this.getContentPane().add(jTabbedPane, BorderLayout.CENTER);
        this.getContentPane().add(zoneBoutons, BorderLayout.SOUTH);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public static void changerGrille() {
        setCellules();
        jTabbedPane.removeAll();
        jTabbedPane.add("Simulation", cellulePan);
    }

    public static void setCellules() {
        cellulePan = new JPanel();
        cellulePan.setLayout(new GridBagLayout());
        cellulePan.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // ajouter les cellule
        gbc = new GridBagConstraints();
        // Tableau des dellules
        Configuration.CELLULES = new Cellule[Configuration.LONGEURGRILLE][Configuration.LARGEURGRILLE];
        // Remplissage
        for (int row = 0; row < Configuration.LONGEURGRILLE; row++) {
            for (int col = 0; col < Configuration.LARGEURGRILLE; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                // une cellule
                Cellule cellule = new Cellule();
                cellule.setxCell(col);
                cellule.setyCell(row);
                Border border = null;
                if (row < Configuration.LONGEURGRILLE - 1) {
                    if (col < Configuration.LARGEURGRILLE - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < Configuration.LARGEURGRILLE - 1) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                Configuration.CELLULES[row][col] = cellule;
                cellule.setBorder(border);
                cellulePan.add(cellule, gbc);
            }
        }
    }

    public static void dessinerNouveauEtat(Cellule[][] nouveau) {
        cellulePan = new JPanel();
        cellulePan.setLayout(new GridBagLayout());
        cellulePan.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // ajouter les cellule
        gbc = new GridBagConstraints();
        // Tableau des dellules
        Configuration.CELLULES = null;
        Configuration.CELLULES = new Cellule[Configuration.LONGEURGRILLE][Configuration.LARGEURGRILLE];
        // Remplissage
        for (int row = 0; row < Configuration.LONGEURGRILLE; row++) {
            for (int col = 0; col < Configuration.LARGEURGRILLE; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                Cellule cellule = nouveau[row][col];
                Border border;
                if (row < Configuration.LONGEURGRILLE - 1) {
                    if (col < Configuration.LARGEURGRILLE - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < Configuration.LARGEURGRILLE - 1) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                Configuration.CELLULES[row][col] = cellule;
                cellule.setBorder(border);
                cellulePan.add(cellule, gbc);
                cellule.update();
            }
        }
        jTabbedPane.removeAll();
        jTabbedPane.add("Simulation", cellulePan);
    }

}
