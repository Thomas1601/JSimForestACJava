package database;

import controllers.Configuration;
import controllers.EtatEnum;
import models.Cellule;
import views.Fenetre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseJSimForest {

    public static final String url = "jdbc:mysql://localhost:3306/jsimforest";
    public static final String user = "root";
    public static final String password = "";
    public static final String viderConfiguration = "TRUNCATE TABLE `configuration`";
    public static final String viderCellues = "TRUNCATE TABLE `cellule`";


    public static void sauvegarder() {
        Connection connection;
        Statement statement;
        String sqlCOnfiguration = "INSERT INTO `configuration` (`longeurgrille`, `largeurgrille`, `nombredepas`, `pasactuelle`, `vitessesimulation`) VALUES ('" + Configuration.LONGEURGRILLE + "', '" + Configuration.LARGEURGRILLE + "', '" + Configuration.NOMBREDEPAS + "', '" + Configuration.PASACTUELLE + "', '" + Configuration.VITESSESIMULATION + "')";
        // Sauvegarde de la configuration
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            // Vider la table
            statement.execute(viderConfiguration);
            // inserer les nouvelles valeurs
            statement.execute(sqlCOnfiguration);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // Sauvegarde des cellules
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            // Vider la table
            statement.execute(viderCellues);
            // inserer les nouvelles valeurs
            StringBuilder sqlCellues = new StringBuilder("INSERT INTO `cellule` (`xcell`, `ycell`, `etatcell`, `posetat`) VALUES ");
            for (int row = 0; row < Configuration.LONGEURGRILLE; row++) {
                for (int col = 0; col < Configuration.LARGEURGRILLE; col++) {
                    sqlCellues.append(Configuration.CELLULES[row][col].toString());
                    if ((row == Configuration.LONGEURGRILLE - 1) && (col == Configuration.LARGEURGRILLE - 1)) {
                        sqlCellues.append(";");
                    } else {
                        sqlCellues.append(", ");
                    }
                }
            }
            statement.execute(String.valueOf(sqlCellues));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void chargerlesInfos() {
        Connection connection;
        Statement statement;
        String sqlCOnfiguration = "SELECT * FROM `configuration`";
        String sqlCellules = "SELECT * FROM `cellule`";
        // Selection de la configuration
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            // lecture de la table
            ResultSet result = statement.executeQuery(sqlCOnfiguration);
            while (result.next()) {
                Configuration.LONGEURGRILLE = result.getInt("longeurgrille");
                Configuration.LARGEURGRILLE = result.getInt("largeurgrille");
                Configuration.NOMBREDEPAS = result.getInt("nombredepas");
                Configuration.VITESSESIMULATION = result.getInt("vitessesimulation");
                Configuration.PASACTUELLE = result.getInt("pasactuelle");
                // afficher
                Fenetre.changerGrille();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // Selection des cellules
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            // charger les infos
            ResultSet result = statement.executeQuery(sqlCellules);
            Cellule[][] list = new Cellule[Configuration.LONGEURGRILLE][Configuration.LARGEURGRILLE];
            int row, col, pos;
            String etat;
            while (result.next()) {
                row = result.getInt("ycell");
                col = result.getInt("xcell");
                etat = result.getString("etatcell");
                pos = result.getInt("posetat");

                list[row][col] = new Cellule(col, row, stringToEtatCellule(etat), pos);
            }
            // afficher les cellules recuperees
            Fenetre.dessinerNouveauEtat(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static EtatEnum stringToEtatCellule(String etat) {
        if (etat.equalsIgnoreCase("jeunepousse")) {
            return EtatEnum.JEUNEPOUSSE;
        }
        if (etat.equalsIgnoreCase("arbuste")) {
            return EtatEnum.ARBUSTE;
        }
        if (etat.equalsIgnoreCase("arbre")) {
            return EtatEnum.ARBRE;
        }
        if (etat.equalsIgnoreCase("enfeu")) {
            return EtatEnum.ENFEU;
        }
        if (etat.equalsIgnoreCase("encendre")) {
            return EtatEnum.ENCENDRE;
        }
        if (etat.equalsIgnoreCase("infecte")) {
            return EtatEnum.INFECTE;
        }
        return EtatEnum.VIDE;
    }
}

