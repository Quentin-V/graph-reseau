package ovh.quinta.reseau.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener des boutons de la ControlFrame
 */
public class ControlListener implements ActionListener {

	/**
	 * La ControlFrame
	 */
	ControlFrame ctrl;

	/**
	 * Constructeur du listener
	 * @param c la ControlFrame
	 */
	public ControlListener(ControlFrame c) {
		ctrl = c;
	}

	/**
	 * Méthode qui appelle les fonctions de la fenêtre de controle en fonction du bouton appuyé
	 * @param e l'event de l'action performée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "chemin" -> ctrl.plusCourtChemin();
			case "route" -> ctrl.afficherTableRoutage();
			case "pnlCalcul", "pnlModif" -> ctrl.showPanel(e.getActionCommand());
			case "addRouteur" -> ctrl.addRouteur();
			case "addOrdi" -> ctrl.addOrdi();
			case "delMachine" -> ctrl.delMachine();
			case "addDelRoute" -> ctrl.addDelRoute();
		}
	}
}
