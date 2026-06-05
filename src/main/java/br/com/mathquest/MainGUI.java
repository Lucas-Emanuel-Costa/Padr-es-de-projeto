package br.com.mathquest;

import br.com.mathquest.gui.MathQuestFrame;

import javax.swing.SwingUtilities;

public class MainGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MathQuestFrame().setVisible(true));
    }
}
