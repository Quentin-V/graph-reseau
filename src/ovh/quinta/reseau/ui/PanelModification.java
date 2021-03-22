package ovh.quinta.reseau.ui;

import ovh.quinta.reseau.metier.Machine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel utilisateur permettant d'ajouter ou supprimer des éléments du réseau
 */
public class PanelModification extends DarkJPanel {

	/**
	 * La frame de controle
	 */
	ControlFrame ctrl;

	/**
	 * Les textfields d'entrée utilisateur
	 */
	JTextField tfName, tfCout;

	/**
	 * Les boutons du panel
	 */
	DarkJButton btnAjouterRouteur, btnAjouterOrdinateur, btnSupprimerMachine, btnRoute;

	/**
	 * Les ComboBoxes du panel
	 */
	JComboBox<String> comboA, comboB;

	/**
	 * Les panels internes
	 */
	DarkJPanel pnlMachine, pnlRoute;

	/**
	 * Les labels du panel
	 */
	WhiteJLabel lblCout, lblNom, lblRoute, lblRoute2;

	/**
	 * Construction du panel
	 * @param ctrl la frame de controle
	 */
	public PanelModification(ControlFrame ctrl) {
		this.ctrl = ctrl;

		// Instanciation du panel
		pnlMachine = new DarkJPanel();
		pnlRoute = new DarkJPanel();

		// Instanciation des labels
		lblCout = new WhiteJLabel("Cout :");
		lblNom = new WhiteJLabel("Nom :");
		lblRoute = new WhiteJLabel("Route entre :");
		lblRoute2 = new WhiteJLabel("et");

		// Instanciation des textfields
		tfName = new JTextField(10);
		tfCout = new JTextField(3);

		//Instanciation des boutons, ajout des listeners, affectations des actions
		btnAjouterRouteur = new DarkJButton("Ajouter routeur");
		btnAjouterRouteur.addActionListener(ctrl.controlListener);
		btnAjouterRouteur.setActionCommand("addRouteur");

		btnAjouterOrdinateur = new DarkJButton("Ajouter ordinateur");
		btnAjouterOrdinateur.addActionListener(ctrl.controlListener);
		btnAjouterOrdinateur.setActionCommand("addOrdi");

		btnSupprimerMachine = new DarkJButton("Supprimer machine");
		btnSupprimerMachine.addActionListener(ctrl.controlListener);
		btnSupprimerMachine.setActionCommand("delMachine");

		btnRoute = new DarkJButton("Ajouter/Supprimer route");
		btnRoute.addActionListener(ctrl.controlListener);
		btnRoute.setActionCommand("addDelRoute");

		// Instanciation des ComboBoxes
		comboA = new JComboBox<>();
		comboB = new JComboBox<>();

		// Ajout des composants aux panels internes
		pnlMachine.add(lblNom);
		pnlMachine.add(tfName);
		pnlMachine.add(btnAjouterRouteur);
		pnlMachine.add(btnAjouterOrdinateur);
		pnlMachine.add(btnSupprimerMachine);

		pnlRoute.add(lblRoute);
		pnlRoute.add(comboA);
		pnlRoute.add(lblRoute2);
		pnlRoute.add(comboB);
		pnlRoute.add(lblCout);
		pnlRoute.add(tfCout);
		pnlRoute.add(btnRoute);

		// Ajout des panels internes au panel et affectation du layout
		setLayout(new GridLayout(2, 1));
		add(pnlMachine);
		add(pnlRoute);
	}

	/**
	 * Recharge les ComboBoxes avec les machines du réseau
	 * @param machines les machines du réseau
	 */
	void reload(ArrayList<Machine> machines) {
		comboA.removeAllItems();
		comboB.removeAllItems();
		for(Machine m : machines) {
			comboA.addItem(m.getName());
			comboB.addItem(m.getName());
		}
		repaint();
	}

}
