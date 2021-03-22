package ovh.quinta.reseau.ui;

import javax.swing.*;
import java.awt.*;

/**
 * JButton à fond gris et texte blanc
 */
public class DarkJButton extends JButton {
	/**
	 * Constructeur du bouton qui prend le nom/texte en paramètre
	 * @param btnName nom du bouton
	 */
	DarkJButton(String btnName) {
		super(btnName);
		this.setBackground(ControlFrame.background);
		this.setForeground(Color.white);
	}
}
