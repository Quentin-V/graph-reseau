package ovh.quinta.reseau.ui;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel avec un fond gris
 */
public class DarkJPanel extends JPanel {
	/**
	 * Constructeur sans paramètre
	 */
	DarkJPanel() {
		super();
		setDark();
	}

	/**
	 * Constructeur avec un LayoutManager
	 * @param layout le layoutmanager du panel
	 */
	DarkJPanel(LayoutManager layout) {
		super(layout);
		setDark();
	}

	/**
	 * Affecte la couleur grise au fond du panel
	 */
	private void setDark() {
		setBackground(ControlFrame.background);
	}
}
