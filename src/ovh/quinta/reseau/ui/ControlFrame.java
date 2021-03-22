package ovh.quinta.reseau.ui;

import ovh.quinta.reseau.*;
import ovh.quinta.reseau.metier.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Frame qui permet à l'utilisateur d'effectuer les actions sur le réseau
 */
public class ControlFrame extends JFrame {

	/**
	 * Couleur du background (Dark Theme) utilisé dans plusieurs phrases
	 */
	public static final Color background = new Color(68, 68, 68);

	/**
	 * Main (Controler)
	 */
	Main main;

	/**
	 * Listener des boutons de l'interface
	 */
	ControlListener controlListener;

	/**
	 * Panels de l'interface
	 */
	DarkJPanel menu, pnlAction;
	/**
	 * Boutons du menu
	 */
	JButton btnCalcul, btnModif;

	/**
	 * Panel de calcul
	 */
	PanelCalcul pnlCalcul;

	/**
	 * Panel de modification du réseau
	 */
	PanelModification pnlModification;

	/**
	 * Constructeur de la frame de controle
	 * @param main Main (Controler)
	 */
	public ControlFrame(Main main) {
		// Affectation du main
		this.main = main;

		// Instanciation du listener
		controlListener = new ControlListener(this);

		// Instanciation des panels de controle et invisibilisation du panel de modification
		pnlCalcul = new PanelCalcul(this);
		pnlModification = new PanelModification(this);
		pnlModification.setVisible(false);

		// Instanciation des boutons, ajout des listeners et des actions
		btnCalcul = new JButton("Calcul");
		btnCalcul.addActionListener(controlListener);
		btnCalcul.setActionCommand("pnlCalcul");

		btnModif = new JButton("Modification réseau");
		btnModif.addActionListener(controlListener);
		btnModif.setActionCommand("pnlModif");

		// Instanciation du menu et ajout des boutons
		menu = new DarkJPanel(new GridLayout(1, 2));
		menu.add(btnCalcul);
		menu.add(btnModif);

		// Instanciation du panel conteneur et ajout des panels internes
		pnlAction = new DarkJPanel();
		pnlAction.add(pnlCalcul);
		pnlAction.add(pnlModification);

		// Ajout deu menu et panel conteneur à la fenêtre
		add(menu, BorderLayout.NORTH);
		add(pnlAction);

		// Cchangement du titre, de la taille de fenêtre et empêche le fenêtre de changer de taille
		setTitle("Action réseau");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Appelle la méthode pour afficher le plus court chemin entre
	 * les deux machines sélectionnées par l'utilisateur
	 */
	public void plusCourtChemin() {
		String a, b;
		a = (String) pnlCalcul.comboA.getSelectedItem();
		b = (String) pnlCalcul.comboB.getSelectedItem();
		main.plusCourtChemin(a, b);
	}

	/**
	 * Appelle la méthode pour afficher la table de routage
	 * de la machine sélectionée par l'utilisateur
	 */
	public void afficherTableRoutage() {
		String machine = (String) pnlCalcul.comboRoute.getSelectedItem();
		main.tableRoutage(machine);
	}

	/**
	 * Appelle la méthode pour ajouter un routeur au réseau
	 */
	public void addRouteur() {
		addMachine(true);
	}

	/**
	 * Appelle la méthode pour ajouter un ordinateur au réseau
	 */
	public void addOrdi() {
		addMachine(false);
	}

	/**
	 * Appelle la méthode pour ajouter un routeur ou ordinateur au réseau
	 * @param isRouteur paramètre qui définit si la machine à ajouter est un routeur
	 */
	private void addMachine(boolean isRouteur) {
		String name = pnlModification.tfName.getText();
		if(name == null) {
			errorMessage("Veuillez spécifier un nom pour la machine à ajouter");
			return;
		}
		if(isRouteur) main.addRouteur(name);
		else main.addOrdi(name);
		reload();
	}

	/**
	 * Appelle la méthode pour ajouter ou supprimer une route dans le réseau
	 */
	public void addDelRoute() {
		if(!pnlModification.tfCout.getText().matches("^[0-9]*$")) return;
		String a = (String) pnlModification.comboA.getSelectedItem();
		String b = (String) pnlModification.comboB.getSelectedItem();
		assert a != null;
		assert b != null;
		if(a.equals(b)) {
			errorMessage("Impossible de créer une route qui retourne sur la même machine");
			return;
		}
		main.addDelRoute(a, b, pnlModification.tfCout.getText());
	}

	/**
	 * Appelle la méthode pour supprimer une route dans le réseau
	 */
	public void delMachine() {
		String nomMachine = pnlModification.tfName.getText();
		if(nomMachine == null) {
			errorMessage("Veuillez spécifier le nom de la machine à supprimer");
			return;
		}
		main.delMachine(pnlModification.tfName.getText());
	}

	/**
	 * Affiche le panel interne voulu en fonction du choix de l'utilisateur
	 * @param panel Un string qui définit quel panel à afficher
	 */
	public void showPanel(String panel) {
		boolean showPanelCalcul = panel.equals("pnlCalcul");
		pnlCalcul.setVisible(showPanelCalcul);
		pnlModification.setVisible(!showPanelCalcul);
		reload();
	}

	/**
	 * Reload les panels internes
	 */
	public void reload() {
		ArrayList<Machine> machines = main.getMachines();
		pnlCalcul.reload(machines);
		pnlModification.reload(machines);
		pack();
		repaint();
	}

	/**
	 * Ouvre un pop up d'erreur
	 * @param message le message d'erreur
	 */
	public void errorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
}
