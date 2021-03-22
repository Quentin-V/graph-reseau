package ovh.quinta.reseau.ui;

import ovh.quinta.reseau.metier.Machine;
import ovh.quinta.reseau.metier.Routeur;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel utilisateur permettant d'effectuer les opérations sur le réseau
 */
public class PanelCalcul extends DarkJPanel {

	/**
	 * La frame de controle
	 */
	ControlFrame ctrl;

	/**
	 * Les labels du panel
	 */
	WhiteJLabel lblMachineA, lblMachineB, lblRoute;

	/**
	 * Les panels internes
	 */
	DarkJPanel pnlChemin, pnlRoute;

	/**
	 * Les boutons du panel
	 */
	DarkJButton cheminBtn, routeBtn;

	/**
	 * ComboBoxes du panel
	 */
	JComboBox<String> comboA, comboB, comboRoute;

	/**
	 * Constructeur du panel
	 * @param ctrl La frame de controle
	 */
	PanelCalcul(ControlFrame ctrl) {
		this.ctrl = ctrl;

		// Instanciation des panels
		pnlChemin = new DarkJPanel();
		pnlRoute = new DarkJPanel();

		// Instanciacion des labels
		lblMachineA = new WhiteJLabel("Machine A :");
		lblMachineB = new WhiteJLabel("Machine B :");
		lblRoute = new WhiteJLabel("Table de :");

		// Instanciation des boutons
		cheminBtn = new DarkJButton("Trouver le plus court chemin");
		routeBtn = new DarkJButton("Créer la table de routage");

		// Ajout des listeners et des actions
		cheminBtn.addActionListener(ctrl.controlListener);
		routeBtn.addActionListener(ctrl.controlListener);

		cheminBtn.setActionCommand("chemin");
		routeBtn.setActionCommand("route");

		// Instanciation des ComboBoxes
		comboA = new JComboBox<>();
		comboB = new JComboBox<>();
		comboRoute = new JComboBox<>();

		// Ajout des labels et boutons aux panels internes
		pnlChemin.add(lblMachineA);
		pnlChemin.add(comboA);
		pnlChemin.add(lblMachineB);
		pnlChemin.add(comboB);
		pnlChemin.add(cheminBtn);

		pnlRoute.add(lblRoute);
		pnlRoute.add(comboRoute);
		pnlRoute.add(routeBtn);

		// Affectation du layout du panel
		setLayout(new GridLayout(2, 1));
		add(pnlChemin);
		add(pnlRoute);
	}

	/**
	 * Recharge les ComboBoxes avec les machines du réseau
	 * @param machines les machines du réseau
	 */
	void reload(ArrayList<Machine> machines) {
		comboA.removeAllItems();
		comboB.removeAllItems();
		comboRoute.removeAllItems();
		for(Machine m : machines) {
			comboA.addItem(m.getName());
			comboB.addItem(m.getName());
			if(m instanceof Routeur)
				comboRoute.addItem(m.getName());
		}
		repaint();
	}

}
