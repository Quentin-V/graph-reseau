package ovh.quinta.reseau.ui;

import ovh.quinta.reseau.metier.Machine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Fenêtre représentant une table de routage
 */
public class TableRoutage extends JFrame {
	/**
	 * Constructeur de la fenêtre
	 * @param table HashMap représentant la table de routage
	 * @param machine Le nom de la machine représentée par la table
	 */
	public TableRoutage(Map<Machine, ArrayList<HashMap<Machine, Integer>>> table, String machine) {
		// Panel principal contenant X lignes et une colonne ou X est le nombre de machines du réseau
		DarkJPanel container = new DarkJPanel(new GridLayout(table.size(), 1));

		// Pour chaque machine de la table, on crée la ligne la représentant dans la fenêtre
		table.forEach((k, v) -> {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			JPanel pnl = new JPanel(flowLayout);
			pnl.setBackground(ControlFrame.background);
			StringBuilder labelString = new StringBuilder();
			labelString.append(k).append(" : ");
			for(HashMap<Machine, Integer> hash : v) {
				hash.forEach((k2, v2) -> labelString.append(k2).append("(").append(v2).append("), "));
			}
			WhiteJLabel lbl = new WhiteJLabel(labelString.substring(0, labelString.length() - 2));
			lbl.setFont(new Font(lbl.getName(), Font.PLAIN, 20));
			pnl.add(lbl);
			container.add(pnl);
		});
		// Panel scrollable au cas où il y a trop de machines à représenter
		JScrollPane jScrollPane = new JScrollPane(container);
		add(jScrollPane);

		// Affectation de la taille de la fenêtre, 500 en largeur et maximum 700 en hauteur.
		setSize(new Dimension(500, Math.min(table.size() * 50, 700)));
		// Affecte toutes les autres valeurs de la fenêtre
		setTitle("Table de routage de " + machine);
		setBackground(ControlFrame.background);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
