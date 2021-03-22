package ovh.quinta.reseau.ui;

import javax.swing.*;
import java.awt.*;

/**
 * JLabel avec le texte blanc
 */
public class WhiteJLabel extends JLabel {
	/**
	 * Constructeur qui prend le texte du JLabel en paramètre
	 * @param text le texte du JLabel
	 */
	WhiteJLabel(String text) {
		super(text);
		setForeground(Color.white);
	}
}
