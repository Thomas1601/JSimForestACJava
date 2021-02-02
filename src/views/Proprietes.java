package views;

import controllers.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Proprietes extends JOptionPane {
    /**
     * Configuration
     * */
    private JPanel panel;
    public static final String[] LABEL_TEXTS = {"Longeur", "Largeur", "Nombre de pas", "Rapidité"};
    public static final int COLS = 8;
    private Map<String, JTextField> labelFieldMap = new HashMap<>();

    public Proprietes() {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        for (int i = 0; i < LABEL_TEXTS.length; i++) {
            String labelText =  LABEL_TEXTS[i];
            panel.add(new JLabel(labelText), createGbc(0, i));

            JTextField textField = new JTextField(COLS);
            labelFieldMap.put(labelText, textField);
            panel.add(textField, createGbc(1, i));
        }

        // avoir conf
        labelFieldMap.get("Longeur").setText(String.valueOf(Configuration.LONGEURGRILLE));
        labelFieldMap.get("Largeur").setText(String.valueOf(Configuration.LARGEURGRILLE));
        labelFieldMap.get("Nombre de pas").setText(String.valueOf(Configuration.NOMBREDEPAS));
        labelFieldMap.get("Rapidité").setText(String.valueOf(Configuration.VITESSESIMULATION));

        panel.setBorder(BorderFactory.createTitledBorder("Configurer la simulation"));

        String[] options = {"Valider", "Annuler"};
        int reply = JOptionPane.showOptionDialog(null, panel, "Configuration", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, initialValue);

        if (reply == 0) {
            Configuration.LONGEURGRILLE = Integer.parseInt(labelFieldMap.get("Longeur").getText());
            Configuration.LARGEURGRILLE = Integer.parseInt(labelFieldMap.get("Largeur").getText());
            Configuration.NOMBREDEPAS = Integer.parseInt(labelFieldMap.get("Nombre de pas").getText());
            Configuration.VITESSESIMULATION = Integer.parseInt(labelFieldMap.get("Rapidité").getText());

            Fenetre.changerGrille();
        }

    }

    public static GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1.0;
        gbc.weighty = gbc.weightx;
        if (x == 0) {
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(3, 3, 3, 8);
        } else {
            gbc.anchor = GridBagConstraints.LINE_END;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(3, 3, 3, 3);
        }
        return gbc;
    }

    public String getText(String labelText) {
        JTextField textField = labelFieldMap.get(labelText);
        if (textField != null) {
            return textField.getText();
        } else {
            throw new IllegalArgumentException(labelText);
        }
    }

}
